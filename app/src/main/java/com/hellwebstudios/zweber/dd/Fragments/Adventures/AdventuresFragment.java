package com.hellwebstudios.zweber.dd.Fragments.Adventures;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.Adventure;
import com.hellwebstudios.zweber.dd.DataObjects.Chapter;
import com.hellwebstudios.zweber.dd.ListAdapters.AdvExListAdapter;
import com.hellwebstudios.zweber.dd.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdventuresFragment extends Fragment {

    //Global vars
    DataHelper db;
    List<String> sChars;
    private Spinner spinChars;

    TextView tvAdd;
    TextView tvAdvName;
    TextView tvAddDesc;
    
    //ExpandableListView code.
    ExpandableListView exListView;
    List<Adventure> adventures;
    Map<String, List<Chapter>> chapters;
    Cursor res; 
    
    ExpandableListAdapter expandableListAdapter;
    Adventure newAdv;

    public AdventuresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adventures, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        db = new DataHelper(getContext());
        
        //Initial db call, to grab all Adventures.
        res = db.getAllAdv();
        fillData(res);

        //ExpandableListView code.
        exListView = (ExpandableListView) getView().findViewById(R.id.exAdvListView);

        exListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Toast.makeText(getContext(), adventures.get(groupPosition).Name + ": " + chapters.get(adventures.get(groupPosition).Name).get(childPosition).Name, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        tvAdd = (TextView) getView().findViewById(R.id.txtNewAdventure);
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder abAddAdv = new AlertDialog.Builder(getActivity());
                abAddAdv.setTitle("Please enter the info below.");

                View view = (LayoutInflater.from(getActivity()).inflate(R.layout.view_add_adventure, null));

                tvAdvName = (TextView) view.findViewById(R.id.txtAdvName);
                tvAddDesc = (TextView) view.findViewById(R.id.txtAdvDesc);

                //Spinner
                spinChars = (Spinner) view.findViewById(R.id.spinChar);

                sChars = new ArrayList<>();

                db = new DataHelper(getActivity());
                Cursor res = db.getAllCharacters();

                if (res.getCount() == 0)
                    return;
                else
                    //Loop and fill the List.
                    while (res.moveToNext())
                        sChars.add(res.getString(1));

                //Init adapter
                ArrayAdapter<String> ad = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, sChars);
                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinChars.setAdapter(ad);
                abAddAdv.setView(view);

                res.close();

                abAddAdv.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Save logic here, or validate logic...
                        db = new DataHelper(getActivity());
                        Adventure newAdv = new Adventure();
                        newAdv.AdvID = 0;
                        newAdv.Name = tvAdvName.getText().toString();
                        newAdv.Desc = tvAddDesc.getText().toString();

                        int CharID = (int) spinChars.getSelectedItemId() + 1;
                        newAdv.CharID = CharID;

                        newAdv.NumChapters = 0;

                        valFields(newAdv);
                    }
                });

                AlertDialog a = abAddAdv.create();
                a.show();
            }
        });
