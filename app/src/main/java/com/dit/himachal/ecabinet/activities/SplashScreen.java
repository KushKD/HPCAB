package com.dit.himachal.ecabinet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dit.himachal.ecabinet.R;

import java.util.prefs.Preferences;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

               // if(Preferences.getInstance().isLoggedIn){
                    Intent mainIntent = new Intent(SplashScreen.this, Login.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
//                }else{
//                    Intent mainIntent = new Intent(SplashScreen.this, Registration.class);
//                    SplashScreen.this.startActivity(mainIntent);
//                    SplashScreen.this.finish();
//                }



            }
        }, 3000);
    }
}