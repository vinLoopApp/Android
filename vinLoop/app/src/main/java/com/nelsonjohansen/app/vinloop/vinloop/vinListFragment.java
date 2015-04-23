package com.nelsonjohansen.app.vinloop.vinloop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class vinListFragment extends ListFragment{

    // Log tag
    private static final String TAG = vinActivity.class.getSimpleName();

    // Crime json url
    private static final String url = "http://www.pandodroid.com/wine/jTest.php";
    private static Context mAppContext;
    private ProgressDialog pDialog;
    private ArrayList<Winery> wineryList = new ArrayList<>();
    private WineryAdapter adapter;
    private ListView listView;
    private Callbacks mCallbacks;
    boolean mDualPane;
    int mCurCheckPosition = 0;

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
        mCallbacks = (Callbacks)activity;
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);

        //http://stackoverflow.com/questions/24794377/android-capture-searchview-text-clear-by-clicking-x-button

        //Opening a dialog from another dialog button click. For Distance etc.
        //http://stackoverflow.com/questions/5662538/android-display-another-dialog-from-a-dialog

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                if(!TextUtils.isEmpty(query)) {
                    adapter.getFilter().filter(query.toString());
                    return true;
                } else {
                    adapter.getFilter().filter(query.toString());
                    return true;
                }
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!TextUtils.isEmpty(query)) {
                    adapter.getFilter().filter(query.toString());
                    return true;
                }

                return false;
            }

        });
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(queryListener);
    }

    private String grid_currentQuery = null; // holds the current query...

    final private SearchView.OnQueryTextListener queryListener = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextChange(String newText) {
            if (TextUtils.isEmpty(newText)) {
                getActivity().getActionBar().setSubtitle("List");
                grid_currentQuery = null;
            } else {
                getActivity().getActionBar().setSubtitle("List - Searching for: " + newText);
                grid_currentQuery = newText;

            }
            getLoaderManager().restartLoader(0, null, vinListFragment.this);
            return false;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            Toast.makeText(getActivity(), "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
            return false;
        }
    };*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //listView = (ListView) findViewById(R.id.listView);
        //final WineryAdapter adapter = new WineryAdapter(wineryList);
        adapter = new WineryAdapter(wineryList);
        setListAdapter(adapter);

        //THIS SHOULD BE DONE ASYNC

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

                                winery.setName(obj.getString("name"));
                                winery.setDeal(obj.getString("deal"));
                                winery.setLikes(obj.getString("likes"));
                                winery.setThumbnailUrl(obj.getString("image"));
                                winery.setOrigPrice(obj.getString("origPrice"));
                                winery.setNewPrice(obj.getString("newPrice"));
                                winery.setPriceRate(obj.getString("priceRate"));
                                winery.setOrigPrice(obj.getString("origPrice"));
                                winery.setLatitude(obj.getString("lat"));
                                winery.setLatitude(obj.getString("lng"));

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
            showDetails(mCurCheckPosition);
        }

    }

    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void showDetails(int index) {
        mCurCheckPosition = index;

        Log.d("Dualpane mode", "on" + mDualPane);

        if (mDualPane) {
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
            intent.setClass(getActivity(), vinActivity.class);
            intent.putExtra("index", 3);
            startActivity(intent);
        }
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
            Log.d("getView", " ");

            if(convertView == null){
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.list_item_winery, null);
            }

            if (imageLoader == null)
                imageLoader = volleySingleton.getInstance().getImageLoader();

            NetworkImageView thumbNail = (NetworkImageView) convertView
                    .findViewById(R.id.winery_list_item_iconNetworkImageView);

            TextView name = (TextView) convertView.findViewById(R.id.winery_list_item_locTextView);
            TextView descr = (TextView) convertView.findViewById(R.id.winery_list_item_dealTextView);
            //TextView dist = (TextView) convertView.findViewById(R.id.winery_list_item_distTextView);

            //for(int i = 0; i < wineryList.size(); i++){
            //    Log.d("Name in view: ", wineryList.get(i).getName());
            //}

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
                    Log.d("Constraing size: ", String.valueOf(constraint.length()));
                    if (constraint == null || constraint.length() == 0) {
                        // set the Original result to return
                        results.count = mOriginalValue.size();
                        results.values = mOriginalValue;
                        Log.d("Empty search: ", "true");
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        Log.d("Showing contraint: ", constraint.toString().toLowerCase());
                        Log.d("originalvalues size: ", String.valueOf(mOriginalValue.size()));

                        for (int i = 0; i < mOriginalValue.size(); i++) {
                            Winery data = mOriginalValue.get(i);
                            if (data.getName().toLowerCase().startsWith(constraint.toString())) {
                                Log.d("Showing data", data.getName().toLowerCase());
                                FilteredArrList.add(data);
                            }
                        }
                        Log.d("Showing filtered: ", String.valueOf(FilteredArrList.size()));
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
