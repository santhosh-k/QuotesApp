package net.santhoshk.quotesapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.santhoshk.quotesapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by santhosh.k on 4/6/17.
 */

public class PicsGridViewAdapter extends BaseAdapter {

    Context mContext;

    JSONArray array;

    LayoutInflater inflater;



    public PicsGridViewAdapter(Context c, JSONArray data) {
        mContext = c;
        array = data;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return array.length();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View finalView;
        try {
            if (view == null) {
                finalView = inflater.inflate(R.layout.grid_view_layout, null);
            } else {
                finalView = view;
            }
            String imgUrl = ((String) ((JSONObject) (array.get(i))).get("imgUrl"));
            String quote = ((String) ((JSONObject) (array.get(i))).get("quote"));
            ImageView imageView = (ImageView) finalView.findViewById(R.id.tileImage);
            TextView textView = (TextView) finalView.findViewById(R.id.tileText);
            textView.setText(quote);
            textView.setLines(1);
            Picasso.with(finalView.getContext()).load(imgUrl).into(imageView);
            return finalView;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
