package com.example.akshaydesai.drinkmate;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.widget.EditText;
import android.widget.RadioButton;
import android.content.Intent;

public class SettingsActivity extends AppCompatActivity {

    protected Intent Db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Db = (Intent) extras.get("Db");
    }

    public void sexEntered(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.maleButton:
                if (checked)
                    Db.putExtra("order", "sexEntered Male");
                    break;
            case R.id.femaleButton:
                if (checked)
                    Db.putExtra("order", "sexEntered Female");
                    break;
        }
        startService(Db);

    }
    public void enterClicked(View view){
        EditText editText = (EditText) findViewById(R.id.weightEnter);
        String weight = editText.getText().toString();
        Db.putExtra("order", "weightEntered "+ weight);
        startService(Db);
    }


}
