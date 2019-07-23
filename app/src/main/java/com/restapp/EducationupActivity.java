package com.restapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class EducationupActivity extends AppCompatActivity {
    RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educationup);
        setTitle("Update Education Details");

        Bundle bundle = getIntent().getExtras();
        final String idval = bundle.getString("id");

        final String educationurl = "http://139.59.65.145:9090/user/educationdetail/" + bundle.getString("id");

        mQueue = Volley.newRequestQueue(this);

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.tileduup1);
        final TextInputLayout til2 = (TextInputLayout) findViewById(R.id.tileduup2);
        final TextInputLayout til3 = (TextInputLayout) findViewById(R.id.tileduup3);
        final TextInputLayout til4 = (TextInputLayout) findViewById(R.id.tileduup4);
        final TextInputLayout til5 = (TextInputLayout) findViewById(R.id.tileduup5);
        final TextInputEditText tiet1 = findViewById(R.id.tieteduup1);
        final TextInputEditText tiet2 = findViewById(R.id.tieteduup2);
        final TextInputEditText tiet3 = findViewById(R.id.tieteduup3);
        final TextInputEditText tiet4 = findViewById(R.id.tieteduup4);
        final TextInputEditText tiet5 = findViewById(R.id.tieteduup5);

        Button btn15 = (Button) findViewById(R.id.button15);
        btn15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tiet1.getText().length() == 0) {
                    til1.setError("Please your University/College name");
                }
                if (tiet2.getText().length() == 0) {
                    til2.setError("Please enter your degree");
                }
                if (tiet3.getText().length() == 0) {
                    til3.setError("Please enter location of University/College");
                }
                if (tiet4.getText().length() == 0) {
                    til4.setError("Enter start year");
                }
                if (tiet5.getText().length() == 0) {
                    til5.setError("Enter end year");
                }

                if (tiet1.getText().length() != 0 && tiet2.getText().length() != 0 && tiet3.getText().length() != 0 && tiet4.getText().length() != 0 && tiet5.getText().length() != 0) {
                    til1.setErrorEnabled(false);
                    til2.setErrorEnabled(false);
                    til3.setErrorEnabled(false);
                    til4.setErrorEnabled(false);
                    til5.setErrorEnabled(false);


                    StringRequest request = new StringRequest(Request.Method.PUT, educationurl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        Log.e("education:",response);
                                        Toasty.success(EducationupActivity.this, "Details updated!", Toast.LENGTH_SHORT,true).show();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("id", idval);
                                        Intent intent = new Intent(EducationupActivity.this, ResultActivity.class);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        EducationupActivity.this.finish();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    StringRequest request = new StringRequest(Request.Method.POST, educationurl,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        Log.e("education:",response);
                                                        Bundle bundle = new Bundle();
                                                        bundle.putString("id", idval);
                                                        Intent intent = new Intent(EducationupActivity.this, ResultActivity.class);
                                                        intent.putExtras(bundle);
                                                        startActivity(intent);
                                                        EducationupActivity.this.finish();
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
                                            String organisation = tiet1.getText().toString();
                                            String degree = tiet2.getText().toString();
                                            String location = tiet3.getText().toString();
                                            String start = tiet4.getText().toString();
                                            String end = tiet5.getText().toString();

                                            params.put("start_year", start);
                                            params.put("degree", degree);
                                            params.put("organisation", organisation);
                                            params.put("location", location);
                                            params.put("end_year", end);

                                            return new JSONObject(params).toString().getBytes();
                                        }
                                        @Override
                                        public String getBodyContentType() {
                                            return "application/json";
                                        }
                                    };
                                    mQueue.add(request);
                                    error.printStackTrace();
                                }
                            }
                    ) {
                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<String, String>();
                            String organisation = tiet1.getText().toString();
                            String degree = tiet2.getText().toString();
                            String location = tiet3.getText().toString();
                            String start = tiet4.getText().toString();
                            String end = tiet5.getText().toString();

                            params.put("start_year", start);
                            params.put("degree", degree);
                            params.put("organisation", organisation);
                            params.put("location", location);
                            params.put("end_year", end);

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
