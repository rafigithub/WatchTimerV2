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
import android.widget.Toast;

/**
 * Created by Rafi on 13/04/2016.
 */
public class Timer {

    private TimerView timerView;
    private Context context;
    private Handler handler = new Handler();
    private long millisRemaining;
    private Runnable timerRun;
    private CountDownTimer testTimer;


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

        /*timerView.setOnTouchListener(new OnSwipeTouchListener(context) {
            @Override
            public void onSwipeLeft() {

                final Activity activity = (Activity) context;
                FragmentManager fragmentManager = activity.getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SetTimer fragment = new SetTimer();
                fragmentTransaction.add(R.id.main, fragment);
                fragmentTransaction.commit();
            }

            public void onSwipeRight() {

                final Activity activity = (Activity) context;
                activity.finish();
                System.exit(0);
            }
        });*/
        //setUpTimer();
    }


    private void setUpTimer(){

        final String timeString = timerView.getTime().getText().toString();
        millisRemaining = MilliConversions.stringToMilli(timeString);

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
                Toast.makeText(context,"finish countdowntimer",Toast.LENGTH_SHORT).show();
            }
        };

       /* timerRun = new Runnable() {
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
                    Toast.makeText(context,"finish handler",Toast.LENGTH_SHORT).show();
                }
                else{
                    millisRemaining=millisRemaining-400;
                    handler.postDelayed(this, 400);
                }
            }
        };*/
    }

    public void startTimer(){

        //handler.post(timerRun);
        testTimer.start();
    }

    private void cancelTimer(){

        testTimer.cancel();
        //handler.removeCallbacks(timerRun);
    }

    public long getMillisRemaining(){

        return millisRemaining;
    }
}
