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
import com.hellwebstudios.zweber.dd.DataObjects.DDCharacter;
import com.hellwebstudios.zweber.dd.DataObjects.DiceSet;
import com.hellwebstudios.zweber.dd.ListAdapters.CharacterListAdapter;
import com.hellwebstudios.zweber.dd.ListAdapters.DiceSetAdapter;
import com.hellwebstudios.zweber.dd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiceSetsFragment extends Fragment {

    //Global vars
    private ListView lvDS;
    private DiceSetAdapter adapter;
    private List<DiceSet> mDSList;
    DataHelper db;
    List<String> sChars;
    private Spinner spinDSChars;
    TextView tvDSN;

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
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        db = new DataHelper(getActivity());
        lvDS = (ListView) getView().findViewById(R.id.lvDiceSets);

        //Call setDS()
        setDS();

        //new Button
        final TextView tvAdd = (TextView) getView().findViewById(R.id.txtAddDS);
        tvAdd.setOnClickListener(new View.OnClickListener() {
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

        //Handle onItemClick event.
        lvDS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Grab the selectedIndex.
                Integer sDSID = (Integer) view.getTag();

                //Take the user to the DiceSetDieFragment.
                DiceSetDieFragment fragment = new DiceSetDieFragment();

                //Create a bundle, and setArguments of the fragment.
                Bundle bundle = new Bundle();
                bundle.putInt("DSID", sDSID);
                fragment.setArguments(bundle);

                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "diceSetDieFragment");
                fragmentTransaction.commit();
            }
        });
    }

    //setDS
    private void setDS()
    {
        mDSList = new ArrayList<>();
        Cursor res = db.getAllDS();

        //Loop to populate the DiceSets list.
        while (res.moveToNext())
            mDSList.add(new DiceSet(res.getInt(0), res.getString(1), res.getInt(2)));

        //init adapter
        adapter = new DiceSetAdapter(getActivity(), mDSList);
        lvDS.setAdapter(adapter);

        res.close();
    }

    //ValFields(DS ds)
    private void valFields(DiceSet ds)
    {
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
                setDS();
            }
            else
                Toast.makeText(getActivity(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
