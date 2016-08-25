package com.naukri.apptracer.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.naukri.apptracer.logger.AppTracerTimingLogger;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by vishnu on 13/4/15.
 */
public class GsonRequest<T> extends Request<T> {

    private final Gson gson;
    private final Class<T> clazz;
    private final Response.Listener<T> mListener;
    private Map<String, String> mParams;
    private String mUrl;

    public GsonRequest(int method, String url, Map<String, String> params,
                       Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        GsonBuilder gsonBuilder = new GsonBuilder();
        this.gson = gsonBuilder.create();
        this.clazz = clazz;
        mParams = params;
        mUrl = url;
        this.mListener = listener;

        setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, "UTF-8");
            AppTracerTimingLogger.info("URL == "+ mUrl+"    "+"Parameters == "+ mParams.toString());
            return Response.success(gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

}
