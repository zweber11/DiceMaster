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
import com.hellwebstudios.zweber.dd.DataObjects.CharClass;
import com.hellwebstudios.zweber.dd.DataObjects.DDCharacter;
import com.hellwebstudios.zweber.dd.ListAdapters.CharClassListAdapter;
import com.hellwebstudios.zweber.dd.ListAdapters.CharacterListAdapter;
import com.hellwebstudios.zweber.dd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CharClassesFrag extends Fragment {

    private ListView lvClasses;
    private CharClassListAdapter adapter;
    private List<CharClass> mClassList;
    DataHelper db;

    public CharClassesFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_char_classes, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        db = new DataHelper(getActivity());
        lvClasses = (ListView) getView().findViewById(R.id.lvClasses);

        //Call setClasses()
        setClasses();

        //AddClass btn
        TextView tvAddClass = (TextView) getView().findViewById(R.id.txtAddClass);
        tvAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Take the user to the NewCharacterFragment.
                NewCharClassFrag fragment = new NewCharClassFrag();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "newCharClassFrag");
                fragmentTransaction.commit();

            }
        });

        //Handle the onItemClick event
        lvClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Grab the selectedIndex
                final Integer sClassID = (Integer) view.getTag();

                //Check if the ID is =< 12.
                if (sClassID <= 12) {
                    Toast.makeText(getContext(), "Default class, can't edit.", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder abAddAdv = new AlertDialog.Builder(getActivity());
                    abAddAdv.setTitle("Please enter the info below.");

                    View v = (LayoutInflater.from(getActivity()).inflate(R.layout.view_add_adventure, null));

                    final TextView txtClassName = (TextView) v.findViewById(R.id.txtAdvName);

                    TextView tvClassName = (TextView) v.findViewById(R.id.textView);
                    tvClassName.setText("Class Name");

                    TextView tvAdv = (TextView) v.findViewById(R.id.textView3);
                    tvAdv.setVisibility(View.GONE);

                    TextView tvAdvDesc = (TextView) v.findViewById(R.id.txtAdvDesc); //Disable unneeded fields.
                    tvAdvDesc.setVisibility(View.GONE);

                    TextView tvChar = (TextView) v.findViewById(R.id.textView23);
                    tvChar.setVisibility(View.GONE);

                    //Spinner
                    Spinner spinChars = (Spinner) v.findViewById(R.id.spinChar); //Disable unneeded fields.
                    spinChars.setVisibility(View.GONE);

                    CharClass classFromDB = db.getClass(sClassID);
                    txtClassName.setText(classFromDB.ClassName);

                    abAddAdv.setView(v);

                    abAddAdv.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Save logic here, or validate logic...
                            db = new DataHelper(getActivity());
                            CharClass classToUp = new CharClass();
                            classToUp.ClassID = sClassID;
                            classToUp.ClassName = txtClassName.getText().toString();

                            AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());
                            if (txtClassName.length() == 0) {
                                myAlert.setMessage("Please enter a Class Name.")
                                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).create();
                                myAlert.show();
                            } else if (txtClassName.length() > 30) {
                                myAlert.setMessage("Please enter a Class Name under 30 characters.")
                                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).create();
                                myAlert.show();
                            } else {
                                if (db.updateClass(classToUp)) {
                                    Toast.makeText(getActivity(), "Class updated successfully.", Toast.LENGTH_SHORT).show();
                                    setClasses();
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


    //setClasses()
    private void setClasses()
    {
        mClassList = new ArrayList<>();
        Cursor res = db.getAllClasses();

        //Loop to populate the DDCharacter list.
        while (res.moveToNext())
            mClassList.add(new CharClass(res.getInt(0), res.getString(1)));

        //init adapter
        adapter = new CharClassListAdapter(getActivity(), mClassList);
        lvClasses.setAdapter(adapter);

        res.close();
    }
}
