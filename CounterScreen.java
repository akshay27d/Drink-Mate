package com.example.akshaydesai.drinkmate;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import android.widget.TextSwitcher;
import java.util.concurrent.TimeUnit;
import android.widget.TextView;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import java.text.DecimalFormat;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CounterScreen extends AppCompatActivity {

    protected User user;
    protected Intent Db;
    protected double count;
    protected int mixed;
    protected int shot;
    protected int wine;
    protected int beer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_screen);
        checkUI();
        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_counter_screen);
        initialize();
        Log.d("ON RESUME", "Achieved");
    }

    public void mixedClicked(View view){
        Log.d("GOOD", "worked1");

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Db = (Intent) extras.get("Db");
        Db.putExtra("order", "drinkEntered Mixed");
        startService(Db);

        count++;
        mixed++;
        updateMixedCount();
        Log.d("COUNTS", Double.toString(count));
        calculateBAC();


    }

    public void shotClicked(View view){
        Log.d("GOOD", "worked2");

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Db = (Intent) extras.get("Db");
        Db.putExtra("order", "drinkEntered Shot");
        startService(Db);

        count++;
        shot++;
        updateShotCount();
        calculateBAC();
    }

    public void wineClicked(View view){
        Log.d("GOOD", "worked3");

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Db = (Intent) extras.get("Db");
        Db.putExtra("order", "drinkEntered Wine");
        startService(Db);

        count++;
        wine++;
        updateWineCount();
        calculateBAC();
    }

    public void beerClicked(View view){
        Log.d("GOOD", "worked4");

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Db = (Intent) extras.get("Db");
        Db.putExtra("order", "drinkEntered Beer");
        startService(Db);

        count++;
        beer++;
        updateBeerCount();
        calculateBAC();
    }


    public void checkUI(){
        Context con = getApplication();
        SQLiteDatabase myDB = con.openOrCreateDatabase("DMDB", MODE_PRIVATE, null);
        Cursor curs = myDB.query("UserInfo", null, null, null, null, null, null);
        curs.moveToFirst();

        try {
            String s = curs.getString(1);
            double w = curs.getDouble(2);
            User temp = new User(s, w);
            user = temp;
            Toast.makeText(con, user.getSex()+" "+ Double.toString(user.getWeight())+" lbs", 1).show();

        }catch(Exception e){
            Toast.makeText(con, "ERROR: Please enter your sex and weight in Settings", 1).show();
            finish();
        }
    }

    public void initialize(){
        count=0;
        mixed=0;
        shot=0;
        wine=0;
        beer=0;

        Context con = getApplication();
        SQLiteDatabase myDB = con.openOrCreateDatabase("DMDB", MODE_PRIVATE, null);
        Cursor curs = myDB.query("CountTable", null, "Time >= datetime('now', '-12 hours')", null, null, null, null);
        curs.moveToFirst();
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        form.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date s=null;
        String d=null;

        do {
            try {
                try {
                    s = form.parse(curs.getString(0));
                } catch (Exception e) {
                }
                d = curs.getString(1);
                Log.d("//////////////  ", s + " " + d);
                switch(d){
                    case "Mixed":
                        mixed++;
                        break;
                    case "Shot":
                        shot++;
                        break;
                    case "Wine":
                        wine++;
                        break;
                    case "Beer":
                        beer++;
                        break;
                    default:
                        Log.d("ERROR", "did not work at count");
                }
                count++;
                if (curs.isLast()) {
                    break;
                }
                curs.moveToNext();
            }catch(Exception e){
                break;
            }

        } while(true);

        TextView shotDP = (TextView)findViewById(R.id.shotDP);
        shotDP.setText("Shots: " +Integer.toString(shot));

        TextView beerDP = (TextView)findViewById(R.id.beerDP);
        beerDP.setText("Beers: " +Integer.toString(beer));

        TextView mixedDP = (TextView)findViewById(R.id.mixedDP);
        mixedDP.setText("Mixes: " +Integer.toString(mixed));

        TextView wineDP = (TextView)findViewById(R.id.wineDP);
        wineDP.setText("Wines: " +Integer.toString(wine));

        calculateBAC();
    }

    public Date get1stDrink(){
        Context con = getApplication();
        SQLiteDatabase myDB = con.openOrCreateDatabase("DMDB", MODE_PRIVATE, null);
        Cursor curs = myDB.query("CountTable", null, "Time >= datetime('now', '-12 hours')", null, null, null, null);
        Date s = null;

        try {
            curs.moveToFirst();
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            form.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                s = form.parse(curs.getString(0));
            } catch (Exception e) {
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return s;

    }
    public void calculateBAC(){
        double bac=0;
        try {
            Date first = get1stDrink();
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            double diffMinutes;
            if(count==1){
                diffMinutes=0;
            }
            else {
                Date current = new Date();
                double diff = current.getTime() - first.getTime();
                diffMinutes = diff / (60 * 1000);
                Log.d("TEST RESULT", Double.toString(diffMinutes));
            }
            bac = ((count * 1.5) / user.getWeight() * (user.getSex().equals("Male") ? .73 : .66) * 5.14) - .015 * (diffMinutes / 60.0);
        }catch(Exception e){
            e.printStackTrace();
        }

        Log.d("BACCC", Double.toString(bac));

        updateBAC(bac);
    }

    public void updateShotCount(){
        TextView text = (TextView)findViewById(R.id.shotDP);
        TextSwitcher TS = (TextSwitcher)findViewById(R.id.shotCount);

        TS.removeView(text);
        TS.addView(text);
        text.setText("Shots: " +Integer.toString(shot));
    }

    public void updateBeerCount(){
        TextView text = (TextView)findViewById(R.id.beerDP);
        TextSwitcher TS = (TextSwitcher)findViewById(R.id.beerCount);

        TS.removeView(text);
        TS.addView(text);
        text.setText("Beers: " +Integer.toString(beer));
    }

    public void updateMixedCount(){
        TextView text = (TextView)findViewById(R.id.mixedDP);
        TextSwitcher TS = (TextSwitcher)findViewById(R.id.mixedCount);

        TS.removeView(text);
        TS.addView(text);
        text.setText("Mixes: " +Integer.toString(mixed));
    }

    public void updateWineCount(){
        TextView text = (TextView)findViewById(R.id.wineDP);
        TextSwitcher TS = (TextSwitcher)findViewById(R.id.wineCount);

        TS.removeView(text);
        TS.addView(text);
        text.setText("Wines: " +Integer.toString(wine));
    }

    public void updateBAC(double bac){
        TextView text = (TextView)findViewById(R.id.bacDP);
        TextSwitcher TS = (TextSwitcher)findViewById(R.id.bacDisplay);

        TS.removeView(text);
        TS.addView(text);
        text.setText("BAC \n " + Double.toString(new BigDecimal(bac)
                .setScale(4,RoundingMode.HALF_DOWN).doubleValue()));
        Log.d("COUNTBBBACCCS", Double.toString(bac));

    }
}
