package net.santhoshk.quotesapp.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.squareup.picasso.Picasso;

import net.santhoshk.quotesapp.Adapters.GridViewAdapter;
import net.santhoshk.quotesapp.Adapters.PicsGridViewAdapter;
import net.santhoshk.quotesapp.MainActivity;
import net.santhoshk.quotesapp.R;

import org.json.JSONArray;

public class SelectedTopicActivity extends AppCompatActivity {

    String url = "https://trquotesapp.herokuapp.com/getQuotes/topic/%s/page/%d/count/%d";
    ProgressBar progressBar;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_selected_topic);
        progressBar = (ProgressBar) findViewById(R.id.loadingProgressBarLarge);

        progressBar.setVisibility(View.VISIBLE);
        Bundle extras = getIntent().getExtras();
        String topic = extras.getString("title");
        String url2 = String.format(url,topic,1,5);
        Toast.makeText(this,url2,Toast.LENGTH_SHORT).show();
        setTitle(topic);
        RequestQueue mRequestQueue;
        gridView = (GridView) findViewById(R.id.specificTopicGrid);

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024 * 10); // 1MB cap

        Network network = new BasicNetwork(new HurlStack());

        mRequestQueue = new RequestQueue(cache, network);

        mRequestQueue.start();

        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response",response.toString());
                        gridView.setAdapter(new PicsGridViewAdapter(getBaseContext(),response));
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        mRequestQueue.add(jsObjRequest);
    }

    public void hideProgressSpinner() {
        ProgressBar pb = (ProgressBar) findViewById(R.id.loadingProgressBarLarge);
            pb.setVisibility(View.INVISIBLE);
        }
}
