package com.nelsonjohansen.app.vinloop.vinloop;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.lang.Object;

import java.util.ArrayList;

public class vinListFragment extends ListFragment{

    private static final String TAG = "vinListFragment";

    private ArrayList<Winery> mWineries;

    //private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    /*// TODO: Rename and change types of parameters
    public static vinListFragment newInstance(String param1, String param2) {
        vinListFragment fragment = new vinListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public vinListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //read book on this line
        mWineries = vinData.get(getActivity()).getWineries();

        /*mAdapter = new ArrayAdapter<Winery>(getActivity(),
                android.R.layout.simple_list_item_1,
                mWineries);*/
        WineryAdapter mAdapter = new WineryAdapter(mWineries);
        setListAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        Winery w = ((WineryAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, w.getName() + " was Clicked");
    }

    private class WineryAdapter extends ArrayAdapter<Winery>{
        public WineryAdapter(ArrayList<Winery> wineries){
            super(getActivity(), 0, wineries);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            //If we were not given a view then inflate one
            if(convertView == null){
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_winery, null);
            }

            //configure view for this winery
            Winery w = getItem(position);

            TextView nameTextView =
                    (TextView)convertView.findViewById(R.id.winery_list_item_dealTextView);
            nameTextView.setText(w.getName());

            TextView locTextView =
                    (TextView)convertView.findViewById(R.id.winery_list_item_locTextView);
            locTextView.setText(w.getLocation());

            ImageView imageView =
                    (ImageView)convertView.findViewById(R.id.winery_list_item_iconImageView);
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
