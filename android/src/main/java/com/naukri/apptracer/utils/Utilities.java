package com.naukri.apptracer.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.AlarmManager.RTC_WAKEUP;

/**
 * Created by vishnu.anand on 5/31/2016.
 */
public class Utilities {

    public static void setInexactAlarm(Context context, Class<? extends Service> cls,
                                       long interval) {

        Intent ServiceIntent = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,
                ServiceIntent, PendingIntent.FLAG_NO_CREATE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getService(context, 0, ServiceIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.setInexactRepeating(RTC_WAKEUP,
                    System.currentTimeMillis(), interval, pendingIntent);
        }
    }

    public static void clearJobAlarm(Context context, Class<? extends Service> cls) {
        Intent ServiceIntent = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,
                ServiceIntent, PendingIntent.FLAG_NO_CREATE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    public static String getCombineScreenNameAndStartLoadTime(String screenName, long startLoadTime) {
        return screenName + "_" + startLoadTime;
    }

    public static long getTotalLoadTime(long startLoadTime, long endLoadTime) {
        return endLoadTime - startLoadTime;
    }

    public static String getAppVersionCode(Context context) {
        try {
            return String.valueOf(context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
            return ""; //not connected
        if (info.getType() == ConnectivityManager.TYPE_WIFI)
            return "WIFI";
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int networkType = info.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                    return "4G";
                default:
                    return "";
            }
        }
        return "";
    }

    public static void setUserIdSharedPreference(Context context, String userId) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Constants.SHARED_PREFERENCE_USER_ID, userId);
        editor.commit();
    }

    public static String getUserIdFromPrefernce(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedpreferences.getString(Constants.SHARED_PREFERENCE_USER_ID, "0");
    }

    public static void clearSharedPreferences(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static String getCurrentDateTime(Long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        Date date = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
