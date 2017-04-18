package com.hellwebstudios.zweber.dd.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.DiceSet;
import com.hellwebstudios.zweber.dd.DataObjects.DiceSetDie;
import com.hellwebstudios.zweber.dd.R;

import java.util.List;
import java.util.Map;

/**
 * Created by zweber on 4/16/2017.
 */

public class DiceSetExListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<DiceSet> diceSets;
    private Map<String, List<DiceSetDie>> dsd;

    public DiceSetExListAdapter(Context context, List<DiceSet> diceSets, Map<String, List<DiceSetDie>> dsd) {
        this.context = context;
        this.diceSets = diceSets;
        this.dsd = dsd;
    }

    @Override
    public int getGroupCount() {
        return diceSets.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dsd.get(diceSets.get(groupPosition).Name).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        Integer dsID = diceSets.get(groupPosition).ID;
        return dsID;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        DataHelper db = new DataHelper(context);
        String dName = db.getDiceName(dsd.get(diceSets.get(groupPosition)).get(childPosition).DiceID);
        return dName;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        DataHelper db = new DataHelper(context);
        Integer dsID = (Integer) getGroup(groupPosition);
        DiceSet dsFromView = db.getDSByID(dsID);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_ex_par_object, null);
        }

        //Manipulate the textViews to either display data/hide the ones you don't need.
        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtExParent1);
        txtTitle.setText(dsFromView.Name);

        TextView txt2 = (TextView) convertView.findViewById(R.id.txtExParent2);
        txt2.setText(db.getCharName(dsFromView.CharID));

        TextView txt3 = (TextView) convertView.findViewById(R.id.txtExParent3);
        txt3.setVisibility(View.GONE);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        DataHelper db = new DataHelper(context);
        Integer dsdID = dsd.get(diceSets.get(groupPosition).Name).get(childPosition).DiceID;
        String dName = db.getDiceName(dsdID);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_ex_child_object, null);
        }

        TextView txtChapName = (TextView) convertView.findViewById(R.id.txtExChild1);
        txtChapName.setText(dName);

        TextView txt2 = (TextView) convertView.findViewById(R.id.txtExChild2);
        txt2.setVisibility(View.GONE);

        TextView txt3 = (TextView) convertView.findViewById(R.id.txtExChild3);
        txt3.setVisibility(View.GONE);

        convertView.setTag(dsdID);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
