package com.example.rafi.watchtimerv2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends WearableActivity{

    private PowerManager.WakeLock wakeLock;
    private ArrayList<TimerView> timers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "awake");

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        if(sharedPref.contains("times")){

            Gson gson = new Gson();
            String json = sharedPref.getString("times", null);
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            ArrayList<String> timeWhenLeaving = gson.fromJson(json, type) ;

            if(timeWhenLeaving!=null){

                for(String times:timeWhenLeaving){

                    Timer timer = new Countdown(this, times);
                    TimerView timerView = new TimerView(this, timer, (LinearLayout)findViewById(R.id.contenedor));
                    timers.add(timerView);
                }
            }
            else{

                Toast.makeText(this,"saved time array is null",Toast.LENGTH_SHORT).show();
            }
        }

        View main = findViewById(R.id.main);
        main.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {

            @Override
            public void onSwipeLeft() {

                setTimerFragment();
            }

            @Override
            public void onSwipeRight() {

                finish();
                //moveTaskToBack(true);
                /*for(Timer timer: timers){
                    if(timer.getTimerView().getPlayButton().getTag().equals("pause")){
                        timersRunning = true;
                    }
                }
                if(timersRunning){
                    moveTaskToBack(true);
                    timersRunning = false;
                }
                else{
                    timersRunning = false;
                    saveData();
                    finish();
                }*/
            }
        });
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails){

        super.onEnterAmbient(ambientDetails);

        if(timersAreRunning()){

            wakeLock.acquire();
        }

        RelativeLayout main = (RelativeLayout)findViewById(R.id.main);
        main.setBackgroundColor(Color.BLACK);
        if(timers.size()!=0){

            for(TimerView timerView: timers){
                LinearLayout container = timerView.getTimerContainer();
                LinearLayout border = (LinearLayout) container.findViewById(R.id.hijo);
                border.setBackgroundColor(Color.WHITE);
                for (int i=0;i<border.getChildCount();i++){
                    border.getChildAt(i).setBackgroundColor(Color.BLACK);
                    if(border.getChildAt(i) instanceof TextView){
                        ((TextView) border.getChildAt(i)).getPaint().setAntiAlias(false);
                    }
                }
            }
        }
    }

    @Override
    public void onExitAmbient(){

        super.onExitAmbient();

        if(wakeLock.isHeld()){

            wakeLock.release();
        }

        RelativeLayout main = (RelativeLayout) findViewById(R.id.main);
        main.setBackgroundColor(Color.parseColor("#303F9F"));

        if(timers.size()!=0){

            for(TimerView timerView: timers){
                LinearLayout container = timerView.getTimerContainer();
                LinearLayout border = (LinearLayout) container.findViewById(R.id.hijo);
                border.setBackgroundColor(Color.BLACK);
                for (int i=0;i<border.getChildCount();i++){
                    border.getChildAt(i).setBackgroundColor(Color.parseColor("#303F9F"));
                    if(border.getChildAt(i) instanceof TextView){
                        ((TextView) border.getChildAt(i)).getPaint().setAntiAlias(true);
                    }
                }
            }
        }
    }

    public void setTimerFragment(){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SetTimer fragment = new SetTimer();
        fragmentTransaction.add(R.id.main, fragment);
        fragmentTransaction.commit();
    }

    public ArrayList<TimerView> getTimerArray(){

        return timers;
    }

    private boolean timersAreRunning(){

        boolean isRunning=false;

        if (timers.size()>0){

            for(TimerView timerView: timers){

                ImageButton playButton = (ImageButton)timerView.getTimerContainer().findViewById(R.id.playButton);
                if(playButton.getTag().equals("pause")){
                    isRunning = true;
                }
            }
        }
        return isRunning;
    }

    @Override
    public void onDestroy(){

        super.onDestroy();
        if (wakeLock.isHeld()){
            wakeLock.release();
        }
        saveData();
    }

    public void saveData(){

        ArrayList<String> timeWhenLeaving = new ArrayList<>();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();

        for(TimerView timerView: timers){

            timeWhenLeaving.add(timerView.getStartingTime());
        }
        String json = gson.toJson(timeWhenLeaving, type);
        editor.putString("times", json);
        editor.apply();
    }
}

