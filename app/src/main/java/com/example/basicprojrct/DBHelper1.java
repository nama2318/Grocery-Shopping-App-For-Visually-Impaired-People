package com.example.basicprojrct;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper1 extends SQLiteOpenHelper {

    public DBHelper1(Context context ) {
        super(context,"AdminData",null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase DB1) {
        DB1.execSQL("create Table AdminDetails(AdminID TEXT ,name TEXT,password PASSWORD,number NUMBER)");
        DB1.execSQL("create Table UserBill(ID INTEGER primary key autoincrement,UserID TEXT ,UserName TEXT,number TEXT,Total INTEGER,date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB1, int i, int i1) {
        DB1.execSQL("drop Table if exists AdminDetails");
    }
    public Boolean insetAdminData(String name,String number,String email,String password){
        SQLiteDatabase DB1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("AdminID",email);
        contentValues.put("name",name);
        contentValues.put("password",password);
        contentValues.put("number",number);
        long result= DB1.insert("AdminDetails",null,contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }
    public Cursor getData(){
        SQLiteDatabase DB1 = this.getWritableDatabase();
        Cursor cursor = DB1.rawQuery("Select * from AdminDetails ",null);
        return cursor;
    }

    public Boolean insertItem(String user,String username,String number,String Total,String date){
        SQLiteDatabase DB1 = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UserId",user);
        contentValues.put("UserName",username);
        contentValues.put("number",number);
        contentValues.put("Total",Total);
        contentValues.put("date",date);
        long result = DB1.insert("UserBill",null,contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public ArrayList<HashMap<String, String>> GetItems(){
        SQLiteDatabase DB1 = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM UserBill";
        Cursor cursor = DB1.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("userid",cursor.getString(1));
            user.put("number",cursor.getString(2));
            user.put("totalb",cursor.getString(3));
            user.put("dateb",cursor.getString(4));
            userList.add(user);
        }
        return  userList;
    }

    public Cursor getdb(){
        SQLiteDatabase DB1 = this.getWritableDatabase();
        String query = "SELECT * FROM UserBill";
        Cursor cursor = DB1.rawQuery(query,null);
        return cursor;
    }

    public Cursor getrowcount(){
        SQLiteDatabase DB1 = this.getWritableDatabase();
        String query = "SELECT COUNT(*) FROM UserBill";
        Cursor cursor = DB1.rawQuery(query,null);
        return cursor;
    }

    public String getadminname(String adminid){
        SQLiteDatabase DB1 = this.getWritableDatabase();
        String q = "SELECT * FROM AdminDetails";
        Cursor cursor = DB1.rawQuery(q,null);
        while (cursor.moveToNext()) {
            if(cursor.getString(0).equals(adminid)) {
                String Adminname = cursor.getString(1);
                return Adminname;
            }
        }
        return "FAIL";
    }
}