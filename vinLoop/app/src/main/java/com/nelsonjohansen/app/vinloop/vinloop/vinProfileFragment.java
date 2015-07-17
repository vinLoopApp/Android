package com.nelsonjohansen.app.vinloop.vinloop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by NelsonJ on 4/27/2015.
 */
public class vinProfileFragment extends Fragment implements View.OnClickListener {

    private final static String profileFirstNameKey = "PROFILE_FIRST_NAME_KEY";
    private final static String profileLastNameKey = "PROFILE_LAST_NAME_KEY";
    private final static String profileZIPKey = "PROFILE_ZIP_KEY";
    private final static String profileGenderKey = "PROFILE_GENDER_KEY";
    private final static String profileBirthdayKey = "PROFILE_BIRTHDAY_KEY";
    private final static String profileEmailKey = "PROFILE_EMAIL_KEY";
    private final static String profileNotificationsKey = "PROFILE_NOTIFICATIONS_KEY";


    public static vinProfileFragment newInstance() {

        vinProfileFragment f = new vinProfileFragment();

        Bundle args = new Bundle();

        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.my_awesome_toolbar_profile);
        toolbar.setTitleTextColor(getActivity().getResources().getColor(R.color.text_white));
        toolbar.setTitle("Profile");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v){

        /*switch (v.getId()) {
            case R.id.favorites_button:
                //openSearch();

                return;
            case R.id.profile_button:
                Intent intentProfile = new Intent();
                intentProfile.setClass(getActivity(), vinProfileActivity.class);
                //intentProfile.putExtra("index", 3);
                startActivity(intentProfile);
                return;
            default:
                return super.onOptionsItemSelected(item);
        }
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.get_favorites_list_file), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        //user winery name as key since winery name should be unique for the moment
        //may need to use wineryUniqueID.
        //editor.putString(getShownName(), String.valueOf(!fav));
        editor.commit();*/

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_profile, menu);

        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");

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

    private void hideKeyboard(View view) {
        // Check if no view has focus:
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
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

        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.get_favorites_list_file), Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor = sharedPref.edit();
        //user winery name as key since winery name should be unique for the moment

        //may need to use wineryUniqueID.
        //editor.putString(getShownName(), String.valueOf(!fav));
        //editor.apply();

        View v = inflater.inflate(R.layout.profile_fragment, parent, false);

        final EditText firstName = (EditText) v.findViewById(R.id.firstNameInput);
        String firstNameText = sharedPref.getString(profileFirstNameKey, null);

        //if user has already created a profile, reload that data
        if(firstNameText != null){
            firstName.setText(firstNameText);
        }

        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editor.putString(profileFirstNameKey, firstName.getText().toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //used to close keyboard if the user clicks off the edittext box
        firstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        final EditText lastName = (EditText) v.findViewById(R.id.lastNameInput);
        String lastNameText = sharedPref.getString(profileLastNameKey, null);

        //if user has already created a profile, reload that data
        if(lastNameText != null){
            lastName.setText(lastNameText);
        }

        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editor.putString(profileLastNameKey, lastName.getText().toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        final EditText zipInput = (EditText) v.findViewById(R.id.addressInput);
        String zipInputText = sharedPref.getString(profileZIPKey, null);

        //if user has already created a profile, reload that data
        if(zipInputText != null){
            zipInput.setText(zipInputText);
        }

        zipInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editor.putString(profileZIPKey, zipInput.getText().toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        zipInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        final EditText genderInput = (EditText) v.findViewById(R.id.genderInput);
        String genderInputText = sharedPref.getString(profileGenderKey, null);

        //if user has already created a profile, reload that data
        if(genderInputText != null){
            genderInput.setText(genderInputText);
        }

        genderInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editor.putString(profileGenderKey, genderInput.getText().toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        genderInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        final EditText birthdayInput = (EditText) v.findViewById(R.id.birthdayInput);
        String birthdayInputText = sharedPref.getString(profileBirthdayKey, null);

        //if user has already created a profile, reload that data
        if(birthdayInputText != null){
            birthdayInput.setText(birthdayInputText);
        }

        birthdayInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editor.putString(profileBirthdayKey, birthdayInput.getText().toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        birthdayInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    hideKeyboard(v);
                }
            }
        });

        final EditText emailInput = (EditText) v.findViewById(R.id.emailInput);
        String emailInputText = sharedPref.getString(profileEmailKey, null);

        //if user has already created a profile, reload that data
        if(emailInputText != null){
            emailInput.setText(emailInputText);
        }

        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editor.putString(profileEmailKey, emailInput.getText().toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        emailInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        return v;
    }
}


