package com.example.basicprojrct;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Menu extends AppCompatActivity {
    Button b1;
    String msg,ttt1;
    DBHelper dbHelper;
    String mno;
    ListView listview1;
    SQLiteDatabase DB;
    String Item;
    TextToSpeech t1;
    double total;
    int selectitem = 0;
    int price = 0;
    String pid;
    int qty=1;
    int qtystatus=0;
    int qtyy;
    DBHelper1 dbHelper1;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    public static String userpass,numberpass;
    public static String totalpass;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    float x11,x21,y11,y21;
    String products[] ={" \n 1.Sugar/kg \t\t\t35rs \n ","\n 2.Tea/kg  \t\t\t30rs \n","\n 3.Wheat Flour/kg \t\t25rs \n","\n 4.Gram Flour/kg \t\t20rs \n","\n 5.Rice Flour/kg \t\t30rs \n","\n 6.lentils/kg \t\t\t40 rs \n","\n 7.DryFruits/kg \t\t100rs \n","\n 8.Corn Flour/kg \t\t30rs \n","\n 9.Oil \t\t\t130rs \n","\n 10.Milk \t\t\t25rs "};
    String[] prodname = {"Sugar","Tea","Wheat Flour","Gram Flour","Rice Flour","Lentils","Dry Fruits","Corn Flour","Oil","Milk"};
    String[] prodprice = {"Rs.35","Rs.30","Rs.25","Rs.20","Rs.30","Rs.40","Rs.100","Rs.30","Rs.130","Rs.25"};
    int[] prodimages = {R.drawable.sugar, R.drawable.tea,R.drawable.wheatflour,R.drawable.gramflour,R.drawable.riceflour,R.drawable.lentils,R.drawable.dryfruits,R.drawable.cornflour,R.drawable.oil,R.drawable.milk};
    ArrayAdapter<String> adapter = null;
    String menulist = "Sugar,35 Rupees.Tea,30 Rupees.Wheat Flour,25 Rupees.Gram Flour,20 Rupees.Rice Flour,30 Rupees.lentils,40 Rupees.DryFruits,100 Rupees.Corn Flour,30 Rupees.Oil,130 Rupees.Milk,25 Rupees.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        dbHelper = new DBHelper(this);
        dbHelper1 = new DBHelper1(this);
        b1=findViewById(R.id.b1);
        listview1 = (ListView) findViewById(R.id.l1);
        ProgramAdapter programAdapter = new ProgramAdapter(this,prodname,prodprice,prodimages);
//        adapter=new ArrayAdapter<String>(Menu.this,R.layout.listiitem, prodname,prodprice,prodimages);
        listview1.setAdapter(programAdapter);
        speakitems();
        //speakmenu();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billspeak();
                smss();
            }
        });
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
                    qtystatus = 0;
//                   selectitem=0;
                    listen();
                    //bill();
                    return true;
                }
                else if(x11 > x21){
//                    selectitem=1;
                    qtystatus = 1;
                    listen();
                    return true;
                }
        }
        return false;
    }

    public void speakmenu(){
        String additemsay = "Swipe Right To Enter Product ID, Swipe left to Enter Quantity. If you want to generate Total, swipe left or right and say Bill.";
            t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    t1.setLanguage(Locale.UK);
                    t1.setSpeechRate((float) 0.8);
                    t1.speak(additemsay, TextToSpeech.QUEUE_FLUSH, null);
                }
            });



