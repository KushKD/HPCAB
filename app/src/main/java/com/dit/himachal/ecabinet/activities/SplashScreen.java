package com.dit.himachal.ecabinet.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.dit.himachal.ecabinet.R;
import com.dit.himachal.ecabinet.utilities.Preferences;

public class SplashScreen extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    Animation animationUptoDown , animationDownToUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

       // PreventScreenshot.on(SplashScreen.this);

        textView = findViewById(R.id.splash_text);
        imageView = findViewById(R.id.splash_image);
        animationUptoDown = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.uptodownanim);
        animationDownToUp = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.downtotopanim);

        imageView.setAnimation(animationUptoDown);
        textView.setAnimation(animationDownToUp);
        Preferences.getInstance().loadPreferences(SplashScreen.this);
        requestPermissions();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                if(Preferences.getInstance().isLoggedIn){
                    Intent mainIntent = new Intent(SplashScreen.this, MainActivity2.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }else{
                    Intent mainIntent = new Intent(SplashScreen.this, Login.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }



            }
        }, 6000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // PreventScreenshot.on(SplashScreen.this);

    }




    @Override
    protected void onStop() {
        // PreventScreenshot.on(SplashScreen.this);
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        //  PreventScreenshot.on(SplashScreen.this);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onPause() {
        // PreventScreenshot.on(SplashScreen.this);
        super.onPause();

    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET,
                       // Manifest.permission.CALL_PHONE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.CHANGE_NETWORK_STATE,
                        //Manifest.permission.ACCESS_FINE_LOCATION,
                        //Manifest.permission.ACCESS_COARSE_LOCATION,
                        //Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                        //Manifest.permission.CAMERA


                }, 0);
            }
        }


    }


}