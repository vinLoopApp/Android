package com.nelsonjohansen.app.vinloop.vinloop;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.SupportMapFragment;


public class vinDetailActivity extends AppCompatActivity {

    //Log tag
    private static final String TAG = vinDetailActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.detailFragmentContainer);

        if(fragment == null){
            vinDetailFragment details = new vinDetailFragment();
            Log.d("fragmentArgsSetNull", "");
            details.setArguments(getIntent().getExtras());
            fm.beginTransaction()
                    .add(R.id.detailFragmentContainer, details)
                    .commit();
        }else {
            Log.d("fragmentArgsSetNonNull", "");
            //trying to reset arguments when orientation changes, causes crash and probably bad idea!
            //fragment.setArguments(getIntent().getExtras());
        }

        /*SupportMapFragment mMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.details_map);

        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.details_map, mMapFragment).commit();
        }*/
    }
}
