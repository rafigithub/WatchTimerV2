package com.example.rafi.watchtimerv2;

import android.os.CountDownTimer;

/**
 * Created by Rafi on 13/04/2016.
 */
public class TimerCounter {

    private CountDownTimer timer;
    private String currentTime;

    public TimerCounter(String startingTime, long tick){

        currentTime = startingTime;
        String[] startingTimeSplit = startingTime.split(":");
        long minutes = Integer.parseInt(startingTimeSplit[0]);
        long seconds = Integer.parseInt(startingTimeSplit[1]);
        long numericStartingTime = MilliConversions.minSecToMilli(minutes, seconds);

        this.timer = new CountDownTimer(numericStartingTime, tick) {
            @Override
            public void onTick(long millisUntilFinished) {

                long[] numericCurrentTime = MilliConversions.milliToMinSec(millisUntilFinished);
                currentTime = String.format("%02d:%02d",numericCurrentTime[0],numericCurrentTime[1]);
            }

            @Override
            public void onFinish() {


            }
        };
    }

    public String getCurrentTime(){

        return currentTime;
    }

    public void startTimer(){

        timer.start();
    }

    public void cancelTimer(){

        timer.cancel();
    }

}
