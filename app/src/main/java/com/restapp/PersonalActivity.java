package com.restapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

public class PersonalActivity extends AppCompatActivity {
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        setTitle("Personal Details");

        Bundle bundle = getIntent().getExtras();
        final String idval = bundle.getString("id");
        final String emailval = bundle.getString("email");

        final String personalurl = "http://139.59.65.145:9090/user/personaldetail/" + bundle.getString("id");

        mQueue = Volley.newRequestQueue(this);

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.tilper1);
        final TextInputLayout til2 = (TextInputLayout) findViewById(R.id.tilper2);
        final TextInputLayout til3 = (TextInputLayout) findViewById(R.id.tilper3);
        final TextInputLayout til4 = (TextInputLayout) findViewById(R.id.tilper4);
        final TextInputLayout til5 = (TextInputLayout) findViewById(R.id.tilper5);
        final TextInputEditText tiet1 = findViewById(R.id.tietper1);
        final TextInputEditText tiet2 = findViewById(R.id.tietper2);
        final TextInputEditText tiet3 = findViewById(R.id.tietper3);
        final TextInputEditText tiet4 = findViewById(R.id.tietper4);
        final TextInputEditText tiet5 = findViewById(R.id.tietper5);

        Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tiet1.getText().length() == 0) {
                    til1.setError("Please enter name");
                }
                if (tiet2.getText().length() == 0) {
                    til2.setError("Please enter location");
                }
                if (tiet3.getText().length() == 0) {
                    til3.setError("Please enter phone number");
                }
                if (tiet4.getText().length() == 0) {
                    til4.setError("Please enter skills");
                }
                if (tiet5.getText().length() == 0) {
                    til5.setError("Please enter links");
                }

                if (tiet1.getText().length() != 0 && tiet2.getText().length() != 0 && tiet3.getText().length() != 0 && tiet4.getText().length() != 0 && tiet5.getText().length() != 0) {
                    til1.setErrorEnabled(false);
                    til2.setErrorEnabled(false);
                    til3.setErrorEnabled(false);
                    til4.setErrorEnabled(false);
                    til5.setErrorEnabled(false);

                    StringRequest request = new StringRequest(Request.Method.POST, personalurl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Log.e("personal:", response);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("id", idval);
                                        Intent intent = new Intent(PersonalActivity.this, PersonalimActivity.class);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        PersonalActivity.this.finish();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                }
                            }
                    ) {
                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<String, String>();
                            String name = tiet1.getText().toString();
                            String location = tiet2.getText().toString();
                            String phone = tiet3.getText().toString();
                            String skills = tiet4.getText().toString();
                            String links = tiet5.getText().toString();

                            params.put("skills", skills);
                            params.put("mobile_no", phone);
                            params.put("name", name);
                            params.put("links", links);
                            params.put("location", location);
                            params.put("email", emailval);

                            return new JSONObject(params).toString().getBytes();
                        }
                        @Override
                        public String getBodyContentType() {
                            return "application/json";
                        }
                    };
                    mQueue.add(request);
                }
            }
        });
    }
}






