package com.nelsonjohansen.app.vinloop.vinloop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by NelsonJ on 6/1/2015.
 */
public class vinAboutFragment extends Fragment {

    public static vinAboutFragment newInstance() {

        vinAboutFragment f = new vinAboutFragment();

        Bundle args = new Bundle();

        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){

        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.my_awesome_toolbar_about);
        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.text_white));
        toolbar.setTitle("About vinLoop");
        try {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch(NullPointerException e){
            Log.d("Error setting toolbar: ", "In favorites fragment");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {

        if (parent == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }

        /*SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.get_favorites_list_file), Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor = sharedPref.edit();
        //user winery name as key since winery name should be unique for the moment

        //may need to use wineryUniqueID.
        //editor.putString(getShownName(), String.valueOf(!fav));
        //editor.apply();
        */

        View v = inflater.inflate(R.layout.about_fragment, parent, false);

        //CheckBox ckBox1 = (CheckBox) v.findViewById(R.id.checkBox);

        //int id = Resources.getSystem().getIdentifier("btn_check_holo_light", "drawable", "android");
        //ckBox1.setButtonDrawable(id);

        return v;
    }

}
