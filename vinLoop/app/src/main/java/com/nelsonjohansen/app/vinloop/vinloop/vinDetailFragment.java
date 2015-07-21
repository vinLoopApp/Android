package com.nelsonjohansen.app.vinloop.vinloop;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by NelsonJ on 4/9/2015.
 */


public class vinDetailFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {

    public static final String EXTRA_WINERY_ID =
            "com.nelsonjohansen.app.vinloop.winery_id";

    private static final Winery w = new Winery();
    private static boolean fav = false;
    private static final String TAG = "details";
    private static final String url = "http://zoomonby.com/vinLoop/wineryTable.php";

    NetworkImageView thumbNail;
    NetworkImageView staticGoogleMap;
    TextView dealLoc;
    TextView dealTitle;
    TextView dealAddr;
    TextView dealPhoneNum;
    TextView dealWebsiteURL;
    TextView origPriceTextV;
    TextView newPriceTextV;

    protected static final String STATIC_GOOGLE_MAP_API_ENDPOINT =
            "https://maps.googleapis.com/maps/api/staticmap?";

    public static vinDetailFragment newInstance(int index, String name, String distance,
                                                String dealText, String origPrice,
                                                String newPrice, String latitude,
                                                String longitude) {

        vinDetailFragment f = new vinDetailFragment();

        Bundle args = new Bundle();

        args.putInt("index", index);
        args.putString("name", name);
        args.putString("distance", distance);
        args.putString("dealText", dealText);
        args.putString("image", url);
        args.putString("origPrice", origPrice);
        args.putString("newPrice", newPrice);
        args.putString("Lat", latitude);
        args.putString("Long", longitude);

        f.setArguments(args);

        return f;
    }

    private int getShownIndex() {
        return getArguments().getInt("index", 0);
    }
    private String getShownName(){ return getArguments().getString("name", null); }
    private String getShownDist(){ return getArguments().getString("distance", null); }
    private String getShownDealText(){ return getArguments().getString("dealText", null); }
    private String getShownURLText(){ return getArguments().getString("image", null); }
    private String getOrigPrice(){return getArguments().getString("origPrice", null); }
    private String getNewPrice(){return getArguments().getString("newPrice", null); }
    private String getLat() { return getArguments().getString("Lat", null); }
    private String getLong() { return getArguments().getString("Long", null); }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        inflater.inflate(R.menu.detail_menu, menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        setRetainInstance(true);

        //setup winery data structure to hold info from JSON

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

                                JSONObject obj = response.getJSONObject(i);

                                String winName = obj.getString("name");

                                Log.d(winName, getShownName());

                                if(getShownName().toLowerCase().equals(winName.toLowerCase())) {
                                    Log.d("creating winery ", getShownName());
                                    w.setName(winName);
                                    w.setAddress(obj.getString("address"));
                                    w.setPhoneNum(obj.getString("phone"));
                                    w.setWebURL(obj.getString("weburl"));
                                    w.setBio(obj.getString("bio"));

                                    initialize();

                                    //found our winery so stop search
                                    break;
                                }


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

        // Adding request to request queue, start async
        volleySingleton.getInstance().addToRequestQueue(vinReq);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.my_awesome_toolbar_details);
        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.text_white));
        toolbar.setTitle("Details");

        try {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch(NullPointerException e){
            Log.d("Error setting toolbar: ", "In favorites fragment");
        }
    }

    static final CameraPosition NAPA =
            new CameraPosition.Builder().target(new LatLng(38.3047, 122.2989))
                    .zoom(15.5f)
                    .bearing(0)
                    .tilt(25)
                    .build();

    @Override
    public void onMapReady(GoogleMap map) {
        //map.setMyLocationEnabled(true);
        Log.d("map portion executed", " ");
        map.moveCamera(CameraUpdateFactory.newCameraPosition(NAPA));
    }

    private void initialize(){
        dealTitle.setText(getShownDealText());
        dealLoc.setText(w.getName());

        //origPriceTextV.setText("$" + getOrigPrice());
        //origPriceTextV.setPaintFlags(origPriceTextV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        //newPriceTextV.setText("$" + getNewPrice());

        dealAddr.setText(w.getAddress());
        dealAddr.setFocusableInTouchMode(false);
        dealAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:" + "0,0?q=1600+Amphitheatre+Parkway%2C+CA"));
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        dealPhoneNum.setText("+1" + w.getPhoneNum());
        dealPhoneNum.setFocusableInTouchMode(false);
        dealPhoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + w.getPhoneNum()));
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        dealWebsiteURL.setText("Website");
        dealWebsiteURL.setFocusableInTouchMode(false);
        dealWebsiteURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, w.getWebURL());
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
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

        Log.d("items", getShownName() + " " + getShownDist() + " " + getShownDealText() + " " + getShownIndex());

        View v = inflater.inflate(R.layout.detail_fragment, parent, false);

        final Button favButton = (Button) v.findViewById(R.id.detailFavButton);
        favButton.setOnClickListener(this);

        ImageLoader imageLoader = volleySingleton.getInstance().getImageLoader();

        thumbNail = (NetworkImageView) v
                .findViewById(R.id.details_winery_list_item_iconNetworkImageView);

        thumbNail.setImageUrl(getShownURLText(), imageLoader);

        //SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.details_map);

        //mapFragment.getMapAsync(this);

        //map.setMyLocationEnabled(true);

        //perhaps do this async
        staticGoogleMap = (NetworkImageView) v
                .findViewById(R.id.details_winery_list_item_iconNetworkImageView_StaticGoogleMap);

        String parameters = "";
        String center = "center=" + getLat() + "," + getLong();
        String zoom = "zoom=13";
        String size = "size=600x300";
        String type = "maptype=roadmap";

        parameters = center + "&" + zoom + "&" + size + "&" + type;

        staticGoogleMap.setImageUrl(STATIC_GOOGLE_MAP_API_ENDPOINT + parameters, imageLoader);

        https://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&zoom=13&size=600x300&maptype=roadmap

        dealTitle = (TextView) v.findViewById(R.id.details_deal_title);

        dealLoc = (TextView) v.findViewById(R.id.details_deal_location);

        //origPriceTextV = (TextView) v.findViewById(R.id.details_deal_orig_price);

        //newPriceTextV = (TextView) v.findViewById(R.id.details_deal_new_price);

        dealAddr = (TextView) v.findViewById(R.id.winery_street_address);

        dealPhoneNum = (TextView) v.findViewById(R.id.phone_number);

        dealWebsiteURL = (TextView) v.findViewById(R.id.winery_website_url);

        //http://stackoverflow.com/questions/21691656/solved-google-maps-mapfragment-causing-the-app-to-crash

        //If need to have bold and non-bold etc.. this will bring text in exactly as formated on db
        //mytextview.setText(Html.fromHtml(sourceString));

        return v;
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
        editor.apply();

    }

}
