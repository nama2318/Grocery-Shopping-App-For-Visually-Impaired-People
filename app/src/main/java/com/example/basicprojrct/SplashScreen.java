package com.example.basicprojrct;

import android.content.Intent;
import android.os.Handler;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {
    ImageView imageView;
    TextView textview1,textview2;
    Animation top,bottom;

    LottieAnimationView l1;
    ImageView i1;
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        l1=findViewById(R.id.amn);
        i1=findViewById(R.id.img);
        ll=findViewById(R.id.b11);

        i1.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        l1.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        ll.animate().translationY(-1600).setDuration(1000).setStartDelay(5000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,start.class);
                startActivity(intent);
                finish();
            }
        },5800);

    }
}