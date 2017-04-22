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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.Chapter;
import com.hellwebstudios.zweber.dd.DataObjects.RollAttackSet;
import com.hellwebstudios.zweber.dd.DataObjects.RollSkill;
import com.hellwebstudios.zweber.dd.ListAdapters.RASetListAdapter;
import com.hellwebstudios.zweber.dd.ListAdapters.RollSkillListAdapter;
import com.hellwebstudios.zweber.dd.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChapMenuFragment extends Fragment {

    //Global vars
    private ListView lvRSkills;
    private RollSkillListAdapter adapter;
    private List<RollSkill> mRSList;
    DataHelper db;
    int chapID = 0;
    TextView txtChapTitle, txtAttack, txtSkill;

    //Skill roll controls.
    Spinner spinSkills, spinD20;
    List<String> sSkills;
    List<Integer> sD20;

    //Attack roll controls
    Spinner spinDS;
    List<String> sDS;

    //RASets calls.
    private ListView lvRASets;
    private List<RollAttackSet> mRASetList;
    private RASetListAdapter adapter2;

    //Global int var, handles whether you're going to generate RollAttack entries in the preceding AttackRollsFragment.
    int add = 0;
    Integer RSID;
    int RASID, charID;
    Chapter c;
    TextView txtRTH;

    //AttackSet spinner calls.
    Spinner spinRTH;
    List<Integer> sRTH;
    int number, maxNumb, init, rth;

    public ChapMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chap_menu, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new DataHelper(getActivity());

        lvRASets = (ListView) getView().findViewById(R.id.lvRollAttackSets);
        lvRSkills = (ListView) getView().findViewById(R.id.lvRollSkill);
        txtRTH = (TextView) getView().findViewById(R.id.txtRTH);

        //Bundle it.
        Bundle bundle = getArguments();
        if (bundle != null) {
            chapID = bundle.getInt("ChapID", 0);
        }

        //Grab the Chapter, and display the ChapterName.
        c = db.getChap(chapID);

        //Initialize TextViews
        txtChapTitle = (TextView) getView().findViewById(R.id.txtChapTitle);
        txtChapTitle.setText(c.Name);

        //region *** Roll Attack calls
        //Attack roll button - separate .xml from the Skill roll. Dropdown to select a DS.
        txtAttack = (TextView) getView().findViewById(R.id.txtAttackRoll);
        txtAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder abAddAdv = new AlertDialog.Builder(getActivity());
                abAddAdv.setTitle("Please select an Attack Dice Set.");

                View view = (LayoutInflater.from(getActivity()).inflate(R.layout.view_add_attack_roll, null));

                //SpinDS
                spinDS = (Spinner) view.findViewById(R.id.spinDS);
                sDS = new ArrayList<>();

                //Select only the Character-specific DiceSets.
                charID = db.getCharIDByAdvID(c.getAdvID());
                db = new DataHelper(getActivity());
                Cursor res = db.getCharDS(charID);

                if (res.getCount() == 0) {
                    Toast.makeText(getActivity(), "Please add Dice Sets for your Character.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                    while (res.moveToNext()) //Loop and fill the List.
                        sDS.add(res.getString(1));

                //Init adapter
                ArrayAdapter<String> ad = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, sDS);
                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinDS.setAdapter(ad);

                res.close();

                //Spinners
                spinRTH = (Spinner) view.findViewById(R.id.spinRTH); //RTH

                sRTH = new ArrayList<>();
                number = 1;
                maxNumb = 21;

                //Loop through and generate a list of possible rolls.
                while (number < maxNumb) {
                    sRTH.add(number);
                    number++;
                }

                //RTH Adapter
                ArrayAdapter<Integer> ad3 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, sRTH);
                ad3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinRTH.setAdapter(ad3);
                spinRTH.setSelection(0);

                //Finish up, and set the View.
                abAddAdv.setView(view);

                abAddAdv.setPositiveButton("Roll", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String sDSN = (String) spinDS.getSelectedItem();

                        //Convert the DSN to DSID.
                        int sDSID = db.getDSIDByName(sDSN);

                        //Take the user to the AttackRollsFragment.
                        AttackRollsFragment fragment = new AttackRollsFragment();

                        //Add check to ensure the DiceSet contains Die. If not, inform the User to create some.
                        Cursor dsd = db.getDSDByDSID(sDSID);
                        if (dsd.getCount() == 0) {
                            Toast.makeText(getActivity(), "Please add Die to this Dice Set.", Toast.LENGTH_SHORT).show();
                        } else {
                            //Create a bundle, and setArguments of the fragment.
                            Bundle bundle = new Bundle();
                            bundle.putInt("DSID", sDSID);

                            //Pass the chapID as well for the RollAttack generation loop.
                            bundle.putInt("ChapID", chapID);

                            //Flag to handle the next fragment.
                            add = 1;
                            bundle.putInt("Add", add);

                            //Send in the initiative & rth values from the Spinners.
                            init = 1;
                            bundle.putInt("init", init);

                            rth = (int) spinRTH.getSelectedItem();
                            bundle.putInt("rth", rth);

                            fragment.setArguments(bundle);

                            android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment, "attackRollsFragment");
                            fragmentTransaction.commit();
                        }
                    }
                });

                AlertDialog a = abAddAdv.create();
                a.show();
            }
        });

        //endregion

        //region **Roll Skill calls
        //Skill roll button - separate .xml from the Attack roll. D20, populate with Skills...
        txtSkill = (TextView) getView().findViewById(R.id.txtSkillRoll);
        txtSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RSAlert(0, 0, 0); //Call RSAlert(), which displays an alert dialog.
            }
        });

        //Roll Skills onItemClick event.
        lvRSkills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Grab the selectedIndex.
                RSID = (Integer) view.getTag();
                int sID = db.getSkillByRSID(RSID);
                int r = db.getRollByRSID(RSID);

                RSAlert(RSID, sID, r);
            }
        });

        //endregion


        //region **RollAttackSets

        //Roll Attack sets .itemClick event handler.
        lvRASets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Grab the selectedIndex.
                RASID = (Integer) view.getTag();

                //Take the user to the AttackRollsFragment.
                AttackRollsFragment fragment = new AttackRollsFragment();

                //Create a bundle to save the sRASID.
                Bundle bundle = new Bundle();

                //Convert the RASID into a DSID.
                Integer DSID = db.getDSIDByRASID(RASID);

                bundle.putInt("DSID", DSID);
                bundle.putInt("ChapID", chapID);
                bundle.putInt("RASID", RASID);

                //Flag to handle the next fragment.
                add = 0;
                bundle.putInt("Add", add);

                fragment.setArguments(bundle);

                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "attackRollsFragment");
                fragmentTransaction.commit();
            }
        });

        //endregion

        //Call setRollSkills()
        setRollSkills(chapID);

        //Call getRASets();
        getRASets(chapID);
    }

    //Populates the ListView with the appropriate rolls from the db based on the passed in Chapter.
    private void setRollSkills(int chapID)
    {
        mRSList = new ArrayList<>();
        Cursor res = db.getRSByChapID(chapID);

        //Loop to populate the RollSkill list.
        while (res.moveToNext())
            mRSList.add(new RollSkill(res.getInt(0), res.getInt(1), res.getInt(2), res.getInt(3)));

        //init adapter
        adapter = new RollSkillListAdapter(getActivity(), mRSList);
        lvRSkills.setAdapter(adapter);

        res.close();
    }

    //getRASets(int chapID)
    private void getRASets(int chapID)
    {
        mRASetList = new ArrayList<>();
        Cursor res = db.getRASetsByChapID(chapID);

        //Loop to populate the RollAttackSet list.
        while (res.moveToNext())
            mRASetList.add(new RollAttackSet(res.getInt(0), res.getInt(1), res.getInt(2), res.getInt(3), res.getInt(4)));

        //init adapter
        adapter2 = new RASetListAdapter(getActivity(), mRASetList);
        lvRASets.setAdapter(adapter2);

        res.close();
    }


    //private void RSAlert(), creates an alertDialog, served up for a new/existing RollSkill.
    private void RSAlert(final int RSID, int sID, int roll)
    {
        AlertDialog.Builder abAddAdv = new AlertDialog.Builder(getActivity());
        abAddAdv.setTitle("Please enter the roll info below.");

        View view = (LayoutInflater.from(getActivity()).inflate(R.layout.view_add_skill_roll, null));

        //SpinnerSkill
        spinSkills = (Spinner) view.findViewById(R.id.spinSkill);
        sSkills = new ArrayList<>();

        db = new DataHelper(getActivity());
        Cursor res = db.getRecentSkills();

        if (res.getCount() == 0)
            return;
        else
            while (res.moveToNext()) //Loop and fill the List.
                sSkills.add(res.getString(1));

        //Init adapter
        ArrayAdapter<String> ad = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, sSkills);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSkills.setAdapter(ad);

        res.close();

        //Grab the sName based on the sID.
        String sName = db.getSNByID(sID);

        //Set the selectedSkill here.
        if (sID == 0)
            spinSkills.setSelection(0);
        //http://stackoverflow.com/questions/2390102/how-to-set-selected-item-of-spinner-by-value-not-by-position
        else if (!sName.equals(null)) {
            int sPos = ad.getPosition(sName);
            spinSkills.setSelection(sPos);
        }

        //SpinnerD20
        spinD20 = (Spinner) view.findViewById(R.id.spinD20);
        sD20 = new ArrayList<>();

        int i = 1;
        while (i < 21) {
            sD20.add(i);
            i++;
        }

        //Adapter
        ArrayAdapter<Integer> ad2 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, sD20);
        ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinD20.setAdapter(ad2);

        //Set the selectedItem here.
        if (roll == 0)
            spinD20.setSelection(0);
        else
            spinD20.setSelection(roll - 1);

        //Finish up, and set the View.
        abAddAdv.setView(view);

        abAddAdv.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                RollSkill rs = new RollSkill();

                String skill = spinSkills.getSelectedItem().toString();
                int sID = db.getSIDBySN(skill);
                rs.SkillID = sID;
                rs.ChapID = chapID;

                int r = (int) spinD20.getSelectedItemId() + 1;
                rs.Roll = r;

                //LastUsed value for the Skill...
                Date d = new Date();

                SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd, yyyy. hh:mm aa");
                String dateText = df.format(d);

                //Save a new RollSkill object, or update the existing one...
                if (RSID == 0) {  //new RS obj.
                    rs.ID = 0;

                    if (db.addRS(rs)) {
                        Toast.makeText(getActivity(), "Skill: " + spinSkills.getSelectedItem().toString() + ", # rolled: " + spinD20.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                        setRollSkills(chapID);
                    } else
                        Toast.makeText(getActivity(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                }
                else { //Existing RS obj...
                    rs.ID = RSID;

                    db.updateRS(rs);
                    setRollSkills(chapID);

                    Toast.makeText(getActivity(), "Roll updated.", Toast.LENGTH_SHORT).show();
                }

                //Update LastUsed value for of the selected Skill.
                db.updateSkillDT(sID, dateText);
            }
        });

        AlertDialog a = abAddAdv.create();
        a.show();
    }

}
