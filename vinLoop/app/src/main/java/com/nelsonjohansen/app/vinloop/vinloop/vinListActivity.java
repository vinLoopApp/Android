package com.nelsonjohansen.app.vinloop.vinloop;

import android.support.v4.app.Fragment;

/**
 * Created by NelsonJ on 3/30/2015.
 */
public class vinListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new vinListFragment();
    }

}
