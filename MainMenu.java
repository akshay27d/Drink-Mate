package com.example.akshaydesai.drinkmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;
import android.provider.BaseColumns;
import android.content.Context;

public class MainMenu extends AppCompatActivity{

    Context con = getApplication();
    SQLiteDatabase myDB = context.openOrCreateDatabase("DMDB", MODE_PRIVATE, null);
    String UserInfo = "UserInfo";
    String sex = "Sex";
    String weight = "Weight";
    myDB.execSQL("CREATE TABLE "+UserInfo+" ("+sex+" TEXT PRIMARY KEY,"+weight+" INTEGER)";);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void startClicked(View view){
        Intent intent = new Intent(this, CounterScreen.class);
        startActivity(intent);
    }

    public void recordsClicked(View view){
        Intent intent = new Intent(this, RecordsActivity.class);
        startActivity(intent);
    }

    public void settingsClicked(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void aboutClicked(View view){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

}