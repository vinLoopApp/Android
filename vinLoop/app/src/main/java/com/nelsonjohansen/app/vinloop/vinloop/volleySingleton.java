package com.nelsonjohansen.app.vinloop.vinloop;

/**
 * Created by NelsonJ on 4/1/2015.
 */

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


/*
The best way to maintain volley core objects and request queue is,
making them global by creating a singleton class which extends Application object.
 */

public class volleySingleton extends Application{

    public static final String TAG = "volleySingleton";

    private static volleySingleton mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    /*private volleySingleton(){
        mRequestQueue = Volley.newRequestQueue(MainActivity.getAppContext());
        mImageLoader = new ImageLoader(mRequestQueue, new bitmapLruCache());
    }*/

    public static volleySingleton getInstance(){
        if(mInstance == null){
            mInstance = new volleySingleton();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new bitmapLruCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}