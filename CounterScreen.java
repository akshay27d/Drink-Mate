package com.example.akshaydesai.drinkmate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CounterScreen extends AppCompatActivity {

    protected User user;
    protected int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_screen);
        getUI();
    }

    public void mixedClicked(View view){
        Log.d("GOOD", "worked1");
    }

    public void shotClicked(View view){
        Log.d("GOOD", "worked2");
    }

    public void wineClicked(View view){
        Log.d("GOOD", "worked3");
    }

    public void beerClicked(View view){
        Log.d("GOOD", "worked4");
    }


    public void getUI(){
        Context con = getApplication();
        SQLiteDatabase myDB = con.openOrCreateDatabase("DMDB", MODE_PRIVATE, null);
        Cursor curs = myDB.query("UserInfo", null, null, null, null, null, null);
        curs.moveToFirst();

        try {
            String s = curs.getString(1);
            int w = curs.getInt(2);
            count++;
            User retval = new User(s, w);
            Log.d("]]]]]]]]]]]]]]]"+ Integer.toString(count), retval.toString());
            user = retval;
            Toast.makeText(con, user.getSex()+" "+ Integer.toString(user.getWeight())+" lbs", 1).show();

        }catch(Exception e){
            Toast.makeText(con, "ERROR: Please enter your sex and weight in Settings", 1).show();
            finish();
        }
    }
}
