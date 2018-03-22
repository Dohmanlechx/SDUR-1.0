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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Starts.");
        // Connects to the right layout file.
        View view = inflater.inflate(R.layout.tab2_events, container, false);

        mEventListView = view.findViewById(R.id.listview_events);
        mEventList = new ArrayList<>();

        // Adding data from database.

        // TODO Lägga in alla events på en gång från Firebase med hjälp av en for loop.

        // Event 1.
        mEventList.add(new Event("GOKART!!!", "gokarttext", "2018-03-22", "13:00", "på dukis", "www.gokart.se"));
        // Event 2.
        mEventList.add(new Event("kids!!!", "kidstext", "2018-03-24", "16:00", "på dövashus", "www.kids.se"));
        // Event 3.
        mEventList.add(new Event("fest!!!", "festtext", "2018-04-22", "13:00", "på dukis", "www.dukis.se"));
        // Event 4.
        mEventList.add(new Event("GOKART!!!", "gokarttext", "2018-03-22", "13:00", "på dukis", "www.gokart.se"));
        // Event 5.
        mEventList.add(new Event("kids!!!", "kidstext", "2018-03-24", "16:00", "på dövashus", "www.kids.se"));
        // Event 6.
        mEventList.add(new Event("fest!!!", "festtext", "2018-04-22", "13:00", "på dukis", "www.dukis.se"));
        // Event 7.
        mEventList.add(new Event("GOKART!!!", "gokarttext", "2018-03-22", "13:00", "på dukis", "www.gokart.se"));
        // Event 8.
        mEventList.add(new Event("kids!!!", "kidstext", "2018-03-24", "16:00", "på dövashus", "www.kids.se"));
        // Event 9.
        mEventList.add(new Event("fest!!!", "festtext", "2018-04-22", "13:00", "på dukis", "www.dukis.se"));

        // TODO Fixa dividers (gaps)
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(getResources().getColor(R.color.colorPrimary));
        shape.setStroke(10, getResources().getColor(R.color.colorAccent));
        shape.setCornerRadius(15);

        // Initializing the adapter.
        Log.d(TAG, "onCreateView: Initializing the adapter...");
        mEventListAdapter = new EventListAdapter(getContext(), mEventList);
        mEventListView.setAdapter(mEventListAdapter);
        mEventListView.setDivider(this.getResources().getDrawable(R.drawable.transperant_color));
        mEventListView.setDividerHeight(20);
        mEventListView.setBackground(shape);

        mEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Do something
                // Ex: display msg with product id get from view.getTag
                Toast.makeText(getContext(), "Clicked id = " + view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });

        Log.d(TAG, "onCreateView: Ends.");
        return view;
    }
}
