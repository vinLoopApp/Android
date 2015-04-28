package com.nelsonjohansen.app.vinloop.vinloop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by NelsonJ on 4/27/2015.
 */
public class vinProfileFragment extends Fragment implements View.OnClickListener {

    public static vinProfileFragment newInstance() {

        vinProfileFragment f = new vinProfileFragment();

        Bundle args = new Bundle();

        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onClick(View v){

        /*switch (v.getId()) {
            case R.id.favorites_button:
                //openSearch();

                return;
            case R.id.profile_button:
                Intent intentProfile = new Intent();
                intentProfile.setClass(getActivity(), vinProfileActivity.class);
                //intentProfile.putExtra("index", 3);
                startActivity(intentProfile);
                return;
            default:
                return super.onOptionsItemSelected(item);
        }
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.get_favorites_list_file), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        //user winery name as key since winery name should be unique for the moment
        //may need to use wineryUniqueID.
        //editor.putString(getShownName(), String.valueOf(!fav));
        editor.commit();*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){

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


        View v = inflater.inflate(R.layout.profile_fragment, parent, false);

        EditText firstName = (EditText) v.findViewById(R.id.firstNameInput);
        firstName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("testing", " ");

            }
        });


        /*final Button favButton = (Button) v.findViewById(R.id.detailFavButton);
        favButton.setOnClickListener(this);

        ImageLoader imageLoader = volleySingleton.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView) v
                .findViewById(R.id.details_winery_list_item_iconNetworkImageView);

        thumbNail.setImageUrl("http://mthoodwinery.com/wp-content/uploads/2014/11/Winery-rows.jpg", imageLoader);*/

        //http://stackoverflow.com/questions/21691656/solved-google-maps-mapfragment-causing-the-app-to-crash

        //TextView deal = (TextView) v.findViewById(R.id.details_deal);

        //LinearLayout transLayout = (LinearLayout) v.findViewById(R.id.linear_layout_details_fragment_child);
        //transLayout.setAlpha((float)0.8);

        //if need to have bold and non-bold etc.. this will bring text in exactly as formated on db
        //mytextview.setText(Html.fromHtml(sourceString));

        //deal.setText(String.valueOf(getShownIndex()));
        return v;
    }
}
