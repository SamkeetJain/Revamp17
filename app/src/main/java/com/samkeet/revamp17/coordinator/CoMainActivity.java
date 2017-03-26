package com.samkeet.revamp17.coordinator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.content.IntentCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.imbryk.viewPager.LoopViewPager;
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
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import me.relex.circleindicator.CircleIndicator;

public class CoMainActivity extends AppCompatActivity {

    private static final long RIPPLE_DURATION = 250;
    private boolean isOpen = false;
    private Handler handler1, handler2;
    private int delay = 3000;
    private int page1 = 0;
    private int page2 = 0;
    private GuillotineAnimation mGuillotineAnimation;
    private LoopViewPager viewPager1, viewPager2;
    private CircleIndicator indicator1, indicator2;
    private MyPagerAdapter1 myPageAdapter1;
    private MyPagerAdapter2 myPageAdapter2;

    private ImageView fb1, yt1, tw1, is1;


    Runnable runnable1 = new Runnable() {
        public void run() {
            if (myPageAdapter1.getCount() == page1) {
                page1 = 0;
            } else {
                page1++;
            }
            viewPager1.setCurrentItem(page1, true);
            handler1.postDelayed(this, delay);
        }
    };

    Runnable runnable2 = new Runnable() {
        public void run() {
            if (myPageAdapter2.getCount() == page2) {
                page2 = 0;
            } else {
                page2++;
            }
            viewPager2.setCurrentItem(page2, true);
            handler2.postDelayed(this, delay);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        handler1.removeCallbacks(runnable1);
        handler2.removeCallbacks(runnable2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_main);

        initSocialMedia();

        handler1 = new Handler();
        viewPager1 = (LoopViewPager) findViewById(R.id.viewpager);
        indicator1 = (CircleIndicator) findViewById(R.id.indicator);
        myPageAdapter1 = new MyPagerAdapter1();
        viewPager1.setAdapter(myPageAdapter1);
        indicator1.setViewPager(viewPager1);
        viewPager1.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page1 = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        handler2 = new Handler();
        viewPager2 = (LoopViewPager) findViewById(R.id.viewpager2);
        indicator2 = (CircleIndicator) findViewById(R.id.indicator2);
        myPageAdapter2 = new MyPagerAdapter2();
        viewPager2.setAdapter(myPageAdapter2);
        indicator2.setViewPager(viewPager2);
        viewPager2.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page2 = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (Constants.Methods.networkState(getApplicationContext(), (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE))) {

            Version version = new Version();
            version.execute();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FrameLayout root = (FrameLayout) findViewById(R.id.root);
        View contentHamburger = findViewById(R.id.content_hamburger);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.coordinator_nav_drawer, null);
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
                Intent intent = new Intent(getApplicationContext(), CoPaymentActivity.class);
                startActivity(intent);
                mGuillotineAnimation.close();
            }
        });

        LinearLayout mRegistration = (LinearLayout) findViewById(R.id.registration_group);
        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CoRegistrationActivity.class);
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
    protected void onResume() {
        super.onResume();
        handler1.postDelayed(runnable1, delay);
        handler2.postDelayed(runnable2, delay);
        Version version = new Version();
        version.execute();
    }

    private class MyPagerAdapter1 extends PagerAdapter {

        @Override
        public int getCount() {
            return 11;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {

            ImageView imageView = new ImageView(view.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            switch (position) {
                case 0:
                    imageView.setImageResource(R.drawable.train0);
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.photo1);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.photo2);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.photo3);
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.photo4);
                    break;
                case 5:
                    imageView.setImageResource(R.drawable.photo5);
                    break;
                case 6:
                    imageView.setImageResource(R.drawable.photo6);
                    break;
                case 7:
                    imageView.setImageResource(R.drawable.photo7);
                    break;
                case 8:
                    imageView.setImageResource(R.drawable.photo9);
                    break;
                case 9:
                    imageView.setImageResource(R.drawable.photo10);
                    break;
                case 10:
                    imageView.setImageResource(R.drawable.photo11);
                    break;
                default:
                    break;
            }

            view.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            return imageView;
        }
    }

    private class MyPagerAdapter2 extends PagerAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {

            ImageView imageView = new ImageView(view.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            switch (position) {
                case 0:
                    imageView.setImageResource(R.drawable.one);
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.two);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.three);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.four);
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.five);
                    break;
                case 5:
                    imageView.setImageResource(R.drawable.six);
                    break;
                case 6:
                    imageView.setImageResource(R.drawable.seventh);
                    break;
                case 7:
                    imageView.setImageResource(R.drawable.eight);
                    break;
                case 8:
                    imageView.setImageResource(R.drawable.nine);
                    break;
                case 9:
                    imageView.setImageResource(R.drawable.ten);
                    break;
                default:
                    break;
            }

            view.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            return imageView;
        }
    }

    @Override
    public void onBackPressed() {
        if (!isOpen) {
            super.onBackPressed();
        }
        mGuillotineAnimation.close();
    }

    public void initSocialMedia() {
        fb1 = (ImageView) findViewById(R.id.fb1);
        fb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String finalurl;
                PackageManager packageManager = getApplicationContext().getPackageManager();
                try {
                    int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
                    if (versionCode >= 3002850) { //newer versions of fb app
                        finalurl = "fb://facewebmodal/f?href=" + "https://www.facebook.com/TheLocalTrain";
                    } else { //older versions of fb app
                        finalurl = "fb://page/" + "TheLocalTrain";
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    finalurl = "https://www.facebook.com/TheLocalTrain"; //normal web url
                }

                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = finalurl;
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            }
        });

        yt1 = (ImageView) findViewById(R.id.yt1);
        yt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com/channel/UCTzjnbxgPIHYD0_qDBvNOSQ?sub_confirmation=1"));
                startActivity(intent);
            }
        });

        tw1 = (ImageView) findViewById(R.id.tw1);
        tw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                PackageManager packageManager = getApplicationContext().getPackageManager();
                try {
                    // get the Twitter app if possible
                    packageManager.getPackageInfo("com.twitter.android", 0);
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=143588226"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } catch (Exception e) {
                    // no Twitter app, revert to browser
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/TheLocalTrain"));
                }
                startActivity(intent);
            }
        });

        is1 = (ImageView) findViewById(R.id.is1);
        is1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(Intent.ACTION_VIEW);
                String url = "https://www.instagram.com/thelocaltrain";
                PackageManager packageManager = getApplicationContext().getPackageManager();
                try {
                    if (packageManager.getPackageInfo("com.instagram.android", 0) != null) {
                        if (url.endsWith("/")) {
                            url = url.substring(0, url.length() - 1);
                        }
                        final String username = url.substring(url.lastIndexOf("/") + 1);
                        // http://stackoverflow.com/questions/21505941/intent-to-open-instagram-user-profile-on-android
                        intent.setData(Uri.parse("http://instagram.com/_u/" + username));
                        intent.setPackage("com.instagram.android");
                        startActivity(intent);
                        return;
                    }
                } catch (PackageManager.NameNotFoundException ignored) {
                }
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
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
                AlertDialog.Builder builder = new AlertDialog.Builder(CoMainActivity.this);

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


}
