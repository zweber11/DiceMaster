package com.hellwebstudios.zweber.dd.ListAdapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.DDCharacter;
import com.hellwebstudios.zweber.dd.R;

import java.util.List;

/**
 * Created by zweber on 12/28/2016.
 */
public class CharacterListAdapter extends BaseAdapter {

    private Context mContext;
    private List<DDCharacter> mCharList;

    DataHelper db;

    //Constructor
    public CharacterListAdapter(Context mContext, List<DDCharacter> mCharList) {
        this.mContext = mContext;
        this.mCharList = mCharList;
    }

    @Override
    public int getCount() {
        return mCharList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCharList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        db = new DataHelper(mContext);

        View v = View.inflate(mContext, R.layout.character_selector_item, null);

        TextView tvCharName = (TextView)v.findViewById(R.id.char_name);
        TextView tvCharClass = (TextView)v.findViewById(R.id.char_class);
        TextView tvCharRace = (TextView)v.findViewById(R.id.char_race);

        //Set text for TextViews
        tvCharName.setText(mCharList.get(position).getCharacterName());

        //ClassID
        Integer classID = mCharList.get(position).getCharClassID();
        tvCharClass.setText(db.getCN(classID));

        //RaceID
        Integer raceID = mCharList.get(position).getCharRaceID();
        tvCharRace.setText(db.getRN(raceID));

        //Save the CharacterID as tag.
        v.setTag(mCharList.get(position).getCharacterID());

        return v;
    }
}
