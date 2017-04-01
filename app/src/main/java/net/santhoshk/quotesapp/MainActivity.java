package net.santhoshk.quotesapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toast.makeText(this,"Testing Main Activity",Toast.LENGTH_SHORT).show();
        GridView gridView = (GridView) findViewById(R.id.quotesCategoryGrid);
        gridView.setAdapter(new BaseAdapter() {
            LayoutInflater inflater = (LayoutInflater) getBaseContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @Override
            public int getCount() {
                return new DataClass().getGridViewData().size();
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
                if(view==null){
                    finalView = new View(getBaseContext());
                    finalView = inflater.inflate(R.layout.grid_view_layout,null);

                } else {
                    finalView = (View) view;
                }

                int id = getBaseContext().getResources().getIdentifier("tiger", "drawable",getBaseContext().getPackageName());
                ImageView imageView = (ImageView) finalView.findViewById(R.id.tileImage);
                TextView textView = (TextView) finalView.findViewById(R.id.tileText);
                imageView.setImageResource(id);
                textView.setText("Tiger");
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
               // finalView.setLayoutParams(new AbsListView.LayoutParams(400,400));
                return finalView;
            }

        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(),new DataClass().getGridViewData().get(i).get("topic"),Toast.LENGTH_LONG).show();
            }
        });
    }
}
