package com.example.adminandroidgroup6.support;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeCurrent {

    public static String date(){
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(" HH:mm:ss \t dd/MM/yyyy");
        String dateString = df.format(date);
        return dateString;
    }
}
