package com.nelsonjohansen.app.vinloop.vinloop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by NelsonJ on 6/1/2015.
 */
public class vinAboutActivity extends AppCompatActivity {

    //Log tag
    private static final String TAG = vinContactActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.aboutFragmentContainer);

        if(fragment == null){
            vinAboutFragment settings = new vinAboutFragment();
            Log.d("fragmentArgsSetNull", "");
            settings.setArguments(getIntent().getExtras());
            fm.beginTransaction()
                    .add(R.id.aboutFragmentContainer, settings)
                    .commit();
        }else {
            Log.d("fragmentArgsSetNonNull", "");
            //trying to reset arguments when orientation changes, causes crash and probably bad idea!
            //fragment.setArguments(getIntent().getExtras());
        }
    }

}