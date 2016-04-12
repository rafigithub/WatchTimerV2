package com.example.rafi.watchtimerv2;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    private LinearLayout parent;

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

    private String getMessage(){
        return this.message;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.content_main,container, false);
        vista.setOnTouchListener(new OnSwipeTouchListener(getActivity().getApplicationContext()) {
            @Override
            public void onSwipeLeft() {
                setMessage(String.format("%02d:%02d",getMinutePicker(),getSecondPicker()));
                parent = (LinearLayout) getActivity().findViewById(R.id.contenedor);
                TimerView timerView= new TimerView(getActivity(),message, parent);
                /*if (getSecondPicker() < 10) {
                    setMessage(getString(R.string.timeUnderTen, getMinutePicker(), getSecondPicker()));
                    ViewGroup contenedor= (ViewGroup) getActivity().findViewById(R.id.contenedor);
                    CreateTimerView timerView= new CreateTimerView(getActivity(),message);
                    timerView.attachTimerToView(contenedor);
                } else {
                    setMessage(getString(R.string.timeOverTen, getMinutePicker(), getSecondPicker()));
                    ViewGroup contenedor= (ViewGroup) getActivity().findViewById(R.id.contenedor);
                    CreateTimerView timerView= new CreateTimerView(getActivity(),message);
                    timerView.attachTimerToView(contenedor);
                }*/
                dismiss();
            }
            public void onSwipeRight() {
                dismiss();
            }
        });
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

    return vista;
    }

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
}
