package com.example.rafi.watchtimerv2;

import java.util.concurrent.TimeUnit;

/**
 * Created by Rafi on 02/03/2016.
 */
public class MilliConversions {

    public static long[] milliToMinSec(long millis){

        long[] result=new long[2];
        result[1]=TimeUnit.MILLISECONDS.toSeconds(millis) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
        result[0]=TimeUnit.MILLISECONDS.toMinutes(millis);

        return result;
    }

    public static long minSecToMilli(long minutes, long seconds){
        return seconds*1000+minutes*60*1000;
    }


}
