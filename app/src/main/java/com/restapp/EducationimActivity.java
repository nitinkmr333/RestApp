package com.restapp;

import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class EducationimActivity extends AppCompatActivity {

    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;
    private ImageView imageView;
    private Button btn;
    private static String url="http://139.59.65.145:9090/user/educationdetail/certificate";
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educationim);
        ((GlobalClass) EducationimActivity.this.getApplication()).setPhotoflag2(false);
        setTitle("Upload Certificate1");

        Bundle bundle = getIntent().getExtras();
        final String idval = bundle.getString("id");

        imageView = (ImageView)findViewById(R.id.imageButton2);
        queue = Volley.newRequestQueue(this);


        btn = (Button) findViewById(R.id.button30);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((GlobalClass) EducationimActivity.this.getApplication()).getphotoflag2() == true) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", idval);
                    Intent intent = new Intent(EducationimActivity.this, ProfessionalActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    EducationimActivity.this.finish();
                } else {
                    Toasty.error(EducationimActivity.this, "Please upload certificate!", Toast.LENGTH_SHORT, false).show();
                }
            }
        });

        imageView = (ImageView) findViewById(R.id.imageButton); //imageButton is an ImageView
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(EducationimActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);


            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage();
                } else {
                    Toasty.error(EducationimActivity.this, "Permission denied!", Toast.LENGTH_SHORT, false).show();
                }
                return;
            }
        }
    }
    public void selectImage(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                imageView.setImageBitmap(bitmap);
                uploadImage(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private byte[] getFileDataFromDrawable (Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void uploadImage(final Bitmap bitmap){
        Bundle bundle = getIntent().getExtras();
        final String idval = bundle.getString("id");
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(url, null, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    JSONObject obj = new JSONObject(new String(response.data));
                    if (obj.toString().contains("Successfully")) {
                        Toasty.success(EducationimActivity.this, "Certificated Uploaded Successfully!", Toast.LENGTH_SHORT, false).show();
                        ((GlobalClass) EducationimActivity.this.getApplication()).setPhotoflag2(true);
                    } else {
                        Toasty.error(EducationimActivity.this, "Certificated Upload failed!", Toast.LENGTH_SHORT, false).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(EducationimActivity.this, "Certificated Upload failed!", Toast.LENGTH_SHORT, false).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<>();
                params.put("uid",String.valueOf(idval));
                return params;
            }

            @Override
            protected Map<String,DataPart> getByteData() throws AuthFailureError{
                Map<String,DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("photo", new DataPart(imagename+".png",getFileDataFromDrawable(bitmap)));
                return params;
            }
        };
        queue.add(volleyMultipartRequest);
    }


}
