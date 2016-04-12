package com.example.rafi.watchtimerv2;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by Rafi on 02/03/2016.
 */
public interface CountDownInterface {
    void countDownTimerSetup(long timeInMillis, long tickInMillis, Context context, final TextView numberView);
}
