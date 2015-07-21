package com.nelsonjohansen.app.vinloop.vinloop;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.nelsonjohansen.app.vinloop.vinloop.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.PriorityQueue;


public class vinFavoritesFragment extends ListFragment {

    //private OnFragmentInteractionListener mListener;

    private static final String TAG = vinFavoriteActivity.class.getSimpleName();
    private static final String url = "http://zoomonby.com/vinLoop/dealTable.php";

    private AbsListView mListView;
    private ArrayList<Winery> wineryList = new ArrayList<>();
    private WineryAdapter mAdapter;

    public static vinFavoritesFragment newInstance(int index) {
        vinFavoritesFragment fragment = new vinFavoritesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public vinFavoritesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mAdapter = new WineryAdapter(wineryList);
        setListAdapter(mAdapter);

    }

    /*@Override
         public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_layout, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        //mListView.setOnItemClickListener(this);

        return view;
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        Context context = getActivity();
        final SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.get_favorites_list_file), Context.MODE_PRIVATE);
        //int defaultValue = getResources().getInteger(R.string.saved_high_score_default);
        //long highScore = sharedPref.getInt(getString(R.string.saved_high_score), defaultValue);

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

                                String favorite = sharedPref.getString(obj.getString("name"), null);

                                if(favorite != null) {
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

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        mAdapter.notifyDataSetChanged();
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

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.my_awesome_toolbar_favorite);
        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.text_white));
        toolbar.setTitle("Favorite");

        try {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch(NullPointerException e){
            Log.d("Error setting toolbar: ", "In favorites fragment");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        inflater.inflate(R.menu.menu_favorites, menu);

        //http://stackoverflow.com/questions/24794377/android-capture-searchview-text-clear-by-clicking-x-button

        //Opening a dialog from another dialog button click. For Distance etc.
        //http://stackoverflow.com/questions/5662538/android-display-another-dialog-from-a-dialog

        /*SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
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

        });*/
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
            //listView.setEmptyView(getActivity().findViewById(R.id.empty_list_view));
            final ColorDrawable myColor = new ColorDrawable(
                    getResources().getColor(R.color.list_spacer_color)
            );

            listView.setDivider(myColor);
            listView.setDividerHeight(10);

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setMessage("Delete this Winery from favorites?");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    //delete key value pair
                                    Context context = getActivity();
                                    SharedPreferences sharedPref = context.getSharedPreferences(
                                            getString(R.string.get_favorites_list_file), Context.MODE_PRIVATE);

                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    //user winery name as key since winery name should be unique for the moment

                                    //may need to use wineryUniqueID.
                                    editor.remove(wineryList.get(position).getName()).apply();

                                    //delete from current array to update list in real time.
                                    wineryList.remove(position);

                                    mAdapter.notifyDataSetChanged();

                                    dialog.cancel();
                                }
                            });
                    builder1.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                    return false;
                }

            });


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

            setEmptyText("No Favorites Yet!");

            return convertView;

        }

        @Override
        public int getCount() {
            return wineryList.size();
        }

    }

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
    }*/

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
