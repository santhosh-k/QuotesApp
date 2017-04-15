package net.santhoshk.quotesapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import net.santhoshk.quotesapp.Models.RoundedCornersTransformation;
import net.santhoshk.quotesapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sandy on 1/4/17.
 */
public class GridViewAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    JSONArray response;
    public GridViewAdapter(Context context, JSONArray r){
        mContext = context;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        response = r;
    }


    @Override
    public int getCount() {
        return response.length();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View finalView = null;
        if (view == null) {
            finalView = inflater.inflate(R.layout.grid_view_layout_main_activity, null);
        } else {
            finalView = view;
        }
        try {
            String title = ((String) ((JSONObject) (response.get(i))).get("fullTitle"));
            String thumbUrl = ((String) ((JSONObject) (response.get(i))).get("fullImg"));
            int id = mContext.getResources().getIdentifier(title.toLowerCase(), "drawable", mContext.getPackageName());
            if(id==0){
                id = mContext.getResources().getIdentifier("cute", "drawable", mContext.getPackageName());
            }
            ImageView imageView = (ImageView) finalView.findViewById(R.id.tileImage);
            TextView textView = (TextView) finalView.findViewById(R.id.tileText);
            if(thumbUrl.isEmpty()){
                imageView.setImageResource(id);
            } else {
                Picasso.with(finalView.getContext()).load(thumbUrl).into(imageView);
            }
            textView.setText(title);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return finalView;
    }
}
