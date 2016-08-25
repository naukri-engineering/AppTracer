package com.naukri.apptracer.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.naukri.apptracer.AppTracer;
import com.naukri.apptracer.R;
import com.naukri.apptracer.database.DatabaseHelper;
import com.naukri.apptracer.database.AppTracerDatabase;
import com.naukri.apptracer.pojo.AppTracerApiPojo;
import com.naukri.apptracer.utils.Utilities;
import com.naukri.apptracer.volley.GsonRequest;
import com.naukri.apptracer.volley.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by vishnu.anand on 6/21/2016.
 */
public class AppTracerService extends IntentService {

    private final static String TAG = "AppTracerService";

    public AppTracerService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        JSONObject params = getScreenLoadTimeParameters();
        if (params.length() > 0) {
            callNewMonkLoadTimeApi(params);
        }
    }

    private void callNewMonkLoadTimeApi(JSONObject params) {

        GsonRequest<AppTracerApiPojo> request = new GsonRequest<AppTracerApiPojo>(Request.Method.POST,
                AppTracer.getInstance().getScreenLoadTimeUrl(), getNewMonApiParams(params), AppTracerApiPojo.class,
                SuccessListener(), ErrorListener());
        VolleyController.getInstance(getApplicationContext()).addToRequestQueue(request, TAG);
    }


    private Response.Listener<AppTracerApiPojo> SuccessListener() {
        return new Response.Listener<AppTracerApiPojo>() {
            @Override
            public void onResponse(AppTracerApiPojo response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DatabaseHelper.deleteAllDataFromTable(getApplicationContext(), AppTracerDatabase.TABLE_SCREEN_LOAD_TIME);
                        DatabaseHelper.deleteAllDataFromTable(getApplicationContext(), AppTracerDatabase.TABLE_API_LOAD_TIME);
                    }
                }).start();
            }
        };
    }

    private Response.ErrorListener ErrorListener() {

        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (AppTracer.getInstance().getCleanOnErrorStatus()) {
                            if (error instanceof ServerError || error instanceof ParseError || error instanceof AuthFailureError) {
                                DatabaseHelper.deleteAllDataFromTable(getApplicationContext(), AppTracerDatabase.TABLE_SCREEN_LOAD_TIME);
                                DatabaseHelper.deleteAllDataFromTable(getApplicationContext(), AppTracerDatabase.TABLE_API_LOAD_TIME);
                            }
                        }
                    }
                }).start();

            }
        };
    }

    private JSONObject getScreenLoadTimeParameters() {
        Cursor screenCursor = DatabaseHelper.getScreenLoadTime(getApplicationContext());
        if (screenCursor != null && screenCursor.getCount() > 0) {

            JSONObject baseObject = new JSONObject();
            Resources resources = getResources();
            try {
                baseObject.put(resources.getString(R.string.appId), AppTracer.getInstance().getScreenLoadTimeAppId());
                baseObject.put(resources.getString(R.string.uId), Utilities.getUserIdFromPrefernce(getApplicationContext()));
                baseObject.put(resources.getString(R.string.environment), getEnvironmentObject());

                JSONArray loadTimeArray = new JSONArray();
                int count = 0;
                while (screenCursor.moveToNext()) {
                    if (isAnyApiDataOfParticularScreen(screenCursor)) {
                        JSONObject screenObject = getParticularScreenObject(screenCursor);
                        if(screenObject != null) {
                            loadTimeArray.put(count, screenObject);
                            count++;
                        }
                    }
                }

                if(loadTimeArray.length() > 0) {
                    baseObject.put(resources.getString(R.string.loadtime), loadTimeArray);
                }
                else {
                    return new JSONObject();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                screenCursor.close();
            }

            return baseObject;
        }

        return new JSONObject();
    }

    private JSONObject getParticularScreenObject(Cursor cursor) {
        JSONObject object = new JSONObject();
        Resources resources = getResources();

        int nameIndex = cursor.getColumnIndex(AppTracerDatabase.SCREEN_NAME);
        int screenInstanceIdIndex = cursor.getColumnIndex(AppTracerDatabase.SCREEN_INSTANCE_ID);
        int dateIndex = cursor.getColumnIndex(AppTracerDatabase.CURRENT_TIMESTAMP);
        int totalLoadTimeIndex = cursor.getColumnIndex(AppTracerDatabase.TOTAL_LOAD_TIME);

        String screenName = cursor.getString(nameIndex);
        String screenInstanceId = cursor.getString(screenInstanceIdIndex);
        Long current_date = cursor.getLong(dateIndex);
        long totalLoadTime = cursor.getLong(totalLoadTimeIndex);
        try {
            object.put(resources.getString(R.string.tag), screenName);
            object.put(resources.getString(R.string.cpu), "0");
            object.put(resources.getString(R.string.battery), "0");
            object.put(resources.getString(R.string.gmtTimestamp), Utilities.getCurrentDateTime(current_date));
            object.put(resources.getString(R.string.loadTime), totalLoadTime);
            object.put(resources.getString(R.string.memory), "0");

            JSONObject networkObject = new JSONObject();
            networkObject.put(resources.getString(R.string.type), Utilities.getNetworkType(getApplicationContext()));
            networkObject.put(resources.getString(R.string.bytesTransferred), "0");
            object.put(resources.getString(R.string.network), networkObject);

            JSONArray apiArray = getApiDataOfParticularScreen(screenInstanceId);
            if(apiArray.length() > 0) {
                object.put(resources.getString(R.string.api), apiArray);
            }
            else {
                return null;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    private boolean isAnyApiDataOfParticularScreen(Cursor screenCursor) {
        int screenInstanceIdIndex = screenCursor.getColumnIndex(AppTracerDatabase.SCREEN_INSTANCE_ID);
        String screenInstanceId = screenCursor.getString(screenInstanceIdIndex);
        if (getApiDataOfParticularScreen(screenInstanceId).length() > 0) {
            return true;
        }
        return false;
    }

    private JSONArray getApiDataOfParticularScreen(String screenInstanceId) {
        JSONArray apiArray = new JSONArray();
        Resources resources = getResources();

        Cursor apiCursor = DatabaseHelper.getApisCursorOfParticularScreenFromDb(getApplicationContext(), screenInstanceId);

        if (apiCursor != null && apiCursor.getCount() > 0) {

            int count = 0;
            while (apiCursor.moveToNext()) {
                JSONObject apiObject = new JSONObject();

                int apiNameIndex = apiCursor.getColumnIndex(AppTracerDatabase.API_NAME);
                int totalLoadTimeIndex = apiCursor.getColumnIndex(AppTracerDatabase.TOTAL_LOAD_TIME);
                String apiName = apiCursor.getString(apiNameIndex);
                long totalLoadTime = apiCursor.getLong(totalLoadTimeIndex);

                try {
                    apiObject.put(resources.getString(R.string.screen_load_time_name), apiName);
                    apiObject.put(resources.getString(R.string.loadTime), totalLoadTime);
                    apiObject.put(resources.getString(R.string.bytesTransferred), "0");

                    apiArray.put(count, apiObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                count++;
            }

            if (apiCursor != null) {
                apiCursor.close();
            }
        }

        return apiArray;
    }

    private JSONObject getEnvironmentObject() {
        JSONObject envObject = new JSONObject();
        Resources resources = getResources();
        try {
            JSONObject osObject = new JSONObject();
            osObject.put(resources.getString(R.string.screen_load_time_name), "Android");
            osObject.put(resources.getString(R.string.screen_load_time_version), String.valueOf(Build.VERSION.SDK_INT));

            JSONObject appObject = new JSONObject();
            appObject.put(resources.getString(R.string.screen_load_time_version), Utilities.getAppVersionCode(getApplicationContext()));

            JSONObject deviceObject = new JSONObject();
            deviceObject.put(resources.getString(R.string.screen_load_time_name), Build.BRAND);
            deviceObject.put(resources.getString(R.string.model), Build.MODEL);

            envObject.put(resources.getString(R.string.os), osObject);
            envObject.put(resources.getString(R.string.app), appObject);
            envObject.put(resources.getString(R.string.device), deviceObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return envObject;
    }

    private Map<String, String> getNewMonApiParams(JSONObject params) {
        Map<String, String> object = new HashMap<String, String>();
        object.put("boomAppsLogs", params.toString());

        return object;
    }

}
