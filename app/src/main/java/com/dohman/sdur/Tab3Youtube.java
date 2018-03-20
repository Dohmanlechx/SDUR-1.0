package com.dohman.sdur;

import android.app.Activity;
import android.graphics.Typeface;
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

        // Declaring fonts.
        Typeface custom_font_1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface custom_font_2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");

        // Setting the fonts.
        textViewDBHeader.setTypeface(custom_font_1);
        textViewDBText.setTypeface(custom_font_2);

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
}
