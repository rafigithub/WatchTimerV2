package com.example.rafi.watchtimerv2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * Created by Rafi on 20/02/2016.
 */
public class Dialogo extends DialogFragment {

    private int secondPicker=0;
    private int minutePicker=0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final ViewGroup main= (ViewGroup) getView().findViewById(R.id.main);
        View vista = inflater.inflate(R.layout.content_main,main, false);
        // Inflate and set the layout for the dialog
        builder.setView(vista);
        vista.setOnTouchListener(new OnSwipeTouchListener(getActivity().getApplicationContext()) {
            @Override
            public void onSwipeLeft() {
                final LinearLayout parent = (LinearLayout) getView().findViewById(R.id.contenedor);
                LayoutInflater inflater = getActivity().getLayoutInflater();
                ViewGroup timerFrame =(ViewGroup) inflater.inflate(R.layout.timer, main, false);
                TextView numberFrame = (TextView) timerFrame.findViewById(R.id.numberView);
                TextView originalTime = (TextView) timerFrame.findViewById(R.id.originalTime);
                if (secondPicker < 10) {
                    String message = getString(R.string.timeUnderTen, minutePicker, secondPicker);
                    numberFrame.setText(message);
                    originalTime.setText(message);
                } else {
                    String message = getString(R.string.timeOverTen, minutePicker, secondPicker);
                    numberFrame.setText(message);
                    originalTime.setText(message);
                }
                parent.addView(timerFrame);
                    /*ViewGroup hijo=(ViewGroup) timerFrame.findViewById(R.id.hijo);
                    for(int i=0;i<hijo.getChildCount();i++){
                        View child= hijo.getChildAt(i);
                        child.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
                            @Override
                            public void onSwipeLeft() {
                                Dialogo fragment = new Dialogo();
                                fragment.show(getFragmentManager(), "string_cualquiera");
                            }
                            public void onSwipeRight() {
                                onStop();
                                finish();
                                System.exit(0);
                            }
                        });
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
        numberPickerSetup(mSecondPicker,59,0,1,true);

        mSecondPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                secondPicker = newVal;
            }
        });
        mMinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minutePicker = newVal;
            }
        });
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        return alert;
    }

    private void numberPickerSetup(NumberPicker numberPicker, int maxValue, int minValue, int setValue, boolean wrapWheel){

        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);
        numberPicker.setWrapSelectorWheel(wrapWheel);
        numberPicker.setValue(setValue);
    }

    private void numberPickerSetup(NumberPicker numberPicker, int maxValue, int minValue, boolean wrapWheel){

        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);
        numberPicker.setWrapSelectorWheel(wrapWheel);
    }

    private void numberPickerSetup(NumberPicker numberPicker, int maxValue, int minValue){

        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);
    }




}
