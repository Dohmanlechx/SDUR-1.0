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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dohman on 2018-03-17.
 */

public class Tab2Events extends Fragment {
    private static final String TAG = "Tab2Events";

    private FragmentActivity myContext;

    private ListView mEventListView;
    private EventListAdapter mEventListAdapter;
    private List<Event> mEventList;
    private Event event;

    private DatabaseReference eventRef;
//    private GradientDrawable shape;

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
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "TAB2, onCreateView: Starts.");
        // Connects to the right layout file.
        View tab2view = inflater.inflate(R.layout.tab2_events, container, false);

        mEventListView = tab2view.findViewById(R.id.listview_events);
        mEventList = new ArrayList<>();

        // Initializing the database.
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        eventRef = database.getReference("Events");

        // Receiving data from database into the arraylist.
        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: Starts.");
                mEventList.clear();
                // Going through all the children.
                for (DataSnapshot children : dataSnapshot.getChildren()) {
                    event = children.getValue(Event.class);
                    mEventList.add(event);
                }
                // Initializing the adapter.
                Log.d(TAG, "onCreateView: Initializing the adapter...");
                mEventListAdapter = new EventListAdapter(getContext(), mEventList);
                mEventListView.setAdapter(mEventListAdapter);
                mEventListView.setDividerHeight(8);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: Error! " + databaseError);
            }
        });

        // OnClickListener on all views.
        mEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = mEventList.get(position).getLaenk();
//                Intent facebookIntent = openFacebook(myContext, url);
                if (url.startsWith("http") || url.startsWith("www")) {
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                    String facebookUrl = getFacebookPageURL(myContext, url);
                    facebookIntent.setData(Uri.parse(facebookUrl));
                    startActivity(facebookIntent);
                } else {
                    Toast.makeText(myContext, getString(R.string.toast_nolink), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Log.d(TAG, "onCreateView: Ends.");
        return tab2view;
    }

    // Method to get the right URL to use in the intent.
    private String getFacebookPageURL(Context context, String url) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { // Newer versions of Facebook app.
                Log.d(TAG, "getFacebookPageURL: Found APP.");
                return "fb://facewebmodal/f?href=" + url;
            } else { // Older versions of Facebook app
                Log.d(TAG, "getFacebookPageURL: Found APP.");
                return "fb://page/" + url.replaceAll("\\D+","");
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "getFacebookPageURL: Did not find APP.", e);
            return url; // Normal web url.
        }
    }
}