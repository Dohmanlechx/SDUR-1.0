package com.dohman.sdur;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // SectionPageAdapter is that puts fragments together into an tab-menu.
    private SectionsPageAdapter mAdapter;
    // ViewPager is often used in combination with Fragments. Putting it together.
    private ViewPager mViewPager;
    // TabLayout is what it stands, I guess, setting up the layout for Tab-Bars.
    private TabLayout mTabLayout;
    // Module scope because we need to dismiss it in onStop to avoid memory leaks.
    private AlertDialog mDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Starts.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Setting the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Creating the adapter for those fragments.
        mAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Setting up the ViewPager with the sections mAdapter.
        // Without the ViewPager, you won't be able to swipe between tabs.
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        mTabLayout = findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        // Selecting the "Events" tab as home/launch screen, see method.
        selectPage(1);

        Log.d(TAG, "onCreate: Ends.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflating the menu; this adds items to the action bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handles action bar item clicks.
        int id = item.getItemId();

        switch (id) {
            case R.id.mainmenu_about:
                showAboutDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showAboutDialog() {
        Log.d(TAG, "showAboutDialog: Starts.");
        View messageView = getLayoutInflater().inflate(R.layout.about, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setIcon(R.mipmap.ic_launcher);

        builder.setView(messageView);

        mDialog = builder.create();
        mDialog.setCanceledOnTouchOutside(true);

        TextView versionTv = messageView.findViewById(R.id.about_version);
        versionTv.setText("v" + BuildConfig.VERSION_NAME);

        TextView abouturl = messageView.findViewById(R.id.about_url);
        if (abouturl != null) {
            abouturl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String s = ((TextView) v).getText().toString();
                    intent.setData(Uri.parse(s));
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(MainActivity.this, getString(R.string.toast_notfoundbrowser), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        // THE EMAIL BUTTON.
        ImageButton buttonEmail = messageView.findViewById(R.id.button_contactus);
        buttonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEmail();
            }
        });
        // THE FACEBOOK BUTTON.
        ImageButton buttonFacebook = messageView.findViewById(R.id.button_facebook);
        buttonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFacebook();
            }
        });
        // THE INSTAGRAM BUTTON.
        ImageButton buttonInstagram = messageView.findViewById(R.id.button_instagram);
        buttonInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callInstagram();
            }
        });

        mDialog.show();
    }

    private void callEmail() {
        Log.d(TAG, "callEmail: Starts.");
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "florian@sdur.se", null));
        try {
            // Letting the user to choose email app.
            startActivity(Intent.createChooser(emailIntent, getString(R.string.choose_emailclient)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(MainActivity.this, getString(R.string.toast_notfoundemail), Toast.LENGTH_LONG).show();
        }
    }

    //TODO denna öppnar inte appen
    private void callFacebook() {
        Log.d(TAG, "callFacebook: Starts.");
        String url = "https://www.facebook.com/n/?SDUR1937";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        // It opens automatically in the Facebook app if available, otherwise browser.
        startActivity(browserIntent);
    }

    private void callInstagram() {
        Log.d(TAG, "callInstagram: Starts.");
        Uri uri = Uri.parse("https://instagram.com/_u/sdur08");
        Intent instagramIntent = new Intent(Intent.ACTION_VIEW, uri);
        instagramIntent.setPackage("com.instagram.android");
        try {
            startActivity(instagramIntent);
        } catch (ActivityNotFoundException e) {
            // Opening the browser if Instagram app is not installed.
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/sdur08")));
        }
    }

    @Override
    protected void onStop() {
        // Module scope because we need to dismiss it in onStop to avoid memory leaks.
        super.onStop();
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    // Adding fragments & titles to an mAdapter, and finishing it off
    // with setting the mAdapter to our ViewPager.
    private void setupViewPager(ViewPager viewPager) {
        mAdapter.addFragment(new Tab1Membership(), getString(R.string.tab_text_1));
        mAdapter.addFragment(new Tab2Events(), getString(R.string.tab_text_2));
        mAdapter.addFragment(new Tab3Youtube(), getString(R.string.tab_text_3));
        viewPager.setAdapter(mAdapter);
    }

    void selectPage(int pageIndex) {
        mTabLayout.setScrollPosition(pageIndex, 0f, true);
        mViewPager.setCurrentItem(pageIndex);
    }
}

