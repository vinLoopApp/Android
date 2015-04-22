package com.nelsonjohansen.app.vinloop.vinloop;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.SupportMapFragment;


public class vinActivity extends FragmentActivity {

    private static vinActivity mInstance;
    private static Context mAppContext;

    // Log tag
    private static final String TAG = vinActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.detailFragmentContainer);

        //vinDetailFragment details = new vinDetailFragment();
        //details.setArguments(getIntent().getExtras());

        if(fragment == null){
            //fragment = new vinDetailFragment();
            vinDetailFragment details = new vinDetailFragment();
            details.setArguments(getIntent().getExtras());
            fm.beginTransaction()
                    .add(R.id.detailFragmentContainer, details)
                    .commit();
        }

        //getSupportFragmentManager().beginTransaction().add(R.id.detailFragmentContainer, details).commit();

        SupportMapFragment mMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.details_map);

        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.details_map, mMapFragment).commit();
        }
    }
}
