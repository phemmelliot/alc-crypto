package com.example.phemmelliot.concrypt.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.phemmelliot.concrypt.activity.LandingPage;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, LandingPage.class);
        startActivity(intent);
        finish();
    }
}
