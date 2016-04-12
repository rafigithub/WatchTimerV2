package com.example.rafi.watchtimerv2;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Rafi on 25/02/2016.
 */

//Class that creates a graphical interface for a timer.
public class TimerView extends LinearLayout{

    //Create a Linear Layout that will contain the template from "timer.xml".
    private LinearLayout timer;
    //Create a Linear Layout that will contain the timer's parent.
    private LinearLayout parent;

    /*ImageButton playButton;
    ImageButton resetButton;
    ImageButton eraseButton;
    TextView numberView;*/

    //The constructor takes an initial time and a parent to attach the timer to.
    public TimerView(final Context context, String sTime, LinearLayout parent){

        //Get the context
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        //Inflate the template into the timer layout and attach it to parent
        this.timer = (LinearLayout) inflater.inflate(R.layout.timer,parent,true);
        this.parent = parent;
        //Then set the timer to the initial time
        TextView initialTime = (TextView) timer.findViewById(R.id.numberView);
        initialTime.setText(sTime);
    }
}

    public void removeTimer(){


    }

/*
    playButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {

            ImageButton play=(ImageButton) v;

            if(play.getTag().equals("play")){
                countDownTimerSetup(timeInMillis, 100,context,numberView);
                countDownTimer.start();
                play.setImageResource(R.drawable.ic_media_pause);
                play.setTag("pause");
            }

            else if(play.getTag().equals("pause")){
                countDownTimer.cancel();
                play.setImageResource(R.drawable.ic_media_play);
                play.setTag("play");
            }
        }
    });

    eraseButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {

            ImageButton erase=(ImageButton) v;

            try {
                countDownTimer.cancel();
            }
            catch (Exception e){
                System.out.println("El relajo era porque no existe el timer");
            }
            ViewGroup parent = (ViewGroup) findViewById(R.id.contenedor);
            ViewGroup toRemove = (ViewGroup) erase.getParent().getParent();
            parent.removeView(toRemove);

        }
    });

    resetButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {

            ImageButton reset=(ImageButton) v;


        }
    });*/
