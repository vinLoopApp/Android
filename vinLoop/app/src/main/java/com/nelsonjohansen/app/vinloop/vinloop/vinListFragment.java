package com.nelsonjohansen.app.vinloop.vinloop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //listView = (ListView) findViewById(R.id.listView);
        final WineryAdapter adapter = new WineryAdapter(wineryList);
        setListAdapter(adapter);

        //handleIntent(getIntent());

        //pDialog = new ProgressDialog(mAppContext);
        // Showing progress dialog before making http request
        //.setMessage("Loading...");
        //pDialog.show();

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

    private class WineryAdapter extends ArrayAdapter<Winery> {
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

            NetworkImageView thumbNail = (NetworkImageView) convertView
                    .findViewById(R.id.winery_list_item_iconNetworkImageView);

            TextView name = (TextView) convertView.findViewById(R.id.winery_list_item_locTextView);
            TextView descr = (TextView) convertView.findViewById(R.id.winery_list_item_dealTextView);
            //TextView dist = (TextView) convertView.findViewById(R.id.winery_list_item_distTextView);


            //configure view for this winery
            Winery w = getItem(position);

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
    }

}
