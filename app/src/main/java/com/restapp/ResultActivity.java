package com.restapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class ResultActivity extends AppCompatActivity {
    RequestQueue mQueue;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // handle button activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mybutton) {
            startActivity(new Intent(ResultActivity.this,LoginActivity.class));
            Toasty.success(ResultActivity.this, "Logged out Successfully!", Toast.LENGTH_SHORT, true).show();
            ResultActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setTitle("User Details"+" ("+((GlobalClass) ResultActivity.this.getApplication()).getEmail()+")");

        Bundle bundle = getIntent().getExtras();
        final String idval = bundle.getString("id");

        mQueue = Volley.newRequestQueue(this);

        final String personalurl = "http://139.59.65.145:9090/user/personaldetail/" + bundle.getString("id");
        final String educationurl = "http://139.59.65.145:9090/user/educationdetail/" + bundle.getString("id");
        final String professionalurl = "http://139.59.65.145:9090/user/professionaldetail/" + bundle.getString("id");

        final TextView tv = (TextView) findViewById(R.id.textView11);
        final TextView tv2 = (TextView) findViewById(R.id.textView12);
        final TextView tv3 = (TextView) findViewById(R.id.textView13);

        //personal details
        StringRequest request = new StringRequest(Request.Method.GET, personalurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("data");
                            JSONObject jsonObject1 = new JSONObject(data);
                            String skills = jsonObject1.getString("skills");
                            String image = jsonObject1.getString("image");
                            String uid = jsonObject1.getString("uid");
                            String phone = jsonObject1.getString("mobile_no");
                            String name = jsonObject1.getString("name");
                            String links = jsonObject1.getString("links");
                            String location = jsonObject1.getString("location");
                            String id = jsonObject1.getString("id");
                            tv.setText("\n      Phone number : " + phone + "\n\n      Skills : " + skills + "\n\n      Location : " + location + "\n\n      Links : " + links);
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
                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        mQueue.add(request);

        //education details
        StringRequest request2 = new StringRequest(Request.Method.GET, educationurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("data");
                            JSONObject jsonObject1 = new JSONObject(data);
                            String uid = jsonObject1.getString("uid");
                            String image = jsonObject1.getString("image_path");
                            String start = jsonObject1.getString("start_year");
                            String degree = jsonObject1.getString("degree");
                            String organisation = jsonObject1.getString("organisation");
                            String location = jsonObject1.getString("location");
                            String id = jsonObject1.getString("id");
                            String end = jsonObject1.getString("end_year");
                            ((GlobalClass) ResultActivity.this.getApplication()).setSomeVariable(id);
                            Thread.sleep(200);
                            tv2.setText("\n      University/College : " + organisation + "\n\n      Degree : " + degree + "\n\n      Location : " + location + "\n\n      Start Year : " + start + "\n\n      End Year : " + end);
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
                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        mQueue.add(request2);

        //professional details
        StringRequest request3 = new StringRequest(Request.Method.GET, professionalurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("data");
                            JSONObject jsonObject1 = new JSONObject(data);
                            String end = jsonObject1.getString("end_date");
                            String uid = jsonObject1.getString("uid");
                            String organisation = jsonObject1.getString("organisation");
                            String designation = jsonObject1.getString("designation");
                            String id = jsonObject1.getString("id");
                            String start = jsonObject1.getString("start_date");

                            ((GlobalClass) ResultActivity.this.getApplication()).setSomeVariable2(id);
                            Thread.sleep(200);
                            tv3.setText("\n      Organisation : " + organisation + "\n\n      Designation : " + designation + "\n\n      Start Date : " + start + "\n\n      End Date : " + end);

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
                return new JSONObject(params).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        mQueue.add(request3);

        //personal update
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", idval);
                Intent intent = new Intent(ResultActivity.this, PersonalupActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                ResultActivity.this.finish();
            }
        });

        //educational update
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", idval);
                Intent intent = new Intent(ResultActivity.this, EducationupActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                ResultActivity.this.finish();
            }
        });
        //education delete
        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv2.setText("");
                StringRequest request = new StringRequest(Request.Method.DELETE, "http://139.59.65.145:9090/user/educationdetail/"+((GlobalClass) ResultActivity.this.getApplication()).getSomeVariable(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Toasty.success(ResultActivity.this, "Details deleted successfully!", Toast.LENGTH_SHORT,true).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toasty.info(ResultActivity.this, "No details found!", Toast.LENGTH_SHORT,true).show();
                                error.printStackTrace();
                            }
                        }
                ) {
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<String, String>();
                        return new JSONObject(params).toString().getBytes();
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }
                };
                mQueue.add(request);
            }
        });

        //professional update
        findViewById(R.id.button10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", idval);
                Intent intent = new Intent(ResultActivity.this, ProfessionalupActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                ResultActivity.this.finish();
            }
        });
        //professional delete
        findViewById(R.id.button11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv3.setText("");
                StringRequest request = new StringRequest(Request.Method.DELETE, "http://139.59.65.145:9090/user/professionaldetail/"+((GlobalClass) ResultActivity.this.getApplication()).getSomeVariable2(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Toasty.success(ResultActivity.this, "Details deleted successfully!", Toast.LENGTH_SHORT,true).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toasty.info(ResultActivity.this, "No details found!", Toast.LENGTH_SHORT,true).show();
                                error.printStackTrace();
                            }
                        }
                ){
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<String, String>();
                        return new JSONObject(params).toString().getBytes();
                    }
                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }
                };
                mQueue.add(request);
            }
        });

        //showing personal image
        ImageView perimage = (ImageView) findViewById(R.id.imageView2);
        String URL1 = "http://139.59.65.145:9090/user/personaldetail/profilepic/" + bundle.getString("id");
        Picasso.get().load(URL1).into(perimage);

        //showing certificate image
        ImageView certificate = (ImageView) findViewById(R.id.imageView3);
        String URL2 = "http://139.59.65.145:9090/user/educationdetail/certificate/" + bundle.getString("id");
        Picasso.get().load(URL2).into(certificate);
    }
}
