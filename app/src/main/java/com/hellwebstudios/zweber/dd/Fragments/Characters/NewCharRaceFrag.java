package com.hellwebstudios.zweber.dd.Fragments.Characters;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.CharRace;
import com.hellwebstudios.zweber.dd.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewCharRaceFrag extends Fragment {

    //Global vars
    DataHelper db;
    int raceID = 0;
    Button btnSave;

    TextView tvTitle;

    TextView tvRaceName;

    public NewCharRaceFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_char_race, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        btnSave = (Button) getView().findViewById(R.id.btnSaveRace);

        //Bundle it.
        Bundle bundle = getArguments();
        if (bundle != null) {
            raceID = bundle.getInt("RaceID", 0);
        }

        //Initialize TextViews.
        tvRaceName = (TextView) getView().findViewById(R.id.txtRaceName);

        //Handle Add/Edits here. (Look @ sRaceID passed in via the Intent.)
        if (raceID == 0) {
            Toast.makeText(getActivity(), "Create a new Race to your liking.", Toast.LENGTH_SHORT).show();
            tvRaceName.setEnabled(true);
        } else {
            db = new DataHelper(getActivity());

            tvTitle = (TextView) getView().findViewById(R.id.txtAddEditRace);
            btnSave.setText("Update");

            CharRace crFromDB = db.getRace(raceID);

            //Load data.
            tvRaceName.setText(crFromDB.getRaceName());

            //Check to see if the RaceID is between 1 & 16. If so, disable being able to edit.
            if (raceID > 16) {
                tvTitle.setText("Edit Race");
                tvRaceName.setEnabled(true);
            } else {
                tvTitle.setText("Default Race - Can't Edit.");
                tvRaceName.setEnabled(false);
                btnSave.setVisibility(View.GONE);
            }
        }

        //BtnCancel
        Button btnCancel = (Button) getView().findViewById(R.id.btnCancelRace);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRF();
            }
        });

        //BtnSave
        Button btnSave = (Button) getView().findViewById(R.id.btnSaveRace);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                //Save the Race
                SaveRace();
            }
        });
    }

    //CRF()
    public void CRF()
    {
        //Take the user to the RacesFragment.
        CharRacesFrag fragment = new CharRacesFrag();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    //SaveRace()
    public void SaveRace() {
        db = new DataHelper(getActivity());
        CharRace raceObj = new CharRace();

        if (raceID == 0) //New Race
            raceObj.RaceID = 0;
        else //Existing
            raceObj.RaceID = raceID;

        raceObj.RaceName = tvRaceName.getText().toString();
        CheckFields(raceObj);
    }

    //Method that does the error handling and then sends off to DB.
    //Accepts cToSave as parameter, which either a new/existing Race object.
    public void CheckFields(CharRace crToSave)
    {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());
        if (tvRaceName.length() == 0) {
            myAlert.setMessage("Please enter a Race Name.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        } else if (tvRaceName.length() > 30) {
            myAlert.setMessage("Please enter a Race Name under 30 characters.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        } else {
            if (raceID == 0) { //New
                if (db.addRace(crToSave)) {
                    Toast.makeText(getActivity(), "Race added successfully.", Toast.LENGTH_SHORT).show();
                    CRF();
                }
                else
                    Toast.makeText(getActivity(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
            }
            else { //Existing
                if (db.updateRace(crToSave)) {
                    Toast.makeText(getActivity(), "Race updated successfully.", Toast.LENGTH_SHORT).show();
                    CRF();
                }
                else
                    Toast.makeText(getActivity(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }




}
