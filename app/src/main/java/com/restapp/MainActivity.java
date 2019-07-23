package com.restapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent maintologin = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(maintologin);
        MainActivity.this.finish();
    }
}
