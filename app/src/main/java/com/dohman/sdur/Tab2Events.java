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
    private Event event;

    private DatabaseReference eventRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Starts.");
        // Connects to the right layout file.
        View tab2view = inflater.inflate(R.layout.tab2_events, container, false);

        mEventListView = tab2view.findViewById(R.id.listview_events);
        mEventList = new ArrayList<>();

        // TODO Koppla rätt och läsa in data, sen komma på snyggare sätt att koda
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        eventRef = database.getReference("Events");

        //TODO Logd körs 4 ggr, vilket är rätt, problemet är nu bara att värdena returnerar null
        // TODO alltså finns det 4 st children i Firebase

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot children : dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: Starts.");
                    event = children.getValue(Event.class);
                    Log.d(TAG, "onDataChangeTEST: " + event.getNamn());
//                    String name = children.child("namn").getValue(String.class);
//                    Log.d(TAG, "onDataChange: Name: " + name);
//                    event.setNamn(name);
//                    String infotext = children.child("infotext").getValue(String.class);
//                    Log.d(TAG, "onDataChange: Info: " + infotext);
//                    event.setInfotext(infotext);
//                    String date = children.child("datum").getValue(String.class);
//                    Log.d(TAG, "onDataChange: Date: " + date);
//                    event.setDatum(date);
//                    String time = children.child("tid").getValue(String.class);
//                    Log.d(TAG, "onDataChange: Time: " + time);
//                    event.setTid(time);
//                    String place = children.child("plats").getValue(String.class);
//                    Log.d(TAG, "onDataChange: Place: " + place);
//                    event.setPlats(place);
//                    String link = children.child("laenk").getValue(String.class);
//                    Log.d(TAG, "onDataChange: Link: " + link);
//                    event.setLaenk(link);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        eventRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot children : dataSnapshot.getChildren()) {
//                    Log.d(TAG, "onDataChange: Starts.");
//                    Event event = children.getValue(Event.class);
//                    String name = children.child("namn").getValue(String.class);
//                    Log.d(TAG, "onDataChange: Name: " + name);
//                    event.setNamn(name);
//                    String infotext = children.child("infotext").getValue(String.class);
//                    Log.d(TAG, "onDataChange: Info: " + infotext);
//                    event.setInfotext(infotext);
//                    String date = children.child("datum").getValue(String.class);
//                    Log.d(TAG, "onDataChange: Date: " + date);
//                    event.setDatum(date);
//                    String time = children.child("tid").getValue(String.class);
//                    Log.d(TAG, "onDataChange: Time: " + time);
//                    event.setTid(time);
//                    String place = children.child("plats").getValue(String.class);
//                    Log.d(TAG, "onDataChange: Place: " + place);
//                    event.setPlats(place);
//                    String link = children.child("laenk").getValue(String.class);
//                    Log.d(TAG, "onDataChange: Link: " + link);
//                    event.setLaenk(link);
//
//                    mEventList.add(new Event(name, infotext, date, time, place, link));
//
////                    addEvent(event);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        // Testkoden nedanför funkar.
//        for (int i = 0; mEventList.size())
        if (event != null) {
            mEventList.add(new Event(event.getNamn(), event.getInfotext(), event.getDatum(), event.getTid(), event.getPlats(), event.getLaenk()));
        }
        //        mEventList.add(new Event("textnamn", "textinfo", "testdatum", "testtid", "testplats","testlank"));


        // TODO Lägga in alla events på en gång från Firebase med hjälp av en for loop.

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(getResources().getColor(R.color.colorAccent));
        shape.setStroke(5, getResources().getColor(R.color.colorAccent));
        shape.setCornerRadius(10);

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

//    private void addEvent(Event event) {
//        Log.d(TAG, "addEvent: Starts.");
//        mEventList.add(event);
//    }
}
