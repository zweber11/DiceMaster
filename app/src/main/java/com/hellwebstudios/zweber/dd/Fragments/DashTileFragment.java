package com.hellwebstudios.zweber.dd.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.DashboardGrid;
import com.hellwebstudios.zweber.dd.ListAdapters.DashboardGridListAdapter;
import com.hellwebstudios.zweber.dd.R;

import java.util.ArrayList;
import java.util.List;

public class DashTileFragment extends Fragment {

    //Global vars
    DataHelper db;
    private ListView lvDashboardGrid;
    private DashboardGridListAdapter adapter;
    private List<DashboardGrid> mDashboardGridList;

    Spinner spinGridSetting;
    List<String> sGS;
    int sItemID;
    DashboardGrid dg;

    public DashTileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dash_tile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db = new DataHelper(getContext());
        lvDashboardGrid = (ListView) getView().findViewById(R.id.lvDashboardGrid);

        setGrid();
    }

    private void setGrid() {
        mDashboardGridList = new ArrayList<>();

        Cursor res = db.getAllDashboardGrids();
        if (res.getCount() == 0) {
            Toast.makeText(getContext(), "Database error, no settings found.", Toast.LENGTH_SHORT).show();
            return;
        }

        //Populate the ArrayList with dashboardGrid settings.
        while (res.moveToNext())
            mDashboardGridList.add(new DashboardGrid(res.getInt(0), res.getString(1), res.getInt(2), res.getString(3), res.getString(4)));

        //Init adapter
        adapter = new DashboardGridListAdapter(getContext(), mDashboardGridList);
        lvDashboardGrid.setAdapter(adapter);
    }
}
