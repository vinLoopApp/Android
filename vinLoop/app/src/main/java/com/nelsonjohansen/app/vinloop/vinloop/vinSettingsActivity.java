package com.nelsonjohansen.app.vinloop.vinloop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by NelsonJ on 5/31/2015.
 */
public class vinSettingsActivity extends AppCompatActivity {

    //Log tag
    private static final String TAG = vinSettingsActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.settingsFragmentContainer);

        if(fragment == null){
            vinSettingsFragment settings = new vinSettingsFragment();
            Log.d("fragmentArgsSetNull", "");
            settings.setArguments(getIntent().getExtras());
            fm.beginTransaction()
                    .add(R.id.settingsFragmentContainer, settings)
                    .commit();
        }else {
            Log.d("fragmentArgsSetNonNull", "");
            //trying to reset arguments when orientation changes, causes crash and probably bad idea!
            //fragment.setArguments(getIntent().getExtras());
        }
    }
}
