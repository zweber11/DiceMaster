package com.hellwebstudios.zweber.dd.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hellwebstudios.zweber.dd.Fragments.Characters.CharactersFragment;
import com.hellwebstudios.zweber.dd.Fragments.Adventures.AdventuresFragment;
import com.hellwebstudios.zweber.dd.Fragments.DiceSets.DiceSetsFragment;
import com.hellwebstudios.zweber.dd.Fragments.Skills.SkillsFragment;
import com.hellwebstudios.zweber.dd.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    //App logic goes here...
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        //Box clicks.
        //Sessions
        LinearLayout boxSessions = (LinearLayout) getView().findViewById(R.id.boxSessions);
        boxSessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Display the SessionMenu Fragment.
                AdventuresFragment fragment = new AdventuresFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "adventuresFragment");
                fragmentTransaction.commit();
            }
        });

        //Characters
        LinearLayout boxChars = (LinearLayout) getView().findViewById(R.id.boxChars);
        boxChars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Display the CharactersMenu Fragment.
                CharactersFragment fragment = new CharactersFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "charactersFragment");
                fragmentTransaction.commit();

            }
        });

        //Skills
//        LinearLayout boxSkills = (LinearLayout) getView().findViewById(R.id.boxSkills);
//        boxSkills.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //Display the SkillsMenu Fragment.
//                SkillsFragment fragment = new SkillsFragment();
//                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment, "skillsFragment");
//                fragmentTransaction.commit();
//
//            }
//        });

        //DiceSets
        LinearLayout boxDiceSets = (LinearLayout) getView().findViewById(R.id.boxDiceSets);
        boxDiceSets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Display the DiceSets Menu Fragment.
                DiceSetsFragment fragment = new DiceSetsFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "diceSetsFragment");
                fragmentTransaction.commit();

            }
        });

        //Settings
        LinearLayout boxSettings = (LinearLayout) getView().findViewById(R.id.boxSettings);
        boxSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Display the Settings Menu Fragment.
                SettingsFragment fragment = new SettingsFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "settingsFragment");
                fragmentTransaction.commit();

            }
        });

        //About
        LinearLayout boxAbout = (LinearLayout) getView().findViewById(R.id.boxAbout);
        boxAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Display the About Menu Fragment.
                AboutFragment fragment = new AboutFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "aboutFragment");
                fragmentTransaction.commit();

            }
        });

        //Help
        LinearLayout boxHelp = (LinearLayout) getView().findViewById(R.id.boxHelp);
        boxHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent webLink = new Intent(Intent.ACTION_VIEW);
                webLink.setData(Uri.parse("https://docs.google.com/document/d/1-7EwIJYdVmmivkyp-dV0eznPsDFqz100nZpOeoZEFF0/edit"));
                startActivity(webLink);
                //https://docs.google.com/document/d/1-7EwIJYdVmmivkyp-dV0eznPsDFqz100nZpOeoZEFF0/edit

            }
        });
    }
}
