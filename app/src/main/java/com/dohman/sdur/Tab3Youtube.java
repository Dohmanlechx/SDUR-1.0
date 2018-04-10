package com.dohman.sdur;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * Created by Dohman on 2018-03-17.
 */

public class Tab3Youtube extends Fragment implements YouTubePlayer.OnInitializedListener {
    private static final String TAG = "Tab3Youtube";

    private FragmentActivity myContext;

    public static String FACEBOOK_URL = "https://www.facebook.com/groups/193360381417515/";
    public static String FACEBOOK_PAGE_ID = "193360381417515";

    // My API-key for Google.
    private static final String API_KEY = "AIzaSyA7tz7XjWHy_cYyRk69xFfE2demIjX7gYE";
    // My API-key for YouTube.
    private static final String YOUTUBE_VIDEO_ID = "XU76X00N0xY";
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    TextView textViewDBHeader;
    TextView textViewDBText;

    private YouTubePlayerSupportFragment mPlayerSupportFragment;

    // Creating an "myContext" from this method.
    @Override
    public void onAttach(Activity activity) {
        if (activity instanceof FragmentActivity) {
            myContext = (FragmentActivity) activity;
        }
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "TAB3, onCreateView: Starts.");
        // Connects to the right layout file.
        View tab3view = inflater.inflate(R.layout.tab3_youtube, container, false);

        // Finding the TextViews.
        textViewDBHeader = tab3view.findViewById(R.id.tv_header_du_bestammer);
        textViewDBText = tab3view.findViewById(R.id.tv_du_bestammer);

        // Finding the Facebook button and making it clickable.
        // It takes the user to the Facebook-app and the group there.
        textViewDBText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent facebookIntent = openFacebook(myContext);
//                startActivity(facebookIntent);
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(myContext);
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            }
        });

        // Initializing the Youtube-clip.
        mPlayerSupportFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment_container, mPlayerSupportFragment).commit();

        mPlayerSupportFragment.initialize(API_KEY, this);

        transaction = getFragmentManager().beginTransaction();
        transaction.commit();

        Log.d(TAG, "TAB3, onCreateView: Ends.");

        return tab3view;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        // If the video successfully play.
        Log.d(TAG, "onInitializationSuccess: Video playing ok.");
        if (!wasRestored) {
            player.cueVideo(YOUTUBE_VIDEO_ID);
            player.loadVideo(YOUTUBE_VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorResults) {
        // If the video failed to play.
        Log.d(TAG, "onInitializationFailure: Video failed to load.");
        if (errorResults.isUserRecoverableError()) {
            errorResults.getErrorDialog(myContext, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format("Misslyckades att spela upp videon. (%1$s)", errorResults.toString());
            Toast.makeText(myContext, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    // Method taking the user to the Facebook App,
    // or to the Facebook in browser if fails.
    private static Intent openFacebook(Context context) {
        Log.d(TAG, "openFacebook: Starts.");
        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0);
            //  return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/193360381417515/"));
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/groups/193360381417515/"));
        } catch (PackageManager.NameNotFoundException e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/groups/193360381417515/"));
        }
    }

    // Method to get the right URL to use in the intent.
    private String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { // Newer versions of Facebook app.
                Log.d(TAG, "getFacebookPageURL: Found APP.");
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { // Older versions of Facebook app
                Log.d(TAG, "getFacebookPageURL: Found APP.");
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "getFacebookPageURL: Did not find APP.", e);
            return FACEBOOK_URL; // Normal web url.
        }
    }
}
