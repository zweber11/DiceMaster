package com.hellwebstudios.zweber.dd.ListAdapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.DiceSet;
import com.hellwebstudios.zweber.dd.R;

import java.util.List;

/**
 * Created by zweber on 1/6/2017.
 */
public class DiceSetAdapter extends BaseAdapter {

    private Context mContext;
    private List<DiceSet> mDSList;

    DataHelper db;

    public DiceSetAdapter(Context mContext, List<DiceSet> mDSList) {
        this.mContext = mContext;
        this.mDSList = mDSList;
    }

    @Override
    public int getCount() {
        return mDSList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDSList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        db = new DataHelper(mContext);

        View v = View.inflate(mContext, R.layout.dice_set_selector_item, null);

        //Textviews
        TextView tvName = (TextView) v.findViewById(R.id.ds_name);
        TextView tvCharID = (TextView) v.findViewById(R.id.ds_charID);

        //Set text
        tvName.setText(mDSList.get(position).getName());

        //CharID
        Integer charID = mDSList.get(position).getCharID();
        tvCharID.setText(db.getCharName(charID));

        //Save the CharID as tag.
        v.setTag(mDSList.get(position).getID());

        return v;
    }
}
