package com.example.akshaydesai.drinkmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.widget.PopupWindow;
import android.view.Gravity;
import android.view.Display;
import android.util.DisplayMetrics;
import android.widget.DatePicker;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        Context con = getApplication();
        SQLiteDatabase myDB = con.openOrCreateDatabase("DMDB", MODE_PRIVATE, null);

        TextView lastDay= (TextView) findViewById(R.id.lastDay);
        lastDay.setText("Drinks in last 24 hours: "+lastDay(myDB));
        TextView lastWeek= (TextView) findViewById(R.id.lastWeek);
        lastWeek.setText("Drinks in last 7 days: "+lastWeek(myDB));
        TextView mostIn12= (TextView) findViewById(R.id.mostIn12);
        mostIn12.setText("Most drinks in 24 hours: "+mostIn12(myDB));
        TextView daysUsed= (TextView) findViewById(R.id.daysUsed);
        daysUsed.setText("Days this app was used: "+daysUsed(myDB));
        TextView daysSinceLastUsed= (TextView) findViewById(R.id.daysSinceLastUsed);
        daysSinceLastUsed.setText("Days since last drink: "+ daysSinceLastUsed(myDB));

    }

    public int lastDay(SQLiteDatabase myDB){
        Cursor curs = myDB.query("CountTable", null, "Time >= datetime('now', '-24 hours')", null, null, null, null);
        int drinks = curs.getCount();
        return drinks;
    }

    public int lastWeek(SQLiteDatabase myDB){
        Cursor curs = myDB.query("CountTable", null, "Time >= datetime('now', '-168 hours')", null, null, null, null);
        int drinks = curs.getCount();
        return drinks;
    }

    public int mostIn12(SQLiteDatabase myDB){
        String[] selection = {"Time"};
        Cursor curs = myDB.query("CountTable", selection, null, null, null, null, "Time ASC");
        Cursor timeFind;
        curs.moveToFirst();

        String s= "";
        int most=0;
        for(int i =0;i<curs.getCount(); i++){
            try {
                s = curs.getString(0);
            } catch (Exception e) {
            }

            timeFind = myDB.rawQuery("select Time from CountTable where Time BETWEEN '" + s + "' AND datetime('"+s+"', '+12 hours') ORDER BY Time ASC", null);
            if (timeFind.getCount()>most){
                most = timeFind.getCount();
            }
            curs.moveToNext();
        }

        return most;
    }

    public int daysUsed(SQLiteDatabase myDB){
        String[] selection = {"Time"};
        Cursor curs = myDB.query("CountTable", selection, null, null, null, null, "Time ASC");
        curs.moveToFirst();
        ArrayList<String> datesUsed = new ArrayList<String>();
        String s = "";

        for(int i =0; i <curs.getCount(); i++){
            try {
                s = curs.getString(0).split(" ")[0];
            } catch (Exception e) {
            }
            if (datesUsed.indexOf(s) == -1){
                datesUsed.add(s);
            }
            curs.moveToNext();
        }

        return (datesUsed.size());
    }

    public int daysSinceLastUsed(SQLiteDatabase myDB){
        Cursor curs= myDB.query("CountTable", null, "Time >= datetime('now', '-12 hours') AND Time = (SELECT MAX(Time) FROM CountTable)", null, null, null, null);
        curs.moveToFirst();

        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        form.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date s=null;
        try {
            s = form.parse(curs.getString(0));
        } catch (Exception e) {
        }
        Date currentTime = new Date();
        try{currentTime = form.parse(currentTime.toString());}catch(Exception e){}

        double diff = currentTime.getTime() - s.getTime();
        double diffHours = diff / (60* 60 * 1000);
        Log.d("LAST TEST   ", diffHours+"   "+(int)(diffHours+12)+"    "+ (int)((diffHours+12)/24) + "   "+(int)((diffHours+12.0)/24)*24);
        return (int)((diffHours+12.0)/24)*24;

    }


}
