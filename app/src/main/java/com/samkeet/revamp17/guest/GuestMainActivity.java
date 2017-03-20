package com.samkeet.revamp17.guest;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.samkeet.revamp17.Constants;
import com.samkeet.revamp17.DevelopersActivity;
import com.samkeet.revamp17.LoginActivity;
import com.samkeet.revamp17.PaymentInfoActivity;
import com.samkeet.revamp17.R;
import com.samkeet.revamp17.ScheduleActivity;
import com.samkeet.revamp17.events.EventsMainActivity;
import com.yalantis.guillotine.animation.GuillotineAnimation;
import com.yalantis.guillotine.interfaces.GuillotineListener;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import dmax.dialog.SpotsDialog;

public class GuestMainActivity extends AppCompatActivity {

    private static final long RIPPLE_DURATION = 250;
    private boolean isOpen = false;
    private GuillotineAnimation mGuillotineAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_main);

        Version version = new Version();
        version.execute();

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

        LinearLayout boxyGroup = (LinearLayout) findViewById(R.id.boxy);
        boxyGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GuestMainActivity.this);

                builder.setTitle("Boxy Boys");
                builder.setMessage("Known for its firm values of transformation, affirmative action, intergrity, professionalism,\n" +
                        "independence and teamwork.\n" +
                        "Boxy Boyz epitomises excellence and dynamic leadership, striving for success both on and off the field.\n"
                        + "\nTeam:\n\n" + "Anupam Bhattacharya \n" +
                        "Varun Badola \n" +
                        "Puneet Sachdev\n" +
                        "Manav Gohil\n" +
                        "Jay Bhanushali\n" +
                        "Mohit Mailk\n" +
                        "Dilzan wadia \n" +
                        "Abhishek Kapur \n" +
                        "Balraj Syal \n" +
                        "Maninder singh \n" +
                        "Shray rai tiwari \n" +
                        "Rakshit Pant\n");

                AlertDialog dialog = builder.create();
                dialog.show();
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

    private class Version extends AsyncTask<Void, Void, Integer> {
        boolean auth = false;

        protected void onPreExecute() {

        }

        protected Integer doInBackground(Void... params) {
            try {

                URL url = new URL(Constants.URLs.BASE + Constants.URLs.VERSION);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                Uri.Builder _data = new Uri.Builder().appendQueryParameter("s", "s");
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(_data.build().getEncodedQuery());
                writer.flush();
                writer.close();

                InputStreamReader in = new InputStreamReader(connection.getInputStream());
                StringBuilder jsonResults = new StringBuilder();
                // Load the results into a StringBuilder
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }
                connection.disconnect();
                JSONObject jsonObject = new JSONObject(jsonResults.toString());
                String temp = jsonObject.getString("version");
                if (temp.equals("NA")) {
                    auth = false;
                } else {
                    if (temp.equals(Constants.VERSION_CODE)) {
                        auth = false;
                    } else {
                        auth = true;
                    }
                }
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
                auth = false;
            }

            return 1;
        }

        protected void onPostExecute(Integer result) {
            if (auth) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GuestMainActivity.this);

                builder.setTitle("New version is available");
                builder.setMessage("Your using an outdated app, Please update your app to get latest and accurate information");

                String positiveText = "Update";
                builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String appPackageName = "com.samkeet.revamp17"; // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                });
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (!isOpen) {
            super.onBackPressed();
        }
        mGuillotineAnimation.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Version version = new Version();
        version.execute();
    }
}
