package com.example.akshaydesai.drinkmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import 	android.text.method.LinkMovementMethod;
import android.view.View;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView text = (TextView)findViewById(R.id.AboutText);
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
