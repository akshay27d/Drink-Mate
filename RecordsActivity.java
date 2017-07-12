package com.example.akshaydesai.drinkmate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.view.Gravity;
import android.view.Display;
import android.util.DisplayMetrics;


public class RecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

    }

    public void buttonClicked(View view){

        LayoutInflater layoutInflater;
        View popupView;
        final PopupWindow popupWindow;
        Button finish;

        if(view.getId() == R.id.startDate || view.getId() == R.id.endDate) {
            layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            popupView = layoutInflater.inflate(R.layout.activity_date_entered, null);
            popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            finish = (Button) popupView.findViewById(R.id.dismiss1);
            finish.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });

            popupWindow.showAsDropDown((EditText) findViewById(R.id.placeholder), 90, 0);
        }
        else if ((view.getId() ==R.id.startTime || view.getId() == R.id.endTime)) {
            layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            popupView = layoutInflater.inflate(R.layout.activity_time_entered, null);
            popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            finish = (Button) popupView.findViewById(R.id.dismiss2);
            finish.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });

            popupWindow.showAsDropDown((EditText) findViewById(R.id.placeholder), 90, 0);
        }

    }


}
