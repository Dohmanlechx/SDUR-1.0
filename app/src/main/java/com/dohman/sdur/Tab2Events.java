package com.dohman.sdur;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    private ListView mEventListView;
    private EventListAdapter mEventListAdapter;
    private List<Event> mEventList;

    private DatabaseReference ref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Starts.");
        // Connects to the right layout file.
        View tab2view = inflater.inflate(R.layout.tab2_events, container, false);

        mEventListView = tab2view.findViewById(R.id.listview_events);
        mEventList = new ArrayList<>();

        // TODO Koppla rätt och läsa in data, sen komma på snyggare sätt att koda
        ref = FirebaseDatabase.getInstance().getReference().child("Events");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshotChildren : dataSnapshot.getChildren()) {
                    Event event = dataSnapshot.getValue(Event.class);
                    String tid = (String) dataSnapshot.child("tid").getValue();
                    String infotext = (String) dataSnapshot.child("infotext").getValue();
                    String date = (String) dataSnapshot.child("datum").getValue();
                }





//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    Event event = ds.getValue(Event.class);
//                    mEventList.add(event);
//                    Log.d(TAG, "onDataChange: added activity: " + event.getLaenk());
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // TODO Lägga in alla events på en gång från Firebase med hjälp av en for loop.

        // Event 1.
//        mEventList.add();

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(getResources().getColor(R.color.colorAccent));
//        shape.setStroke(5, getResources().getColor(R.color.colorAccent));
//        shape.setCornerRadius(50);

        // Initializing the adapter.
        Log.d(TAG, "onCreateView: Initializing the adapter...");
        mEventListAdapter = new EventListAdapter(getContext(), mEventList);
        mEventListView.setAdapter(mEventListAdapter);
        mEventListView.setDividerHeight(10);
        mEventListView.setBackground(shape);

        // OnClickListener on all views.
        mEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Do something
                // Ex: display msg with product id get from view.getTag
                Toast.makeText(getContext(), "Clicked id = " + view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });

        Log.d(TAG, "onCreateView: Ends.");
        return tab2view;
    }
}
