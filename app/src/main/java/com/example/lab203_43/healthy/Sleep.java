package com.example.lab203_43.healthy;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sleep implements Serializable {
    private int id;
    private String date;
    private String toBedTime;
    private String awakeTime;
    private String sleepTime;

    public Sleep(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getToBedTime() {
        return toBedTime;
    }

    public void setToBedTime(String toBedTime) {
        this.toBedTime = toBedTime;
    }

    public String getAwakeTime() {
        return awakeTime;
    }

    public void setAwakeTime(String awakeTime) {
        this.awakeTime = awakeTime;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }

    public void sleeptimeCal(){
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date toBed = format.parse(toBedTime);
            Date awake = format.parse(awakeTime);
            long difference = 0;
            boolean after = false;
            if (toBed.after(awake))
            {
                difference = ((awake.getTime() - toBed.getTime()) / 1000) / 60;
                after = true;
            }
            else if (toBed.before(awake))
            {
                difference = ((toBed.getTime() - awake.getTime()) / 1000) / 60;
            }
            difference = Math.abs(difference);
            int hour = (int) difference / 60;
            if (after)
            {
                hour = 24 - hour;
            }
            int minute = (int) difference % 60;
            sleepTime = String.format("%02d", hour) + ":" + String.format("%02d", minute);
        }
        catch (Exception e)
        {
            Log.d("USER", e.getMessage());
        }
    }
}
