package com.nelsonjohansen.app.vinloop.vinloop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

public class filterPickerDialogFragment extends DialogFragment {

    //Strings and other types for data passed into and from this fragment back to calling activity
    public static final String PRICE_FILTER_SELECTED_ONE = "com.nelsonjohansen.app.vinloop.vinloop.price1";
    public static final String PRICE_FILTER_SELECTED_TWO = "com.nelsonjohansen.app.vinloop.vinloop.price2";
    public static final String PRICE_FILTER_SELECTED_THREE = "com.nelsonjohansen.app.vinloop.vinloop.price2";
    public static final String PRICE_FILTER_SELECTED_FOUR = "com.nelsonjohansen.app.vinloop.vinloop.price2";

    public static final String DISTANCE_FILTER_SELECTED = "com.nelsonjohansen.app.vinloop.vinloop.distance";

    public static final String WALK_IN_FILTER_SELECTED = "com.nelsonjohansen.app.vinloop.vinloop.walkIn";
    public static final String BY_APPT_FILTER_SELECTED = "com.nelsonjohansen.app.vinloop.vinloop.byAppt";

    private boolean oneDollarBool = false;
    private boolean twoDollarBool = false;
    private boolean threeDollarBool = false;
    private boolean fourDollarBool = false;

    CharSequence[] arrayDist = {"0.5", "1.0", "2.5","5", "10","25", "50"};
    CharSequence[] arraySort = {"stuff","better stuff","best stuff"};

    private String distance;

    private boolean walkIn = false;
    private boolean byAppt = false;

    private void sendResults(int resultCode){
        if(getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(PRICE_FILTER_SELECTED_ONE, oneDollarBool);
        i.putExtra(PRICE_FILTER_SELECTED_TWO, twoDollarBool);
        i.putExtra(PRICE_FILTER_SELECTED_THREE, threeDollarBool);
        i.putExtra(PRICE_FILTER_SELECTED_FOUR, fourDollarBool);
        i.putExtra(DISTANCE_FILTER_SELECTED, distance);
        i.putExtra(WALK_IN_FILTER_SELECTED, walkIn);
        i.putExtra(BY_APPT_FILTER_SELECTED, byAppt);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    public static filterPickerDialogFragment newInstance(boolean price1,
                                                         boolean price2,
                                                         boolean price3,
                                                         boolean price4,
                                                         String distancein,
                                                         boolean walkin,
                                                         boolean byapptin){
        Bundle args = new Bundle();
        //key value pair when fragment is created, can have filter view use previously
        //selected options.
        args.putSerializable(PRICE_FILTER_SELECTED_ONE, price1);
        args.putSerializable(PRICE_FILTER_SELECTED_TWO, price2);
        args.putSerializable(PRICE_FILTER_SELECTED_THREE, price3);
        args.putSerializable(PRICE_FILTER_SELECTED_FOUR, price4);
        args.putSerializable(DISTANCE_FILTER_SELECTED, distancein);
        args.putSerializable(WALK_IN_FILTER_SELECTED, walkin);
        args.putSerializable(BY_APPT_FILTER_SELECTED, byapptin);

        filterPickerDialogFragment fragment = new filterPickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        final View v = getActivity().getLayoutInflater()
                .inflate(R.layout.filter_page, null);

        getDialog().setTitle("Filters");

        final Button okButton = (Button) v.findViewById(R.id.okButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.d("Result ", "OK");
                sendResults(Activity.RESULT_OK);
                getDialog().dismiss();
            }
        });

        final Button cancelButton = (Button) v.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.d("Result ", "OK");
                sendResults(Activity.RESULT_CANCELED);
                getDialog().dismiss();
            }
        });

        final Button oneDollarButton = (Button) v.findViewById(R.id.oneDollarSignButton);

        oneDollarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.d("Price1 ", "clicked");
                oneDollarBool = !oneDollarBool;
            }
        });

        final Button twoDollarButton = (Button) v.findViewById(R.id.twoDollarSignButton);

        twoDollarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.d("Price2 ", "clicked");
                twoDollarBool = !twoDollarBool;
            }
        });

        final Button threeDollarButton = (Button) v.findViewById(R.id.threeDollarSignButton);

        threeDollarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.d("Price3 ", "clicked");
                threeDollarBool = !threeDollarBool;
            }
        });

        final Button fourDollarButton = (Button) v.findViewById(R.id.fourDollarSignButton);

        fourDollarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.d("Price4 ", "clicked");
                fourDollarBool = !fourDollarBool;
            }
        });

        final Button distanceChoice = (Button) v.findViewById(R.id.BestMatchButtonDistance);

        distanceChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.d("Distance ", "clicked");
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Distance");
                builder.setSingleChoiceItems(arrayDist, 1, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        distance = String.valueOf(arrayDist[which]);
                        distanceChoice.setText(arrayDist[which] + " mile radius");
                    }
                });

                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //sendResults(Activity.RESULT_OK);
                            }
                        });
                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.show();
            }
        });

        final Button sortByChoice = (Button) v.findViewById(R.id.BestMatchButtonSortBy);

        sortByChoice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.d("sortBy ", "clicked");
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Option");
                builder.setSingleChoiceItems(arraySort, 1, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        distance = String.valueOf(arraySort[which]);
                        distanceChoice.setText(arraySort[which] + " sort option");
                    }
                });

                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //sendResults(Activity.RESULT_OK);
                            }
                        });
                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.show();
            }
        });

        final Switch walkInSwitch = (Switch) v.findViewById(R.id.switchWalkIn);

        walkInSwitch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.d("WalkInSwitch ", "clicked");
                walkIn = !walkIn;
            }
        });

        final Switch byApptSwitch = (Switch) v.findViewById(R.id.switchAppt);

        byApptSwitch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.d("WalkInSwitch ", "clicked");
                byAppt = !byAppt;
            }
        });

        return v;
    }

    /*@Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final View v = getActivity().getLayoutInflater()
                .inflate(R.layout.filter_page, null);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                sendResults(Activity.RESULT_OK);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResults(Activity.RESULT_CANCELED);
                            }
                        })
                .create();
    }*/

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}