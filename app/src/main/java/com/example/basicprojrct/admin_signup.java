package com.example.basicprojrct;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class admin_signup extends AppCompatActivity {
    EditText name , number , email,pass;
    TextView login;
    DBHelper1 dbHelper1;
    Button signUpAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);
        name=findViewById(R.id.a_textName);
        number=findViewById(R.id.a_textNumber);
        email=findViewById(R.id.a_textEmail);
        pass=findViewById(R.id.a_textPass);
        signUpAcc = findViewById(R.id.a_btnSignUpAcc);
        dbHelper1 = new DBHelper1(this);
        login=findViewById(R.id.a_loginAcc);
        signUpAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1 = name.getText().toString();
                String number1 = number.getText().toString();
                String email1 = email.getText().toString();
                String pass1 = pass.getText().toString();
                if(name1.equals("") || number1.equals("") || email1.equals("") || pass1.equals("")){
                    Toast.makeText(admin_signup.this,"Please Enter Your Data.",Toast.LENGTH_SHORT).show();
                }else {
                    boolean b1 = dbHelper1.insetAdminData(name1, number1, email1, pass1);
                    if (b1) {
                        Toast.makeText(admin_signup.this, "Data inserted", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(admin_signup.this, admin_login.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(admin_signup.this, "Failed To insert Data", Toast.LENGTH_SHORT).show();
                    }
                    dbHelper1.close();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(admin_signup.this,admin_login.class);
                startActivity(i);
            }
        });
    }
}