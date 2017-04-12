package com.hellwebstudios.zweber.dd.Fragments.Skills;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.Skill;
import com.hellwebstudios.zweber.dd.ListAdapters.SkillListAdapter;
import com.hellwebstudios.zweber.dd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SkillsFragment extends Fragment {

    private ListView lvSkills;
    private SkillListAdapter adapter;
    private List<Skill> mSkillList;
    DataHelper db;

    public SkillsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_skills, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        db = new DataHelper(getActivity());
        lvSkills = (ListView) getView().findViewById(R.id.lvSkills);

        //setSkills()
        setSkills();

        //Handle the onItemClick event.
        lvSkills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Grab the selectedIndex
//                Integer sSkillID = (Integer) view.getTag();
//
//                //Create a bundle.
//                Bundle bundle = new Bundle();
//                bundle.putInt("SkillID", sSkillID);
//
//                NewSkillFrag fragment = new NewSkillFrag();
//                fragment.setArguments(bundle);
//                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment, "newSkillFragment");
//                fragmentTransaction.commit();
            }
        });
    }

    //Pulls objs from DB.
    public void setSkills()
    {
        mSkillList = new ArrayList<>();
        Cursor res = db.getAllSkills();

        //Loop to populate the SKill list.
        while (res.moveToNext())
            mSkillList.add(new Skill(res.getInt(0), res.getString(1), res.getString(2), res.getInt(3)));

        //Init adapter
        adapter = new SkillListAdapter(getActivity(), mSkillList);
        lvSkills.setAdapter(adapter);

        res.close();
    }

    //OnFocusChangeListener
    @Override
    public void onResume()
    {
        //Refresh the games screen.
        setSkills();

        super.onResume();
    }
}
