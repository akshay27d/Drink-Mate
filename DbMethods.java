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


        if (order.equals("create")){
            create(myDB);
        }
        else if (orders[0].equals("sexEntered")){
            sexEntered(orders[1], myDB);
        }
        else if (orders[0].equals("weightEntered")){
            weightEntered(Integer.parseInt(orders[1]), myDB);
        }
        else if (order.equals("getUserInfo")){
            //getUserInfo(myDB);
        }
    }

    public void create(SQLiteDatabase myDB){
        String UserInfo = "UserInfo";
        String sex = "Sex";
        String weight = "Weight";
        String ID = "Id";
        String create = "CREATE TABLE IF NOT EXISTS "+UserInfo+" ( "+ID+" INTEGER PRIMARY KEY, "+sex+" TEXT ,"+weight+" INTEGER);";
        myDB.execSQL(create);
        myDB.close();
    }

    public void sexEntered(String sex, SQLiteDatabase myDB){            //True for Male, False for Female
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

    public void weightEntered(int weight, SQLiteDatabase myDB){            //True for Male, False for Female
        Log.d("WEIGHT", Integer.toString(weight));
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

    public User getUserInfo(){
        Context con = getApplication();
        SQLiteDatabase myDB = con.openOrCreateDatabase("DMDB", MODE_PRIVATE, null);
        Cursor curs = myDB.query("UserInfo", null, null, null, null, null, null);
        curs.moveToFirst();
        String s = curs.getString(1);
        int w = curs.getInt(2);

        User retval = new User(s,w);
        return retval;

    }
}
