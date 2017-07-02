package com.example.akshaydesai.drinkmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.widget.EditText;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void sexEntered(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.maleButton:
                if (checked)
                    Log.d("---------", "Male");
                    break;
            case R.id.femaleButton:
                if (checked)
                    Log.d("---------", "Female");
                    break;
        }
    }
    public void enterClicked(View view){
        EditText editText = (EditText) findViewById(R.id.weightEnter);
        String message = editText.getText().toString();
    }
}
