package com.nelsonjohansen.app.vinloop.vinloop;

/**
 * Created by NelsonJ on 5/30/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerRecyclerAdapter extends RecyclerView.Adapter<DrawerRecyclerAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
    private static final int TYPE_ITEM = 1;    // IF the view under inflation and population is header or Item;
    private static final int TYPE_FOOTER = 2;

    private static final int HEADER_POSITION = 0;
    private static final int PROFILE_POSITION = 1;
    private static final int FAVORITES_POSITION = 2;
    private static final int SETTINGS_POSITION = 3;
    private static final int ABOUT_POSITION = 4;
    //private static final int CONTACT_POSITION = 5;

    private String mNavTitles[]; // String Array to store the passed titles Value from MainActivity.java
    private int mIcons[];       // Int Array to store the passed icons resource value from MainActivity.java

    private String name;        //String Resource for header View Name
    private int profile;        //int Resource for header view profile picture
    private String email;       //String Resource for header view email
    private static Context context;


    // Creating a ViewHolder which extends the RecyclerView View Holder
    // ViewHolder are used to to store the inflated views in order to recycle them

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int Holderid;

        TextView textView;
        ImageView imageView;
        ImageView profile;
        Context contxt;

        // Creating ViewHolder Constructor with View and viewType As a parameter
        public ViewHolder(View itemView, int ViewType, Context c) {
            super(itemView);
            contxt = c;

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created

            if(ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.drawer_itemName); // Creating TextView object with the id of textView from item_row.xml
                imageView = (ImageView) itemView.findViewById(R.id.drawer_icon);// Creating ImageView object with the id of ImageView from item_row.xml
                Holderid = 1;                                               // setting holder id as 1 as the object being populated are of type item row
            }
            else if(ViewType == TYPE_HEADER){
                profile = (ImageView) itemView.findViewById(R.id.circleView);// Creating Image view object from header.xml for profile pic
                Holderid = 0;                                                // Setting holder id = 0 as the object being populated are of type header view
            } else {
                Holderid = 2;
            }
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent();

            switch (getLayoutPosition ()) {
                case HEADER_POSITION:
                    intent.setClass(context, vinProfileActivity.class);
                    intent.putExtra("index", 0);
                    contxt.startActivity(intent);
                    break;
                case PROFILE_POSITION:
                    intent.setClass(contxt, vinProfileActivity.class);
                    intent.putExtra("index", 1);
                    contxt.startActivity(intent);
                    break;
                case FAVORITES_POSITION:
                    intent.setClass(contxt, vinFavoriteActivity.class);
                    intent.putExtra("index", 2);
                    contxt.startActivity(intent);
                    break;
                case SETTINGS_POSITION:
                    intent.setClass(contxt, vinContactActivity.class);
                    intent.putExtra("index", 3);
                    contxt.startActivity(intent);
                    break;
                case ABOUT_POSITION:
                    intent.setClass(contxt, vinAboutActivity.class);
                    intent.putExtra("index", 4);
                    contxt.startActivity(intent);
                    break;
                default:
                    break;
            }
        }

    }

        DrawerRecyclerAdapter(Context c, String Titles[],int Icons[],String Name,String Email, int Profile){ // MyAdapter Constructor with titles and icons parameter
            // titles, icons, name, email, profile pic are passed from the main activity
            context = c;
            mNavTitles = Titles;
            mIcons = Icons;
            name = Name;
            email = Email;
            profile = Profile;
            //here we assign those passed values to the values we declared here in adapter
        }



        //Below first we ovverride the method onCreateViewHolder which is called when the ViewHolder is
        //Created, In this method we inflate the item_row.xml layout if the viewType is Type_ITEM or else we inflate header.xml
        // if the viewType is TYPE_HEADER
        // and pass it to the view holder

        @Override
        public DrawerRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_drawer_item,parent,false); //Inflating the layout

                ViewHolder vhItem = new ViewHolder(v, viewType, context); //Creating ViewHolder and passing the object of type view

                return vhItem; // Returning the created object

                //inflate your layout and pass it to view holder

            } else if (viewType == TYPE_HEADER) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_drawer_header,parent,false); //Inflating the layout

                ViewHolder vhHeader = new ViewHolder(v, viewType, context); //Creating ViewHolder and passing the object of type view

                return vhHeader; //returning the object created


            } else if(viewType == TYPE_FOOTER) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_drawer_footer,parent,false); //Inflating the layout

                ViewHolder vhFooter = new ViewHolder(v, viewType, context); //Creating ViewHolder and passing the object of type view

                return vhFooter; //returning the object created

            }
            return null;

        }

        //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
        // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
        // which view type is being created 1 for item row
        @Override
        public void onBindViewHolder(DrawerRecyclerAdapter.ViewHolder holder, int position) {
            if(holder.Holderid == 1) {                              // as the list view is going to be called after the header view so we decrement the
                // position by 1 and pass it to the holder while setting the text and image
                holder.textView.setText(mNavTitles[position - 1]); // Setting the Text with the array of our Titles
                holder.imageView.setImageResource(mIcons[position - 1]);// Settimg the image with array of our icons
            }
            else if(holder.Holderid == 0){
                holder.profile.setImageResource(profile);           // Similarly we set the resources for header view
            }else{
            }
        }

        // This method returns the number of items present in the list
        @Override
        public int getItemCount() {
            return mNavTitles.length+2; // the number of items in the list will be +1 the titles including the header view.
        }


        // Witht the following method we check what type of view is being passed
        @Override
        public int getItemViewType(int position) {
            if (isPositionHeader(position))
                return TYPE_HEADER;
            else if(isPositionFooter(position))
                return TYPE_FOOTER;

            return TYPE_ITEM;
        }

        private boolean isPositionHeader(int position) {
            return position == 0;
        }

        private boolean isPositionFooter(int position) { return position == 5; }

}
