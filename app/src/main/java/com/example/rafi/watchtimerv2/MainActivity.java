/*
package com.example.rafi.watchtimer;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
    }
}
*/
package com.example.rafi.watchtimerv2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends Activity/* implements View.OnClickListener*/ {

    boolean returningFromHidden;
    private final ArrayList<CountDownTimer> timerBasico=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if(savedInstanceState!=null){
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(this.getApplicationContext());
            Gson gson = new Gson();
            String json = appSharedPrefs.getString("MyObject", "");
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            ArrayList<String> tiempos= gson.fromJson(json, type);
            LayoutInflater inflater = getLayoutInflater();
            final ViewGroup main= (ViewGroup) findViewById(R.id.main);
            final LinearLayout parent = (LinearLayout) findViewById(R.id.contenedor);
            for (int i=0;i<tiempos.size();i++){
                View timerFrame = inflater.inflate(R.layout.timer, main, false);
                TextView numberFrame = (TextView) timerFrame.findViewById(R.id.numberView);
                TextView originalTime = (TextView) timerFrame.findViewById(R.id.originalTime);
                numberFrame.setText(tiempos.get(i));
                originalTime.setText(tiempos.get(i));
                parent.addView(timerFrame);
            }
        }*/

        View main = findViewById(R.id.main);
        main.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeLeft() {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SetTimer fragment = new SetTimer();
                fragmentTransaction.add(R.id.main, fragment);
                fragmentTransaction.commit();
            }

            public void onSwipeRight() {
                onStop();
                finish();
                System.exit(0);
            }
        });

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        returningFromHidden=true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        ViewGroup nivel0 = (ViewGroup) findViewById(R.id.contenedor);
        ArrayList<String> tiempos = new ArrayList<>();
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        for (int i=0; i<nivel0.getChildCount();i++){
            ViewGroup nivel1= (ViewGroup) nivel0.getChildAt(i);
            ViewGroup nivel2 = (ViewGroup) nivel1.getChildAt(0);
            outer:for (int j=0; j<nivel2.getChildCount();j++) {
                View child = nivel2.getChildAt(j);
                if (child.getTag().equals("originalNumber")) {
                    String tiempo = ((TextView) child).getText().toString();
                    tiempos.add(tiempo);
                    break outer;
                }
            }
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        String json = gson.toJson(tiempos, type);
        prefsEditor.putString("MyObject", json);
        prefsEditor.apply();
    }

    @Override
    protected void onStart(){
        super.onStart();

        ViewGroup main= (ViewGroup) findViewById(R.id.main);
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getApplicationContext());
        if (appSharedPrefs.contains("MyObject") && !returningFromHidden){

            Gson gson = new Gson();
            String json = appSharedPrefs.getString("MyObject", "");
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            ArrayList<String> tiempos = gson.fromJson(json, type);
            LayoutInflater inflater = getLayoutInflater();
//            final ViewGroup main = (ViewGroup) findViewById(R.id.main);
            final LinearLayout parent = (LinearLayout) findViewById(R.id.contenedor);
            for (int i = 0; i < tiempos.size(); i++) {
                View timerFrame = inflater.inflate(R.layout.timer, main, false);
                TextView numberFrame = (TextView) timerFrame.findViewById(R.id.numberView);
                TextView originalTime = (TextView) timerFrame.findViewById(R.id.originalTime);
                numberFrame.setText(tiempos.get(i));
                originalTime.setText(tiempos.get(i));
                parent.addView(timerFrame);
            }
        }
    }




    @Override
    public void onPause()
    {
        super.onPause();
    }


    /*class Dialogo extends DialogFragment {
        private int secondPicker=0;
        private int minutePicker=0;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            super.onCreateDialog(savedInstanceState);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final ViewGroup main= (ViewGroup) findViewById(R.id.main);
            View vista = inflater.inflate(R.layout.content_main,main, false);
            // Inflate and set the layout for the dialog
            builder.setView(vista);
            vista.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
                @Override
                public void onSwipeLeft() {
                    final LinearLayout parent = (LinearLayout) findViewById(R.id.contenedor);
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
                    *//*ViewGroup hijo=(ViewGroup) timerFrame.findViewById(R.id.hijo);
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
                    }*//*
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
    }*/

