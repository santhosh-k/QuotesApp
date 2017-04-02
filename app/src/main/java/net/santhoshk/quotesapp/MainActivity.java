package net.santhoshk.quotesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.widget.GridView;
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

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this,"Testing Main Activity",Toast.LENGTH_SHORT).show();
        final GridView gridView = (GridView) findViewById(R.id.quotesCategoryGrid);
        RequestQueue mRequestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024 * 10); // 1MB cap

        Network network = new BasicNetwork(new HurlStack());

        mRequestQueue = new RequestQueue(cache, network);

        mRequestQueue.start();

        String url = "http://tr-santhoshk.rhcloud.com/getAllTopics";

        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response",response.toString());
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
