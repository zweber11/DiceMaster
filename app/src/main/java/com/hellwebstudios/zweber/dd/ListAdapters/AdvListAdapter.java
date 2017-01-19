package com.hellwebstudios.zweber.dd.ListAdapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.Adventure;
import com.hellwebstudios.zweber.dd.R;

import java.util.List;

/**
 * Created by zweber on 1/4/2017.
 */
public class AdvListAdapter extends BaseAdapter {

    //Props
    private Context mContext;
    private List<Adventure> mAdvList;

    DataHelper db;

    //Constructor
    public AdvListAdapter(Context mContext, List<Adventure> mAdvList) {
        this.mContext = mContext;
        this.mAdvList = mAdvList;
    }

    @Override
    public int getCount() {
        return mAdvList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAdvList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        db = new DataHelper(mContext);

        View v = View.inflate(mContext, R.layout.adventure_selector_item, null);

        //TextView initialization.
        TextView tvName = (TextView) v.findViewById(R.id.adv_name);
        TextView tvDesc = (TextView) v.findViewById(R.id.adv_desc);
        TextView tvChar = (TextView) v.findViewById(R.id.adv_char);
//        TextView tvNumChaps = (TextView) v.findViewById(R.id.adv_chaps);

        //set Text.
        tvName.setText(mAdvList.get(position).getName());
        tvDesc.setText(mAdvList.get(position).getDesc());

        //CharID
        Integer charID = mAdvList.get(position).getCharID();
        tvChar.setText(db.getCharName(charID));

//        tvNumChaps.setText(mAdvList.get(position).getNumChapters());
//        String num = mAdvList.get(position).getNumChapters() + "";
//        tvNumChaps.setText(num);

        //Save the AdvID as tag.
        v.setTag(mAdvList.get(position).getAdvID());

        return v;
    }
}
