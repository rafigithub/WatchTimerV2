package com.example.rafi.watchtimerv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by Rafi on 25/02/2016.
 */

//Class that creates a graphical interface for a timer.
public class TimerView extends LinearLayout{

    //Create a Linear Layout that will contain the template from "timer.xml".
    private LinearLayout timer;
    //Create a Linear Layout that will contain the timer's parent.
    private LinearLayout parent;
    //And variables to store the timers' buttons and displays.
    private ImageButton playButton;
    private ImageButton resetButton;
    private ImageButton eraseButton;
    private TextView time;
    //Finally, a variable to store the starting time for the timer.
    private String startingTime;

    //The constructor takes an initial time and a parent to attach the timer to.
    public TimerView(final Context context, String startingTime, LinearLayout parent){

        //Get the context
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        //Inflate the template into the timer layout and attach it to parent
        this.timer = (LinearLayout) inflater.inflate(R.layout.timer,parent,false);
        parent.addView(timer);
        this.parent = parent;
        //Then set the timer to the initial time
        this.time = (TextView) timer.findViewById(R.id.numberView);
        time.setText(startingTime);
        //And set the starting time variable as well
        this.startingTime = startingTime;
        //Set the buttons of the template to this object button variables
        this.playButton = (ImageButton) timer.findViewById(R.id.playButton);
        this.eraseButton = (ImageButton) timer.findViewById(R.id.eraseButton);
        this.resetButton = (ImageButton) timer.findViewById(R.id.resetButton);
    }


    public ImageButton getPlayButton(){

        return playButton;
    }
    public ImageButton getResetButton(){

        return resetButton;
    }
    public ImageButton getEraseButton(){

        return eraseButton;
    }



    public void removeTimerView(){

        parent.removeView(timer);
    }

    public void updateTimerView(String currentTime){

        time.setText(currentTime);
    }

    public void resetTimerView() {

        time.setText(startingTime);
    }

    public TextView getTime(){

        return time;
    }

}

