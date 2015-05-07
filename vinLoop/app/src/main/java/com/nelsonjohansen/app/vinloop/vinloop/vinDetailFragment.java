package com.nelsonjohansen.app.vinloop.vinloop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by NelsonJ on 4/9/2015.
 */


public class vinDetailFragment extends Fragment implements View.OnClickListener {

    public static final String EXTRA_WINERY_ID =
            "com.nelsonjohansen.app.vinloop.winery_id";

    private static Winery w;
    private static boolean fav = false;
    private static final String TAG = "details";
    private static final String url = "http://zoomonby.com/vinLoop/wineryTable.php";

    public static vinDetailFragment newInstance(int index, String name, String distance, String dealText) {

        vinDetailFragment f = new vinDetailFragment();

        Bundle args = new Bundle();

        args.putInt("index", index);
        args.putString("name", name);
        args.putString("distance", distance);
        args.putString("dealText", dealText);

        f.setArguments(args);

        return f;
    }

    private int getShownIndex() {
        return getArguments().getInt("index", 0);
    }
    private String getShownName(){ return getArguments().getString("name", null); }
    private String getShownDist(){ return getArguments().getString("distance", null); }
    private String getShownDealText(){ return getArguments().getString("dealText", null); }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onClick(View v){
        //user has selected to favorite this winery, add it to the users favorites file
        Log.d("Favorite add/removed", " ");
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.get_favorites_list_file), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        //user winery name as key since winery name should be unique for the moment
        //may need to use wineryUniqueID.
        editor.putString(getShownName(), String.valueOf(!fav));
        editor.commit();

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

        final Winery winery = new Winery();

        Log.d("Pulling from db: ", "true");
        // Creating volley request obj
        JsonArrayRequest vinReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        //hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                //name,address,phone,weburl,bio

                                JSONObject obj = response.getJSONObject(i);


                                // adding winery to wineries array

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //hidePDialog();

            }
        });

        Log.d("items", getShownName() + " " + getShownDist() + " " + getShownDealText() + " " + getShownIndex());

        View v = inflater.inflate(R.layout.detail_fragment, parent, false);

        final Button favButton = (Button) v.findViewById(R.id.detailFavButton);
        favButton.setOnClickListener(this);

        ImageLoader imageLoader = volleySingleton.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView) v
                .findViewById(R.id.details_winery_list_item_iconNetworkImageView);

        thumbNail.setImageUrl("http://mthoodwinery.com/wp-content/uploads/2014/11/Winery-rows.jpg", imageLoader);

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
