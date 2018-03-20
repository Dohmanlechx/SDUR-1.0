package com.dohman.sdur;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Starts.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        // Setting up the ViewPager with the sections adapter.
        // Without the ViewPager, you won't be able to swipe between tabs.
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        getSupportFragmentManager().beginTransaction().replace(R.id.tab3_youtube, fragment).addToBackStack(null).commit();

        Log.d(TAG, "onCreate: Ends.");
    }

    // Adding fragments & titles to an adapter, and finishing it off
    // with setting the adapter to our ViewPager.
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Membership(), getString(R.string.tab_text_1));
        adapter.addFragment(new Tab2Events(), getString(R.string.tab_text_2));
        adapter.addFragment(new Tab3Youtube(), getString(R.string.tab_text_3));
        viewPager.setAdapter(adapter);
    }
}

