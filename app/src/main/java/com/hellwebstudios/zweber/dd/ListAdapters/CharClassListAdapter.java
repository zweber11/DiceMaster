package com.hellwebstudios.zweber.dd.ListAdapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hellwebstudios.zweber.dd.DataObjects.CharClass;
import com.hellwebstudios.zweber.dd.R;

import java.util.List;

/**
 * Created by zweber on 1/1/2017.
 */
public class CharClassListAdapter extends BaseAdapter {

    private Context mContext;
    private List<CharClass> mClassList;

    public CharClassListAdapter(Context mContext, List<CharClass> mClassList) {
        this.mContext = mContext;
        this.mClassList = mClassList;
    }

    @Override
    public int getCount() {
        return mClassList.size();
    }

    @Override
    public Object getItem(int position) {
        return mClassList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.char_class_selector_item, null);

        TextView tvClassName = (TextView)v.findViewById(R.id.class_name);

        //Set text for TextViews
        tvClassName.setText(mClassList.get(position).getClassName());

        //Save the ClassID as tag.
        v.setTag(mClassList.get(position).getClassID());

        return v;
    }
}
