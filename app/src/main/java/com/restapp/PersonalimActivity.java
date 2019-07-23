package com.restapp;

import android.Manifest;
import android.app.Activity;
import android.app.Person;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class PersonalimActivity extends AppCompatActivity {

    RequestQueue mQueue;
    ImageButton ibutton;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalim);

        ((GlobalClass) PersonalimActivity.this.getApplication()).setPhotoflag(false);

        setTitle("Upload Photograph");
        Bundle bundle = getIntent().getExtras();
        final String idval = bundle.getString("id");
        final String emailval = bundle.getString("email");

        btn = (Button) findViewById(R.id.button6);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((GlobalClass) PersonalimActivity.this.getApplication()).getphotoflag() == true) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", idval);
                    Intent intent = new Intent(PersonalimActivity.this, EducationActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    PersonalimActivity.this.finish();
                } else {
                    Toasty.error(PersonalimActivity.this, "Please upload your photograph!", Toast.LENGTH_SHORT, false).show();
                }
            }
        });

        ibutton = (ImageButton) findViewById(R.id.imageButton2);
        ibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(PersonalimActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        });
    }

    private void showFileChooser() {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        pickImageIntent.setType("image/*");
//        pickImageIntent.putExtra("aspectX", 1);
//        pickImageIntent.putExtra("aspectY", 1);
//        pickImageIntent.putExtra("scale", true);
//        pickImageIntent.putExtra("outputFormat",
//                Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(pickImageIntent, GET_FROM_GALLERY);
    }

    private static final int GET_FROM_GALLERY = 1;
    String encodedImage;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            //Toasty.info(PersonalActivity.this, "Data found", Toast.LENGTH_SHORT, false);
            Uri selectedImage = data.getData();
            ibutton = (ImageButton) findViewById(R.id.imageButton2);
            ibutton.setImageURI(selectedImage);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream boas = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, boas);
                byte[] byteArrayImage = boas.toByteArray();
                encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
                SendImage(encodedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void SendImage(final String encodedImage) {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://139.59.65.145:9090/user/personaldetail/pp/post",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("upload", response);
                        Toasty.success(PersonalimActivity.this, "Image Uploaded Successfully!", Toasty.LENGTH_SHORT, false);
                        ((GlobalClass) PersonalimActivity.this.getApplication()).setPhotoflag(true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toasty.error(PersonalimActivity.this, "Error uploading image!", Toast.LENGTH_SHORT, false).show();
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                Bundle bundle = getIntent().getExtras();
                String idval = bundle.getString("id");

                Map<String, String> params = new HashMap<String, String>();
                params.put("photo", encodedImage);
                params.put("uid", idval);
                return new JSONObject(params).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/json; charset=UTF-8";
            }
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        mQueue = Volley.newRequestQueue(this);
        mQueue.add(stringRequest);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showFileChooser();
                } else {
                    Toasty.error(PersonalimActivity.this, "Permission denied!", Toast.LENGTH_SHORT, false).show();
                }
                return;
            }
        }
    }

}
