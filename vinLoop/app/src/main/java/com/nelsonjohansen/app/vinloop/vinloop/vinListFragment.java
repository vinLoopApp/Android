package com.nelsonjohansen.app.vinloop.vinloop;

import android.graphics.Typeface;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class vinListFragment extends ListFragment
                            implements GoogleApiClient.ConnectionCallbacks,
                                       GoogleApiClient.OnConnectionFailedListener,
                                       LocationListener{

    // Log tag
    private static final String TAG = vinDetailActivity.class.getSimpleName();
    private static final String DIALOG_FILTER_REQUEST = "DialogFilterRequest";
    private static final String TEST_DIALOG = "TestDialog";
    private static final int REQUEST_FILTERS = 0;

    public static final int OUT_OF_SERVICE = 0;
    public static final int TEMPORARILY_UNAVAILABLE = 1;
    public static final int AVAILABLE = 2;

    Typeface fontANDB;
    Typeface fontMPR;
    Typeface fontMPSB;
    Typeface fontANR;
    Typeface fontANB;

    //info@vinloop.com

    public static final double meterToMileConversionValue = 0.000621371;

    String TITLES[] = {"Profile", "Favorites", "Contact", "About vinLoop"};
    int ICONS[] = {R.drawable.ic_account_circle_black_48dp, R.drawable.ic_favorite_border_black_48dp, R.drawable.ic_email_black_48dp, R.drawable.ic_bug_report_black_48dp};

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter rAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    private DrawerLayout mDrawerLayout;                   // Declaring DrawerLayout
    private ActionBarDrawerToggle mDrawerToggle;          // Declaring DrawerToggle listener(opened and closed states)

    String NAME = "Nelson Johansen";
    String EMAIL = "njjohansen@ucdavis.edu";
    int PROFILE = R.drawable.logo_480;

    // Winery json url
    private static final String url = "http://zoomonby.com/vinLoop/dealTable.php";
    private ProgressDialog pDialog;
    private ArrayList<Winery> wineryList = new ArrayList<>();
    private WineryAdapter adapter;
    private filterHelper mFilterHelper;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest = LocationRequest.create();

    private ArrayAdapter<String> mAdapter;

    boolean mDualPane;
    int mCurCheckPosition = 0;

    Location deviceLoc = new Location("Device");
    Location wineryLoc = new Location("Winery");

    private LayoutInflater inflater;
    ImageLoader imageLoader = volleySingleton.getInstance().getImageLoader();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public vinListFragment() {
    }

    public interface Callbacks {
        void onWinerySelected(Winery winery);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //unchecked cast of activity to Callbacks which assumes activity has implemented
        //the interface of Callbacks.
        //mCallbacks = (Callbacks)activity;
    }

    @Override
    public void onDetach(){
        super.onDetach();
        //mCallbacks = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        hideKeyboard(getView());
        stopLocationUpdates();
    }

    @Override
    public void onResume(){
        super.onResume();
        hideKeyboard(getView());
    }

    private void hideKeyboard(View view) {
        // Check if no view has focus:
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void stopLocationUpdates() {
        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, getActivity());
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, );
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        Log.d("GPS connected", " ");

        if (mLastLocation != null) {
            deviceLoc.setLatitude(mLastLocation.getLatitude());
            deviceLoc.setLongitude(mLastLocation.getLongitude());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        Log.e("Connected?", String.valueOf(mGoogleApiClient.isConnected()));
        //new Thread(new GetContent()).start();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        // called when the GPS provider is turned off (user turning off the GPS on the phone)
    }

    @Override
    public void onProviderEnabled(String provider) {
        // called when the GPS provider is turned on (user turning on the GPS on the phone)
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // called when the status of the GPS provider changes
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //http://stackoverflow.com/questions/24794377/android-capture-searchview-text-clear-by-clicking-x-button

        //Opening a dialog from another dialog button click. For Distance etc.
        //http://stackoverflow.com/questions/5662538/android-display-another-dialog-from-a-dialog

    }

    //Handle actionbar button clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items which is now handled within navbar, old code kept for future uses.
        switch (item.getItemId()) {
            /*case R.id.favorites_button:
                //openSearch();
                Intent intent = new Intent();
                intent.setClass(getActivity(), vinFavoriteActivity.class);
                intent.putExtra("index", 3);
                startActivity(intent);
                return true;
            case R.id.profile_button:
                Intent intentProfile = new Intent();
                intentProfile.setClass(getActivity(), vinProfileActivity.class);
                //intentProfile.putExtra("index", 3);
                startActivity(intentProfile);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != Activity.RESULT_OK) return;
        if(requestCode == REQUEST_FILTERS){
            //get data from the price buttons
            mFilterHelper.setPriceButton1((boolean) data
                    .getSerializableExtra(filterPickerDialogFragment.PRICE_FILTER_SELECTED_ONE));
            mFilterHelper.setPriceButton2((boolean) data
                    .getSerializableExtra(filterPickerDialogFragment.PRICE_FILTER_SELECTED_TWO));
            mFilterHelper.setPriceButton3((boolean) data
                    .getSerializableExtra(filterPickerDialogFragment.PRICE_FILTER_SELECTED_THREE));
            mFilterHelper.setPriceButton4((boolean) data
                    .getSerializableExtra(filterPickerDialogFragment.PRICE_FILTER_SELECTED_FOUR));
            mFilterHelper.setDistance((String) data
                    .getSerializableExtra(filterPickerDialogFragment.DISTANCE_FILTER_SELECTED));
            mFilterHelper.setWalkIn((boolean) data
                    .getSerializableExtra(filterPickerDialogFragment.WALK_IN_FILTER_SELECTED));
            mFilterHelper.setByAppt((boolean) data
                    .getSerializableExtra(filterPickerDialogFragment.BY_APPT_FILTER_SELECTED));

            Log.d("Price1: ", String.valueOf(mFilterHelper.isPriceButton1()));
            //at this point we have the selected price filter so update the list somehow

            adapter.getFilter().filter(DIALOG_FILTER_REQUEST);

        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            //SelectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        /*Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);*/
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.my_awesome_toolbar);

        //toolbar.setTitle("");
        try {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        } catch(NullPointerException e){
            Log.d("Error setting toolbar: ", "In favorites fragment");
        }
        //toolbar.setNavigationIcon(getActivity().getResources().getDrawable(R.drawable.ic_navdrawer));

        //ViewGroup decor = (ViewGroup) getActivity().getWindow().getDecorView();
        //View child = decor.getChildAt(0);
        //decor.removeView(child);
        //FrameLayout container = (FrameLayout) mDrawerLayout.findViewById(R.id.container);
        //container.addView(child);

        //decor.addView(mDrawerLayout);

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.left_drawer);
        // / Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);
        // Letting the system know that the list objects are of fixed size

        rAdapter = new DrawerRecyclerAdapter(getActivity(),TITLES,ICONS,NAME,EMAIL,PROFILE);

        mRecyclerView.setAdapter(rAdapter);
        // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(getActivity());
        // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);
        // Setting the layout Manager

        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mTitle);
                //getActivity().invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
                getActivity().invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mDrawerTitle);
                //getActivity().invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
                getActivity().invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        //mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_drawer);

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mFilterHelper = new filterHelper();

        //RelativeLayout mActionBarLayout = (RelativeLayout) inflater.inflate(R.layout.action_bar_custom, null);

        final SearchView searchView = (SearchView) getActivity().findViewById(R.id.search);
        final int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        final int magId = getResources().getIdentifier("android:id/search_mag_icon", null, null);
        final EditText textView = (EditText) searchView.findViewById(id);

        //remove magnifying glass in searchView hint field
        //ImageView magImage = (ImageView) searchView.findViewById(magId);
        //magImage.setLayoutParams(new LinearLayout.LayoutParams(0, 0));

        //remove blue line below search text in search view.
        int searchPlateId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_plate", null, null);
        View searchPlateView = searchView.findViewById(searchPlateId);

        try {
            searchPlateView.setBackgroundColor(getResources().getColor(R.color.searchbar_background_color_lighter));
        } catch (Throwable t) {
            t.printStackTrace();
        }
        searchView.setQueryHint(" Search for vinLoop Deals");
        searchView.clearFocus();
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);

        //Applies white color on searchview text
        textView.setTextSize(14);
        textView.setHintTextColor(getResources().getColor(R.color.off_white));
        textView.setTextColor(getResources().getColor(R.color.text_white));
        textView.setCursorVisible(false);

        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                textView.setCursorVisible(true);
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setCursorVisible(true);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {

                textView.setCursorVisible(true);

                if (!TextUtils.isEmpty(query)) {
                    adapter.getFilter().filter(query);
                    return true;
                } else {
                    adapter.getFilter().filter(query);
                    searchView.clearFocus();
                    textView.setCursorVisible(false);
                    return true;
                }
            }

            @Override
            public boolean onQueryTextSubmit(String query) {

                textView.setCursorVisible(true);

                if (!TextUtils.isEmpty(query)) {
                    adapter.getFilter().filter(query);
                    searchView.clearFocus();
                    return true;
                }
                searchView.clearFocus();
                textView.setCursorVisible(false);
                return false;
            }

        });

        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        final ImageButton filterButton =  (ImageButton) getActivity().findViewById(R.id.filter_button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                filterPickerDialogFragment filter =
                        filterPickerDialogFragment.newInstance(
                                mFilterHelper.isPriceButton1()
                                , mFilterHelper.isPriceButton2()
                                , mFilterHelper.isPriceButton3()
                                , mFilterHelper.isPriceButton4()
                                , mFilterHelper.getDistance()
                                , mFilterHelper.isWalkIn()
                                , mFilterHelper.isByAppt());

                //setting up communication between list fragment and dialog fragment
                filter.setTargetFragment(vinListFragment.this, REQUEST_FILTERS);
                //call filter in here somewhere, must change filter code to handle booleans coming
                //from user input via the dialog box, perhaps make a new class called filter to hold
                //all the options the user can choose.
                filter.show(fm, TEST_DIALOG);
            }
        });

        // Sync the toggle state after onRestoreInstanceState has occurred.
        fontANDB = Typeface.createFromAsset(getActivity().getAssets(), "AvenirNext-DemiBold.ttf");
        fontMPR = Typeface.createFromAsset(getActivity().getAssets(), "MyriadPro-Regular.otf");
        fontMPSB = Typeface.createFromAsset(getActivity().getAssets(), "MyriadPro-Semibold.otf");
        fontANR = Typeface.createFromAsset(getActivity().getAssets(), "AvenirNext-Regular.ttf");
        fontANB = Typeface.createFromAsset(getActivity().getAssets(), "AvenirNext-Bold.ttf");

        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.

        View detailsFrag = getActivity().findViewById(R.id.detailFragmentContainer);
        mDualPane = detailsFrag != null && detailsFrag.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (mDualPane) {
            // In dual-pane mode, the list view highlights the selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            Log.d("showing Detials page: ", "true");
            showDetails(mCurCheckPosition);
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    //move all actionbar stuff to on activity created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //setRetainInstance(true);

        //listView = (ListView) findViewById(R.id.listView);
        //final WineryAdapter adapter = new WineryAdapter(wineryList);
        adapter = new WineryAdapter(wineryList);
        setListAdapter(adapter);

        /*FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        final LoginButton loginButton;
        loginButton = (LoginButton) getActivity().findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile","user_friends");
        loginButton.setFragment(this);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginResult.getAccessToken();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });*/

        //THIS SHOULD BE DONE ASYNC AS ALL ANDROID NETWORKING IS ASYNC DON'T NEED TO IMPLEMENT MANUALLY YAY 1!1!1!

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
                                Winery winery = new Winery();
                                winery.setDeal(obj.getString("title"));
                                winery.setName(obj.getString("name"));
                                winery.setNewPrice(obj.getString("price"));
                                winery.setLikes(obj.getString("gets"));
                                winery.setLatitude(obj.getString("latitude"));
                                winery.setLongitude(obj.getString("longitude"));
                                winery.setByAppt(obj.getString("byappt"));
                                winery.setByWalk(obj.getString("bywalk"));
                                winery.setVarietal(obj.getString("varietal"));
                                winery.setThumbnailUrl(obj.getString("imgURL"));
                                winery.setPriceRate(obj.getString("pricelevel"));
                                winery.setOrigPrice(obj.getString("origprice"));

                                // adding winery to wineries array
                                wineryList.add(winery);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //hidePDialog();

            }
        });

        // Adding request to request queue
        volleySingleton.getInstance().addToRequestQueue(vinReq);

        //if(((AppCompatActivity) getActivity()).getSupportActionBar() != null) {

            //LayoutInflater inflator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //View v = inflator.inflate(R.layout.action_bar_custom, null);

            //((AppCompatActivity) getActivity()).getSupportActionBar().setLogo(R.drawable.logo186x62);
            //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayUseLogoEnabled(true);
            //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
            //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
            //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(true);

            //((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(mActionBarLayout);

        //}

        /*ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout,
                R.string.details_bought_title_text, R.string.details_availability_preview){

            public void onDrawerSlide(View drawerView, float slideOffset){
                //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            }

            // Called when a drawer has settled in a completely closed state.
            public void onDrawerClosed(View view) {
                //((AppCompatActivity) getActivity()).getSupportActionBar().show();
            }

            // Called when a drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView) {
                //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            }

        };

        // Set the drawer toggle as the DrawerListener
        //mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){

        super.onActivityCreated(savedInstanceState);

        /*mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

                //((AppCompatActivity) getActivity()).getSupportActionBar().setCustomView(v);

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });*/

    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_fragment, container, false);

        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) view.findViewById(R.id.left_drawer);

        String[] osArray = { "Android", "iOS", "Windows", "OS X", "Linux" };
        //mDrawerList.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, osArray));

        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        return view;
    }*/

    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void showDetails(int index) {
        mCurCheckPosition = index;

        Winery wineryDetailInfo = wineryList.get(index);

        Log.d("Dualpane mode", "on" + mDualPane);

        // Otherwise we need to launch a new activity to display
        // the dialog fragment with selected text.
        Intent intent = new Intent();
        intent.setClass(getActivity(), vinDetailActivity.class);
        intent.putExtra("index", index);
        intent.putExtra("name", wineryDetailInfo.getName());
        intent.putExtra("distance", wineryDetailInfo.getDist());
        intent.putExtra("dealText", wineryDetailInfo.getDeal());
        intent.putExtra("image", wineryDetailInfo.getThumbnailUrl());
        intent.putExtra("origPrice", wineryDetailInfo.getOrigPrice());
        intent.putExtra("newPrice", wineryDetailInfo.getNewPrice());
        intent.putExtra("Lat", wineryDetailInfo.getLatitude());
        intent.putExtra("Long", wineryDetailInfo.getLongitude());
        startActivity(intent);

        /*if (mDualPane) {
            // We can display everything in-place with fragments, so update
            // the list to highlight the selected item and show the data.
            getListView().setItemChecked(index, true);

            // Check what fragment is currently shown, replace if needed.
            vinDetailFragment details = (vinDetailFragment)
                    getFragmentManager().findFragmentById(R.id.detailFragmentContainer);
            if (details == null || details.getShownIndex() != index) {
                // Make new fragment to show this selection.
                details = vinDetailFragment.newInstance(index);

                // Execute a transaction, replacing any existing fragment
                // with this one inside the frame.
                //will always be 0 since we are only opening a details fragment at the moment
                //if wanted to open a different fragment would add more values.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (index == 0) {
                    ft.replace(R.id.detailFragmentContainer, details);
                } else {
                    ft.replace(R.id.detailFragmentContainer, details);
                }
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent();
            intent.setClass(getActivity(), vinDetailActivity.class);
            intent.putExtra("index", index);
            intent.putExtra("name", wineryDetailInfo.getName());
            intent.putExtra("distance", wineryDetailInfo.getDist());
            intent.putExtra("dealText", wineryDetailInfo.getDeal());
            startActivity(intent);
        }*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Winery w = ((WineryAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, w.getName() + " was Clicked");

        showDetails(position);
    }

    private class WineryAdapter extends ArrayAdapter<Winery> implements Filterable {
        //private ArrayList<Winery> arrayListFiltered = new ArrayList<>();
        ArrayList<Winery> mOriginalValue = null;
        //ArrayList<Winery> FilteredArrList = new ArrayList<Winery>();

        private LayoutInflater inflater;
        ImageLoader imageLoader = volleySingleton.getInstance().getImageLoader();

        public WineryAdapter(ArrayList<Winery> wineryI) {
            super(getActivity(),0,wineryI);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            //If we were not given a view then inflate one

            if(convertView == null){
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.list_item_winery, null);
            }

            if (imageLoader == null)
                imageLoader = volleySingleton.getInstance().getImageLoader();

            ListView listView = getListView();
            ColorDrawable myColor = new ColorDrawable(
                    getResources().getColor(R.color.list_spacer_color)
            );
            listView.setDivider(myColor);
            listView.setDividerHeight(10);

            NetworkImageView thumbNail = (NetworkImageView) convertView
                   .findViewById(R.id.winery_list_item_iconNetworkImageView);


            TextView descr = (TextView) convertView.findViewById(R.id.winery_list_item_dealTextView);
            descr.setTypeface(fontANDB);

            TextView name = (TextView) convertView.findViewById(R.id.winery_list_item_locTextView);
            name.setTypeface(fontMPR);

            TextView locCity = (TextView) convertView.findViewById(R.id.winery_list_item_locCityTextView);
            locCity.setTypeface(fontMPR);

            TextView circle = (TextView) convertView.findViewById(R.id.dot);
            circle.setTypeface(fontMPR);

            TextView dist = (TextView) convertView.findViewById(R.id.winery_list_item_locDistTextView);
            dist.setTypeface(fontMPR);

            TextView type = (TextView) convertView.findViewById(R.id.walkInOrAppt);
            type.setTypeface(fontMPR);

            TextView buys = (TextView) convertView.findViewById(R.id.number_buys);
            buys.setTypeface(fontMPSB);

            TextView oldPrice = (TextView) convertView.findViewById(R.id.oldPrice);
            oldPrice.setTypeface(fontANR);

            TextView newPrice = (TextView) convertView.findViewById(R.id.new_price);
            newPrice.setTypeface(fontANDB);

            //Log.d("Position in list", String.valueOf(position));
            //configure view for this winery
            Winery w = getItem(position);

            //thumbNail.setDefaultImageResId(R.drawable._default);
            //thumbNail.setErrorImageResId(R.drawable.error);

            // thumbnail image
            thumbNail.setImageUrl(w.getThumbnailUrl(), imageLoader);

            // name
            name.setText(w.getName());

            // descr
            descr.setText(String.valueOf(w.getDeal()));

            wineryLoc.setLongitude(Double.valueOf(w.getLongitude()));
            wineryLoc.setLatitude(Double.valueOf(w.getLatitude()));

            //Log.d("Lat", String.valueOf(deviceLoc.getLatitude()));
            //Log.d("Lng", String.valueOf(deviceLoc.getLongitude()));

            //dist
            dist.setText(String.format("%.2f mi", deviceLoc.distanceTo(wineryLoc) * meterToMileConversionValue));

            // release year
            //dist.setText(String.valueOf(w.getDist()));

            return convertView;

        }

        @Override
        public int getCount() {
            return wineryList.size();
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    //wineryList.clear();
                    //wineryList = new ArrayList<Winery>();
                    ArrayList<Winery> temp = (ArrayList<Winery>) results.values;
                    wineryList.clear();
                    //wineryList = (ArrayList<Winery>) results.values; // has the filtered values
                    for(int i = 0; i < temp.size();i++){
                        wineryList.add(i, temp.get(i));
                    }
                    adapter.notifyDataSetChanged();  // notifies the data with new filtered values
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                    ArrayList<Winery> FilteredArrList = new ArrayList<Winery>();

                    if (mOriginalValue == null) {
                        //Log.d("Checing creation: ", "origValueArr");
                        mOriginalValue = new ArrayList<Winery>(wineryList); // saves the original data in mOriginalValues
                    }

                    /********
                     *
                     *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                     *  else does the Filtering and returns FilteredArrList(Filtered)
                     *
                     ********/

                    //Log.d("Constraing size: ", String.valueOf(constraint.length()));
                    if(constraint == null || constraint.length() == 0){
                        // set the Original result to return
                        results.count = mOriginalValue.size();
                        results.values = mOriginalValue;
                        Log.d("Empty search: ", "true");
                    }else if(constraint == DIALOG_FILTER_REQUEST){
                        Log.d("Filtering based on: ", "dialog");
                        results.count = mOriginalValue.size();
                        results.values = mOriginalValue;
                    }else {
                        constraint = constraint.toString().toLowerCase();
                        //Log.d("Showing contraint: ", constraint.toString().toLowerCase());
                        //Log.d("originalvalues size: ", String.valueOf(mOriginalValue.size()));

                        for (int i = 0; i < mOriginalValue.size(); i++) {
                            Winery data = mOriginalValue.get(i);
                            //filter by winery of deal, add location search
                            if (data.getName().toLowerCase().contains(constraint.toString()) ||
                                    data.getDeal().toLowerCase().contains(constraint.toString())) {
                                //Log.d("Showing data", data.getName().toLowerCase());
                                FilteredArrList.add(data);
                            }
                        }
                        //Log.d("Showing filtered: ", String.valueOf(FilteredArrList.size()));
                        // set the Filtered result to return
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }

                    return results;
                }
            };
            return filter;
        }

    }

}
