package com.restapp;

import android.content.Intent;
import android.os.Handler;
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

public class SignupActivity extends AppCompatActivity {
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Sign Up");

        mQueue = Volley.newRequestQueue(this);
        Button btn = (Button) findViewById(R.id.button9);

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.tilsign1);
        final TextInputLayout til2 = (TextInputLayout) findViewById(R.id.tilsign2);
        final TextInputEditText tiet1 = findViewById(R.id.tietsign1);
        final TextInputEditText tiet2 = findViewById(R.id.tietsign2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tiet1.getText().length() == 0) {
                    til1.setError("Please enter email");
                }
                if (tiet2.getText().length() == 0) {
                    til2.setError("Please enter password");
                }
                if (tiet1.getText().length() == 0 || tiet2.getText().length() == 0) {
                    Toasty.warning(SignupActivity.this, "Please enter Email and Password", Toast.LENGTH_SHORT, true).show();
                }
                if (tiet1.getText().length() != 0 && tiet2.getText().length() != 0) {
                    til1.setErrorEnabled(false);
                    til2.setErrorEnabled(false);

                    String url = "http://139.59.65.145:9090/user/signup";
                    StringRequest request = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Log.e("signup:", response);
                                        JSONObject jsonObject = new JSONObject(response);
                                        String data = jsonObject.getString("data");
                                        JSONObject jsonObject2 = new JSONObject(data);
                                        String data2 = jsonObject2.getString("id");
                                        JSONObject jsonObject3 = new JSONObject(data);
                                        String data3 = jsonObject3.getString("email");

                                        Bundle bundle = new Bundle();
                                        bundle.putString("id", data2);
                                        bundle.putString("email", data3);
                                        ((GlobalClass) SignupActivity.this.getApplication()).setEmail(bundle.getString("email"));

                                        Intent intent = new Intent(SignupActivity.this, PersonalActivity.class);
                                        intent.putExtras(bundle);
                                        startActivity(intent);

                                    } catch (Exception e) {
                                        Toasty.error(SignupActivity.this, "Error creating profile!", Toast.LENGTH_SHORT, true).show();
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
                            String email = tiet1.getText().toString();
                            String password = tiet2.getText().toString();
                            params.put("email", email);
                            params.put("password", password);
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

        findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signuptologin = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(signuptologin);
                SignupActivity.this.finish();
            }
        });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toasty.info(this, "Press BACK again to exit", Toast.LENGTH_SHORT, false).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
