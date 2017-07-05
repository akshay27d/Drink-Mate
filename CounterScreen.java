package com.example.akshaydesai.drinkmate;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;


import java.util.concurrent.TimeUnit;

public class CounterScreen extends AppCompatActivity {

    protected User user;
    protected Intent Db;
    protected int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_screen);
        checkUI();
        calculateBAC();
    }

    public void mixedClicked(View view){
        Log.d("GOOD", "worked1");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Db = (Intent) extras.get("Db");
        Db.putExtra("order", "drinkEntered Mixed");
        startService(Db);
    }

    public void shotClicked(View view){
        Log.d("GOOD", "worked2");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Db = (Intent) extras.get("Db");
        Db.putExtra("order", "drinkEntered Shot");
        startService(Db);
    }

    public void wineClicked(View view){
        Log.d("GOOD", "worked3");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Db = (Intent) extras.get("Db");
        Db.putExtra("order", "drinkEntered Wine");
        startService(Db);
    }

    public void beerClicked(View view){
        Log.d("GOOD", "worked4");
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Db = (Intent) extras.get("Db");
        Db.putExtra("order", "drinkEntered Beer");
        startService(Db);
    }


    public void checkUI(){
        Context con = getApplication();
        SQLiteDatabase myDB = con.openOrCreateDatabase("DMDB", MODE_PRIVATE, null);
        Cursor curs = myDB.query("UserInfo", null, null, null, null, null, null);
        curs.moveToFirst();

        try {
            String s = curs.getString(1);
            double w = curs.getDouble(2);
            User temp = new User(s, w);
            user = temp;
            Toast.makeText(con, user.getSex()+" "+ Double.toString(user.getWeight())+" lbs", 1).show();

        }catch(Exception e){
            Toast.makeText(con, "ERROR: Please enter your sex and weight in Settings", 1).show();
            finish();
        }
    }

    public void getCT(){
        count =0;
        Context con = getApplication();
        SQLiteDatabase myDB = con.openOrCreateDatabase("DMDB", MODE_PRIVATE, null);
        Cursor curs = myDB.query("CountTable", null, "Time >= datetime('now', '-12 hours')", null, null, null, null);
        curs.moveToFirst();
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        form.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date s=null;
        try{s = form.parse(curs.getString(0));}catch (Exception e){}
        String d = curs.getString(1);
        Log.d("//////////////  ", s + " " + d);
        count++;

        do {
            curs.moveToNext();
            try{s = form.parse(curs.getString(0));}catch (Exception e){}
            d = curs.getString(1);
            Log.d("//////////////  ", s + " " + d);
            count++;

        } while(!curs.isLast());
    }

    public Date get1stDrink(){
        Context con = getApplication();
        SQLiteDatabase myDB = con.openOrCreateDatabase("DMDB", MODE_PRIVATE, null);
        Cursor curs = myDB.query("CountTable", null, "Time >= datetime('now', '-12 hours')", null, null, null, null);
        Date s = null;

        try {
            curs.moveToFirst();
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            form.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                s = form.parse(curs.getString(0));
            } catch (Exception e) {
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return s;

    }
    public void calculateBAC(){
        Date first = get1stDrink();
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date current= new Date();

        double diff = current.getTime() - first.getTime();
        double diffMinutes = diff / (60 * 1000);
        double diffHours = diff / (60 * 60 * 1000);
        Log.d("TEST RESULT", Double.toString(diffMinutes));
        getCT();


        double bac = ((count*1.5)/user.getWeight()*(user.getSex().equals("Male") ? .73 : .66)*5.14)-.015*(diffMinutes/60.0);

        Log.d("FINAL BAC", Double.toString(bac));
    }

}
