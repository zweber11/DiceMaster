package com.hellwebstudios.zweber.dd.ListAdapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.Chapter;
import com.hellwebstudios.zweber.dd.R;

import java.util.List;

/**
 * Created by zweber on 1/4/2017.
 */
public class ChapsListAdapter extends BaseAdapter {

    //Props
    private Context mContext;
    private List<Chapter> mChapList;

    DataHelper db;

    public ChapsListAdapter(Context mContext, List<Chapter> mChapList) {
        this.mContext = mContext;
        this.mChapList = mChapList;
    }

    @Override
    public int getCount() {
        return mChapList.size();
    }

    @Override
    public Object getItem(int position) {
        return mChapList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        db = new DataHelper(mContext);

        View v = View.inflate(mContext, R.layout.chaps_selector_item, null);

        //TextView initialization.
        TextView chapName = (TextView) v.findViewById(R.id.chap_name);

        //Set Text.
        chapName.setText(mChapList.get(position).getName());

        //Save the ChapID as tag.
        v.setTag(mChapList.get(position).getChapID());

        return v;
    }
}
