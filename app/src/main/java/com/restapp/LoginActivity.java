package com.restapp;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        final ProgressBar p = (ProgressBar) findViewById(R.id.progressBar);
        p.setVisibility(View.GONE);

        mQueue = Volley.newRequestQueue(this);
        Button btn = (Button) findViewById(R.id.button5);

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.tillog1);
        final TextInputLayout til2 = (TextInputLayout) findViewById(R.id.tillog2);
        final TextInputEditText tiet1 = findViewById(R.id.tietlog1);
        final TextInputEditText tiet2 = findViewById(R.id.tietlog2);

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
                    Toasty.warning(LoginActivity.this, "Please enter Email and Password", Toast.LENGTH_SHORT, true).show();
                }
                if (tiet1.getText().length() != 0 && tiet2.getText().length() != 0) {
                    til1.setErrorEnabled(false);
                    til2.setErrorEnabled(false);

                    //progressbar
                    if (p.getVisibility() != View.VISIBLE) { // check if it is visible
                        p.setVisibility(View.VISIBLE); // if not set it to visible
                        v.setVisibility(View.VISIBLE); // use 1 or 2 as parameters.. arg0 is the view(your button) from the onclick listener
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            p.setVisibility(View.GONE);
                        }
                    }, 2500);

                    String url = "http://139.59.65.145:9090/user/login";

                    StringRequest request = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String data = jsonObject.getString("data");
                                        JSONObject jsonObject2 = new JSONObject(data);
                                        String data2 = jsonObject2.getString("id");

                                        ((GlobalClass) LoginActivity.this.getApplication()).setEmail(tiet1.getText().toString());
                                        Bundle bundle = new Bundle();
                                        bundle.putString("id", data2);
                                        Intent intent = new Intent(LoginActivity.this, ResultActivity.class);
                                        Toasty.success(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT, true).show();
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    } catch (JSONException e) {
                                        p.setVisibility(View.GONE);
                                        Toasty.error(LoginActivity.this, "Invalid email or password!", Toast.LENGTH_SHORT, true).show();
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
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logintosignup = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(logintosignup);
                LoginActivity.this.finish();
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
