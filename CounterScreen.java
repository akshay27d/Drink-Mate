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
import android.widget.ImageButton;
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
import android.graphics.Color;
import android.widget.ViewSwitcher.ViewFactory;
import 	android.view.Gravity;
import android.view.View.OnLongClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

public class CounterScreen extends AppCompatActivity {

    protected User user;
    protected Intent Db;
    protected double count;
    protected int mixed;
    protected int shot;
    protected int wine;
    protected int beer;
    protected String last;
    protected TextSwitcher TSShot;
    protected TextSwitcher TSBeer;
    protected TextSwitcher TSMixed;
    protected TextSwitcher TSWine;
    protected TextSwitcher TSTotal;
    protected TextSwitcher TSBac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_screen);
        checkUI();
        createTS();
        last=" ";
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        updateTotalCount();
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
        updateTotalCount();
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
        updateTotalCount();
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
        updateTotalCount();
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
            Toast.makeText(con, user.getSex()+" "+ Double.toString(user.getWeight())+" lbs", 0).show();

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

        updateShotCount();
        updateBeerCount();
        updateMixedCount();
        updateWineCount();
        updateTotalCount();
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

        if(bac<0)
            bac=0;
        
        updateBAC(bac);
    }

    public void updateShotCount(){TSShot.setText("Shots: "+Integer.toString(shot));
    }

    public void updateBeerCount(){
        TSBeer.setText("Beers: "+Integer.toString(beer));
    }

    public void updateMixedCount(){
        TSMixed.setText("Mixes: " +Integer.toString(mixed));
    }

    public void updateWineCount(){
       TSWine.setText("Wines: " +Integer.toString(wine));
    }

    public void updateTotalCount(){
        TSTotal.setText("Total: "+Integer.toString((int)count));
    }

    public void updateBAC(double bac){
        if(bac >= 0.25)
            ((TextView)TSBac.getNextView()).setTextColor(Color.parseColor("#ff0000"));
        else if (bac<=0.08)
            ((TextView)TSBac.getNextView()).setTextColor(Color.parseColor("#00e600"));
        else
            ((TextView)TSBac.getNextView()).setTextColor(Color.parseColor("#e6e600"));
        TSBac.setText("BAC\n" + Double.toString(new BigDecimal(bac)
                .setScale(4,RoundingMode.HALF_DOWN).doubleValue()));
    }

    public void createTS(){
        ViewFactory factory = new ViewFactory() {
            public View makeView() {
                TextView myText = new TextView(CounterScreen.this);
                myText.setTextSize(20);
                myText.setTextColor(Color.parseColor("#D4AF37"));
                myText.setGravity(Gravity.CENTER);
                return myText;
            }
        };

        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        in.setDuration(300);
        out.setDuration(300);

        TSShot = (TextSwitcher) findViewById(R.id.shotCount);
        TSShot.setFactory(factory);
        TSShot.setInAnimation(in);
        TSShot.setOutAnimation(out);

        TSMixed = (TextSwitcher) findViewById(R.id.mixedCount);
        TSMixed.setFactory(factory);
        TSMixed.setInAnimation(in);
        TSMixed.setOutAnimation(out);

        TSBeer = (TextSwitcher) findViewById(R.id.beerCount);
        TSBeer.setFactory(factory);
        TSBeer.setInAnimation(in);
        TSBeer.setOutAnimation(out);

        TSWine = (TextSwitcher) findViewById(R.id.wineCount);
        TSWine.setFactory(factory);
        TSWine.setInAnimation(in);
        TSWine.setOutAnimation(out);

        TSTotal = (TextSwitcher) findViewById(R.id.totalCount);
        TSTotal.setFactory(factory);
        TSTotal.setInAnimation(in);
        TSTotal.setOutAnimation(out);

        ViewFactory factoryBAC = new ViewFactory() {
            public View makeView() {
                TextView myText = new TextView(CounterScreen.this);
                myText.setTextSize(40);
                myText.setTextColor(Color.parseColor("#D4AF37"));
                myText.setGravity(Gravity.CENTER);
                return myText;
            }
        };

        Animation out2 = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        Animation in2 = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        in2.setDuration(400);
        out2.setDuration(400);

        TSBac = (TextSwitcher) findViewById(R.id.bacDisplay);
        TSBac.setFactory(factoryBAC);
        TSBac.setInAnimation(in2);
        TSBac.setOutAnimation(out2);
    }

    public void optionsClicked(View view) {
        PopupMenu options = new PopupMenu(CounterScreen.this, (ImageButton) findViewById(R.id.options));
        options.getMenuInflater().inflate(R.menu.popup_menu, options.getMenu());

        options.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle().equals("Undo")){

                    Context con = getApplication();
                    SQLiteDatabase myDB = con.openOrCreateDatabase("DMDB", MODE_PRIVATE, null);
                    Cursor curs = myDB.query("CountTable", null, "Time = (SELECT MAX(Time) FROM CountTable)", null, null, null, null);
                    curs.moveToLast();
                    Log.d("FAULT", curs.getString(1));
                    boolean go=true;
                    try {
                        switch (curs.getString(1)) {
                            case "Wine":
                                if(wine!=0){
                                    wine--;
                                    updateWineCount();}
                                break;
                            case "Beer":
                                if(beer!=0){
                                    beer--;
                                    updateBeerCount();}
                                break;
                            case "Shot":
                                if(shot!=0){
                                    shot--;
                                    updateShotCount();}
                                break;
                            case "Mixed":
                                if(mixed!=0){
                                    mixed--;
                                    updateMixedCount();}
                                break;
                            default:
                                go = false;
                        }
                    }catch(Exception e){}
                    if(go){
                        if(count!=0){
                            count--;
                            updateTotalCount();
                            calculateBAC();}
                    }
                    Intent intent = getIntent();
                    Bundle extras = intent.getExtras();
                    Db = (Intent) extras.get("Db");
                    Db.putExtra("order", "removeLast");
                    startService(Db);
                }
                return true;
            }
        });
        options.show();//showing popup menu
    }

}

