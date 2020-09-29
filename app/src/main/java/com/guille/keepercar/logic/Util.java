package com.guille.keepercar.logic;

import android.util.Log;

import com.guille.keepercar.data.model.Configuration;
import com.guille.keepercar.data.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Guille on 10/14/2015.
 */
public class Util {

    private static User user = null;

    private static HashMap<Integer, String> configMap = new HashMap<>();

    public static String getConfig(Integer key) {
        return configMap.get(key);
    }

    public static void setUser(User user) {
        Util.user = user;
    }

    public static User getUser() {
        if (user == null) {
            return new User();
        }
        return user;
    }

    public static void setConfig(List<Configuration> configMap) {

        for (Configuration c : configMap)
            Util.configMap.put(c.getId(), c.getValue().trim());

    }

    public static void setConfig(Integer key, String value) {

        Util.configMap.put(key, value);
    }

    public static HashMap<Integer, String> getConfigMap() {
        return configMap;
    }

    public static String formarDate(Date date) {

        if (date == null) return " ";
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        return sdf.format(date.getTime());
    }


    public static String formarDate(Calendar calendar) {

        if (calendar == null) return " ";

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        return sdf.format(calendar.getTime());
    }

    public static String formatDate(String date) {

        if (null == date) return " ";

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        return sdf.format(date);
    }

    public static String formarDateForApi(Date date) {

        if (null == date) return " ";

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        return sdf.format(date.getTime());
    }
//
//    public static Date getDate(String date) throws ParseException {
//
//
//        String myFormat = "yyyy-MM-dd"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
////
////        SimpleDateFormat dateFormatOfStringInDB = new SimpleDateFormat("MM/dd/yyyy");
////        Date d1 = dateFormatOfStringInDB.parse(yourDBString);
////
//        return sdf.parse(date);
//    }

    public static Date getDateFromApi(String date) throws ParseException {


        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
//
//        SimpleDateFormat dateFormatOfStringInDB = new SimpleDateFormat("MM/dd/yyyy");
//        Date d1 = dateFormatOfStringInDB.parse(yourDBString);
//
        if(date==null)
            return new Date();

        return sdf.parse(date);
    }

    public static Date getDate(String date) throws ParseException {


        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
//
//        SimpleDateFormat dateFormatOfStringInDB = new SimpleDateFormat("MM/dd/yyyy");
//        Date d1 = dateFormatOfStringInDB.parse(yourDBString);
//
        if(date==null)
            return sdf.parse(new Date().toString());

        return sdf.parse(date);
    }

    public static Long getDiffDays(Date date) {

        Date d = new Date();

//        // Establecer las fechas
//        System.out.println("En dias: " + date);
//        System.out.println("En dias actual: " + d);
        // conseguir la representacion de la fecha en milisegundos
        long milis1 = date.getTime();
        long milis2 = d.getTime();

//        // calcular la diferencia en milisengundos
        long diff = milis2 - milis1;
//
//        // calcular la diferencia en segundos
//        long diffSeconds = diff / 1000;
//
//        // calcular la diferencia en minutos
//        long diffMinutes = diff / (60 * 1000);
//
//        // calcular la diferencia en horas
//        long diffHours = diff / (60 * 60 * 1000);

        // calcular la diferencia en dias
        long diffDays = diff / (24 * 60 * 60 * 1000);
//
//        System.out.println("En milisegundos: " + diff + " milisegundos.");
//        System.out.println("En segundos: " + diffSeconds + " segundos.");
//        System.out.println("En minutos: " + diffMinutes + " minutos.");
//        System.out.println("En horas: " + diffHours + " horas.");
//        System.out.println("En dias: " + diffDays + " dias.");

        return diffDays;
    }

    public static void LOG_D(Object o, String message) {
        Log.d(o.getClass().getName(), message);
    }
}
