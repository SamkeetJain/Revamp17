package com.samkeet.revamp17.admin;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.samkeet.revamp17.Constants;
import com.samkeet.revamp17.DevelopersActivity;
import com.samkeet.revamp17.LoginActivity;
import com.samkeet.revamp17.PaymentInfoActivity;
import com.samkeet.revamp17.R;
import com.samkeet.revamp17.ScheduleActivity;
import com.samkeet.revamp17.coordinator.CoPaymentActivity;
import com.samkeet.revamp17.coordinator.CoRegistrationActivity;
import com.samkeet.revamp17.events.EventsMainActivity;
import com.yalantis.guillotine.animation.GuillotineAnimation;
import com.yalantis.guillotine.interfaces.GuillotineListener;

public class AdminMainActivity extends AppCompatActivity {

    private static final long RIPPLE_DURATION = 250;
    private boolean isOpen = false;
    private GuillotineAnimation mGuillotineAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FrameLayout root = (FrameLayout) findViewById(R.id.root);
        View contentHamburger = findViewById(R.id.content_hamburger);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.admin_nav_drawer, null);
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

        LinearLayout mPayments = (LinearLayout) findViewById(R.id.payments_group);
        mPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminPaymentActivity.class);
                startActivity(intent);
                mGuillotineAnimation.close();
            }
        });

        LinearLayout mAddCo = (LinearLayout) findViewById(R.id.addco_group);
        mAddCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddCoActivity.class);
                startActivity(intent);
                mGuillotineAnimation.close();
            }
        });

        LinearLayout mRegistration = (LinearLayout) findViewById(R.id.registration_group);
        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminRegistrationActivity.class);
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

        LinearLayout mSchedule = (LinearLayout) findViewById(R.id.sch_group);
        mSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScheduleActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout mReachUs = (LinearLayout) findViewById(R.id.reach_group);
        mReachUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double latitude = 13.1158112;
                double longitude = 77.6342008;
                String label = "Reva University";

                String uriBegin = "geo:" + latitude + "," + longitude;
                String query = latitude + "," + longitude + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=17";
                Uri uri = Uri.parse(uriString);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
        final LinearLayout mWebsite = (LinearLayout) findViewById(R.id.website_group);
        mWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sWebsite = "http://www.revampthefest.com";
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW);
                websiteIntent.setData(Uri.parse(sWebsite));
                startActivity(websiteIntent);
            }
        });
        LinearLayout mPaymentInfo = (LinearLayout) findViewById(R.id.pay_group);
        mPaymentInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PaymentInfoActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout mDevelopers = (LinearLayout) findViewById(R.id.dev_group);
        mDevelopers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DevelopersActivity.class);
                startActivity(intent);
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
