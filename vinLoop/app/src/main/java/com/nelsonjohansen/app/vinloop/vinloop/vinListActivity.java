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
    protected  int getLayoutResId(){
        return R.layout.activity_masterdetail;
    }

    public void onWinerySelected(Winery winery){
        if(findViewById(R.id.detailFragmentContainer) == null){

        }else {

        }
    }

}
