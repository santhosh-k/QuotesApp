package net.santhoshk.quotesapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.View;
import android.view.ViewManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;

import net.santhoshk.quotesapp.Adapters.GridViewAdapter;
import net.santhoshk.quotesapp.Activities.SelectedTopicActivity;
import net.santhoshk.quotesapp.Models.HeaderGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final JSONArray[] data = {new JSONArray()};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("QuotesApp");
        actionBar.setSubtitle("Subs");
/*        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);*/
        Toast.makeText(this,"Testing Main Activity",Toast.LENGTH_SHORT).show();
        ImageView imageView = (ImageView) findViewById(R.id.cateView);
        final HeaderGridView gridView = (HeaderGridView) findViewById(R.id.quotesCategoryGrid);
        ((ViewManager)imageView.getParent()).removeView(imageView);
        //gridView.addHeaderView(imageView,null,false);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Intent intent = new Intent(view.getContext(), SelectedTopicActivity.class);
                    intent.putExtra("fullTitle",((JSONObject)(data[0].get(i))).get("fullTitle").toString());
                    intent.putExtra("quotesAvailabe",((JSONObject)(data[0].get(i))).get("quotesAvailabe").toString());
                    intent.putExtra("title",((JSONObject)(data[0].get(i))).get("title").toString());
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        RequestQueue mRequestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024 * 10); // 1MB cap

        Network network = new BasicNetwork(new HurlStack());

        mRequestQueue = new RequestQueue(cache, network);

        mRequestQueue.start();

        String url = "http://trquotesapp.herokuapp.com/getAllTopics";

        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response",response.toString());
                        data[0] = response;

                        gridView.setAdapter(new GridViewAdapter(MainActivity.this,response));
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        mRequestQueue.add(jsObjRequest);

    }
}
