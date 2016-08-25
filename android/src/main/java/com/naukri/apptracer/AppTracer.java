package com.naukri.apptracer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.naukri.apptracer.database.DatabaseHelper;
import com.naukri.apptracer.database.AppTracerDatabase;
import com.naukri.apptracer.service.AppTracerService;
import com.naukri.apptracer.utils.Constants;
import com.naukri.apptracer.utils.Utilities;


/**
 * Created by vishnu.anand on 6/21/2016.
 */
public final class AppTracer {

    private static AppTracer ourInstance;
    private String mScreenLoadTimeAppId;
    private String mScreenLoadTimeUrl;
    private Long mScreenLoadTimeInterval;
    private boolean mCleanOnError;
    private Context mContext;


    public static AppTracer getInstance() {
        if (ourInstance == null) {
            ourInstance = new AppTracer();
        }
        return ourInstance;
    }

    private AppTracer() {
    }

    public void initLoadTime(Context context, String appId, String url, Long interval, boolean cleanOnError) {

        mContext = context;
        mCleanOnError = cleanOnError;
        validatePreconditions(appId, url);
        setScreenLoadtimeInterval(interval);
        AppTracerDatabase.getInstance(context);
        Utilities.setInexactAlarm(context, AppTracerService.class, getScreenLoadTimeInterval());
    }

    public void setScreenLoadTime(final String screenName, final long startLoadTime,
                                  final String screenInstanceId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseHelper.insertScreenLoadTimeData(mContext, screenName, startLoadTime, System.currentTimeMillis(), screenInstanceId);
            }
        }).start();

    }

    public void startApiLoadTime(final String screenInstanceId, final String apiName) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseHelper.insertApiLoadTimeData(mContext, apiName, System.currentTimeMillis(), screenInstanceId);
            }
        }).start();
    }

    public void endApiLoadTime(final String screenInstanceId, final String apiName) {

        final SQLiteDatabase db = DatabaseHelper.getWritableDatabase(mContext);

        new Thread(new Runnable() {
            @Override
            public void run() {
                db.beginTransaction();
                try {
                    long startLoadTime = DatabaseHelper.getApiStartLoadTimeFromDb(mContext, screenInstanceId, apiName);
                    if (startLoadTime != 0L) {
                        long totalLoadTime = Utilities.getTotalLoadTime(startLoadTime, System.currentTimeMillis());
                        DatabaseHelper.updateApiEndLoadTimeData(mContext, System.currentTimeMillis(), totalLoadTime,
                                screenInstanceId, apiName);
                    }
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db.endTransaction();
                }
            }
        }).start();
    }


    public void deleteScreenLoadTime(Long timeStamp)
    {
        DatabaseHelper.deleteScreenLoadTimeData(mContext,timeStamp);
    }

    public void deleteApiScreenLoadTime(Long timeStamp)
    {
        DatabaseHelper.deleteApiLoadTimeData(mContext, timeStamp);
    }

    public void disableScreenLoadTimeFeature() {

        Utilities.clearJobAlarm(mContext, AppTracerService.class);
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseHelper.deleteAllDataFromTable(mContext.getApplicationContext(), AppTracerDatabase.TABLE_SCREEN_LOAD_TIME);
                DatabaseHelper.deleteAllDataFromTable(mContext.getApplicationContext(), AppTracerDatabase.TABLE_API_LOAD_TIME);
            }
        }).start();
    }

    public String getScreenLoadTimeUrl() {
        return mScreenLoadTimeUrl;
    }

    public String getScreenLoadTimeAppId() {
        return mScreenLoadTimeAppId;
    }

    private Long getScreenLoadTimeInterval() {
        return mScreenLoadTimeInterval;
    }

    public boolean getCleanOnErrorStatus() {
        return mCleanOnError;
    }

    public String getScreenInstanceId(String screenName, long startLoadTime) {
        return Utilities.getCombineScreenNameAndStartLoadTime(screenName, startLoadTime);
    }


    public void setUserIdForScreenLoadTime(String userId) {
        Utilities.setUserIdSharedPreference(mContext, userId);
    }

    public void clearUserIdForScreenLoadTime() {
        Utilities.clearSharedPreferences(mContext);
    }

    private void setScreenLoadtimeInterval(Long interval) {
        if (interval != 0) {
            mScreenLoadTimeInterval = interval;
        } else {
            mScreenLoadTimeInterval = Constants.NEW_MONK_API_INTERVAL;
        }
    }


    private void validatePreconditions(String appId, String url) {
        if (appId != null && url != null && !appId.equalsIgnoreCase(Constants.EMPTY_STRING)
                && !url.equalsIgnoreCase(Constants.EMPTY_STRING)) {
            mScreenLoadTimeAppId = appId;
            mScreenLoadTimeUrl = url;
        } else {
            throw new NullPointerException("Precondition failed: Appid and url cannot be null");
        }
    }

}
