package com.example.rafi.watchtimerv2;

import android.os.CountDownTimer;

/**
 * Created by Rafi on 13/04/2016.
 */
public class Timer {

    CountDownTimer timer;

    public Timer(final TimerView timerView, long startingTime, long tick){

        this.timer = new CountDownTimer(startingTime, tick) {
            @Override
            public void onTick(long millisUntilFinished) {

                timerView.updateTimer(currentTime);
            }

            @Override
            public void onFinish() {


            }
        };
    }
}
