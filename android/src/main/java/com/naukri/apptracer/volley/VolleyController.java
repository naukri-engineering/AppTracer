package com.naukri.apptracer.volley;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.naukri.apptracer.utils.Utilities;


/**
 * Created by vishnu on 13/4/15.
 */
public class VolleyController {
    public static final String TAG = "VolleyController";

    private RequestQueue mRequestQueue;
    private Context mContext;
    private static VolleyController mInstance;

    VolleyController(Context context) {
        mContext = context;
    }

    public static VolleyController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyController(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext
                    .getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {

        if (Utilities.isNetworkConnected(mContext)) {
            // set the default tag if tag is
            req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
            getRequestQueue().add(req);
        }
    }

}


