package com.samkeet.revamp17.guest;

import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.samkeet.revamp17.Constants;
import com.samkeet.revamp17.LoginActivity;
import com.samkeet.revamp17.R;
import com.samkeet.revamp17.events.EventsMainActivity;
import com.yalantis.guillotine.animation.GuillotineAnimation;
import com.yalantis.guillotine.interfaces.GuillotineListener;

public class GuestMainActivity extends AppCompatActivity {

    private static final long RIPPLE_DURATION = 250;
    private boolean isOpen = false;
    private GuillotineAnimation mGuillotineAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FrameLayout root = (FrameLayout) findViewById(R.id.root);
        View contentHamburger = findViewById(R.id.content_hamburger);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guest_nav_drawer, null);
        root.addView(guillotineMenu);

        LinearLayout mEvents = (LinearLayout) findViewById(R.id.events_group);
        mEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EventsMainActivity.class);
                startActivity(intent);
                mGuillotineAnimation.close();
            }
        });

        LinearLayout mLogout = (LinearLayout) findViewById(R.id.logout_group);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.SharedPreferenceData.clearData();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                mGuillotineAnimation.close();
                finish();
            }
        });
        mGuillotineAnimation = new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .setGuillotineListener(new GuillotineListener() {
                    @Override
                    public void onGuillotineOpened() {
                        isOpen = true;
                    }

                    @Override
                    public void onGuillotineClosed() {
                        isOpen = false;
                    }
                })
                .build();
    }

    @Override
    public void onBackPressed() {
        if (!isOpen) {
            super.onBackPressed();
        }
        mGuillotineAnimation.close();
    }
}
