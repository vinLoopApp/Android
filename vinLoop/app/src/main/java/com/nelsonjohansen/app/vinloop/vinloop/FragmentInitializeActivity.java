//ASUS ROG G751JY-DH71 17.3-inch Gaming Laptop, GeForce GTX 980M Graphics



package com.nelsonjohansen.app.vinloop.vinloop;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by NelsonJ on 3/30/2015.
 */
//Important note, AppCompatActivity also extends FragmentActivity thus to have
//an action bar we must extend AppCompatActivity  not the latter.
public abstract class FragmentInitializeActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    //subclasses can choose to override this method to return a layout other than
    //activity_fragment
    protected int getLayoutResId(){
        return R.layout.activity_fragment;
    }

    private static vinDetailActivity mInstance;
    private static Context mAppContext;

    // Log tag
    private static final String TAG = vinDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if(fragment == null){
            fragment = new vinListFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //handled within the fragments not the calling activities.
    }*/

}
