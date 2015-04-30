package com.nelsonjohansen.app.vinloop.vinloop;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.google.android.gms.maps.SupportMapFragment;


public class vinDetailActivity extends FragmentActivity {

    private static vinDetailActivity mInstance;
    private static Context mAppContext;

    // Log tag
    private static final String TAG = vinDetailActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.detailFragmentContainer);

        //vinDetailFragment details = new vinDetailFragment();
        //details.setArguments(getIntent().getExtras());

        if(fragment == null){
            //fragment = new vinDetailFragment();
            vinDetailFragment details = new vinDetailFragment();
            Log.d("fragmentArgsSetNull", "");
            details.setArguments(getIntent().getExtras());
            fm.beginTransaction()
                    .add(R.id.detailFragmentContainer, details)
                    .commit();
        }else {
            Log.d("fragmentArgsSetNonNull", "");
            fragment.setArguments(getIntent().getExtras());
        }

        //getSupportFragmentManager().beginTransaction().add(R.id.detailFragmentContainer, details).commit();

        /*final Button favButton = (Button) findViewById(R.id.favorites_button);
        favButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("favButton", " clicked");
            }
        });*/

        SupportMapFragment mMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.details_map);

        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.details_map, mMapFragment).commit();
        }
    }
}
