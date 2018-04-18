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
    private View mView;

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
        mView = View.inflate(mContext, R.layout.event_list, null);

        // Finding the textviews.
        TextView tvEventDate = mView.findViewById(R.id.tv_eventdate);
        TextView tvEventName = mView.findViewById(R.id.tv_eventname);
        TextView tvEventPlace = mView.findViewById(R.id.tv_eventplace);
        TextView tvEventClock = mView.findViewById(R.id.tv_eventclock);
        TextView tvEventText = mView.findViewById(R.id.tv_eventtext);
        TextView tvEventLink = mView.findViewById(R.id.tv_eventlink);

        // Setting text for those textviews.
        tvEventDate.setText(mEventList.get(position).getDatum());
        tvEventName.setText(mEventList.get(position).getNamn());
        tvEventPlace.setText(mEventList.get(position).getPlats());
        tvEventClock.setText(mEventList.get(position).getTid());
        tvEventText.setText(mEventList.get(position).getInfotext());
        tvEventLink.setText(mEventList.get(position).getLaenk());

        // Alternate backgroundcolor for the "cards".
        if (position % 2 == 0) {
            mView.setBackgroundColor(mView.getResources().getColor(R.color.colorPrimaryGray));
        } else {
            mView.setBackgroundColor(mView.getResources().getColor(R.color.colorPrimaryGrayDark));
        }

        // Saving event id to tag.
        mView.setTag(mEventList.get(position).getId());

        return mView;
    }
}