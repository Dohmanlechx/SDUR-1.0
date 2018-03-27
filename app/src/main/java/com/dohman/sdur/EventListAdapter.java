package com.dohman.sdur;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dohman on 2018-03-22.
 */

// This class is an adapter for the ListView of events.

public class EventListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Event> mEventList;

    public EventListAdapter(Context context, List<Event> eventList) {
        mContext = context;
        mEventList = eventList;
    }

    @Override
    public int getCount() {
        return mEventList.size();
    }

    @Override
    public Object getItem(int position) {
        return mEventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflating the layout into a view.
        View view = View.inflate(mContext, R.layout.event_list, null);

        // Finding the textviews.
        TextView tvEventDate = view.findViewById(R.id.tv_eventdate);
        TextView tvEventName = view.findViewById(R.id.tv_eventname);
        TextView tvEventPlace = view.findViewById(R.id.tv_eventplace);
        TextView tvEventClock = view.findViewById(R.id.tv_eventclock);
        TextView tvEventText = view.findViewById(R.id.tv_eventtext);
        TextView tvEventLink = view.findViewById(R.id.tv_eventlink);

        // Setting text for those textviews.
        tvEventDate.setText(mEventList.get(position).getDate());
        tvEventName.setText(mEventList.get(position).getName());
        tvEventPlace.setText(mEventList.get(position).getPlace());
        tvEventClock.setText(mEventList.get(position).getClock());
        tvEventText.setText(mEventList.get(position).getText());
        tvEventLink.setText(mEventList.get(position).getLink());

        // Saving event id to tag.
        view.setTag(mEventList.get(position).getId());

        return view;
    }
}
