package com.hellwebstudios.zweber.dd.ListAdapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hellwebstudios.zweber.dd.DataObjects.CharRace;
import com.hellwebstudios.zweber.dd.R;

import java.util.List;

/**
 * Created by zweber on 1/2/2017.
 */
public class CharRaceListAdapter extends BaseAdapter {

    private Context mContext;
    private List<CharRace> mRaceList;

    public CharRaceListAdapter(Context mContext, List<CharRace> mRaceList) {
        this.mContext = mContext;
        this.mRaceList = mRaceList;
    }

    @Override
    public int getCount() {
        return mRaceList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRaceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.char_race_selector_item, null);

        TextView tvRaceName = (TextView) v.findViewById(R.id.race_name);

        //Set text for TextViews
        tvRaceName.setText(mRaceList.get(position).getRaceName());

        //Save the RaceID as tag.
        v.setTag(mRaceList.get(position).getRaceID());

        return v;
    }
}
