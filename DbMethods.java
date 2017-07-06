package com.example.akshaydesai.drinkmate;

/**
 * Created by akshaydesai on 7/1/17.
 */

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.concurrent.TimeUnit;

public class DbMethods extends IntentService{
    public DbMethods() {
        super("DbMethods");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        String dataString = workIntent.getDataString();
        Bundle extras = workIntent.getExtras();
        String order = (String) extras.get("order");
        String[] orders = order.split(" ");

        Context con = getApplication();
        SQLiteDatabase myDB = con.openOrCreateDatabase("DMDB", MODE_PRIVATE, null);

       // Log.d(orders[0],orders[1]);

        if (order.equals("create")){
            create(myDB);
        }
        else if (orders[0].equals("sexEntered")){
            sexEntered(orders[1], myDB);
        }
        else if (orders[0].equals("weightEntered")){
            weightEntered(Double.parseDouble(orders[1]), myDB);
        }
        else if (orders[0].equals("drinkEntered")){
            drinkEntered(orders[1], myDB);
        }
    }

    public void create(SQLiteDatabase myDB){
        String UserInfo = "UserInfo";
        String sex = "Sex";
        String weight = "Weight";
        String ID = "Id";
        String create = "CREATE TABLE IF NOT EXISTS "+UserInfo+" ( "+ID+" INTEGER PRIMARY KEY, "+sex+" TEXT ,"+weight+" INTEGER);";
        myDB.execSQL(create);

        String countTable = "CountTable";
        String time = "Time";
        String type = "Type";
        create = "CREATE TABLE IF NOT EXISTS "+countTable+" ( "+time+" DATETIME PRIMARY KEY DEFAULT CURRENT_TIMESTAMP, "+type+" TEXT);";
        myDB.execSQL(create);
        myDB.close();
    }

    public void drinkEntered(String type, SQLiteDatabase myDB){
        Log.d("Drinke", type);
        ContentValues myVals = new ContentValues();
        myVals.put("Type" , type);
        boolean goOn= false;

        while (!goOn) {
            try {
                myDB.insertOrThrow("CountTable", null, myVals);
                goOn = true;
            } catch (SQLException e) {
                try {TimeUnit.SECONDS.sleep(1);} catch (Exception d) {}
            }
        }
    }

    public void sexEntered(String sex, SQLiteDatabase myDB){
        Log.d("SEX", sex);
        ContentValues myVals = new ContentValues();
        myVals.put("Sex" , sex);
        myVals.put("Id" , 0);
        try {
            myDB.insertOrThrow("UserInfo", null, myVals);
        }catch(SQLException e){
            myDB.update("UserInfo",myVals,null, null);
        }
        myDB.close();
    }

    public void weightEntered(double weight, SQLiteDatabase myDB){
        Log.d("WEIGHT", Double.toString(weight));
        ContentValues myVals = new ContentValues();
        myVals.put("Weight" , weight);
        myVals.put("Id" , 0);
        try {
            myDB.insertOrThrow("UserInfo", null, myVals);
        }catch(SQLException e){
            myDB.update("UserInfo",myVals,null, null);
        }
        myDB.close();
    }


}
