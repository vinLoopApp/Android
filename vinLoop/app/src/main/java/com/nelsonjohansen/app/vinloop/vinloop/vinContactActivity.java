package com.nelsonjohansen.app.vinloop.vinloop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by NelsonJ on 6/1/2015.
 */
public class vinContactActivity extends AppCompatActivity {

    //Log tag
    private static final String TAG = vinContactActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.contactFragmentContainer);

        if(fragment == null){
            vinContactFragment settings = new vinContactFragment();
            Log.d("fragmentArgsSetNull", "");
            settings.setArguments(getIntent().getExtras());
            fm.beginTransaction()
                    .add(R.id.contactFragmentContainer, settings)
                    .commit();
        }else {
            Log.d("fragmentArgsSetNonNull", "");
            //trying to reset arguments when orientation changes, causes crash and probably bad idea!
            //fragment.setArguments(getIntent().getExtras());
        }
    }

}
