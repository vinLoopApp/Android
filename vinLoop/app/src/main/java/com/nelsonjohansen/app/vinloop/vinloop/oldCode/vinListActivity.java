package com.nelsonjohansen.app.vinloop.vinloop.oldCode;

import android.support.v4.app.Fragment;

import com.nelsonjohansen.app.vinloop.vinloop.SingleFragmentActivity;

/**
 * Created by NelsonJ on 3/30/2015.
 */
public class vinListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new vinListFragment();
    }

}
