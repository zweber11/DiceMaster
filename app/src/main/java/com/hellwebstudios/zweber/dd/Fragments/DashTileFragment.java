package com.hellwebstudios.zweber.dd.Fragments;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        //region **lvDG onItemClick**

        lvDashboardGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                sItemID = (Integer) view.getTag(); //selectedDGID.
                dg = db.getGridByID(sItemID);

                AlertDialog.Builder abGS = new AlertDialog.Builder(getContext());
                abGS.setTitle(dg.Position);

                View v = (LayoutInflater.from(getContext()).inflate(R.layout.view_edit_dg_setting, null));

                //SpinGridSetting
                spinGridSetting = (Spinner) v.findViewById(R.id.spinGridSetting);
                sGS = new ArrayList<>();

                Cursor res2 = db.getAllDash();

                if (res2.getCount() == 0) {
                    Toast.makeText(getContext(), "An error occurred. Please try again", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                    while (res2.moveToNext()) { //Loop and fill the List.
                        //Conditional code to change the data returned (Flag/remove in use options)
                        if (db.valDGOptions(res2.getInt(0))) {
                            sGS.add(res2.getString(1));
                        } else {  } //Skip over it, since it's in use.

                    }

                //Init adapter
                ArrayAdapter<String> ad = new ArrayAdapter<>(getContext(), R.layout.spinner_item, sGS);
                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinGridSetting.setAdapter(ad);

                //Finish up, and set the View.
                abGS.setView(v);

                abGS.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String sGSN = (String) spinGridSetting.getSelectedItem();
                        int sGSID = db.getDSID(sGSN);

                        db.updateDG(sItemID, sGSID, "16777215");
                        Toast.makeText(getContext(), "Dashboard Setting updated successfully.", Toast.LENGTH_SHORT).show();
                        setGrid();
                    }
                });

                AlertDialog a = abGS.create();
                a.show();

            }
        });

        //endregion
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
