package com.hellwebstudios.zweber.dd.ListAdapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.DashboardGrid;
import com.hellwebstudios.zweber.dd.R;

import java.util.List;

/**
 * Created by zweber on 4/20/2017.
 */

public class DashboardGridListAdapter extends BaseAdapter {

    private Context mContext;
    private List<DashboardGrid> mDashboardGridList;
    private DataHelper db;

    public DashboardGridListAdapter(Context mContext, List<DashboardGrid> mDashboardGridList) {
        this.mContext = mContext;
        this.mDashboardGridList = mDashboardGridList;
    }

    @Override
    public int getCount() {
        return mDashboardGridList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDashboardGridList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        db = new DataHelper(this.mContext);
        View v = View.inflate(mContext, R.layout.dashboard_grid_selector_item, null);

        TextView gridPosition = (TextView) v.findViewById(R.id.grid_position);
        TextView gridDSID = (TextView) v.findViewById(R.id.grid_dsid);

        gridPosition.setText(mDashboardGridList.get(position).getPosition());
        String DSN = db.getDSNByID(mDashboardGridList.get(position).getDSID());
        gridDSID.setText(DSN);

        //SVG/Color code, to come later maybe.

        //Set the GridID to the tag
        v.setTag(mDashboardGridList.get(position).getID());

        return v;
    }
}
