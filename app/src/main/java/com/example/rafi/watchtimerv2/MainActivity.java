/*
package com.example.rafi.watchtimer;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
    }
}
*/
package com.example.rafi.watchtimerv2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends Activity/* implements View.OnClickListener*/ {

    boolean returningFromHidden;
    private final ArrayList<CountDownTimer> timerBasico=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if(savedInstanceState!=null){
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(this.getApplicationContext());
            Gson gson = new Gson();
            String json = appSharedPrefs.getString("MyObject", "");
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            ArrayList<String> tiempos= gson.fromJson(json, type);
            LayoutInflater inflater = getLayoutInflater();
            final ViewGroup main= (ViewGroup) findViewById(R.id.main);
            final LinearLayout parent = (LinearLayout) findViewById(R.id.contenedor);
            for (int i=0;i<tiempos.size();i++){
                View timerFrame = inflater.inflate(R.layout.timer, main, false);
                TextView numberFrame = (TextView) timerFrame.findViewById(R.id.numberView);
                TextView originalTime = (TextView) timerFrame.findViewById(R.id.originalTime);
                numberFrame.setText(tiempos.get(i));
                originalTime.setText(tiempos.get(i));
                parent.addView(timerFrame);
            }
        }*/

        View main = findViewById(R.id.main);
        main.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeLeft() {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SetTimer fragment = new SetTimer();
                fragmentTransaction.add(R.id.main, fragment);
                fragmentTransaction.commit();
            }

            public void onSwipeRight() {
                onStop();
                finish();
                System.exit(0);
            }
        });

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        returningFromHidden=true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        ViewGroup nivel0 = (ViewGroup) findViewById(R.id.contenedor);
        ArrayList<String> tiempos = new ArrayList<>();
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        for (int i=0; i<nivel0.getChildCount();i++){
            ViewGroup nivel1= (ViewGroup) nivel0.getChildAt(i);
            ViewGroup nivel2 = (ViewGroup) nivel1.getChildAt(0);
            outer:for (int j=0; j<nivel2.getChildCount();j++) {
                View child = nivel2.getChildAt(j);
                if (child.getTag().equals("originalNumber")) {
                    String tiempo = ((TextView) child).getText().toString();
                    tiempos.add(tiempo);
                    break outer;
                }
            }
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        String json = gson.toJson(tiempos, type);
        prefsEditor.putString("MyObject", json);
        prefsEditor.apply();
    }

    @Override
    protected void onStart(){
        super.onStart();

        ViewGroup main= (ViewGroup) findViewById(R.id.main);
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        if (appSharedPrefs.contains("MyObject") && !returningFromHidden){

            Gson gson = new Gson();
            String json = appSharedPrefs.getString("MyObject", "");
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            ArrayList<String> tiempos = gson.fromJson(json, type);
            LayoutInflater inflater = getLayoutInflater();
//            final ViewGroup main = (ViewGroup) findViewById(R.id.main);
            final LinearLayout parent = (LinearLayout) findViewById(R.id.contenedor);
            for (int i = 0; i < tiempos.size(); i++) {
                View timerFrame = inflater.inflate(R.layout.timer, main, false);
                TextView numberFrame = (TextView) timerFrame.findViewById(R.id.numberView);
                TextView originalTime = (TextView) timerFrame.findViewById(R.id.originalTime);
                numberFrame.setText(tiempos.get(i));
                originalTime.setText(tiempos.get(i));
                parent.addView(timerFrame);
            }
        }
    }




    @Override
    public void onPause()
    {
        super.onPause();
    }



    /*public void newTimerView(String time){

        LayoutInflater inflater = getLayoutInflater();
        LinearLayout parent = (LinearLayout) findViewById(R.id.contenedor);
        ViewGroup main= (ViewGroup) findViewById(R.id.main);
        ViewGroup timerFrame =(ViewGroup) inflater.inflate(R.layout.timer, main, false);
        TextView numberFrame = (TextView) timerFrame.findViewById(R.id.numberView);
        TextView originalTime = (TextView) timerFrame.findViewById(R.id.originalTime);
        numberFrame.setText(time);
        originalTime.setText(time);
        parent.addView(timerFrame);

    }*/
}

