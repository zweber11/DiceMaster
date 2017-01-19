package com.hellwebstudios.zweber.dd.ListAdapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.RollSkill;
import com.hellwebstudios.zweber.dd.R;

import java.util.List;

/**
 * Created by zweber on 1/8/2017.
 */
public class RollSkillListAdapter extends BaseAdapter {

    private Context mContext;
    private List<RollSkill> mRSList;

    DataHelper db;

    public RollSkillListAdapter(Context mContext, List<RollSkill> mRSList) {
        this.mContext = mContext;
        this.mRSList = mRSList;
    }

    @Override
    public int getCount() {
        return mRSList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRSList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        db = new DataHelper(mContext);

        View v = View.inflate(mContext, R.layout.roll_skill_selector_item, null);

        //Textviews
        TextView tvSkillID = (TextView) v.findViewById(R.id.rs_skillID);

        //SkillID
        Integer skillID = mRSList.get(position).getSkillID();
        tvSkillID.setText(db.getSkillName(skillID));

        TextView tvRoll = (TextView) v.findViewById(R.id.rs_roll);
        String r = mRSList.get(position).getRoll() + "";
        tvRoll.setText(r);

        //Save the RSID as tag.
        v.setTag(mRSList.get(position).getID());

        return v;
    }
}
