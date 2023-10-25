package com.example.basicprojrct;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.tts.TextToSpeech;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Locale;

public class start extends AppCompatActivity {
    Button user, admin;
   // DBHelper dbHelper;

    TextToSpeech t1;
    float x11,x21,y11,y21;
    private static final int per=0;

    @Override
    public void onBackPressed() {
        start.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //dbHelper = new DBHelper(this);
        //permission Code
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)){

            }
            else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},per);
            }
        }
        user = (Button) findViewById(R.id.user);
        admin = findViewById(R.id.admin);
        welcomeSpeak();
        user.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(start.this, MainActivity.class);
                startActivity(intent2);
            }
        });

        admin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent11 = new Intent(start.this,firstpage.class);
                startActivity(intent11);
            }
        });

    }

    public void onResume(View view){
        super.onResume();
        resumespeak();
    }

    public void welcomeSpeak(){
        String wl = "Welcome Sir. Swipe Right To User Mode. Swipe Left To Admin Mode.";
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                t1.setLanguage(Locale.UK);
                t1.setSpeechRate((float) 0.8);
                t1.speak(wl,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
    }
    public void onPause(){
        if(t1 != null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x11 = touchEvent.getX();
                y11 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x21 = touchEvent.getX();
                y21 = touchEvent.getY();
                if(x11 < x21){
                    user.callOnClick();
                    return true;
                }
                else if(x11 > x21){
                    admin.callOnClick();
                    return true;
                }
        }
        return false;
    }
    public void resumespeak(){
        String wl = "Hello Sir. Swipe Right To User Mode. Swipe Left To Admin Mode.";
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                t1.setLanguage(Locale.UK);
                t1.setSpeechRate((float) 0.8);
                t1.speak(wl,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
    }
}