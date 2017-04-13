package com.hellwebstudios.zweber.dd.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.Chapter;
import com.hellwebstudios.zweber.dd.R;

import java.util.List;
import java.util.Map;

/**
 * Created by zweber on 4/12/2017.
 */

public class AdvExListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> adventures;
    private Map<String, List<Chapter>> chapters;

    public AdvExListAdapter(Context context, List<String> adventures, Map<String, List<Chapter>> chapters) {
        this.context = context;
        this.adventures = adventures;
        this.chapters = chapters;
    }

    @Override
    public int getGroupCount() {
        return adventures.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return chapters.get(adventures.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return adventures.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return chapters.get(adventures.get(groupPosition)).get(childPosition);
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

        String adventureName = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_ex_par_object, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtExParent1);
        txtTitle.setText(adventureName);

        TextView txt2 = (TextView) convertView.findViewById(R.id.txtExParent2);
        txt2.setVisibility(View.INVISIBLE);

        TextView txt3 = (TextView) convertView.findViewById(R.id.txtExParent3);
        txt3.setVisibility(View.INVISIBLE);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        DataHelper db = new DataHelper(context);
        Integer cID = chapters.get(adventures.get(groupPosition)).get(childPosition).ChapID;

        String chapterName = db.getChapTitle(cID);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_ex_child_object, null);
        }

        TextView txtChapName = (TextView) convertView.findViewById(R.id.txtExChild1);
        txtChapName.setText(chapterName);

        TextView txt2 = (TextView) convertView.findViewById(R.id.txtExChild2);
        txt2.setVisibility(View.INVISIBLE);

        TextView txt3 = (TextView) convertView.findViewById(R.id.txtExChild3);
        txt3.setVisibility(View.INVISIBLE);

        convertView.setTag(cID);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
