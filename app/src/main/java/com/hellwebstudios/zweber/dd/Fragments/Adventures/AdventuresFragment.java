package com.hellwebstudios.zweber.dd.Fragments.Adventures;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.hellwebstudios.zweber.dd.ListAdapters.AdvListAdapter;
import com.hellwebstudios.zweber.dd.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdventuresFragment extends Fragment {

    //Global vars
    private ListView lvAdv;
    private AdvListAdapter adapter;
    private List<Adventure> mAdvList;
    DataHelper db;
    List<String> sChars;
    private Spinner spinChars;

    TextView tvAdd;
    TextView tvAdvName;
    TextView tvAddDesc;

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
        db = new DataHelper(getActivity());
        lvAdv = (ListView) getView().findViewById(R.id.lvAdventures);

        //Call setAdv()
        setAdv();

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

        //lvAdv onItemClick event handler.
        lvAdv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Grab the selectedIndex
                Integer sAdvID = (Integer) view.getTag();

                //Take the user to the ChaptersFragment.
                ChaptersFragment cFragment = new ChaptersFragment();

                //Create a bundle, and setArguments of the fragment.
                Bundle bundle = new Bundle();
                bundle.putInt("AdvID", sAdvID);
                cFragment.setArguments(bundle);

                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, cFragment, "chaptersFragment");
                fragmentTransaction.commit();
            }
        });
    }

    //setAdv(), populates the ListView with Adventures.
    private void setAdv()
    {
        mAdvList = new ArrayList<>();
        Cursor res = db.getAllAdv();

        //Loop to populate the Adventure list.
        while (res.moveToNext())
            mAdvList.add(new Adventure(res.getInt(0), res.getString(1), res.getString(2), res.getInt(3), res.getInt(4)));

        //init adapter
        adapter = new AdvListAdapter(getActivity(), mAdvList);
        lvAdv.setAdapter(adapter);
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
            if (db.addAdv(a)) {
                Toast.makeText(getActivity(), "Adventure added successfully.", Toast.LENGTH_SHORT).show();
                setAdv();
            }
            else
                Toast.makeText(getActivity(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
        }

    }


}
