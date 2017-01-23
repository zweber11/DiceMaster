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
import android.widget.TextView;
import android.widget.Toast;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.DiceSetDie;
import com.hellwebstudios.zweber.dd.DataObjects.RollAttack;
import com.hellwebstudios.zweber.dd.DataObjects.RollAttackSet;
import com.hellwebstudios.zweber.dd.DataObjects.RollSkill;
import com.hellwebstudios.zweber.dd.ListAdapters.RASetListAdapter;
import com.hellwebstudios.zweber.dd.ListAdapters.RollAttackListAdapter;
import com.hellwebstudios.zweber.dd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttackRollsFragment extends Fragment {

    //Global vars
    DataHelper db;

    int DSID = 0;
    int chapID;
    int RASID;
    int add = 0;
    TextView tvDSTitle;
    TextView tvChapMenu;

    private ListView lvAttackRolls;
    private RollAttackListAdapter adapter;
    private List<RollAttack> mRAList;

    Spinner spinD12;
    List<Integer> sD12;

    int number;
    int maxNumb;
    int sDID;
    Integer sRAID;
    int r;
    int Roll;
    int lastRAS;
    int total;
    TextView tvADTotal;
    String t;

    public AttackRollsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attack_rolls, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new DataHelper(getActivity());

        lvAttackRolls = (ListView) getView().findViewById(R.id.lvAR);
        tvDSTitle = (TextView) getView().findViewById(R.id.txtADName);
        tvChapMenu = (TextView) getView().findViewById(R.id.txtChapMenu);
        tvADTotal = (TextView) getView().findViewById(R.id.txtADTotal);

        //Bundle it.
        Bundle bundle = getArguments();
        if (bundle != null) {
            DSID = bundle.getInt("DSID", 0);
            chapID = bundle.getInt("ChapID", 0);
            RASID = bundle.getInt("RASID", 0);
            add = bundle.getInt("Add", 0);
        }

        //Handle add extra.
        if (add == 1) //If add = 1, generate RollAttack entries, & then display them.
        {
            //First generate a RollAttackSet.
            RollAttackSet ras = new RollAttackSet();
            ras.ID = 0;
            ras.ChapID = chapID;
            ras.DSID = DSID;

            db.addRAS(ras);

            lastRAS = db.getLatestRASID();

            //RollAttack entry generation loop
            Cursor res = db.getDieByDSID(DSID);

            //Loop... Will need to lock down if you've already created the RollAttack entries...
            while (res.moveToNext())
            {
                RollAttack ra = new RollAttack();
                ra.ID = 0;
                ra.RASID = lastRAS;
                ra.DID = res.getInt(2);
                ra.Roll = 1;

                db.addRA(ra);
            }

//            Toast.makeText(getActivity(), "# of RollAttack objects: " + db.getRACount(), Toast.LENGTH_SHORT).show();
            //Display new RollAttack entries
            getAttackRolls(lastRAS);
        }
        else //Otherwise, just display existing RollAttack entries.
            getAttackRolls(RASID);

        //Set title.
        tvDSTitle.setText(db.getDSNameByID(DSID));

        //OnItemClick event handler.
        lvAttackRolls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                sRAID = (Integer) view.getTag();

                RollAttack ra = db.getRA(sRAID);

                RASID = ra.RASID;
                sDID = ra.DID;
                Roll = ra.Roll;

                //region **AlertDialog**

                AlertDialog.Builder abAddAdv = new AlertDialog.Builder(getActivity());
                abAddAdv.setTitle("Please enter the roll info below.");

                View viewA = (LayoutInflater.from(getActivity()).inflate(R.layout.view_attack_roll, null));

                //DiceName label
                TextView tvDN = (TextView) viewA.findViewById(R.id.tvDiceTitle);
                tvDN.setText(db.getDiceName(sDID));

                //SpinnerD12
                spinD12 = (Spinner) viewA.findViewById(R.id.spinD12);
                sD12 = new ArrayList<>();

                //Identify what Dice we're looking at, and generate a loop accordingly.
                number = 1;
                maxNumb = 13;

                //Switch statement, sets the maxNumb variable based on what the sDID var is...
                switch (sDID) {
                    case 1: //D4
                       maxNumb = 5; break;
                    case 2: //D6
                        maxNumb = 7; break;
                    case 3: //D8
                        maxNumb = 9; break;
                    case 4: //D10
                        maxNumb = 11; break;
                    case 5: //D12
                        maxNumb = 13; break;
                    default:
                        maxNumb = 5;
                }

                //Loop through and generate a list of possible rolls.
                while (number < maxNumb) {
                    sD12.add(number);
                    number++;
                }

                //Adapter
                ArrayAdapter<Integer> ad2 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, sD12);
                ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinD12.setAdapter(ad2);
                spinD12.setSelection(Roll - 1);

                //Finish up, and set the View.
                abAddAdv.setView(viewA);

                abAddAdv.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Save the RollAttack object here...
                        RollAttack ra = new RollAttack();
                        ra.ID = sRAID;
                        ra.RASID = RASID;
                        ra.DID = sDID;

                        r = (int) spinD12.getSelectedItemId() + 1;
                        ra.Roll = r;

                        //Save the RA to the database.
                        db.updateRA(ra);

                        //After the save, reload the ListView.
                        getAttackRolls(RASID);

                    }
                });

                AlertDialog a = abAddAdv.create();
                a.show();

                //endregion

            }
        });

        //ChapMenu click.
        tvChapMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Take the User back to the ChapMenuFragment, passing in the ChapID as an extra.
                ChapMenuFragment fragment = new ChapMenuFragment();

                //Create a bundle to save the chapID.
                Bundle bundle = new Bundle();
                bundle.putInt("ChapID", chapID);
                fragment.setArguments(bundle);

                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "chapMenuFragment");
                fragmentTransaction.commit();

            }
        });

    }

    //getAttackRolls()
    public void getAttackRolls(int RAS)
    {
        mRAList = new ArrayList<>();
        Cursor res = db.getRAByRASID(RAS);

        total = 0;

        //Loop to populate the AttackRolls list.
        while (res.moveToNext())
        {
            mRAList.add(new RollAttack(res.getInt(0), res.getInt(1), res.getInt(2), res.getInt(3)));

            //Loop through and calc the total roll for the given attack set.
            total = total + res.getInt(3);
        }

        t = String.valueOf(total);
        tvADTotal.setText(t);

        //init adapter
        adapter = new RollAttackListAdapter(getActivity(), mRAList);
        lvAttackRolls.setAdapter(adapter);
    }

}
