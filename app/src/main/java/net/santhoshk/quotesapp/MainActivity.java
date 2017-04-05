package net.santhoshk.quotesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
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
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final JSONArray[] data = {new JSONArray()};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this,"Testing Main Activity",Toast.LENGTH_SHORT).show();
        final GridView gridView = (GridView) findViewById(R.id.quotesCategoryGrid);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                  // Toast.makeText(view.getContext(),((JSONObject)(data[0].get(i))).get("thumbUrl").toString(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), SelectedTopicActivity.class);
                    intent.putExtra("url",((JSONObject)(data[0].get(i))).get("fullImg").toString());
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
