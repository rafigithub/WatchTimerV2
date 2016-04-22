package com.example.rafi.watchtimerv2;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Rafi on 20/02/2016.
 */
public class OnSwipeTouchListener implements View.OnTouchListener {//CREA UNA CLASE NUEVA QUE IMPLEMENTA ONTOUCHLISTENER
    private final GestureDetector gestureDetector;  //DECLARA OBJETO DE TIPO GESTUREDETECTOR
    private View view;
    public OnSwipeTouchListener(Context context, View v) {      //CONSTRUCTOR overloaded con view
        this.view = v;
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public OnSwipeTouchListener(Context context) {      //CONSTRUCTOR overloaded 2
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public void onSwipeLeft() {

    }

    public void onSwipeRight() {

    }
    public void onClick(View view){

    }
    public boolean onTouch(View v, MotionEvent event) {//METODO REQUERIDO AL IMPLEMENTAR ONTOUCHLISTENER
        return gestureDetector.onTouchEvent(event);
    }
    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_DISTANCE_THRESHOLD = 50;
        private static final int SWIPE_VELOCITY_THRESHOLD = 50;
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0)
                    onSwipeRight();
                else
                    onSwipeLeft();
                return true;
            }
            return false;
        }
        @Override
        public boolean onSingleTapUp(MotionEvent e){
            onClick(view);
            return true;
        }
    }
}