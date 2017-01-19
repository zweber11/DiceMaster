package com.hellwebstudios.zweber.dd.ListAdapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.RollAttack;
import com.hellwebstudios.zweber.dd.R;

import java.util.List;

/**
 * Created by zweber on 1/10/2017.
 */
public class RollAttackListAdapter extends BaseAdapter {

    private Context mContext;
    private List<RollAttack> mRAList;

    DataHelper db;

    public RollAttackListAdapter(Context mContext, List<RollAttack> mRAList) {
        this.mContext = mContext;
        this.mRAList = mRAList;
    }

    @Override
    public int getCount() {
        return mRAList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRAList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        db = new DataHelper(mContext);

        View v = View.inflate(mContext, R.layout.roll_attack_selector_item, null);

        //TextViews
        TextView tvdID = (TextView) v.findViewById(R.id.ra_DID);

        //DiceID
        Integer dID = mRAList.get(position).getDID();
        tvdID.setText(db.getDiceName(dID));

        //Roll
        TextView tvRoll = (TextView) v.findViewById(R.id.ra_roll);
        String r = mRAList.get(position).getRoll() + "";
        tvRoll.setText(r);

        //Save the RAID as tag.
        v.setTag(mRAList.get(position).getID());

        return v;
    }
}
