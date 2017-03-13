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
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
