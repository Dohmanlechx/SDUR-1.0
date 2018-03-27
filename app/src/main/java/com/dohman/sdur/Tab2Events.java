package com.dohman.sdur;

import android.content.Context;
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
import android.widget.TextView;
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

    private TextView tvtest;

    private String event1clock;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Starts.");
        // Connects to the right layout file.
        View tab2view = inflater.inflate(R.layout.tab2_events, container, false);

        // TODO Koppla rätt och läsa in data, sen komma på snyggare sätt att koda
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference eventRef = database.getReference("Events");
        DatabaseReference event1Ref = eventRef.child("Event1");
//        DatabaseReference event1ClockRef = event1Ref.child("Clock");
//        DatabaseReference event1DateRef = event1Ref.child("Date");
//        DatabaseReference event1LinkRef = event1Ref.child("Link");
//        DatabaseReference event1NameRef = event1Ref.child("Name");
//        DatabaseReference event1PlaceRef = event1Ref.child("Place");
//        DatabaseReference event1TextRef = event1Ref.child("Text");

        mEventListView = tab2view.findViewById(R.id.listview_events);
        mEventList = new ArrayList<>();

        tvtest = tab2view.findViewById(R.id.textViewTest);

        // Adding data from database.
        event1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                event1clock = dataSnapshot.child("Clock").getValue(String.class);
                Log.d(TAG, "onDataChange: Clock data is " + event1clock);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

        tvtest.setText(event1clock);

        // TODO Lägga in alla events på en gång från Firebase med hjälp av en for loop.

        // Event 1.
        mEventList.add(new Event("GOKART!!!", "Vi åker gokart! Vi träffas vid McDonalds i Alvik! Pris medlem: 100 kr, Icke medlem: 400 kr", "2018-03-22", event1clock, "på dukis", "www.gokart.se"));
        // Event 2.
        // Event 3.
        // Event 4.
        // Event 5.
        // Event 6.
        // Event 7.
        // Event 8.
        // Event 9.

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
