package net.santhoshk.quotesapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import net.santhoshk.quotesapp.R;

import org.json.JSONArray;


public class PictureViewerFragment extends Fragment {

    String url = "https://trquotesapp.herokuapp.com/getQuotes/topic/%s/page/%d/count/%d";
    ProgressBar progressBar;
    GridView gridView;

    public PictureViewerFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_picture_viewer, container, false);
        progressBar = (ProgressBar)v.findViewById(R.id.loadingProgressBarLarge);
        progressBar.setVisibility(View.VISIBLE);
        Bundle extras = getActivity().getIntent().getExtras();
        String topic = extras.getString("title");
        String url2 = String.format(url,topic,1,5);
        Toast.makeText(getActivity(),url2,Toast.LENGTH_SHORT).show();
        getActivity().setTitle(topic);
        RequestQueue mRequestQueue;
        gridView = (GridView) getActivity().findViewById(R.id.specificTopicGrid);

        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024 * 10); // 1MB cap

        Network network = new BasicNetwork(new HurlStack());

        mRequestQueue = new RequestQueue(cache, network);

        mRequestQueue.start();

        JsonArrayRequest jsObjRequest = new JsonArrayRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response",response.toString());
                        gridView.setAdapter(new PicsGridViewAdapter(getActivity().getBaseContext(),response));
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        mRequestQueue.add(jsObjRequest);
        // Inflate the layout for this fragment
        return  v;
    }

    public void hideProgressSpinner() {
        ProgressBar pb = (ProgressBar) getActivity().findViewById(R.id.loadingProgressBarLarge);
        pb.setVisibility(View.INVISIBLE);
    }
}
