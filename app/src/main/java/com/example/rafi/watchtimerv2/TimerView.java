package com.example.rafi.watchtimerv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Rafi on 25/02/2016.
 */

//Class that creates a graphical interface for a timer.
public class TimerView extends LinearLayout{

    //Get a reference to the main activity
    private MainActivity mainActivity;
    //Get a reference to the type of timer this view implements.
    private Timer timer;
    //Create a Linear Layout that will contain the template from "timer.xml".
    private LinearLayout timerContainer;
    //And variables to store the timers' buttons and displays.
    private ImageButton playButton;
    private ImageButton resetButton;
    private ImageButton eraseButton;
    private TextView timeText;

    //The constructor takes an initial time and a parent to attach the timer to.
    public TimerView(final Context context, final Timer timer, ViewGroup parent){

        //Get the context
        super(context);
        this.mainActivity = (MainActivity) context;
        LayoutInflater inflater = LayoutInflater.from(context);
        //Inflate the template into the timer layout and attach it to parent
        this.timerContainer = (LinearLayout) inflater.inflate(R.layout.timer,parent,false);
        parent.addView(timerContainer);
        //Set the timer to the type desired
        this.timer = timer;
        //Then set the timer to the initial time
        this.timeText = (TextView) timerContainer.findViewById(R.id.numberView);
        timeText.setText(timer.getStartingTime());
        //Set the buttons of the template to this object button variables
        this.playButton = (ImageButton) timerContainer.findViewById(R.id.playButton);
        this.eraseButton = (ImageButton) timerContainer.findViewById(R.id.eraseButton);
        this.resetButton = (ImageButton) timerContainer.findViewById(R.id.resetButton);


        playButton.setOnTouchListener(new OnSwipeTouchListener(mainActivity, playButton){

            @Override
            public void onSwipeLeft() {
                mainActivity.setTimerFragment();
            }

            @Override
            public void onSwipeRight() {
                mainActivity.finish();
            }

            public void onClick(View v){

                ImageButton play = (ImageButton) v;

                if(!timeText.getText().toString().equals("00:00")){

                    if(play.getTag().equals("play")){

                        timer.setUpTimer(timeText);
                        timer.startTimer();
                        play.setImageResource(R.drawable.ic_pause_white_48dp);
                        play.setTag("pause");
                    }

                    else if(play.getTag().equals("pause")){

                        timer.cancelTimer();
                        play.setImageResource(R.drawable.ic_play_arrow_white_48dp);
                        play.setTag("play");
                    }
                }
            }
        });

        resetButton.setOnTouchListener(new OnSwipeTouchListener(mainActivity, resetButton){

            @Override
            public void onSwipeLeft() {
                mainActivity.setTimerFragment();
            }

            @Override
            public void onSwipeRight() {
                mainActivity.finish();
            }

            public void onClick(View v){

                timer.cancelTimer();
                timer.setUpTimer(timeText);
                resetTimerView();
            }
        });

        eraseButton.setOnTouchListener(new OnSwipeTouchListener(mainActivity, eraseButton){

            @Override
            public void onSwipeLeft() {
                mainActivity.setTimerFragment();
            }

            @Override
            public void onSwipeRight() {
                mainActivity.finish();
            }

            public void onClick(View v){

                timer.cancelTimer();
                removeTimerView();
                int posInArray = mainActivity.getTimerArray().indexOf(TimerView.this);
                if(posInArray!=-1){
                    //Toast.makeText(mainActivity, "element no"+ posInArray+".", Toast.LENGTH_SHORT).show();
                    mainActivity.getTimerArray().remove(posInArray);
                    if(mainActivity.getTimerArray().size()==0){
                        mainActivity.findViewById(R.id.tutorial).setVisibility(View.VISIBLE);
                    }

                }
                else{
                    Toast.makeText(mainActivity, "element not in array", Toast.LENGTH_SHORT).show();
                }
            }
        });

        timeText.setOnTouchListener(new OnSwipeTouchListener(mainActivity, timeText){

            @Override
            public void onSwipeLeft(){

                mainActivity.setTimerFragment();
            }
            @Override
            public void onSwipeRight(){

                mainActivity.finish();
            }
        });
    }

    public void removeTimerView(){

        ((LinearLayout)timerContainer.getParent()).removeView(timerContainer);
    }

    public void resetTimerView() {

        timeText.setText(timer.getStartingTime());
        playButton.setImageResource(R.drawable.ic_play_arrow_white_48dp);
        playButton.setTag("play");
    }

    public LinearLayout getTimerContainer(){

        return timerContainer;
    }

    public String getStartingTime(){

        return timer.getStartingTime();
    }

    public void cancelTimer(){

        timer.cancelTimer();
    }

}

