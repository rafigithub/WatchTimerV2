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

    public static long minSecToMilli(long[] minSec){
        return minSec[1]*1000+minSec[0]*60*1000;
    }

    public static long[] stringToMinSec(String timeString){

        String[] timeStringSplit = timeString.split(":");
        long minutes = Integer.parseInt(timeStringSplit[0]);
        long seconds = Integer.parseInt(timeStringSplit[1]);
        long[] timeMinSec = {minutes, seconds};
        return timeMinSec;
    }

    public static long stringToMilli(String timeString){

        return MilliConversions.minSecToMilli(MilliConversions.stringToMinSec(timeString));
    }

    public static String milliToString (long millis){

        long[] minSec = MilliConversions.milliToMinSec(millis);
        String milliToString = String.format("%02d:%02d",minSec[0],minSec[1]);
        return milliToString;
    }


}
