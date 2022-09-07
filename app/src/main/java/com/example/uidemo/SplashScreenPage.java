package com.example.uidemo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenPage extends AppCompatActivity {

    private static final long DELAY = 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Timer splashTimer;
        splashTimer = new Timer();
        splashTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenPage.this, MainActivity.class));
                SplashScreenPage.this.finish();
            }
        }, DELAY);
    }
}