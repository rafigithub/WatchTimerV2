package com.example.rafi.watchtimerv2;

import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Rafi on 13/04/2016.
 */
public class Timer {

    private TimerView timerView;
    private TimerCounter timerCounter;

    public Timer(TimerView timerView, TimerCounter timerCounter){

        this.timerView = timerView;
        this.timerCounter = timerCounter;
    }


    /*//Set the listener and event handler for pressing the play button
    playButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {

            ImageButton play = (ImageButton) v;

            if(play.getTag().equals("play")){

                startTimer();
                play.setImageResource(R.drawable.ic_media_pause);
                play.setTag("pause");
            }

            else if(play.getTag().equals("pause")){

                pauseTimer();
                play.setImageResource(R.drawable.ic_media_play);
                play.setTag("play");
            }
        }
    });

    resetButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {

            resetTimer();
        }
    });


    eraseButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {

            removeTimer();
        }
    });*/




}
