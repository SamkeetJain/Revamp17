package com.samkeet.revamp17;

import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Schedule2Activity extends AppCompatActivity {

    public ImageView imageView;
    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule2);

        url = getIntent().getStringExtra("DATA");

        imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(getApplicationContext()).load(Constants.URLs.BASE + url).into(imageView);

        Toast.makeText(getApplicationContext(), "Image is loading, Please wait", Toast.LENGTH_SHORT);
    }
}
