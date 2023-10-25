package com.example.basicprojrct;

import android.content.Intent;
import android.speech.tts.TextToSpeech;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class firstpage extends AppCompatActivity {
    Button login1, Reg1;
    //DBHelper1 dbHelper1;
    TextToSpeech t1;
    float x11,x21,y11,y21;

    @Override
    public void onBackPressed() {
        firstpage.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
        //dbHelper1 = new DBHelper1(this);
        login1 = (Button) findViewById(R.id.aa_btnLogin);
        Reg1 = findViewById(R.id.aa_btnSignUp);

        adminmainspeak();

        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(firstpage.this, admin_login.class);
                startActivity(intent1);
            }
        });
        Reg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(firstpage.this, admin_signup.class);
                startActivity(intent);
            }
        });
    }

    public void adminmainspeak(){
        String wl = "Swipe Right To Login As Admin. Swipe Left To Register as a Admin.";
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
                    login1.callOnClick();
                    return true;
                }
                else if(x11 > x21){
                    Reg1.callOnClick();
                    return true;
                }
        }
        return false;
    }

}