//
//        //lvAdv onItemClick event handler.
//        lvAdv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                //Grab the selectedIndex
//                Integer sAdvID = (Integer) view.getTag();
//
//                //Take the user to the ChaptersFragment.
//                ChaptersFragment cFragment = new ChaptersFragment();
//
//                //Create a bundle, and setArguments of the fragment.
//                Bundle bundle = new Bundle();
//                bundle.putInt("AdvID", sAdvID);
//                cFragment.setArguments(bundle);
//
//                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, cFragment, "chaptersFragment");
//                fragmentTransaction.commit();
//            }
//        });
//
//        lvAdv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//                db = new DataHelper(getActivity());
//
//                //Bring up the AlertDialog & populate with selected Adventure information. Grab the selectedIndex.
//                final Integer sAdvID = (Integer) view.getTag();
//
//                Adventure adv = db.getAdv(sAdvID);
//
//                AlertDialog.Builder abAddAdv = new AlertDialog.Builder(getActivity());
//                abAddAdv.setTitle("Please enter the info below.");
//
//                View view2 = (LayoutInflater.from(getActivity()).inflate(R.layout.view_add_adventure, null));
//
//                tvAdvName = (TextView) view2.findViewById(R.id.txtAdvName);
//                tvAddDesc = (TextView) view2.findViewById(R.id.txtAdvDesc);
//                tvAdvName.setText(adv.getName());
//                tvAddDesc.setText(adv.getDesc());
//
//                //Spinner Chars
//                spinChars = (Spinner) view2.findViewById(R.id.spinChar);
//                sChars = new ArrayList<>();
//                Cursor res = db.getAllCharacters();
//
//                if (res.getCount() == 0)
//                    Toast.makeText(getActivity(), "An error occurred. Please try again", Toast.LENGTH_SHORT).show();
//                else
//                    //Loop and fill the List.
//                    while (res.moveToNext())
//                        sChars.add(res.getString(1));
//
//                //Init adapter
//                ArrayAdapter<String> ad2 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, sChars);
//                ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinChars.setAdapter(ad2);
//                spinChars.setSelection(adv.CharID - 1);
//                abAddAdv.setView(view2);
//
//                res.close();
//
//                abAddAdv.setPositiveButton("Update", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        //Save logic here, or validate logic...
//                        db = new DataHelper(getActivity());
//                        Adventure a = new Adventure();
//                        a.AdvID = sAdvID;
//                        a.Name = tvAdvName.getText().toString();
//                        a.Desc = tvAddDesc.getText().toString();
//
//                        int CharID = (int) spinChars.getSelectedItemId() + 1;
//                        a.CharID = CharID;
//
//                        a.NumChapters = 0;
//
//                        valFields(a);
//                    }
//                });
//
//                AlertDialog a = abAddAdv.create();
//                a.show();
//
//                return true;
//            }
//        });

    }

    private void fillData(Cursor dbCall) {

        db = new DataHelper(getActivity());
        adventures = new ArrayList<>();
        chapters = new HashMap<>();

        //expandableListView code.
        exListView = (ExpandableListView) getView().findViewById(R.id.exAdvListView);

        if (dbCall.getCount() == 0)
            Toast.makeText(getActivity(), "No Adventures were found.", Toast.LENGTH_SHORT).show();
        else {
            //Populate the ArrayList with Adventures.
            while (dbCall.moveToNext()) {

                newAdv = new Adventure();
                newAdv.AdvID = dbCall.getInt(0);
                newAdv.Name = dbCall.getString(1);
                newAdv.Desc = dbCall.getString(2);
                newAdv.CharID = dbCall.getInt(3);
                newAdv.NumChapters = dbCall.getInt(4);

                adventures.add(newAdv);

                List<Chapter> chap = new ArrayList<>();

                Cursor res2 = db.getChapsByAdv(dbCall.getInt(0));

                while (res2.moveToNext())
                    chap.add(new Chapter(res2.getInt(0), res2.getInt(1), res2.getString(2)));

                chapters.put(newAdv.Name, chap);
            }

            exListView.setGroupIndicator(null);

            //Init adapter
            expandableListAdapter = new AdvExListAdapter(this.getContext(), adventures, chapters);
            exListView.setAdapter(expandableListAdapter);
        }

    }

    //valFields, will check fields for adding a new Adventure.
    private void valFields(Adventure a)
    {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());

        if (tvAdvName.length() == 0 || tvAddDesc.length() == 0) {
            myAlert.setMessage("Please enter a Name & Description.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        } else if (tvAdvName.length() > 30 || tvAddDesc.length() > 30) {
            myAlert.setMessage("Please enter a Name & Description under 30 characters.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        } else {
            if (a.AdvID == 0) { //New Adventure
                //Add Adventure and refresh the list.
                db.addAdv(a);
                Toast.makeText(getActivity(), a.Name + " added.", Toast.LENGTH_SHORT).show();
            } else { //Existing Adventure
                db.updateAdv(a);
                Toast.makeText(getActivity(), a.Name + " updated.", Toast.LENGTH_SHORT).show();
            }

            res = db.getAllAdv();
            fillData(res);
        }
    }
}
