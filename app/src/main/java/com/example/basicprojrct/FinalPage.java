package com.example.basicprojrct;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class FinalPage extends AppCompatActivity {
//    TextView r1c1,r1c2,r1c3,r1c4;
//    TextView r2c1,r2c2,r2c3,r2c4;
//    TextView r3c1,r3c2,r3c3,r3c4;
//    TextView r4c1,r4c2,r4c3,r4c4;
//    TextView r5c1,r5c2,r5c3,r5c4;
//    TextView r6c1,r6c2,r6c3,r6c4;
    DBHelper1 dbHelper1;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    TextView datedis,adminname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_page);
//        r1c1 = findViewById(R.id.r11);
//        r1c2 = findViewById(R.id.r12);
//        r1c3 = findViewById(R.id.r13);
//        r1c4 = findViewById(R.id.r14);
//
//        r2c1 = findViewById(R.id.r21);
//        r2c2 = findViewById(R.id.r22);
//        r2c3 = findViewById(R.id.r23);
//        r2c4 = findViewById(R.id.r24);
//
//        r3c1 = findViewById(R.id.r31);
//        r3c2 = findViewById(R.id.r32);
//        r3c3 = findViewById(R.id.r33);
//        r3c4 = findViewById(R.id.r34);
//
//        r4c1 = findViewById(R.id.r41);
//        r4c2 = findViewById(R.id.r42);
//        r4c3 = findViewById(R.id.r43);
//        r4c4 = findViewById(R.id.r44);
//
//        r5c1 = findViewById(R.id.r51);
//        r5c2 = findViewById(R.id.r52);
//        r5c3 = findViewById(R.id.r53);
//        r5c4 = findViewById(R.id.r54);
//
//        r6c1 = findViewById(R.id.r61);
//        r6c2 = findViewById(R.id.r62);
//        r6c3 = findViewById(R.id.r63);
//        r6c4 = findViewById(R.id.r64);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(calendar.getTime());

        datedis = findViewById(R.id.datedis);
        datedis.setText(datedis.getText() + " "+date);
        datedis.setPadding(2,1,18,1);
        datedis.setTextSize(15);
        dbHelper1 = new DBHelper1(this);

        try {
            String adminnm = dbHelper1.getadminname(admin_login.adminid);
            adminname = findViewById(R.id.adminname);
            adminname.setText(adminname.getText()+" "+adminnm);
        }catch (Exception e){
            Toast.makeText(FinalPage.this,""+e,Toast.LENGTH_SHORT).show();
        }

//        r1c1.setText(Menu.userpass);
//        r1c2.setText(Menu.numberpass);
//        r1c3.setText(Menu.totalpass);
//        r1c4.setText(date);
        addtable();
    }

    public void addtable(){
        TableLayout stk = (TableLayout) findViewById(R.id.displaytable);
        String userIdModified;
        Cursor cr = dbHelper1.getdb();
        while (cr.moveToNext()){
            TableRow tbrw = new TableRow(this);
            TextView t1 = new TextView(this);
            userIdModified = cr.getString(1).toString();
            if(cr.getString(1).toString().contains("@")){
                String splited[] = cr.getString(1).toString().split("@");
                userIdModified = splited[0]+"@\n"+splited[1];
            }
            t1.setText(""+userIdModified);
            t1.setTextColor(Color.BLACK);
           // t1.setGravity(Gravity.RIGHT);
            t1.setPadding(38,1,2,1);
            tbrw.addView(t1);

            TextView t2 = new TextView(this);
            t2.setText(""+cr.getString(3).toString());
            t2.setTextColor(Color.BLACK);
            t2.setPadding(39,1,2,1);
            tbrw.addView(t2);

            TextView t3 = new TextView(this);
            t3.setText(""+cr.getString(4).toString());
            t3.setTextColor(Color.BLACK);
            t3.setPadding(50,1,2,1);
            tbrw.addView(t3);

            TextView t4 = new TextView(this);
            t4.setText(""+cr.getString(5).toString());
            t4.setTextColor(Color.BLACK);
            t4.setPadding(25,1,2,1);
            tbrw.addView(t4);
            stk.addView(tbrw);
        }
    }
}