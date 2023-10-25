package com.example.basicprojrct;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.tts.TextToSpeech;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button login, Reg;
    DBHelper dbHelper;
    TextToSpeech t1;
   // String permissions[];
    float x11,x21,y11,y21;
    private static final int per=0;

    @Override
    public void onBackPressed() {
        MainActivity.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //permisiion code
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
//        {
//            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)){
//
//            }
//            else{
//                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},per);
//            }
//        }






        dbHelper = new DBHelper(this);
        login =  findViewById(R.id.btnLogin);
        Reg = findViewById(R.id.btnSignUp);
        usermainspeak();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, Login.class);
                startActivity(intent2);
            }
        });

        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });



    }


    public void onRequestPermisssionResult(int requestCode,String permissions[],int[] grantResults)
    {
        switch (requestCode){
            case per:
            {
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this,"PERMISSION GRANTED ",Toast.LENGTH_SHORT).show();

                }
            }


        }


    }
    public void usermainspeak(){
        String wl = "Swipe Right To Login. Swipe Left To Register.";
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
                    login.callOnClick();
                    return true;
                }
                else if(x11 > x21){
                    Reg.callOnClick();
                    return true;
                }
        }
        return false;
    }

}