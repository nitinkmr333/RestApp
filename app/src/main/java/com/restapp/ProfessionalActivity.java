package com.restapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;

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

public class ProfessionalActivity extends AppCompatActivity {
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional);
        setTitle("Professional Details");
        final ProgressBar p = (ProgressBar) findViewById(R.id.progressBar2);
        p.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();
        final String idval = bundle.getString("id");

        final String professionalurl = "http://139.59.65.145:9090/user/professionaldetail/" + bundle.getString("id");

        mQueue = Volley.newRequestQueue(this);

        final TextInputLayout til1 = (TextInputLayout) findViewById(R.id.tilpro1);
        final TextInputLayout til2 = (TextInputLayout) findViewById(R.id.tilpro2);
        final TextInputLayout til3 = (TextInputLayout) findViewById(R.id.tilpro3);
        final TextInputLayout til4 = (TextInputLayout) findViewById(R.id.tilpro4);
        final TextInputEditText tiet1 = findViewById(R.id.tietpro1);
        final TextInputEditText tiet2 = findViewById(R.id.tietpro2);
        final TextInputEditText tiet3 = findViewById(R.id.tietpro3);
        final TextInputEditText tiet4 = findViewById(R.id.tietpro4);
        tiet3.setFocusable(false);
        tiet4.setFocusable(false);

        tiet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dp = new DatePickerDialog(ProfessionalActivity.this,
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

                DatePickerDialog dp = new DatePickerDialog(ProfessionalActivity.this,
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

        Button btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
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

                    StringRequest request = new StringRequest(Request.Method.POST, professionalurl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        Log.e("education:", response);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("id", idval);
                                        Intent intent = new Intent(ProfessionalActivity.this, ResultActivity.class);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        ProfessionalActivity.this.finish();

                                    } catch (Exception e) {
                                        p.setVisibility(View.GONE);
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    p.setVisibility(View.GONE);
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
