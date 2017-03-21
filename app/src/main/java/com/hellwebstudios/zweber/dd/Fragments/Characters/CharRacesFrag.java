package com.hellwebstudios.zweber.dd.Fragments.Characters;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.hellwebstudios.zweber.dd.DataHelper;
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
                NCCF();
            }
        });

        //Handle the onItemClick event
        lvRaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Grab the selectedIndex
                Integer sRaceID = (Integer) view.getTag();

                //Take the user to NewCharRaceFrag.
                NewCharRaceFrag fragment = new NewCharRaceFrag();

                //Create a bundle, and setArguments of the fragment.
                Bundle bundle = new Bundle();
                bundle.putInt("RaceID", sRaceID);
                fragment.setArguments(bundle);

                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "newCharClassFrag");
                fragmentTransaction.commit();
            }
        });
    }

    //setRaces() pops the ListView.
    private void setRaces()
    {
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

    //NCCF
    private void NCCF()
    {
        NewCharRaceFrag fragment = new NewCharRaceFrag();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, "newCharClassFrag");
        fragmentTransaction.commit();
    }

}
