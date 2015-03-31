package com.nelsonjohansen.app.vinloop.vinloop;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Created by NelsonJ on 3/29/2015.
 */
public class vinData {
    private ArrayList<Winery> mWineries;
    private static vinData svinData;
    private Context mAppContext;

    private vinData(Context appContext){
        mAppContext = appContext;
        mWineries = new ArrayList<Winery>();
        for(int i = 0; i < 100; i++){
            Winery w = new Winery();
            w.setName("Winery" + i);
            w.setLocation(i / 2);
            mWineries.add(w);
        }
    }

    public static vinData get(Context c){
        if(svinData == null){
            svinData = new vinData(c.getApplicationContext());
        }
        return svinData;
    }

    public ArrayList<Winery> getWineries(){
        return mWineries;
    }

    public Winery getWinery(UUID id){
        for(Winery w : mWineries){ 
            if(w.getID().equals(id)){
                return w;
            }
        }
        return null;
    }
}
