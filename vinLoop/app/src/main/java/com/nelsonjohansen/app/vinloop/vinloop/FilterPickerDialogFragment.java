package com.nelsonjohansen.app.vinloop.vinloop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

public class filterPickerDialogFragment extends DialogFragment {

    public static final String PRICE_FILTER_SELECTED = "com.nelsonjohansen.app.vinloop.vinloop.price";

    private int mPrice;

    private void sendResults(int resultCode){
        if(getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(PRICE_FILTER_SELECTED, mPrice);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    public static filterPickerDialogFragment newInstance(int price){
        Bundle args = new Bundle();
        //key value pair
        args.putSerializable(PRICE_FILTER_SELECTED, price);

        filterPickerDialogFragment fragment = new filterPickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v = getActivity().getLayoutInflater()
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
                .create();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
