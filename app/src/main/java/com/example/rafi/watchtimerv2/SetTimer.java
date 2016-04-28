package com.example.rafi.watchtimerv2;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

/**
 * Created by Rafi on 20/02/2016.
 */
public class SetTimer extends DialogFragment {

    private int secondPicker=0;
    private int minutePicker=0;
    private String message;

    private void setSecondPicker(int secondPicker){
        this.secondPicker=secondPicker;
    }

    private int getSecondPicker(){
        return this.secondPicker;
    }

    private void setMinutePicker(int minutePicker){
        this.minutePicker=minutePicker;
    }

    private int getMinutePicker(){
        return this.minutePicker;
    }

    private void setMessage(String message){
        this.message=message;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the dialog in front of the main activity
        View vista = inflater.inflate(R.layout.content_main,container, false);
        //Set a listener and handler for right and left swipes.
        setCreateTimerListener(vista);
        /*vista.setOnTouchListener(new OnSwipeTouchListener(getActivity().getApplicationContext()) {
            @Override
            public void onSwipeLeft() {
                createTimer();
                dismiss();
            }
            public void onSwipeRight() {
                dismiss();
            }
        });*/
        //Create and initialize the number pickers and their listeners and handlers.
        NumberPicker mMinutePicker = (NumberPicker) vista.findViewById(R.id.minute_picker);
        NumberPicker mSecondPicker = (NumberPicker) vista.findViewById(R.id.second_picker);
        numberPickerSetup(mMinutePicker,99,0,true);
        numberPickerSetup(mSecondPicker, 59, 0, true);

        mSecondPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setSecondPicker(newVal);
            }
        });
        mMinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setMinutePicker(newVal);
            }
        });

        setCreateTimerListener(mMinutePicker);
        setCreateTimerListener(mSecondPicker);

       /* mMinutePicker.setOnTouchListener(new OnSwipeTouchListener(getActivity().getApplicationContext()) {
            @Override
            public void onSwipeLeft() {
                createTimer();
                dismiss();
            }
            public void onSwipeRight() {
                dismiss();
            }
        });

        mSecondPicker.setOnTouchListener(new OnSwipeTouchListener(getActivity().getApplicationContext()) {
            @Override
            public void onSwipeLeft() {
                createTimer();
                dismiss();
            }
            public void onSwipeRight() {
                dismiss();
            }
        });*/

    return vista;
    }

    //Overloaded functions to set up number pickers.
    private void numberPickerSetup(NumberPicker numberPicker, int maxValue, int minValue, int setValue, boolean wrapWheel){

        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);
        numberPicker.setWrapSelectorWheel(wrapWheel);
        numberPicker.setValue(setValue);
        numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
    }

    private void numberPickerSetup(NumberPicker numberPicker, int maxValue, int minValue, boolean wrapWheel){

        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);
        numberPicker.setWrapSelectorWheel(wrapWheel);
        numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d",value);
            }
        });
    }

    private void numberPickerSetup(NumberPicker numberPicker, int maxValue, int minValue){

        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);
        numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
    }

    private void setCreateTimerListener(View view){

        view.setOnTouchListener(new OnSwipeTouchListener(getActivity(),view){

            @Override
            public void onSwipeLeft(){

                MainActivity mainActivity = (MainActivity)getActivity();
                setMessage(String.format("%02d:%02d",getMinutePicker(),getSecondPicker()));
                LinearLayout parent = (LinearLayout) mainActivity.findViewById(R.id.contenedor);
                Timer timer = new Countdown(mainActivity, message);
                TimerView timerView = new TimerView(mainActivity, timer, parent);
                mainActivity.getTimerArray().add(timerView);
                if(mainActivity.getTimerArray().size()==1){
                    mainActivity.findViewById(R.id.tutorial).setVisibility(View.GONE);
                }
                dismiss();
            }

            @Override
            public void onSwipeRight(){

                dismiss();
            }
        });
    }
}
