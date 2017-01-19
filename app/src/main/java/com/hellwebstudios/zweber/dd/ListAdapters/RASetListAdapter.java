package com.hellwebstudios.zweber.dd.ListAdapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.RollAttackSet;
import com.hellwebstudios.zweber.dd.R;

import java.util.List;

/**
 * Created by zweber on 1/10/2017.
 */
public class RASetListAdapter extends BaseAdapter {

    private Context mContext;
    private List<RollAttackSet> mRASetList;

    DataHelper db;

    public RASetListAdapter(Context mContext, List<RollAttackSet> mRASetList) {
        this.mContext = mContext;
        this.mRASetList = mRASetList;
    }

    @Override
    public int getCount() {
        return mRASetList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRASetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        db = new DataHelper(mContext);

        View v = View.inflate(mContext, R.layout.ra_set_selector_item, null);

        //TextViews
        TextView tvDSID = (TextView) v.findViewById(R.id.raset_DSID);

        Integer dsID = mRASetList.get(position).getDSID();
        tvDSID.setText(db.getDSNameByID(dsID));

        //Save the RASID as tag.
        v.setTag(mRASetList.get(position).getID());

        return v;
    }
}
