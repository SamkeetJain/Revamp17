package com.samkeet.revamp17;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ScheduleActivity extends AppCompatActivity {

    public Button mDay1, mDay2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        mDay1 = (Button) findViewById(R.id.day1);
        mDay2 = (Button) findViewById(R.id.day2);

        mDay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Schedule2Activity.class);
                intent.putExtra("DATA", "day1.jpg");
                startActivity(intent);
            }
        });

        mDay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Schedule2Activity.class);
                intent.putExtra("DATA", "day2.jpg");
                startActivity(intent);
            }
        });
    }

    public void BackButton(View v) {
        finish();
    }
}
