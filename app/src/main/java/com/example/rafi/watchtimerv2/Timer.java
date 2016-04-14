package com.example.rafi.watchtimerv2;

import android.media.Image;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Rafi on 13/04/2016.
 */
public class Timer {

    private TimerView timerView;
    private CountDownTimer countDownTimer;

    public Timer(final TimerView timerView){

        //setTimerView(timerView);
        this.timerView = timerView;
        //Set the listener and event handler for pressing the play button
        ImageButton playButton = timerView.getPlayButton();
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageButton play = (ImageButton) v;

                if(play.getTag().equals("play")){

                    setUpTimer();
                    startTimer();
                    play.setImageResource(R.drawable.ic_media_pause);
                    play.setTag("pause");
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




    }


    public CountDownTimer getCountDownTimer(){

        return countDownTimer;
    }

    public TimerView getTimerView(){

        return timerView;
    }

    /*public void setTimerView(TimerView timerView){

        this.timerView = timerView;
    }*/

    private void setUpTimer(){

        String timeString = timerView.getTime().getText().toString();
        long timeMillis = MilliConversions.stringToMilli(timeString);
        countDownTimer = new CountDownTimer(timeMillis, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

                String timeString = MilliConversions.milliToString(millisUntilFinished);
                TextView time = timerView.getTime();
                time.setText(timeString);
            }

            @Override
            public void onFinish() {

            }
        };
    }






}
