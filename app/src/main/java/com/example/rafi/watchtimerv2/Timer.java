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
public class Timer {

    private TimerView timerView;
    private Context context;
    private CountDownTimer testTimer;


    public Timer(final TimerView timerView, final Context context){

        this.context = context;
        final Activity mainActivity = (Activity) context;
        //setTimerView(timerView);
        this.timerView = timerView;
        //Set the listener and event handler for pressing the play button
        ImageButton playButton = timerView.getPlayButton();

        playButton.setOnTouchListener(new OnSwipeTouchListener(mainActivity, playButton) {
            @Override
            public void onSwipeLeft() {
                FragmentManager fragmentManager = mainActivity.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SetTimer fragment = new SetTimer();
                fragmentTransaction.add(R.id.main, fragment);
                fragmentTransaction.commit();
            }
            @Override
            public void onSwipeRight() {
                mainActivity.moveTaskToBack(true);
            }
            @Override
            public void onClick(View v){

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

        resetButton.setOnTouchListener(new OnSwipeTouchListener(mainActivity, resetButton) {
            @Override
            public void onSwipeLeft() {
                FragmentManager fragmentManager = mainActivity.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SetTimer fragment = new SetTimer();
                fragmentTransaction.add(R.id.main, fragment);
                fragmentTransaction.commit();
            }
            @Override
            public void onSwipeRight() {
                mainActivity.moveTaskToBack(true);
            }
            @Override
            public void onClick(View v){

                cancelTimer();
                setUpTimer();
                timerView.resetTimerView();
            }
        });

        //Set the listener and event handler for the erase button
        ImageButton eraseButton = timerView.getEraseButton();

        eraseButton.setOnTouchListener(new OnSwipeTouchListener(mainActivity, eraseButton) {
            @Override
            public void onSwipeLeft() {
                FragmentManager fragmentManager = mainActivity.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SetTimer fragment = new SetTimer();
                fragmentTransaction.add(R.id.main, fragment);
                fragmentTransaction.commit();
            }
            @Override
            public void onSwipeRight() {
                mainActivity.moveTaskToBack(true);
            }
            @Override
            public void onClick(View v){

                cancelTimer();
                timerView.removeTimerView();
            }
        });
    }


    private void setUpTimer(){

        final String timeString = timerView.getTime().getText().toString();
        long millisRemaining = MilliConversions.stringToMilli(timeString);

         testTimer  = new CountDownTimer(millisRemaining, 400) {
            @Override
            public void onTick(long millisUntilFinished) {

                TextView time = timerView.getTime();
                String timeRemaining = MilliConversions.milliToString(millisUntilFinished);
                time.setText(timeRemaining);


            }

            @Override
            public void onFinish() {

                Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                timerView.resetTimerView();
                v.vibrate(250);

                Intent intentHome = new Intent(context, MainActivity.class);
                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentHome.putExtra("Default extra","");
                context.startActivity(intentHome);
            }
        };
    }

    public void startTimer(){

        testTimer.start();
    }

    private void cancelTimer(){

        if(testTimer!=null){

            testTimer.cancel();
        }
    }
}