//    @Override
   /* public void onClick(View v){
        ViewGroup l1 =(ViewGroup) v.getParent();
        ViewGroup l2 = (ViewGroup) l1.getParent();
        ViewGroup l3 = (ViewGroup) l2.getParent();
        final TextView numberView=(TextView) l1.findViewById(R.id.numberView);
        final ImageButton play = (ImageButton) l1.findViewById(R.id.play);
        final TextView originalTime=(TextView) l1.findViewById(R.id.originalTime);
        switch (v.getTag().toString()){
            case("borrar"):
                if(!timerBasico.isEmpty()) {
                    try {
                        timerBasico.set((Integer) l2.getTag(), null);
                        l3.removeView(l2);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    l3.removeView(l2);
                }
                break;
            case("play"):
                String tiempo=numberView.getText().toString();
                final String[] arrayTiempo = tiempo.split(":");
                int miliSegundos= Integer.parseInt(arrayTiempo[1])*1000;
                int miliMinutos=Integer.parseInt(arrayTiempo[0])*1000*60;
                final int[] arrayTiempoFinal= new int[2];
                arrayTiempoFinal[0]=Integer.parseInt(arrayTiempo[0]);
                arrayTiempoFinal[1]=Integer.parseInt(arrayTiempo[1]);
                int tiempoTotal = miliMinutos+miliSegundos;
                Outer: if (l2.getTag().equals("nuevo")) {
                    for (int j=0; j<timerBasico.size();j++){
                        if(timerBasico.get(j)==null){
                            setearTimer(j, tiempoTotal, timerBasico, arrayTiempoFinal, numberView, play);
                            l2.setTag(timerBasico.indexOf(timerBasico.get(j)));
                            break Outer;
                        }
                    }
                    addearTimer(tiempoTotal,timerBasico,arrayTiempoFinal,numberView,play);
                    l2.setTag(timerBasico.size()-1);
                }
                else{
                    setearTimer((Integer) l2.getTag(),tiempoTotal,timerBasico,arrayTiempoFinal,numberView,play);
                }
                play.setImageResource(R.drawable.ic_media_pause);
                play.setTag("pause");
                break;
            case ("pause"):
                timerBasico.get((Integer) l2.getTag()).cancel();
                play.setImageResource(R.drawable.ic_media_play);
                play.setTag("play");
                break;
            case ("reset"):
                if(play.getTag().toString().equals("pause") && !l2.getTag().toString().equals("nuevo"))  {
                    timerBasico.get((Integer) l2.getTag()).cancel();
                }
                play.setImageResource(R.drawable.ic_media_play);
                tiempo=originalTime.getText().toString();
                numberView.setText(tiempo);
                play.setTag("play");
                break;
        }
    }*/

   /* private void setearTimer (int pos,int mTiempo, ArrayList<CountDownTimer> timerBasico,final int[] arrayTiempoFinal,final TextView numberView, final ImageButton play){
        final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        timerBasico.set(pos, new CountDownTimer(mTiempo,100) {
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
        }.start());

    }

    private void addearTimer (int mTiempo, ArrayList<CountDownTimer> timerBasico,final int[] arrayTiempoFinal,final TextView numberView, final ImageButton play){

        final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        timerBasico.add(new CountDownTimer(mTiempo, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = MilliConversions.milliToMinSec(millisUntilFinished)[0];
                long seconds = MilliConversions.milliToMinSec(millisUntilFinished)[1];
                String message = String.format("%02d:%02d", minutes, seconds);
                numberView.setText(message);
            }

            @Override
            public void onFinish() {
                String message = "00:00";
                numberView.setText(message);
                v.vibrate(250);
            }
        }.start());

    }*/




    /*private void setearTimer (int pos,int mTiempo, ArrayList<CountDownTimer> timerBasico,final int[] arrayTiempoFinal,final TextView numberView, final ImageButton play){
        final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        timerBasico.set(pos, new CountDownTimer(mTiempo, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                if (arrayTiempoFinal[1] > 0) {
                    arrayTiempoFinal[1]--;
                    if (arrayTiempoFinal[1] > 9) {
                        String message = getString(R.string.timeOverTen, arrayTiempoFinal[0], arrayTiempoFinal[1]);
                        numberView.setText(message);
                    } else {
                        String message = getString(R.string.timeUnderTen, arrayTiempoFinal[0], arrayTiempoFinal[1]);
                        numberView.setText(message);
                        if (arrayTiempoFinal[0] == 0 && arrayTiempoFinal[1] == 0) {
                            v.vibrate(250);
                            *//*if(!isVisible){
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            }*//*
                        }
                    }
                } else if (arrayTiempoFinal[1] == 0 && arrayTiempoFinal[0] > 0) {
                    arrayTiempoFinal[1] = 59;
                    arrayTiempoFinal[0]--;
                    String message = getString(R.string.timeOverTen, arrayTiempoFinal[0], arrayTiempoFinal[1]);
                    numberView.setText(message);
                }
            }

            @Override
            public void onFinish() {
//                play.setImageResource(R.drawable.ic_media_play); PONER ICONO STOP
            }
        }.start());
    }

    private void addearTimer (int mTiempo, ArrayList<CountDownTimer> timerBasico,final int[] arrayTiempoFinal,final TextView numberView, final ImageButton play) {
        final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        timerBasico.add(new CountDownTimer(mTiempo, 1001) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (arrayTiempoFinal[1] > 0) {
                    arrayTiempoFinal[1]--;
                    if (arrayTiempoFinal[1] > 9) {
                        String message = getString(R.string.timeOverTen, arrayTiempoFinal[0], arrayTiempoFinal[1]);
                        numberView.setText(message);
                    } else {
                        String message = getString(R.string.timeUnderTen, arrayTiempoFinal[0], arrayTiempoFinal[1]);
                        numberView.setText(message);
                        if (arrayTiempoFinal[0] == 0 && arrayTiempoFinal[1] == 0) {
                            v.vibrate(250);
                            *//*if(!isVisible){
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                            }*//*
                        }
                    }

                } else if (arrayTiempoFinal[1] == 0 && arrayTiempoFinal[0] > 0) {
                    arrayTiempoFinal[1] = 59;
                    arrayTiempoFinal[0]--;
                    String message = getString(R.string.timeOverTen, arrayTiempoFinal[0], arrayTiempoFinal[1]);
                    numberView.setText(message);
                }
            }

            @Override
            public void onFinish() {
//                play.setImageResource(R.drawable.ic_media_play);
            }
        }.start());
    }*/

    /*public void newTimerView(String time){

        LayoutInflater inflater = getLayoutInflater();
        LinearLayout parent = (LinearLayout) findViewById(R.id.contenedor);
        ViewGroup main= (ViewGroup) findViewById(R.id.main);
        ViewGroup timerFrame =(ViewGroup) inflater.inflate(R.layout.timer, main, false);
        TextView numberFrame = (TextView) timerFrame.findViewById(R.id.numberView);
        TextView originalTime = (TextView) timerFrame.findViewById(R.id.originalTime);
        numberFrame.setText(time);
        originalTime.setText(time);
        parent.addView(timerFrame);

    }*/
}

