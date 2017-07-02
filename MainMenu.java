package com.example.akshaydesai.drinkmate;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context con = getApplication();

        Intent intent = new Intent (con, DbMethods.class);
        intent.putExtra("order", "create");
        startService(intent);

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
