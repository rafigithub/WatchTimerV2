package com.example.rafi.watchtimerv2;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Vibrator;
import android.widget.Toast;

import java.util.ArrayList;


public class TimerCounter extends IntentService {

    private Handler handler;
    private ArrayList<Integer> millisRemaining;

    public TimerCounter() {
        super("TimerCounter");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        handler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        millisRemaining = intent.getIntegerArrayListExtra("millisRemaining");
        count();
    }

    private void count(){

        handler.post(new Runnable() {
            @Override
            public void run() {

                for(int i=0; i<millisRemaining.size();i++){

                    if(millisRemaining.get(i)<=50)
                    {
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        v.vibrate(250);
                        Toast.makeText(getApplicationContext(),"Timer "+ i + " Complete!",Toast.LENGTH_LONG).show();
                    }
                    else{
                        millisRemaining.set(i,millisRemaining.get(i)-200);
                        handler.postDelayed(this, 200);
                    }
                }
            }
        });
    }
}
