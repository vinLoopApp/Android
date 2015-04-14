package com.nelsonjohansen.app.vinloop.vinloop;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by NelsonJ on 4/10/2015.
 */
public class WineryHelper {

    private static WineryHelper sWineryHelper;
    private Context mAppContext;

    private ArrayList<Winery> mWineries;

    //context parameter allows singleton to start activities, access resources and much more
    private WineryHelper(Context appContext){
        mAppContext = appContext;
        mWineries = new ArrayList<Winery>();
    }

    //global application context
    public static WineryHelper get(Context c){
        if(sWineryHelper == null){
            sWineryHelper = new WineryHelper(c.getApplicationContext());
        }
        return sWineryHelper;
    }

    public ArrayList<Winery> getCrimes(){
        return mWineries;
    }

    public Winery getWinery(UUID id){
        for(Winery w : mWineries){
            if(w.getmID().equals(id)){
                return w;
            }
        }
        return null;
    }

}
