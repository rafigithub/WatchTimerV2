package com.example.rafi.watchtimerv2;

import android.os.CountDownTimer;

/**
 * Created by Rafi on 13/04/2016.
 */
public class Timer {

    CountDownTimer timer;
    private TimerView timerView;

    public Timer(final TimerView timerView, String startingTime, long tick){

        String[] startingTimeSplit = startingTime.split(":");
        long minutes = Integer.parseInt(startingTimeSplit[0]);
        long seconds = Integer.parseInt(startingTimeSplit[1]);
        long numericStartingTime = MilliConversions.minSecToMilli(minutes, seconds);

        this.timerView = timerView;
        this.timer = new CountDownTimer(numericStartingTime, tick) {
            @Override
            public void onTick(long millisUntilFinished) {

                long[] numericCurrentTime = MilliConversions.milliToMinSec(millisUntilFinished);
                String currentTime = String.format("%02d:%02d",numericCurrentTime[0],numericCurrentTime[1]);
                timerView.updateTimer(currentTime);
            }

            @Override
            public void onFinish() {


            }
        };
    }

    public void startTimer(){

        timer.start();
    }

    public void cancelTimer(){

        timer.cancel();
    }

}
