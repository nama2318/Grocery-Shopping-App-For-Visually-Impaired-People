package com.example.basicprojrct;

import android.content.Intent;
import android.database.Cursor;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;


public class admin_login extends AppCompatActivity {
    Button login;
    EditText email , password;
    TextView Reg;
    DBHelper1 dbHelper1;
    TextToSpeech t1;
    float x11,x21,y11,y21;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    public static String adminid;
    @Override
    public void onBackPressed() {
        admin_login.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        dbHelper1 = new DBHelper1(this);
        login = (Button) findViewById(R.id.a_btnSubmit_login);
        Reg = findViewById(R.id.a_createAcc);

        email=findViewById(R.id.a_text_email);
        password=findViewById(R.id.a_text_pass);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailCheck = email.getText().toString();
                String passCheck = password.getText().toString();
                Cursor cursor = dbHelper1.getData();
                if(cursor.getCount() == 0){
                    Toast.makeText(admin_login.this,"No entries Exists",Toast.LENGTH_LONG).show();
                }
                if (loginCheck(cursor,emailCheck,passCheck)) {
                    Intent intent = new Intent(admin_login.this,FinalPage.class);
                  intent.putExtra("email",emailCheck);
                    adminid = emailCheck;
                    Toast.makeText(admin_login.this,"Hello",Toast.LENGTH_SHORT).show();
                    email.setText("");
                    password.setText("");
                    startActivity(intent);
                    finish();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(admin_login.this);
                    builder.setCancelable(true);
                    builder.setTitle("Wrong Credential");
                    builder.setMessage("Wrong Credential");
                    builder.show();
                }
                dbHelper1.close();
            }
        });
        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_login.this, FinalPage.class);
                startActivity(intent);
            }
        });
        loginGuide();
    }

    public static boolean loginCheck(Cursor cursor,String emailCheck,String passCheck) {
        while (cursor.moveToNext()){
            if (cursor.getString(0).equals(emailCheck)) {
                if (cursor.getString(2).equals(passCheck)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public void loginGuide(){
        String user = "Swipe Right To Enter your Admin ID.";
        String pass = "Swipe Right To Enter your Password.";
        if(email.getText().toString().equals("") && password.getText().toString().equals("")) {
            t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    t1.setLanguage(Locale.UK);
                    t1.setSpeechRate((float) 0.8);
                    t1.speak(user, TextToSpeech.QUEUE_FLUSH, null);
                }
            });
        }
        else if(!email.getText().toString().equals("") && password.getText().toString().equals("")){
            t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    t1.setLanguage(Locale.UK);
                    t1.setSpeechRate((float) 0.8);
                    t1.speak(pass, TextToSpeech.QUEUE_FLUSH, null);
                }
            });
        }
        else if(!email.getText().toString().equals("") && !password.getText().toString().equals("")){
            t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    t1.setLanguage(Locale.UK);
                    t1.setSpeechRate((float) 0.8);
                    t1.speak("Say Login to get Logged In", TextToSpeech.QUEUE_FLUSH, null);
                    listen();
                }
            });
        }
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
                    loginGuide();
                    listen();
                    return true;
                }
                else if(x11 > x21){
                    loginGuide();
                    listen();
                    return true;
                }
        }
        return false;
    }

    public void listen(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");
        if(!email.getText().toString().equals("") && !password.getText().toString().equals("")) {
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 10000);
        }
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e) {
            Toast.makeText(admin_login.this, " " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String res = result.get(0);
                if(!res.toLowerCase().contains("login") && email.getText().toString().equals("") && password.getText().toString().equals("")){
                    res = res.toLowerCase().replace(" ","");
                    email.setText(res);
                    loginGuide();
                }
                else if(!res.toLowerCase().contains("login") && !email.getText().toString().equals("") && password.getText().toString().equals("")){
                    res = res.toLowerCase().replace(" ","");
                    password.setText(res);
                    loginGuide();
                }
                else if(res.toLowerCase().contains("login") && !email.getText().toString().equals("") && !password.getText().toString().equals("")){
                    login.callOnClick();
                }
                else{
                    Toast.makeText(this,"Something Went Wrong. Try Again",Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public void onPause(){
        if(t1 != null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}