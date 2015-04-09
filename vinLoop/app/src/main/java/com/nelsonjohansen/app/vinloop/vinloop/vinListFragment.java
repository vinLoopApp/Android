package com.nelsonjohansen.app.vinloop.vinloop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    private static final String TAG = MainActivity.class.getSimpleName();

    // Crime json url
    private static final String url = "http://www.pandodroid.com/wine/jTest.php";
    private static Context mAppContext;
    private ProgressDialog pDialog;
    private ArrayList<Winery> wineryList = new ArrayList<>();
    private ListView listView;
    private Callbacks mCallbacks;

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
                                winery.setDescr(obj.getString("deal"));
                                winery.setDist(obj.getString("likes"));
                                winery.setThumbnailUrl(obj.getString("image"));

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

        //Start intent
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
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
            descr.setText(String.valueOf(w.getDescr()));

            // release year
            //dist.setText(String.valueOf(w.getDist()));

            return convertView;

        }
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_winery, container, false);

        // Set the adapter
        //mListView = (AbsListView) view.findViewById(android.R.id.list);
        //((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        //mListView.setOnItemClickListener(this);

        //return view;
    }*/

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    /*public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }*/

}
