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
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.Adventure;
import com.hellwebstudios.zweber.dd.DataObjects.Chapter;
import com.hellwebstudios.zweber.dd.DataObjects.DDCharacter;
import com.hellwebstudios.zweber.dd.DataObjects.DiceSet;
import com.hellwebstudios.zweber.dd.DataObjects.DiceSetDie;
import com.hellwebstudios.zweber.dd.ListAdapters.CharacterListAdapter;
import com.hellwebstudios.zweber.dd.ListAdapters.DiceSetAdapter;
import com.hellwebstudios.zweber.dd.ListAdapters.DiceSetExListAdapter;
import com.hellwebstudios.zweber.dd.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiceSetsFragment extends Fragment {

    //Global vars
    DataHelper db;
    List<String> sChars;
    private Spinner spinDSChars;
    TextView tvDSN, tvAddDS, tvAddDie;

    //ExpandableListView code.
    ExpandableListView exListView;
    List<DiceSet> diceSets;
    Map<String, List<DiceSetDie>> dsd;
    Cursor res;

    ExpandableListAdapter exListAdapter;
    DiceSet newDS;
    Integer dsdID;

    public DiceSetsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dice_sets, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new DataHelper(getContext());
        
        //Initial db call, to grab all DiceSets.
        res = db.getAllDS();
        fillData(res);
        
        //ExListView code.
        exListView = (ExpandableListView) getView().findViewById(R.id.exDiceSetsListView);

        exListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {

                Integer dsdID = dsd.get(diceSets.get(groupPosition).Name).get(childPosition).ID;

                //TODO: Get code from DSDFragment's onItemClick call...

                return false;
            }
        });

        //region ***New DS/DSD buttons***

        //DS
        tvAddDS = (TextView) getView().findViewById(R.id.txtAddDS);
        tvAddDS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder abAddDS = new AlertDialog.Builder(getActivity());
                abAddDS.setTitle("Please enter the info below.");

                View view = (LayoutInflater.from(getActivity()).inflate(R.layout.view_add_dice_set, null));
                tvDSN = (TextView) view.findViewById(R.id.txtDSN);

                //Spinner
                spinDSChars = (Spinner) view.findViewById(R.id.spinDSChar);
                sChars = new ArrayList<>();

                db = new DataHelper(getActivity());
                Cursor res = db.getAllCharacters();

                if (res.getCount() == 0)
                    return;
                else //Loop and fill the List.
                    while (res.moveToNext())
                        sChars.add(res.getString(1));

                //Init adapter
                ArrayAdapter<String> ad = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, sChars);
                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinDSChars.setAdapter(ad);
                abAddDS.setView(view);

                res.close();

                abAddDS.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Save logic here, or validate logic...
                        db = new DataHelper(getActivity());
                        DiceSet ds = new DiceSet();
                        ds.ID = 0;
                        ds.Name = tvDSN.getText().toString();

                        int CharID = (int) spinDSChars.getSelectedItemId() + 1;
                        ds.CharID = CharID;

                        valFields(ds);
                    }
                });

                AlertDialog a = abAddDS.create();
                a.show();

            }
        });

        //DSD
        tvAddDie = (TextView) getView().findViewById(R.id.txtAddDie);
        tvAddDie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Wire this up based on the DSDFragment code...
            }
        });

        //endregion

        //Handle onItemClick event.
//        lvDS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                //Grab the selectedIndex.
//                Integer sDSID = (Integer) view.getTag();
//
//                //Take the user to the DiceSetDieFragment.
//                DiceSetDieFragment fragment = new DiceSetDieFragment();
//
//                //Create a bundle, and setArguments of the fragment.
//                Bundle bundle = new Bundle();
//                bundle.putInt("DSID", sDSID);
//                fragment.setArguments(bundle);
//
//                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment, "diceSetDieFragment");
//                fragmentTransaction.commit();
//            }
//        });
    }

    private void fillData(Cursor res) {

        db = new DataHelper(getContext());
        diceSets = new ArrayList<>();
        dsd = new HashMap<>();

        //exListView code.
        exListView = (ExpandableListView) getView().findViewById(R.id.exDiceSetsListView);

        if (res.getCount() == 0)
            Toast.makeText(getContext(), "No Dice Sets found.", Toast.LENGTH_SHORT).show();
        else {  //Populate the ArrayList with DiceSets.
            while (res.moveToNext()) {
                newDS = new DiceSet();
                newDS.ID = res.getInt(0);
                newDS.Name = res.getString(1);
                newDS.CharID = res.getInt(2);

                diceSets.add(newDS);

                List<DiceSetDie> diceSetDice = new ArrayList<>();

                Cursor res2 = db.getDieByDSID(newDS.ID);

                while (res2.moveToNext())
                    diceSetDice.add(new DiceSetDie(res2.getInt(0), res2.getInt(1), res2.getInt(2)));

                dsd.put(newDS.Name, diceSetDice);
            }

            exListView.setGroupIndicator(null);

            //Init adapter
            exListAdapter = new DiceSetExListAdapter(this.getContext(), diceSets, dsd);
            exListView.setAdapter(exListAdapter);
        }
    }

    //ValFields(DS ds)
    private void valFields(DiceSet ds) {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());

        if (tvDSN.length() == 0) { //Blank DSN Check
            myAlert.setMessage("Please enter a Name.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        } else if (tvDSN.length() > 30) { //Limits the DSN to 30 chars.
            myAlert.setMessage("Please enter a Name under 30 characters.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        } else if (db.valDS(ds.Name) != 0) {
            Toast.makeText(getActivity(), "A Dice Set named " + ds.Name + " already exists.", Toast.LENGTH_SHORT).show();
        } else {
            if (db.addDS(ds)) {
                Toast.makeText(getActivity(), "Dice Set added successfully.", Toast.LENGTH_SHORT).show();
                res = db.getAllDS();
                fillData(res);
            }
            else
                Toast.makeText(getActivity(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
