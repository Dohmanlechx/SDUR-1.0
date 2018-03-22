package com.dohman.sdur;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // SectionPageAdapter is that puts fragments together into an tab-menu.
    private SectionsPageAdapter mAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Starts.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // TODO Is this line even necessary?
        mAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Setting up the ViewPager with the sections mAdapter.
        // Without the ViewPager, you won't be able to swipe between tabs.
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Log.d(TAG, "onCreate: Ends.");
    }

    // Adding fragments & titles to an mAdapter, and finishing it off
    // with setting the mAdapter to our ViewPager.
    private void setupViewPager(ViewPager viewPager) {
        mAdapter.addFragment(new Tab1Membership(), getString(R.string.tab_text_1));
        mAdapter.addFragment(new Tab2Events(), getString(R.string.tab_text_2));
        mAdapter.addFragment(new Tab3Youtube(), getString(R.string.tab_text_3));
        viewPager.setAdapter(mAdapter);
    }
}

