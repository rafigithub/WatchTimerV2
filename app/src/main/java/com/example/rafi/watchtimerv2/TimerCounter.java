package com.example.rafi.watchtimerv2;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Vibrator;
import android.widget.TextView;


public class TimerCounter extends IntentService {

    private Handler handler;
    private Runnable timerRun = new Runnable() {
        @Override
        public void run() {

            TextView time = timerView.getTime();
            String timeRemaining = MilliConversions.milliToString(millisRemaining);
            time.setText(timeRemaining);
            if(millisRemaining<=50)
            {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                timerView.resetTimerView();
                v.vibrate(250);
            }
            else{
                millisRemaining=millisRemaining-200;
                handler.postDelayed(this, 200);
            }


        }
    };
    private long millisRemaining;
    private TimerView timerView;

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


        this.timerView =
    }
}
