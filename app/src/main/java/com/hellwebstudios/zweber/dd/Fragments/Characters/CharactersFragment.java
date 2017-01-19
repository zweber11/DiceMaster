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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.Adventure;
import com.hellwebstudios.zweber.dd.DataObjects.DDCharacter;
import com.hellwebstudios.zweber.dd.ListAdapters.CharacterListAdapter;
import com.hellwebstudios.zweber.dd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CharactersFragment extends Fragment {

    //Global vars
    private ListView lvChars;
    private CharacterListAdapter adapter;
    private List<DDCharacter> mCharList;
    DataHelper db;

    //NewChar controls
    TextView txtName;
    Spinner sClass;
    Spinner sRace;

    List<String> lstClasses;
    List<String> lstRaces;

    public CharactersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_characters, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        db = new DataHelper(getActivity());
        lvChars = (ListView) getView().findViewById(R.id.lvChars);

        //Call setChars()
        setChars();

        //Add button
        TextView tvAdd = (TextView) getView().findViewById(R.id.txtAddChar);
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Grab the selectedIndex
                Integer sCharID = 0;

                //Take the user to NewCharFrag.
                NewCharFrag fragment = new NewCharFrag();

                //Create a bundle, and setArguments of the fragment.
                Bundle bundle = new Bundle();
                bundle.putInt("CharID", sCharID);
                fragment.setArguments(bundle);

                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "newCharFrag");
                fragmentTransaction.commit();

//                db = new DataHelper(getActivity());
//
//                AlertDialog.Builder abAddAdv = new AlertDialog.Builder(getActivity());
//                abAddAdv.setTitle("Please enter Character info below.");
//
//                View view = (LayoutInflater.from(getActivity()).inflate(R.layout.view_add_character, null));
//
//                //init controls
//                txtName = (TextView) view.findViewById(R.id.txtName);
//                sClass = (Spinner) view.findViewById(R.id.spinClass);
//                lstClasses = new ArrayList<>();
//                sRace = (Spinner) view.findViewById(R.id.spinRace);
//                lstRaces = new ArrayList<>();
//
//                //Spinner-Classes
//                Cursor res = db.getAllClasses();
//                if (res.getCount() == 0)
//                    return;
//                else
//                    while (res.moveToNext()) //Loop and fill the List.
//                        lstClasses.add(res.getString(1));
//
//                //Init adapter
//                ArrayAdapter<String> ad = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, lstClasses);
//                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                sClass.setAdapter(ad);
//
//                //Spinner-Races
//                Cursor res2 = db.getAllRaces();
//                if (res2.getCount() == 0)
//                    return;
//                else
//                    while (res2.moveToNext()) //Loop and fill the List.
//                        lstRaces.add(res2.getString(1));
//
//                //Init adapter
//                ArrayAdapter<String> ad2 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, lstRaces);
//                ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                sRace.setAdapter(ad2);
//
//                //Finish up and set the view.
//                abAddAdv.setView(view);
//
//                abAddAdv.setPositiveButton("Save", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        //Save logic here, or validate logic...
//                        db = new DataHelper(getActivity());
//                        DDCharacter c = new DDCharacter();
//
//                        c.CharacterID = 0;
//                        c.CharacterName = txtName.getText().toString();
//
//                        int ClassID = (int) sClass.getSelectedItemId() + 1;
//                        c.CharClassID = ClassID;
//                        int RaceID = (int) sRace.getSelectedItemId() + 1;
//                        c.CharRaceID = RaceID;
//
//                        //Validate here...
//                        CheckFields(c);
//                    }
//                });
//
//                AlertDialog a = abAddAdv.create();
//                a.show();
            }
        });

        //Classes button
        TextView tvClasses = (TextView) getView().findViewById(R.id.txtClasses);
        tvClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Take the user to the ClassesMenu.
                CharClassesFrag fragment = new CharClassesFrag();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "charClassesFrag");
                fragmentTransaction.commit();
            }
        });

        //Races button
        TextView tvRaces = (TextView) getView().findViewById(R.id.txtRaces);
        tvRaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Take the user to the RacesMenu.
                CharRacesFrag fragment = new CharRacesFrag();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "charRacesFrag");
                fragmentTransaction.commit();
            }
        });

        //Handle the onItemClick event.
        lvChars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Grab the selectedIndex
                Integer sCharID = (Integer) view.getTag();

                //Take the user to NewCharFrag.
                NewCharFrag fragment = new NewCharFrag();

                //Create a bundle, and setArguments of the fragment.
                Bundle bundle = new Bundle();
                bundle.putInt("CharID", sCharID);
                fragment.setArguments(bundle);

                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "newCharFrag");
                fragmentTransaction.commit();
            }
        });
    }

    //setChars(), populates the ListView with Characters.
    private void setChars()
    {
        mCharList = new ArrayList<>();
        Cursor res = db.getAllCharacters();

        //Loop to populate the DDCharacter list.
        while (res.moveToNext())
            mCharList.add(new DDCharacter(res.getInt(0), res.getString(1), res.getInt(2), res.getInt(3)));

        //init adapter
        adapter = new CharacterListAdapter(getActivity(), mCharList);
        lvChars.setAdapter(adapter);
    }

    //Method that does the error handling and then sends off to DB.
    //Accepts cToSave as parameter, which either a new/existing Character object.
//    public void CheckFields(DDCharacter c)
//    {
//        AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());
//        if (txtName.length() == 0) {
//            myAlert.setMessage("Please enter a Character Name.")
//                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    })
//                    .create();
//            myAlert.show();
//        } else if (txtName.length() > 30) {
//            myAlert.setMessage("Please enter a Character Name under 30 characters.")
//                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    })
//                    .create();
//            myAlert.show();
//        } else {
//            if (db.addCharacter(c))
//            {
//                Toast.makeText(getActivity(), "Character added successfully.", Toast.LENGTH_SHORT).show();
//                setChars();
//            }
//            else
//                Toast.makeText(getActivity(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
//        }
//    }

}
