package com.nelsonjohansen.app.vinloop.vinloop;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;


public class vinActivity extends FragmentActivity {

    private static vinActivity mInstance;
    private static Context mAppContext;

    // Log tag
    private static final String TAG = vinActivity.class.getSimpleName();

    /*@Override
    protected Fragment createFragment(){
        return new vinDetailFragment();
    }

    @Override
    protected  int getLayoutResId(){
        return R.layout.detail_fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FragmentManager fm = getSupportFragmentManager();
        //Fragment fragment = fm.findFragmentById(R.id.detailFragmentContainer);

        vinDetailFragment details = new vinDetailFragment();
        details.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.detailFragmentContainer, details).commit();

        /*if(fragment == null){
            fragment = new vinDetailFragment();
            fm.beginTransaction()
                    .add(R.id.detailFragmentContainer, fragment)
                    .commit();
        }*/
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
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }

    /*@Override
    protected Fragment createFragment(){
        return new vinListFragment();
    }*/

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    // Used for VolleySingleton

    public static vinActivity getInstance()
    {
        return mInstance;
    }

    public static Context getAppContext()
    {
        return mAppContext;
    }

    public void setAppContext(Context mAppContext)
    {
        this.mAppContext = mAppContext;
    }


}
