package com.hellwebstudios.zweber.dd.Fragments.Characters;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.DDCharacter;
import com.hellwebstudios.zweber.dd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewCharFrag extends Fragment {

    //Global vars
    DataHelper db;
    int CharID = 0;
    TextView tvTitle;
    TextView tvCharName;
    Spinner spinClass;
    Spinner spinRace;

    Button btnCancel;
    Button btnSave;

    List<String> lstClasses;
    List<String> lstRaces;

    public NewCharFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_char, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new DataHelper(getActivity());

        //Bundle it.
        Bundle bundle = getArguments();
        if (bundle != null) {
            CharID = bundle.getInt("CharID", 0);
        }

        //Initialize TextViews/Buttons.
        tvTitle = (TextView) getView().findViewById(R.id.txtAddEditCharacter);
        tvCharName = (TextView) getView().findViewById(R.id.txtCharName);
        spinClass = (Spinner) getView().findViewById(R.id.spinCharClass);
        spinRace = (Spinner) getView().findViewById(R.id.spinCharRace);

        btnCancel = (Button) getView().findViewById(R.id.btnCancelChar);
        btnSave = (Button) getView().findViewById(R.id.btnSaveChar);


        lstClasses = new ArrayList<>();
        lstRaces = new ArrayList<>();

        //Classes spinner
        Cursor res = db.getAllClasses();
        if (res.getCount() == 0)
            return;
        else
            while (res.moveToNext())
                lstClasses.add(res.getString(1));

        //Init classes adapter.
        ArrayAdapter<String> ad = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, lstClasses);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinClass.setAdapter(ad);

        //Races spinner
        Cursor res2 = db.getAllRaces();
        if (res.getCount() == 0)
            return;
        else
            while (res2.moveToNext())
                lstRaces.add(res2.getString(1));

        //Init races adapter
        ArrayAdapter<String> ad2 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, lstRaces);
        ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinRace.setAdapter(ad2);

        //Handle if we're dealing with a new or existing Character.
        if (CharID == 0) //New Character
            tvTitle.setText("New Character");
        else
        {
            tvTitle.setText("Edit Character");

            DDCharacter charFromDB = db.getChar(CharID);

            //Populate the existing Character's details.
            tvCharName.setText(charFromDB.CharacterName);
            spinClass.setSelection(charFromDB.CharClassID - 1);
            spinRace.setSelection(charFromDB.CharRaceID - 1);
        }

        //Cancel/Save button event handlers.
        //Cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CF();
            }
        });

        //Save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                //Handle
                db = new DataHelper(getActivity());
                DDCharacter charObj = new DDCharacter();

                if (CharID == 0) //New Character
                    charObj.CharacterID = 0;
                else
                    charObj.CharacterID = CharID;

                charObj.CharacterName = tvCharName.getText().toString();
                int cID = (int) spinClass.getSelectedItemId() + 1;
                charObj.CharClassID = cID;

                int rID = (int) spinRace.getSelectedItemId() + 1;
                charObj.CharRaceID = rID;

                CheckFields(charObj);

            }
        });

    }

    //Method that does the error handling and then sends off to DB.
    //Accepts charObj as parameter, which either a new/existing DDCharacter object.
    public void CheckFields(DDCharacter charObj)
    {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());
        if (tvCharName.length() == 0) {
            myAlert.setMessage("Please enter a Character Name.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        } else if (tvCharName.length() > 30) {
            myAlert.setMessage("Please enter a Character Name under 30 characters.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        } else {
            if (charObj.CharacterID == 0) { //New
                db.addCharacter(charObj);

                //After the add, take the user back to the CharactersFrag.
                Toast.makeText(getActivity(), "Character added successfully.", Toast.LENGTH_SHORT).show();
                CF();
            }
            else { //Existing
                db.updateCharacter(charObj);

                //After the update, take the user back to the CharactersFrag.
                Toast.makeText(getActivity(), "Character updated successfully.", Toast.LENGTH_SHORT).show();
                CF();
            }
        }
    }

    //CF
    public void CF()
    {
        //Take the User back to the CharactersFragment.
        CharactersFragment fragment = new CharactersFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}
