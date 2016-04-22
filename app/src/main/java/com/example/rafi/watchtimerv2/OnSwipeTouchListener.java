package com.example.rafi.watchtimerv2;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Rafi on 20/02/2016.
 */
public class OnSwipeTouchListener implements View.OnTouchListener {//CREA UNA CLASE NUEVA QUE IMPLEMENTA ONTOUCHLISTENER
    private final GestureDetector gestureDetector;  //DECLARA OBJETO DE TIPO GESTUREDETECTOR
    //private Context context;
    public OnSwipeTouchListener(Context context) {      //CONSTRUCTOR
        //this.context = context;
        gestureDetector = new GestureDetector(context, new GestureListener());
    }
    public void onSwipeLeft() {//METODO VACIO
    }
    public void onSwipeRight() {//METODO VACIO
    }
    /*public void onClick(){

        Toast.makeText(context,"estas tocando", Toast.LENGTH_SHORT).show();
    }*/
    public boolean onTouch(View v, MotionEvent event) {//METODO REQUERIDO AL IMPLEMENTAR ONTOUCHLISTENER
        return gestureDetector.onTouchEvent(event);
    }
    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_MIN_DISTANCE_THRESHOLD_FOR_SWIPE = 50;
        //private static final int SWIPE_MAX_DISTANCE_THRESHOLD_FOR_TOUCH = 10;
        private static final int SWIPE_VELOCITY_THRESHOLD = 50;
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_MIN_DISTANCE_THRESHOLD_FOR_SWIPE && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0)
                    onSwipeRight();
                else
                    onSwipeLeft();
                return true;
            }
           /* else if (Math.abs(distanceX) < SWIPE_MAX_DISTANCE_THRESHOLD_FOR_TOUCH){
                    onClick();
                return true;
            }*/
            return false;
        }
    }
}