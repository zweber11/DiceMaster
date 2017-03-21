package com.hellwebstudios.zweber.dd.Fragments.DiceSets;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.Adventure;
import com.hellwebstudios.zweber.dd.DataObjects.Chapter;
import com.hellwebstudios.zweber.dd.DataObjects.DiceSetDie;
import com.hellwebstudios.zweber.dd.ListAdapters.ChapsListAdapter;
import com.hellwebstudios.zweber.dd.ListAdapters.DSDListAdapter;
import com.hellwebstudios.zweber.dd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiceSetDieFragment extends Fragment {

    //Global vars
    private ListView lvDSD;
    private DSDListAdapter adapter;
    private List<DiceSetDie> mDSDList;
    DataHelper db;

    int dsID = 0;
    TextView dsTitle, tvAdd;
    Spinner spinDice;
    List<String> sDie;

    int dID;
    DiceSetDie dsd;

    public DiceSetDieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dice_set_die, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new DataHelper(getActivity());
        lvDSD = (ListView) getView().findViewById(R.id.lvDiceSetDie);

        //Bundle it.
        Bundle bundle = getArguments();
        if (bundle != null) {
            dsID = bundle.getInt("DSID", 0);
        }

        //Initialize TextViews.
        dsTitle = (TextView) getView().findViewById(R.id.txtDSName);
        dsTitle.setText(db.getDSNameByID(dsID));

        //Call setDSD()
        setDSD(dsID);

        tvAdd = (TextView) getView().findViewById(R.id.txtAddDie);
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder abAddDie = new AlertDialog.Builder(getActivity());
                abAddDie.setTitle("Please select a Die.");
                View view = (LayoutInflater.from(getActivity()).inflate(R.layout.view_add_die, null));

                //Spinner
                spinDice = (Spinner) view.findViewById(R.id.spinDie);
                sDie = new ArrayList<>();

                db = new DataHelper(getActivity());
                Cursor res = db.getAllDie();

                if (res.getCount() == 0)
                    return;
                else
                    while (res.moveToNext())
                        sDie.add(res.getString(1));

                //Init adapter
                ArrayAdapter<String> ad = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, sDie);
                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinDice.setAdapter(ad);
                abAddDie.setView(view);

                abAddDie.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Save logic here, or validate logic...
                        db = new DataHelper(getActivity());
                        DiceSetDie dsd = new DiceSetDie();
                        dsd.ID = 0;
                        dsd.DiceSetID = dsID;

                        int dieID = (int) spinDice.getSelectedItemId() + 1;
                        dsd.DiceID = dieID;

                        if (db.addDSD(dsd)) {
                            Toast.makeText(getActivity(), "Die added successfully.", Toast.LENGTH_SHORT).show();
                            setDSD(dsID);
                        } else
                            Toast.makeText(getActivity(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog a = abAddDie.create();
                a.show();

            }
        });

        //lvDSD OnItemClick event handler.
        lvDSD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Allow the User to add to & modify their dice sets, limiting them to only "Level" them up, and not decrease the DiceID...
                dID = (Integer) view.getTag();
                dsd = db.getDSD(dID);

                //alertDialog popup.
                AlertDialog.Builder abAddDie = new AlertDialog.Builder(getActivity());
                abAddDie.setTitle("Please select a Die.");
                View view2 = (LayoutInflater.from(getActivity()).inflate(R.layout.view_add_die, null));

                //Spinner
                spinDice = (Spinner) view2.findViewById(R.id.spinDie);
                sDie = new ArrayList<>();

                db = new DataHelper(getActivity());
                Cursor res = db.getAllDie();

                if (res.getCount() == 0)
                    return;
                else
                    while (res.moveToNext())
                        sDie.add(res.getString(1));

                //Init adapter
                ArrayAdapter<String> ad = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, sDie);
                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinDice.setAdapter(ad);
                spinDice.setSelection(dsd.DiceID - 1);
                abAddDie.setView(view2);

                res.close();

                abAddDie.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Save logic here, or validate logic...
                        db = new DataHelper(getActivity());
                        DiceSetDie dsdUp = new DiceSetDie();
                        dsdUp.ID = dsd.ID;
                        dsdUp.DiceSetID = dsID;

                        int dieID = (int) spinDice.getSelectedItemId() + 1;

                        //Check DiceID's here.
                        if (dieID > dsd.DiceID - 1) {
                            dsdUp.DiceID = dieID;
                            db.updateDSD(dsdUp);
                            setDSD(dsID);
                        } else
                            Toast.makeText(getActivity(), "Please select a Die greater than your current selection.", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog a = abAddDie.create();
                a.show();
            }
        });
    }

    //setDSD()
    private void setDSD(int dsID)
    {
        mDSDList = new ArrayList<>();
        Cursor res = db.getDSDByDSID(dsID);

        //Loop to populate the Adventure list.
        while (res.moveToNext())
            mDSDList.add(new DiceSetDie(res.getInt(0), res.getInt(1), res.getInt(2)));

        //init adapter
        adapter = new DSDListAdapter(getActivity(), mDSDList);
        lvDSD.setAdapter(adapter);

        res.close();
    }
}
