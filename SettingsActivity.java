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
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity {

    protected Intent Db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Db = (Intent) extras.get("Db");
        EditText editText = (EditText) findViewById(R.id.weightEnter);
        final ImageView ok1 = (ImageView) findViewById(R.id.checkweight);
        ImageView ok2 = (ImageView) findViewById(R.id.checkSex);

        ok1.setVisibility(4);
        ok2.setVisibility(4);

        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Db.putExtra("order", "weightEntered " + s);
                startService(Db);
                ok1.setVisibility(0);
            }
        });

    }

    public void sexEntered(View view){
        boolean checked = ((RadioButton) view).isChecked();

        ImageView ok2 = (ImageView) findViewById(R.id.checkSex);

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
        ok2.setVisibility(0);
        startService(Db);

    }
    public void enterClicked(View view){
        EditText editText = (EditText) findViewById(R.id.weightEnter);
        String weight = editText.getText().toString();
        Db.putExtra("order", "weightEntered "+ weight);
        startService(Db);
    }


}
