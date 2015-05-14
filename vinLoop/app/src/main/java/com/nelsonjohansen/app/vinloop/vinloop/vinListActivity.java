package com.nelsonjohansen.app.vinloop.vinloop;

import android.support.v4.app.Fragment;


/**
 * Created by NelsonJ on 3/30/2015.
 */
public class vinListActivity extends FragmentInitializeActivity
        implements vinListFragment.Callbacks {

    @Override
    protected Fragment createFragment(){
        return new vinListFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected  int getLayoutResId(){
        return R.layout.activity_masterdetail;
    }

    public void onWinerySelected(Winery winery){
        if(findViewById(R.id.detailFragmentContainer) == null){

        }else {

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

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //WineryAdapter.getFilter().filter(newText);

        return true;
    }*/

}
