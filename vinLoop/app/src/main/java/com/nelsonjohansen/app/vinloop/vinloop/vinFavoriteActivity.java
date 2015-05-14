package com.nelsonjohansen.app.vinloop.vinloop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by NelsonJ on 4/27/2015.
 */
public class vinFavoriteActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.favoritesFragmentContainer);

        //vinDetailFragment details = new vinDetailFragment();
        //details.setArguments(getIntent().getExtras());

        if(fragment == null){
            //fragment = new vinDetailFragment();
            vinFavoritesFragment favorites = new vinFavoritesFragment();
            favorites.setArguments(getIntent().getExtras());
            fm.beginTransaction()
                    .add(R.id.favoritesFragmentContainer, favorites)
                    .commit();
        }
    }
}
