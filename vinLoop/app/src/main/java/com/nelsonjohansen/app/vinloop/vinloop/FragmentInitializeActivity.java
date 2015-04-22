//ASUS ROG G751JY-DH71 17.3-inch Gaming Laptop, GeForce GTX 980M Graphics



package com.nelsonjohansen.app.vinloop.vinloop;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

/**
 * Created by NelsonJ on 3/30/2015.
 */
//Important note, ActionBarActivity also extends FragmentActivity thus to have
//an action bar we must extend ActionBarActivity not the latter.
public abstract class FragmentInitializeActivity extends ActionBarActivity {

    protected abstract Fragment createFragment();

    //subclasses can choose to override this method to return a layout other than
    //activity_fragment
    protected int getLayoutResId(){
        return R.layout.activity_fragment;
    }

    private static vinActivity mInstance;
    private static Context mAppContext;

    // Log tag
    private static final String TAG = vinActivity.class.getSimpleName();

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
                    .addToBackStack("vinListFragment_added_to_backstack")
                    .commit();
        }

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }*/

}
