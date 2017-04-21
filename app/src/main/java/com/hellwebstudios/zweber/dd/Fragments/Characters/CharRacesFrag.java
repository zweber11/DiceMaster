package com.hellwebstudios.zweber.dd.Fragments.Characters;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.CharClass;
import com.hellwebstudios.zweber.dd.DataObjects.CharRace;
import com.hellwebstudios.zweber.dd.ListAdapters.CharRaceListAdapter;
import com.hellwebstudios.zweber.dd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CharRacesFrag extends Fragment {

    private ListView lvRaces;
    private CharRaceListAdapter adapter;
    private List<CharRace> mRaceList;
    DataHelper db;

    public CharRacesFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_char_races, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        db = new DataHelper(getActivity());
        lvRaces = (ListView) getView().findViewById(R.id.lvRaces);

        setRaces();

        //AddRaces btn
        TextView tvAddRace = (TextView) getView().findViewById(R.id.txtAddRace);
        tvAddRace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder abAddAdv = new AlertDialog.Builder(getActivity());
                abAddAdv.setTitle("Please enter a Race Name name below.");

                View vi = (LayoutInflater.from(getActivity()).inflate(R.layout.view_one_field, null));

                final TextView txtClassName = (TextView) vi.findViewById(R.id.tvProp1); txtClassName.setText("Race Name");
                final TextView tvRName = (TextView) vi.findViewById(R.id.editProp1);
                abAddAdv.setView(vi);

                abAddAdv.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Save logic here, or validate logic...
                        db = new DataHelper(getActivity());
                        CharRace newR = new CharRace();
                        newR.RaceID = 0;
                        newR.RaceName = tvRName.getText().toString();

                        AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());
                        if (tvRName.length() == 0) {
                            myAlert.setMessage("Please enter a Race Name.")
                                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create();
                            myAlert.show();
                        } else if (tvRName.length() > 30) {
                            myAlert.setMessage("Please enter a Race Name under 30 characters.")
                                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create();
                            myAlert.show();
                        } else {
                            if (db.addRace(newR)) {
                                Toast.makeText(getActivity(), "Class created successfully.", Toast.LENGTH_SHORT).show();
                                setRaces();
                            }
                            else
                                Toast.makeText(getActivity(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                AlertDialog a = abAddAdv.create();
                a.show();

            }
        });

        //Handle the onItemClick event
        lvRaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Grab the selectedIndex
                final Integer sRaceID = (Integer) view.getTag();

                //Check if the ID is =< 16.
                if (sRaceID <= 16) {
                    Toast.makeText(getContext(), "Default Race, can't edit.", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder abAddAdv = new AlertDialog.Builder(getActivity());
                    abAddAdv.setTitle("Please enter a Race Name below.");

                    View v = (LayoutInflater.from(getActivity()).inflate(R.layout.view_one_field, null));

                    final TextView txtRaceName = (TextView) v.findViewById(R.id.tvProp1); txtRaceName.setText("Race Name");
                    final TextView tvRName = (TextView) v.findViewById(R.id.editProp1);
                    CharRace rFromDB = db.getRace(sRaceID);
                    tvRName.setText(rFromDB.RaceName);

                    abAddAdv.setView(v);

                    abAddAdv.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Save logic here, or validate logic...
                            db = new DataHelper(getActivity());
                            CharRace rToUp = new CharRace();
                            rToUp.RaceID = sRaceID;
                            rToUp.RaceName = tvRName.getText().toString();

                            AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());
                            if (tvRName.length() == 0) {
                                myAlert.setMessage("Please enter a Race Name.")
                                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).create();
                                myAlert.show();
                            } else if (tvRName.length() > 30) {
                                myAlert.setMessage("Please enter a Race Name under 30 characters.")
                                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).create();
                                myAlert.show();
                            } else {
                                if (db.updateRace(rToUp)) {
                                    Toast.makeText(getActivity(), "Race updated successfully.", Toast.LENGTH_SHORT).show();
                                    setRaces();
                                }
                                else
                                    Toast.makeText(getActivity(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    AlertDialog a = abAddAdv.create();
                    a.show();
                }

            }
        });
    }

    //setRaces() pops the ListView.
    private void setRaces() {
        mRaceList = new ArrayList<>();
        Cursor res = db.getAllRaces();

        //Loop to pop the Race list.
        while (res.moveToNext())
            mRaceList.add(new CharRace(res.getInt(0), res.getString(1)));

        //init adapter
        adapter = new CharRaceListAdapter(getActivity(), mRaceList);
        lvRaces.setAdapter(adapter);

        res.close();
    }
}
