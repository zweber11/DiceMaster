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
                Integer sClassID = (Integer) view.getTag();

                //Take the user to NewClassFrag.
                NewCharClassFrag fragment = new NewCharClassFrag();

                //Create a bundle, and setArguments of the fragment.
                Bundle bundle = new Bundle();
                bundle.putInt("ClassID", sClassID);
                fragment.setArguments(bundle);

                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "newCharClassFrag");
                fragmentTransaction.commit();

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
    }

}
