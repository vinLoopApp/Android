package com.nelsonjohansen.app.vinloop.vinloop;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by NelsonJ on 4/1/2015.
 */
public class WineryAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Winery> wineryItems;
    ImageLoader imageLoader = volleySingleton.getInstance().getImageLoader();

    public WineryAdapter(Activity activity, List<Winery> wineryItems) {
        this.activity = activity;
        this.wineryItems = wineryItems;
    }

    @Override
    public int getCount() {
        return wineryItems.size();
    }

    @Override
    public Object getItem(int location) {
        return wineryItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //If we were not given a view then inflate one
        /*if(convertView == null){
            convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.list_item_winery, null);
        }*/

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //If we were not given a view then inflate one
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item_winery, null);

        if (imageLoader == null)
            imageLoader = volleySingleton.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.winery_list_item_iconNetworkImageView);

        TextView name = (TextView) convertView.findViewById(R.id.winery_list_item_locTextView);
        TextView descr = (TextView) convertView.findViewById(R.id.winery_list_item_dealTextView);
        //TextView dist = (TextView) convertView.findViewById(R.id.winery_list_item_distTextView);


        //configure view for this winery
        Winery w = wineryItems.get(position);

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