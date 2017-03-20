package com.samkeet.revamp17.guest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.samkeet.revamp17.R;

import dmax.dialog.SpotsDialog;

public class GuestMyEventsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SpotsDialog pd;
    private Context progressDialogContext;
    private boolean authenticationError = true;
    private String errorMessage = "Data Corrupted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_my_events);

    }
}
