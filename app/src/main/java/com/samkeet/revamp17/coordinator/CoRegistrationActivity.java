package com.samkeet.revamp17.coordinator;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.samkeet.revamp17.R;

public class CoRegistrationActivity extends AppCompatActivity {

    private Button regStatus, regList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co_registration);

        regStatus = (Button) findViewById(R.id.regStatus);
        regStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CoRegStatusActivity.class);
                startActivity(intent);
            }
        });

        regList = (Button) findViewById(R.id.regList);
        regList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CoRegListActivity.class);
                startActivity(intent);
            }
        });
    }

    public void BackButton(View v) {finish();

    }
}
