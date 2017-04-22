package com.hellwebstudios.zweber.dd.Fragments.Adventures;


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

    TextView tvAddAdv, tvAdvName, tvAddDesc;
    TextView tvAddChap, tvChapName;

    Spinner spinAdvChaps;
    List<String> sAdvChaps;
    
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

                Integer chapID = chapters.get(adventures.get(groupPosition).Name).get(childPosition).ChapID;

                //Take the use to the ChapMenuFragment.
                ChapMenuFragment fragment = new ChapMenuFragment();

                //Create a bundle to save the chapID.
                Bundle bundle = new Bundle();
                bundle.putInt("ChapID", chapID);
                fragment.setArguments(bundle);

                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "chapMenuFragment");
                fragmentTransaction.commit();

                return false;
            }
        });

        exListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                int itemType = ExpandableListView.getPackedPositionType(id);

                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int c = (int) view.getTag();

                    //EditChapter
                    chapAlert(c);

                    return true;
                } else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    int g = adventures.get(ExpandableListView.getPackedPositionGroup(id)).AdvID;

                    //EditAdventure.
                    advAlert(g);

                    return true;
                } else {
                    //null item, we don't consume the click.
                    return false;
                }
            }
        });

        //region **AddAdventure**

        tvAddAdv = (TextView) getView().findViewById(R.id.txtNewAdventure);
        tvAddAdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //NewAdventure.
                advAlert(0);
            }
        });

        //endregion

        //region **AddChapter**

        tvAddChap = (TextView) getView().findViewById(R.id.txtNewChapter);
        tvAddChap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //NewChapter.
                chapAlert(0);
            }
        });

        //endregion

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
    private void valFields(Adventure a) {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());

        if (tvAdvName.length() == 0 || tvAddDesc.length() == 0) {
            myAlert.setMessage("Please enter a Name & Description.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
            myAlert.show();
        } else if (tvAdvName.length() > 30 || tvAddDesc.length() > 30) {
            myAlert.setMessage("Please enter a Name & Description under 30 characters.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
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

    //valFields, will check fields for adding a new Adventure.
    private void valChapFields(Chapter c) {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());

        if (tvChapName.length() == 0) {
            myAlert.setMessage("Please enter a Chapter Name.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
            myAlert.show();
        } else if (tvChapName.length() > 30) {
            myAlert.setMessage("Please enter a Chapter Name under 30 characters.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
            myAlert.show();
        } else {
            if (c.ChapID == 0) { //New Chapter
                db.addChap(c);
                Toast.makeText(getActivity(), c.Name + " added.", Toast.LENGTH_SHORT).show();
            }
            else { //Existing Chapter
                db.updateChap(c);
                Toast.makeText(getActivity(), c.Name + " updated.", Toast.LENGTH_SHORT).show();
            }

            res = db.getAllAdv();
            fillData(res);
        }
    }

    //advAlert()
    private void advAlert(final int advID) {

        AlertDialog.Builder abAddAdv = new AlertDialog.Builder(getActivity());
        abAddAdv.setTitle("Please enter the info below.");

        View view = (LayoutInflater.from(getActivity()).inflate(R.layout.view_add_adventure, null));

        tvAdvName = (TextView) view.findViewById(R.id.txtAdvName);
        tvAddDesc = (TextView) view.findViewById(R.id.txtAdvDesc);

        //Spinner
        spinChars = (Spinner) view.findViewById(R.id.spinChar);
        sChars = new ArrayList<>();
        Cursor res = db.getAllCharacters();

        if (res.getCount() == 0)
            return;
        else
            //Loop and fill the List.
            while (res.moveToNext())
                sChars.add(res.getString(1));

        //Check the advID (Add vs. Edit)
        if (advID == 0) { }
        else {
            Adventure a = db.getAdv(advID);
            tvAdvName.setText(a.Name);
            tvAddDesc.setText(a.Desc);
            spinChars.setSelection(a.CharID - 1);
        }

        //Init adapter
        ArrayAdapter<String> ad = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, sChars);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinChars.setAdapter(ad);
        abAddAdv.setView(view);

        res.close();

        abAddAdv.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                db = new DataHelper(getActivity());
                Adventure adv = new Adventure();

                //Check the advID (Add vs. Edit)
                if (advID == 0) {
                    adv.AdvID = 0;
                } else {
                    adv.AdvID = advID;
                }

                adv.Name = tvAdvName.getText().toString();
                adv.Desc = tvAddDesc.getText().toString();

                int CharID = (int) spinChars.getSelectedItemId() + 1;
                adv.CharID = CharID;
                adv.NumChapters = 0;

                valFields(adv);
            }
        });

        AlertDialog a = abAddAdv.create();
        a.show();
    }

    //advChapter()
    private void chapAlert(final int chapID) {

        AlertDialog.Builder abAddChap = new AlertDialog.Builder(getActivity());
        abAddChap.setTitle("Please enter Chapter Details.");

        View v = (LayoutInflater.from(getActivity()).inflate(R.layout.view_add_chapter, null));

        //initialize spinner, w/ data.
        spinAdvChaps = (Spinner) v.findViewById(R.id.spinAdvChap);
        sAdvChaps = new ArrayList<>();
        Cursor res = db.getAllAdv();
        if (res.getCount() == 0)
            return;
        else
            while (res.moveToNext()) //Loop and fill the List.
                sAdvChaps.add(res.getString(1));

        tvChapName = (TextView) v.findViewById(R.id.txtChapName); //chapName textbox.

        //Check to see if we're dealing with an add/update
        if (chapID == 0) { }
        else { //Create a Chapter object
            Chapter c = db.getChap(chapID);
            spinAdvChaps.setSelection(c.AdvID - 1);
            tvChapName.setText(c.Name);
        }

        //Init adapter
        ArrayAdapter<String> ad = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, sAdvChaps);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAdvChaps.setAdapter(ad);
        abAddChap.setView(v);
        res.close();
        abAddChap.setView(v);

        //Save action button.
        abAddChap.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                db = new DataHelper(getActivity());
                Chapter c = new Chapter();

                //Check the chapID.
                if (chapID == 0)
                    c.ChapID = 0;
                else
                    c.ChapID = chapID;

                int AdvID = (int) spinAdvChaps.getSelectedItemId() + 1;
                c.AdvID = AdvID;
                c.Name = tvChapName.getText().toString();

                valChapFields(c);
            }
        });

        AlertDialog a = abAddChap.create();
        a.show();
    }
}
