package com.example.rafi.watchtimerv2;

import android.widget.TextView;

/**
 * Created by Rafi on 13/04/2016.
 */
public interface Timer {


    void setUpTimer(TextView timerDisplay);

    void startTimer();

    void cancelTimer();

    String getStartingTime();
}