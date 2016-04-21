package com.example.rafi.watchtimerv2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.media.Image;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Rafi on 13/04/2016.
 */
public class Timer {

    private TimerView timerView;
    private Context context;
    private Handler handler = new Handler();
    private long millisRemaining;
    private Runnable timerRun;


    public Timer(final TimerView timerView, final Context context){

        this.context = context;
        //setTimerView(timerView);
        this.timerView = timerView;
        //Set the listener and event handler for pressing the play button
        ImageButton playButton = timerView.getPlayButton();
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageButton play = (ImageButton) v;

                if(play.getTag().equals("play")){

                    startTimer();
                }

                else if(play.getTag().equals("pause")){

                    cancelTimer();
                    play.setImageResource(R.drawable.ic_media_play);
                    play.setTag("play");
                }
            }
        });

        //Set the listener and event handler for the reset button
        ImageButton resetButton = timerView.getResetButton();
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancelTimer();
                setUpTimer();
                timerView.resetTimerView();
            }
        });

        //Set the listener and event handler for the erase button
        ImageButton eraseButton = timerView.getEraseButton();
        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancelTimer();
                timerView.removeTimerView();
            }
        });

        setUpTimer();
    }


    private void setUpTimer(){

        final int timerViewTimeId = timerView.getTime().getId();
        final String timeString = timerView.getStartingTime();
        millisRemaining = MilliConversions.stringToMilli(timeString);


        timerRun = new Runnable() {
            @Override
            public void run() {

                TextView time = timerView.getTime();
                String timeRemaining = MilliConversions.milliToString(millisRemaining);
                time.setText(timeRemaining);
                if(millisRemaining<=50)
                {
                    Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    timerView.resetTimerView();
                    v.vibrate(250);
                }
                else{
                    millisRemaining=millisRemaining-200;
                    handler.postDelayed(this, 200);
                }
            }
        };
    }

    public void startTimer(){

        ImageButton play = timerView.getPlayButton();
        play.setImageResource(R.drawable.ic_media_pause);
        play.setTag("pause");
        handler.post(timerRun);
    }

    private void cancelTimer(){

        handler.removeCallbacks(timerRun);
    }

    public long getMillisRemaining(){

        return millisRemaining;
    }





}
