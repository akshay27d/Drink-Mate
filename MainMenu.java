package com.example.akshaydesai.drinkmate;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;
import android.content.Context;

public class MainMenu extends AppCompatActivity{

    protected Intent Db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context con = getApplication();

        Db = new Intent (con, DbMethods.class);
        Db.putExtra("order", "create");
        startService(Db);

        setContentView(R.layout.activity_main_menu);
    }

    public void startClicked(View view){
        Intent intent = new Intent(this, CounterScreen.class);
        intent.putExtra("Db", Db);
        startActivity(intent);
    }

    public void recordsClicked(View view){
        Intent intent = new Intent(this, RecordsActivity.class);
        intent.putExtra("Db", Db);
        startActivity(intent);
    }

    public void settingsClicked(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("Db", Db);
        startActivity(intent);
    }

    public void aboutClicked(View view){
        Intent intent = new Intent(this, AboutActivity.class);
        intent.putExtra("Db", Db);
        startActivity(intent);


    }


}
