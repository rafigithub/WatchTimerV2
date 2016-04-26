package com.example.rafi.watchtimerv2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
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