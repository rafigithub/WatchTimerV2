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

/**
 * Created by Rafi on 25/02/2016.
 */

//Class that creates a graphical interface for a timer.
public class TimerView extends LinearLayout{

    //Take the template from "timer.xml"
    private LinearLayout timer;
    //And the layout where it will be placed inside the main activity ("activity_main.xml")
    private LinearLayout contenedor = (LinearLayout) findViewById(R.id.contenedor);
    /*private String sBaseTime;
    ImageButton playButton;
    ImageButton resetButton;
    ImageButton eraseButton;
    TextView numberView;*/


    public TimerView(final Context context, String sTime){

        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        this.timer = (LinearLayout) inflater.inflate(R.layout.timer,contenedor,false);
        this.sBaseTime=sTime;
        String[] auxTime=sTime.split(":");
        int seconds=Integer.parseInt(auxTime[1]);
        int minutes=Integer.parseInt(auxTime[0]);
        final int timeInMillis= seconds*1000+minutes*1000*60;

        this.playButton =(ImageButton) timer.findViewById(R.id.play);
        this.eraseButton=(ImageButton) timer.findViewById(R.id.borrarTimer);
        this.resetButton=(ImageButton) timer.findViewById(R.id.reset);
        this.numberView =(TextView) timer.findViewById(R.id.numberView);

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
        });
//        countDownTimerSetup(this.countDownTimer,timeInMillis,1000);
    }
    /*public static String timeToString(Integer minutes, Integer seconds){
        return minutes.toString()+":"+seconds.toString();
    }

    public static int[] stringToTime(String string){
        int[] auxInt = new int[2];
        String[] auxString= string.split(":");
        auxInt[0]=Integer.parseInt(auxString[0]);
        auxInt[1]=Integer.parseInt(auxString[1]);
        return auxInt;
    }*/

    public void attachTimerToView(ViewGroup parent){
        TextView numberFrame = (TextView) timer.findViewById(R.id.numberView);
        TextView originalTime = (TextView) timer.findViewById(R.id.originalTime);
        numberFrame.setText(sBaseTime);
        originalTime.setText(sBaseTime);
        parent.addView(timer);
    }

    /*public void setCurrentTime(String currentTime){
        TextView numberFrame= (TextView) timer.findViewById(R.id.numberView);
        numberFrame.setText(currentTime);
    }

    public void setOriginalTime(){
        TextView numberFrame= (TextView) timer.findViewById(R.id.numberView);
        numberFrame.setText(sBaseTime);
    }*/

    @Override
    public void countDownTimerSetup(long time, long tick, Context context,final TextView numberView) {

        final Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        countDownTimer = new CountDownTimer(time,tick) {
            @Override
            public void onTick(long millisUntilFinished) {

                long minutes=MilliConversions.milliToMinSec(millisUntilFinished)[0];
                long seconds=MilliConversions.milliToMinSec(millisUntilFinished)[1];
                String message=String.format("%02d:%02d",minutes,seconds);
                numberView.setText(message);

            }

            @Override
            public void onFinish() {

                String message="00:00";
                numberView.setText(message);
                v.vibrate(250);

            }
        };
    }


}
