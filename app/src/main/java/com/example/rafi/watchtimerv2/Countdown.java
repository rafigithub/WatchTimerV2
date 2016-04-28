package com.example.rafi.watchtimerv2;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.widget.TextView;

/**
 * Created by Rafi on 26/04/2016.
 */
public class Countdown implements Timer {

    private MainActivity mainActivity;
    private CountDownTimer testTimer;
    private String startingTime;


    public Countdown(final Context context, String startingTime){

        mainActivity = (MainActivity) context;
        this.startingTime = startingTime;
        //setTimerView(timerView);

    }


    public void setUpTimer(final TextView timerDisplay){

        final String timeString = timerDisplay.getText().toString();
        long millisRemaining = MilliConversions.stringToMilli(timeString);

        testTimer  = new CountDownTimer(millisRemaining, 400) {
            @Override
            public void onTick(long millisUntilFinished) {

                String timeRemaining = MilliConversions.milliToString(millisUntilFinished);
                timerDisplay.setText(timeRemaining);
            }

            @Override
            public void onFinish() {

                Vibrator v = (Vibrator) mainActivity.getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(300);
            }
        };
    }

    public void startTimer(){

        testTimer.start();
    }

    public void cancelTimer(){

        if(testTimer!=null){

            testTimer.cancel();
        }
    }

    public String getStartingTime(){

        return startingTime;
    }
}