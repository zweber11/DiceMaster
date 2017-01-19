package com.hellwebstudios.zweber.dd.ListAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hellwebstudios.zweber.dd.DataObjects.Skill;
import com.hellwebstudios.zweber.dd.R;

import java.util.List;

/**
 * Created by zweber on 12/28/2016.
 */
public class SkillListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Skill> mSkillList;

    public SkillListAdapter(Context mContext, List<Skill> mSkillList) {
        this.mContext = mContext;
        this.mSkillList = mSkillList;
    }

    @Override
    public int getCount() {
        return mSkillList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSkillList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.skill_selector_item, null);

        TextView tvSkillName = (TextView)v.findViewById(R.id.skill_name);
        TextView tvSkillLastUsed = (TextView)v.findViewById(R.id.skill_last_used);
//        TextView tvFavFlag = (TextView)v.findViewById(R.id.skill_favFlag);

        //Set text for TextViews
        tvSkillName.setText(mSkillList.get(position).getSkillName());
        tvSkillLastUsed.setText(mSkillList.get(position).getLastUsed());

        //Favorite flag.
//        int fav = mSkillList.get(position).getFavorite();
//        if (fav == 0) //Unselected.
//        {
//            tvFavFlag.setText("No");
//            tvFavFlag.setTextColor(Color.parseColor("#E43838"));
//        }
//        else //Selected.
//        {
//            tvFavFlag.setText("Yes");
//            tvFavFlag.setTextColor(Color.parseColor("#7DD481"));
//        }

        //Save the SkillID as tag.
        v.setTag(mSkillList.get(position).getSkillID());

        return v;
    }

}
