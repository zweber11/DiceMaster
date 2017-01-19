package com.hellwebstudios.zweber.dd.ListAdapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.DiceSetDie;
import com.hellwebstudios.zweber.dd.R;

import java.util.List;

/**
 * Created by zweber on 1/6/2017.
 */
public class DSDListAdapter extends BaseAdapter {

    private Context mContext;
    private List<DiceSetDie> mDSDList;

    DataHelper db;

    public DSDListAdapter(Context mContext, List<DiceSetDie> mDSDList) {
        this.mContext = mContext;
        this.mDSDList = mDSDList;
    }

    @Override
    public int getCount() {
        return mDSDList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDSDList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        db = new DataHelper(mContext);

        View v = View.inflate(mContext, R.layout.dsd_selector_item, null);

        //Text Views
        TextView tvName = (TextView) v.findViewById(R.id.dice_name);

        //Set text
        Integer dID = mDSDList.get(position).getDiceID();
        tvName.setText(db.getDiceName(dID));

        //Save the CharID as tag.
        v.setTag(mDSDList.get(position).getID());

        return v;
    }
}
