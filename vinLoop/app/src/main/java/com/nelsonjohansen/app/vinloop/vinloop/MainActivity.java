package com.nelsonjohansen.app.vinloop.vinloop;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends SingleFragmentActivity {

    private static MainActivity mInstance;
    private static Context mAppContext;

    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    // Movies json url
    /*private static final String url = "http://www.pandodroid.com/wine/jTest.php";
    private ProgressDialog pDialog;
    private List<Winery> wineryList = new ArrayList<>();
    private ListView listView;
    private WineryAdapter adapter;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new WineryAdapter(this, wineryList);
        listView.setAdapter(adapter);

        //handleIntent(getIntent());

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // Creating volley request obj
        JsonArrayRequest vinReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

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
                hidePDialog();

            }
        });

        // Adding request to request queue
        volleySingleton.getInstance().addToRequestQueue(vinReq);*/
    }

    @Override
    protected Fragment createFragment(){
        return new vinListFragment();
    }

    /*@Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }

    /*@Override
    protected Fragment createFragment(){
        return new vinListFragment();
    }*/

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    // Used for VolleySingleton

    public static MainActivity getInstance()
    {
        return mInstance;
    }

    public static Context getAppContext()
    {
        return mAppContext;
    }

    public void setAppContext(Context mAppContext)
    {
        this.mAppContext = mAppContext;
    }


}
