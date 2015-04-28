package com.nelsonjohansen.app.vinloop.vinloop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by NelsonJ on 4/27/2015.
 */
public class vinProfileActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.profileContainer);

        //vinDetailFragment details = new vinDetailFragment();
        //details.setArguments(getIntent().getExtras());

        if(fragment == null){
            //fragment = new vinDetailFragment();
            vinProfileFragment profile = new vinProfileFragment();
            profile.setArguments(getIntent().getExtras());
            fm.beginTransaction()
                    .add(R.id.profileContainer, profile)
                    .commit();
        }
    }
}
