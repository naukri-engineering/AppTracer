package com.naukri.apptracer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vishnu.anand on 6/2/2016.
 */
public class AppTracerDatabase extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    private static AppTracerDatabase mInstance = null;

    private static final String DATABASE_NAME = "PageLoadTimeDatabase";

    public static final String TABLE_SCREEN_LOAD_TIME = "AppTracer";
    public static final String TABLE_API_LOAD_TIME = "ApiLoadTime";

    public static String ID = "_id";
    public static String SCREEN_NAME = "screen_name";
    public static String SCREEN_INSTANCE_ID = "screen_instance_id";
    public static String API_NAME = "api_name";
    public static String START_LOAD_TIME = "start_load_time";
    public static String END_LOAD_TIME = "end_load_time";
    public static String TOTAL_LOAD_TIME = "total_load_time";
    public static String CURRENT_TIMESTAMP = "cur_timestamp";

    private String CREATE_TABLE_SCREEN_LOAD_TIME = "CREATE TABLE " + TABLE_SCREEN_LOAD_TIME + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SCREEN_NAME + " TEXT,"
            + SCREEN_INSTANCE_ID + " TEXT,"
            + START_LOAD_TIME + " INTEGER,"
            + END_LOAD_TIME + " INTEGER,"
            + CURRENT_TIMESTAMP + " INTEGER,"
            + TOTAL_LOAD_TIME + " INTEGER )";

    private String CREATE_TABLE_API_LOAD_TIME = "CREATE TABLE " + TABLE_API_LOAD_TIME + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SCREEN_INSTANCE_ID + " TEXT,"
            + API_NAME + " TEXT,"
            + START_LOAD_TIME + " INTEGER,"
            + END_LOAD_TIME + " INTEGER,"
            + CURRENT_TIMESTAMP + " INTEGER,"
            + TOTAL_LOAD_TIME + " INTEGER )";


    public AppTracerDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static AppTracerDatabase getInstance(Context activityContext) {

        if (mInstance == null) {
            mInstance = new AppTracerDatabase(activityContext.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_SCREEN_LOAD_TIME);
        db.execSQL(CREATE_TABLE_API_LOAD_TIME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_API_LOAD_TIME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCREEN_LOAD_TIME);

        onCreate(db);
    }

}
