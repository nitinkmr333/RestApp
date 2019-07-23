package com.restapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class ProfessionalupActivity extends AppCompatActivity {
    RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professionalup);
        setTitle("Update Professional Details");

        Bundle bundle = getIntent().getExtras();
        final String idval = bundle.getString("id");

        final String professionalurl = "http://139.59.65.145:9090/user/professionaldetail/" + bundle.getString("id");

        mQueue = Volley.newRequestQueue(this);

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.tilproup1);
        final TextInputLayout til2 = (TextInputLayout) findViewById(R.id.tilproup2);
        final TextInputLayout til3 = (TextInputLayout) findViewById(R.id.tilproup3);
        final TextInputLayout til4 = (TextInputLayout) findViewById(R.id.tilproup4);
        final TextInputEditText tiet1 = findViewById(R.id.tietproup1);
        final TextInputEditText tiet2 = findViewById(R.id.tietproup2);
        final TextInputEditText tiet3 = findViewById(R.id.tietproup3);
        final TextInputEditText tiet4 = findViewById(R.id.tietproup4);
        tiet3.setFocusable(false);
        tiet4.setFocusable(false);

        tiet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dp = new DatePickerDialog(ProfessionalupActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String erg = "";
                                erg = String.valueOf(dayOfMonth);
                                erg += "/" + String.valueOf(monthOfYear + 1);
                                erg += "/" + year;
                                ((TextInputEditText) tiet3).setText(erg);
                            }
                        }, y, m,d);
                dp.show();
            }
        });

        tiet4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dp = new DatePickerDialog(ProfessionalupActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String erg = "";
                                erg = String.valueOf(dayOfMonth);
                                erg += "/" + String.valueOf(monthOfYear + 1);
                                erg += "/" + year;
                                ((TextInputEditText) tiet4).setText(erg);
                            }

                        }, y, m,d);
                dp.show();
            }
        });

        Button btn14 = (Button) findViewById(R.id.button14);
        btn14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tiet1.getText().length() == 0) {
                    til1.setError("Please enter organization");
                }
                if (tiet2.getText().length() == 0) {
                    til2.setError("Please enter your desgination");
                }
                if (tiet3.getText().length() == 0) {
                    til3.setError("Enter start date");
                }
                if (tiet4.getText().length() == 0) {
                    til4.setError("Enter end date");
                }
                if (tiet1.getText().length() != 0 && tiet2.getText().length() != 0 && tiet3.getText().length() != 0 && tiet4.getText().length() != 0) {
                    til1.setErrorEnabled(false);
                    til2.setErrorEnabled(false);
                    til3.setErrorEnabled(false);
                    til4.setErrorEnabled(false);

                    StringRequest request = new StringRequest(Request.Method.PUT, professionalurl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Log.e("education:", response);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("id", idval);
                                        Intent intent = new Intent(ProfessionalupActivity.this, ResultActivity.class);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        ProfessionalupActivity.this.finish();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    StringRequest request = new StringRequest(Request.Method.POST, professionalurl,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        Log.e("education:", response);
                                                        Toasty.success(ProfessionalupActivity.this, "Details updated!", Toast.LENGTH_SHORT,true).show();
                                                        Bundle bundle = new Bundle();
                                                        bundle.putString("id", idval);
                                                        Intent intent = new Intent(ProfessionalupActivity.this, ResultActivity.class);
                                                        intent.putExtras(bundle);
                                                        startActivity(intent);
                                                        ProfessionalupActivity.this.finish();
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
                                            String designation = tiet2.getText().toString();
                                            String start = tiet3.getText().toString();
                                            String end = tiet4.getText().toString();

                                            params.put("end_date", end);
                                            params.put("organisation", organisation);
                                            params.put("designation", designation);
                                            params.put("start_date", start);

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
                            String designation = tiet2.getText().toString();
                            String start = tiet3.getText().toString();
                            String end = tiet4.getText().toString();

                            params.put("end_date", end);
                            params.put("organisation", organisation);
                            params.put("designation", designation);
                            params.put("start_date", start);

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
