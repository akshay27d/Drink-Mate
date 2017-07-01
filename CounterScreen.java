package com.example.akshaydesai.drinkmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class CounterScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_screen);
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

}