//            t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
//                @Override
//                public void onInit(int status) {
//                    t1.setLanguage(Locale.UK);
//                    t1.setSpeechRate((float) 0.8);
//                    t1.speak(additemsay, TextToSpeech.QUEUE_FLUSH, null);
//                }
//            });
    }
    public void listen(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 10000);

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e) {
            Toast.makeText(Menu.this, " " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String res = result.get(0);

                    res = res.toLowerCase().replace(" ","");
//                    selectitem = 1;
                res = res.toLowerCase();
                if(res.contains("bill") || res.contains("billing") || res.contains("generatebill") || res.contains("bil")) {
                    billspeak();
                    smss();
                }else if(qtystatus == 0) {
                    pid = res;
//                    Toast.makeText(Menu.this, "" + pid, Toast.LENGTH_SHORT).show();
                    bill();
                }
                else if(qtystatus == 1){
                    try {
                        if (res.equals("free") || res.equals("tree") || res.equals("three")) {
                            res = "3";
                        } else if (res.equals("tu") || res.equals("two") || res.equals("too"))
                        {
                            res = "2";
                        }
                        qtyy = Integer.parseInt(res);
                        getqty();

                    }catch (Exception e){
                        invalidqty();
                    }
//                    Toast.makeText(Menu.this, "" + qtyy, Toast.LENGTH_SHORT).show();
                }

               /*else if(selectitem == 1){
                   selectitem =0;

                    Toast.makeText(Menu.this,""+qty,Toast.LENGTH_LONG).show();
                    bill();
                }*/


            }
        }

    }
    public void bill()
    {
        if(pid.equals("1") || pid.equals("one") || pid.equals("sugar")) {
            price=35;
//            total=total+price;

        }
     else if(pid.equals("2") || pid.equals("two") || pid.equals("tea") || pid.equals("t")) {
           price=qty*30;
//           total=total+price;
       }
        else if(pid.equals("3") || pid.equals("three") || pid.equals("wheatflour")){
        price=35;
//        total=total+price;
    }
    else if(pid.equals("4") || pid.equals("four") || pid.equals("gramflour")) {
        price=20;
//        total=total+price;
    }
    else if(pid.equals("5") || pid.equals("five") || pid.equals("riceflour")) {
        price=30;
//        total=total+price;
    }
    else if(pid.equals("6") || pid.equals("six") || pid.equals("lentils")) {
        price=40;
//        total=total+price;
    }
    else if(pid.equals("7") || pid.equals("seven") || pid.equals("dryfruits")) {
        price=100;
//        total=total+price;
    }
    else if(pid.equals("8") || pid.equals("eight") || pid.equals("cornflour")){
        price=30;
//        total=total+price;
    }
        else if(pid.equals("9") || pid.equals("nine") || pid.equals("oil")){
            price=130;
//            total=total+price;
        }
        else if(pid.equals("10") || pid.equals("ten") || pid.equals("milk")){
            price=25;
//            total=total+price;
        }
        else{
            Toast.makeText(Menu.this,"No Such Item Available!!!",Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(Menu.this,""+price,Toast.LENGTH_SHORT).show();
    }
    public void getqty(){
//        listen();
        int quantity = qtyy;
        total = total + (price * quantity);
        //bill();
        qtystatus = 0;
    }
    public void onPause(){
        if(t1 != null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
public void smss( )
{
    String usernmm = Login.usernm;
    Cursor cursor = dbHelper.getNumber(usernmm);
    if(cursor.getCount() == 0){
        Toast.makeText(Menu.this,"No entries Exists",Toast.LENGTH_LONG).show();
    }
    else {
        String phno = numberget(cursor, usernmm);
        phno = phno.replace(" ","");
        String username = getusername(cursor,usernmm);
        if(phno != null) {
            Toast.makeText(this, "sms send " + phno, Toast.LENGTH_SHORT).show();
            msg = "Thank You For shopping with Us !! your total is Billing Amount is RS. " + total;
            userpass = usernmm;
            numberpass = phno;
            totalpass = String.valueOf(total);
            //String no =msg1();
            try {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(phno, null, msg, null, null);
                Toast.makeText(this, "sms send", Toast.LENGTH_SHORT).show();
                String datetd = getdatetoday();
                boolean smp = dbHelper1.insertItem(usernmm,username,phno,totalpass,datetd);
                if(smp){
                    Toast.makeText(Menu.this,"Item Inserted",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Menu.this,"Failed To Insert Data",Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, " "+e, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(Menu.this,"No Number Found. Can't Send SMS",Toast.LENGTH_SHORT).show();
        }
    }


}
//    public Cursor msg1(){
//ttt1=Login.email.getText().toString();
//        cursor = DB.rawQuery("Select number from Userdetails where userID=ttt1",null);
//        return cursor;
//
//    }
    public void billspeak(){
        String totalbill = "Your Total Bill is "+total+" Rupees.";
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                t1.setLanguage(Locale.UK);
                t1.setSpeechRate((float) 0.8);
                t1.speak(totalbill, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    public String numberget(Cursor cursor,String usernmm){
        while (cursor.moveToNext()){
            if (cursor.getString(0).equals(usernmm)) {
                String num = cursor.getString(3);
                return num;
            }
        }
        return null;
    }
    public String getusername(Cursor cursor,String userid){
        while (cursor.moveToNext()){
            if (cursor.getString(0).equals(userid)) {
                String username = cursor.getString(1);
                Toast.makeText(Menu.this,""+username,Toast.LENGTH_SHORT).show();
                return username;
            }
        }
        return null;
    }

    public void speakitems(){
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                t1.setLanguage(Locale.getDefault());
                t1.setSpeechRate((float) 0.8);
                t1.speak(menulist, TextToSpeech.QUEUE_FLUSH, null);
                speakmenu();
            }
        });
    }
    public String getdatetoday(){
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yy");
        date = dateFormat.format(calendar.getTime());
        String crdate = date.toString();
        return crdate;
    }

    public void invalidqty(){
        String invalidwarning = "Invalid Quantity, Please Speak Loud And Clear.";
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                t1.setLanguage(Locale.UK);
                t1.setSpeechRate((float) 0.8);
                t1.speak(invalidwarning, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }
}