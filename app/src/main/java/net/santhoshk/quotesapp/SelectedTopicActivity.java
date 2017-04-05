package net.santhoshk.quotesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class SelectedTopicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_topic);
        Bundle extras = getIntent().getExtras();
        //Toast.makeText(this,extras.get("url").toString(),Toast.LENGTH_SHORT).show();
        ImageView im = (ImageView) findViewById(R.id.QuoteImage);
        Picasso.with(this).load(extras.get("url").toString()).into(im);
    }
}
