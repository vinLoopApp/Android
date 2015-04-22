package com.nelsonjohansen.app.vinloop.vinloop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by NelsonJ on 4/9/2015.
 */


public class vinDetailFragment extends Fragment {

    public static final String EXTRA_WINERY_ID =
            "com.nelsonjohansen.app.vinloop.winery_id";

    private Winery w;

    public static vinDetailFragment newInstance(int index) {
        vinDetailFragment f = new vinDetailFragment();

        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
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

        View v = inflater.inflate(R.layout.detail_fragment, parent, false);

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

        Log.d("Test index from EXTRA:", " value: "  + getShownIndex());
        //deal.setText(String.valueOf(getShownIndex()));
        return v;
    }

}
