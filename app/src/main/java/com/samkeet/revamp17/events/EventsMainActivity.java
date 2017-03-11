package com.samkeet.revamp17.events;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.samkeet.revamp17.R;
import com.samkeet.revamp17.myboom.AllMoveDownAnimatorAdapter;
import com.samkeet.revamp17.myboom.CardStackView;

import java.util.Arrays;

public class EventsMainActivity extends AppCompatActivity {

    /*public static Integer[] TEST_DATAS = new Integer[]{
            R.color.color_1,
            R.color.color_2,
            R.color.color_3,
            R.color.color_4,
            R.color.color_5,
            R.color.color_6,
            R.color.color_7,
            R.color.color_8,
            R.color.color_9,
            R.color.color_10,
            R.color.color_11,
            R.color.color_12,
            R.color.color_13,
            R.color.color_14,
            R.color.color_15,
            R.color.color_16,
            R.color.color_17,
            R.color.color_18,
            R.color.color_19,
            R.color.color_20,
            R.color.color_21,
            R.color.color_22,
            R.color.color_23,
            R.color.color_24,
            R.color.color_25,
            R.color.color_26
    };*/

    private CardStackView mStackView;
    private EventsAdapter mEventsAdapter;
    private NavigationTabStrip mNavigationTabStrip;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_main);

        mNavigationTabStrip = (NavigationTabStrip) findViewById(R.id.strip);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));


        mNavigationTabStrip.setViewPager(mViewPager, 0);
        mNavigationTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("PAGE2", position + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        mStackView = (CardStackView) findViewById(R.id.stackview_main);
//        mEventsAdapter = new EventsAdapter(this);
//        mStackView.setAdapter(mEventsAdapter);
//
//        new Handler().postDelayed(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        mEventsAdapter.updateData(Arrays.asList(TEST_DATAS));
//                    }
//                }
//                , 200
//        );
//
//        mStackView.setAnimatorAdapter(new AllMoveDownAnimatorAdapter(mStackView));

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return new FirstFragment();
                case 1:
                    return new SecondFragment();
                case 2:
                    return new ThirdFragment();
                case 3:
                    return new FourthFragment();
                default:
                    return new FirstFragment();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
