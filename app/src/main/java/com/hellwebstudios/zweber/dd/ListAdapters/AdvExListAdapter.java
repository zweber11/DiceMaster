package com.hellwebstudios.zweber.dd.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.Adventure;
import com.hellwebstudios.zweber.dd.DataObjects.Chapter;
import com.hellwebstudios.zweber.dd.R;

import java.util.List;
import java.util.Map;

/**
 * Created by zweber on 4/12/2017.
 */

public class AdvExListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Adventure> adventures;
    private Map<String, List<Chapter>> chapters;

    public AdvExListAdapter(Context context, List<Adventure> adventures, Map<String, List<Chapter>> chapters) {
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
        return chapters.get(adventures.get(groupPosition).Name).size();
//        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {

        Integer advID = adventures.get(groupPosition).AdvID;
        return advID;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return chapters.get(adventures.get(groupPosition)).get(childPosition).Name;
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
        Integer advID = (Integer) getGroup(groupPosition);
        Adventure advFromView = db.getAdv(advID);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_ex_par_object, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtExParent1);
        txtTitle.setText(advFromView.Name);

        TextView txt2 = (TextView) convertView.findViewById(R.id.txtExParent2);
        txt2.setText(advFromView.Desc);

        TextView txt3 = (TextView) convertView.findViewById(R.id.txtExParent3);
        txt3.setText(db.getCharName(advFromView.CharID));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Integer cID = chapters.get(adventures.get(groupPosition).Name).get(childPosition).ChapID;

        String chapterName = chapters.get(adventures.get(groupPosition).Name).get(childPosition).Name;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_ex_child_object, null);
        }

        TextView txtChapName = (TextView) convertView.findViewById(R.id.txtExChild1);
        txtChapName.setText(chapterName);

        TextView txt2 = (TextView) convertView.findViewById(R.id.txtExChild2);
        txt2.setVisibility(View.GONE);

        TextView txt3 = (TextView) convertView.findViewById(R.id.txtExChild3);
        txt3.setVisibility(View.GONE);

        convertView.setTag(cID);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
