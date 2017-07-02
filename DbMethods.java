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

public class DbMethods extends IntentService{
    public DbMethods() {
        super("DbMethods");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        String dataString = workIntent.getDataString();
        Bundle extras = workIntent.getExtras();
        String order = (String) extras.get("order");

        if (order.equals("create")){
            create();
        }
    }

    public void create(){
        Context con = getApplication();
        SQLiteDatabase myDB = con.openOrCreateDatabase("DMDB", MODE_PRIVATE, null);
        String UserInfo = "UserInfo";
        String sex = "Sex";
        String weight = "Weight";
        String create = "CREATE TABLE IF NOT EXISTS "+UserInfo+" ("+sex+" TEXT PRIMARY KEY,"+weight+" INTEGER);";
        myDB.execSQL(create);
    }

    public void sexEntered(boolean sex){            //True for Male, False for Female
        Context con = getApplication();

    }
}
