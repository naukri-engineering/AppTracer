package com.naukri.apptracer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.naukri.apptracer.utils.Utilities;

/**
 * Created by vishnu.anand on 6/2/2016.
 */
public class DatabaseHelper {

    public static SQLiteDatabase getReadableDatabase(Context context) {
        return AppTracerDatabase.getInstance(context).getReadableDatabase();
    }

    public static SQLiteDatabase getWritableDatabase(Context context) {
        return AppTracerDatabase.getInstance(context).getWritableDatabase();
    }

    public static Cursor getScreenLoadTime(Context context) {
        SQLiteDatabase db = getReadableDatabase(context);

        Cursor cursor = db.query(AppTracerDatabase.TABLE_SCREEN_LOAD_TIME, null, null, null, null, null, null, null);

        return cursor;
    }

    public static Cursor getApisCursorOfParticularScreenFromDb(Context context, String screenInstanceId) {
        SQLiteDatabase db = getReadableDatabase(context);

        Cursor cursor = db.query(AppTracerDatabase.TABLE_API_LOAD_TIME, null,
                AppTracerDatabase.SCREEN_INSTANCE_ID + " = ? ", new String[]{screenInstanceId},
                null, null, null, null);

        return cursor;
    }

    public static long getApiStartLoadTimeFromDb(Context context, String screenInstanceId, String apiName) {
        SQLiteDatabase db = getReadableDatabase(context);

        Cursor cursor = db.query(AppTracerDatabase.TABLE_API_LOAD_TIME, null,
                AppTracerDatabase.SCREEN_INSTANCE_ID + " = ? AND "
                        + AppTracerDatabase.API_NAME + " = ? ", new String[]{screenInstanceId, apiName},
                null, null, null, null);

        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int sourceIndex = cursor.getColumnIndex(AppTracerDatabase.START_LOAD_TIME);
                long startLoadTime = cursor.getLong(sourceIndex);
                return startLoadTime;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return 0L;
    }

    public static int updateApiEndLoadTimeData(Context context, Long EndTimeStamp, Long totalLoadTime, String screenInstanceId, String apiName) {
        SQLiteDatabase db = getWritableDatabase(context);

        ContentValues contentValues = new ContentValues();
        contentValues.put(AppTracerDatabase.END_LOAD_TIME, EndTimeStamp);
        contentValues.put(AppTracerDatabase.TOTAL_LOAD_TIME, totalLoadTime);

        return db.update(AppTracerDatabase.TABLE_API_LOAD_TIME, contentValues,
                AppTracerDatabase.SCREEN_INSTANCE_ID + " = ? AND "
                        + AppTracerDatabase.API_NAME + " = ? ", new String[]{screenInstanceId, apiName});
    }

    public static void insertScreenLoadTimeData(Context context, String screenName, Long startLoadTime, Long endLoadTime, String screenInstanceId) {
        SQLiteDatabase db = getWritableDatabase(context);

        ContentValues contentValues = new ContentValues();
        contentValues.put(AppTracerDatabase.SCREEN_NAME, screenName);
        contentValues.put(AppTracerDatabase.SCREEN_INSTANCE_ID, screenInstanceId);
        contentValues.put(AppTracerDatabase.START_LOAD_TIME, startLoadTime);
        contentValues.put(AppTracerDatabase.END_LOAD_TIME, endLoadTime);
        contentValues.put(AppTracerDatabase.CURRENT_TIMESTAMP, System.currentTimeMillis());
        contentValues.put(AppTracerDatabase.TOTAL_LOAD_TIME, Utilities.getTotalLoadTime(startLoadTime, endLoadTime));

        db.insert(AppTracerDatabase.TABLE_SCREEN_LOAD_TIME, null, contentValues);

    }

    public static void insertApiLoadTimeData(Context context, String apiName, Long startLoadTime, String screenInstanceId) {
        SQLiteDatabase db = getWritableDatabase(context);

        ContentValues contentValues = new ContentValues();
        contentValues.put(AppTracerDatabase.API_NAME, apiName);
        contentValues.put(AppTracerDatabase.SCREEN_INSTANCE_ID, screenInstanceId);
        contentValues.put(AppTracerDatabase.START_LOAD_TIME, startLoadTime);
        contentValues.put(AppTracerDatabase.END_LOAD_TIME, 0L);
        contentValues.put(AppTracerDatabase.CURRENT_TIMESTAMP, System.currentTimeMillis());
        contentValues.put(AppTracerDatabase.TOTAL_LOAD_TIME, 0L);

        db.insert(AppTracerDatabase.TABLE_API_LOAD_TIME, null, contentValues);
    }

    public static void deleteAllDataFromTable(Context context, String tableName) {
        SQLiteDatabase db = getWritableDatabase(context);
        db.delete(tableName, null, null);
    }

    public static void deleteScreenLoadTimeData(Context context, Long timeStamp)
    {
        SQLiteDatabase db = getWritableDatabase(context);
        db.delete(AppTracerDatabase.TABLE_SCREEN_LOAD_TIME, AppTracerDatabase.CURRENT_TIMESTAMP + " < ? ",
                new String[]{String.valueOf(timeStamp)});
    }

    public static void deleteApiLoadTimeData(Context context,Long timeStamp)
    {
        SQLiteDatabase db = getWritableDatabase(context);
        db.delete(AppTracerDatabase.TABLE_API_LOAD_TIME, AppTracerDatabase.CURRENT_TIMESTAMP + " < ? "
                , new String[]{String.valueOf(timeStamp)});
    }
}
