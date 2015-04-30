package com.nelsonjohansen.app.vinloop.vinloop;

/**
 * Created by NelsonJ on 4/1/2015.
 * from url http://www.androidhive.info/2014/07/android-custom-listview-with-image-and-text-using-volley/
 * handles the image cache within phone for fast loading into list.
 */

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

public class bitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    public static int getDefaultLruCacheSize() {
        //divide by 1024 minimizes the chance of int overflow.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        return cacheSize;
    }

    public bitmapLruCache() {
        this(getDefaultLruCacheSize());
    }

    public bitmapLruCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        System.out.println("Received image from cache!");
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        System.out.println("Put image in Cache!");
        put(url, bitmap);
    }
}
