package com.hellwebstudios.zweber.dd.Fragments;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.DashboardGrid;
import com.hellwebstudios.zweber.dd.DataObjects.DashboardSetting;
import com.hellwebstudios.zweber.dd.Fragments.Characters.CharactersFragment;
import com.hellwebstudios.zweber.dd.Fragments.Adventures.AdventuresFragment;
import com.hellwebstudios.zweber.dd.Fragments.DiceSets.DiceSetsFragment;
import com.hellwebstudios.zweber.dd.Fragments.Skills.SkillsFragment;
import com.hellwebstudios.zweber.dd.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    //Global vars
    DataHelper db;
    LinearLayout d1, d2, d3, d4, d5, d6;
    TextView tvD1, tvD2, tvD3, tvD4, tvD5, tvD6;
    ImageView ivD1, ivD2, ivD3, ivD4, ivD5, ivD6;
    DashboardGrid dg1, dg2, dg3, dg4, dg5, dg6;
    DashboardSetting ds1, ds2, ds3, ds4, ds5, ds6;
    FragmentManager fmgr;

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

        //region **Dashboard tile click events/Color events**

        d1 = (LinearLayout) getView().findViewById(R.id.llD1);
        //d1.setBackgroundTintList(ColorStateList.valueOf(Integer.parseInt(db.getTileColor(1))));
        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTileClickEvent(1);
            }
        });

        d2 = (LinearLayout) getView().findViewById(R.id.llD2);
        //d2.setBackgroundTintList(ColorStateList.valueOf(Integer.parseInt(db.getTileColor(2))));
        d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTileClickEvent(2);
            }
        });

        d3 = (LinearLayout) getView().findViewById(R.id.llD3);
        //d3.setBackgroundTintList(ColorStateList.valueOf(Integer.parseInt(db.getTileColor(3))));
        d3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTileClickEvent(3);
            }
        });

        d4 = (LinearLayout) getView().findViewById(R.id.llD4);
        //d4.setBackgroundTintList(ColorStateList.valueOf(Integer.parseInt(db.getTileColor(4))));
        d4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTileClickEvent(4);
            }
        });

        d5 = (LinearLayout) getView().findViewById(R.id.llD5);
        //d5.setBackgroundTintList(ColorStateList.valueOf(Integer.parseInt(db.getTileColor(5))));
        d5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTileClickEvent(5);
            }
        });

        d6 = (LinearLayout) getView().findViewById(R.id.llD6);
        //d6.setBackgroundTintList(ColorStateList.valueOf(Integer.parseInt(db.getTileColor(6))));
        d6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTileClickEvent(6);
            }
        });

        //endregion

        //Populate TextViews for now.
        setGridData();

        //region **Old click event code.**

        //Box clicks.
        //Sessions
//        LinearLayout boxSessions = (LinearLayout) getView().findViewById(R.id.llD1);
//        boxSessions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //Display the SessionMenu Fragment.
//                AdventuresFragment fragment = new AdventuresFragment();
//                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment, "adventuresFragment");
//                fragmentTransaction.commit();
//            }
//        });
//
//        //Characters
//        LinearLayout boxChars = (LinearLayout) getView().findViewById(R.id.llD2);
//        boxChars.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //Display the CharactersMenu Fragment.
//                CharactersFragment fragment = new CharactersFragment();
//                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment, "charactersFragment");
//                fragmentTransaction.commit();
//
//            }
//        });
//
//        //Skills
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
//
//        //DiceSets
//        LinearLayout boxDiceSets = (LinearLayout) getView().findViewById(R.id.llD3);
//        boxDiceSets.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //Display the DiceSets Menu Fragment.
//                DiceSetsFragment fragment = new DiceSetsFragment();
//                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment, "diceSetsFragment");
//                fragmentTransaction.commit();
//
//            }
//        });
//
//        //Settings
//        LinearLayout boxSettings = (LinearLayout) getView().findViewById(R.id.llD4);
//        boxSettings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //Display the Settings Menu Fragment.
//                SettingsFragment fragment = new SettingsFragment();
//                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment, "settingsFragment");
//                fragmentTransaction.commit();
//
//            }
//        });
//
//        //About
//        LinearLayout boxAbout = (LinearLayout) getView().findViewById(R.id.llD5);
//        boxAbout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //Display the About Menu Fragment.
//                AboutFragment fragment = new AboutFragment();
//                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment, "aboutFragment");
//                fragmentTransaction.commit();
//
//            }
//        });
//
//        //Help
//        LinearLayout boxHelp = (LinearLayout) getView().findViewById(R.id.llD6);
//        boxHelp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent webLink = new Intent(Intent.ACTION_VIEW);
//                webLink.setData(Uri.parse("https://docs.google.com/document/d/1-7EwIJYdVmmivkyp-dV0eznPsDFqz100nZpOeoZEFF0/edit"));
//                startActivity(webLink);
//                //https://docs.google.com/document/d/1-7EwIJYdVmmivkyp-dV0eznPsDFqz100nZpOeoZEFF0/edit
//
//            }
//        });

        //endregion

    }

    //setTileClickEvent(int TileID/Position)
    public void setTileClickEvent(int DGID) {
        DashboardGrid dg1 = db.getGridByID(DGID);
        int DSID = dg1.DSID;
        fmgr = getFragmentManager();
        switch (DSID) {
            case 1: {
                //Display the Adventures Fragment.
                AdventuresFragment fragment = new AdventuresFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "adventuresFragment");
                fragmentTransaction.commit();
            } break; case 2: {
                //Display the Characters Fragment.
                CharactersFragment fragment = new CharactersFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "charactersFragment");
                fragmentTransaction.commit();
            } break;case 3: {
                //Display the Skills Fragment.
                SkillsFragment fragment = new SkillsFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "skillsFragment");
                fragmentTransaction.commit();
            } break; case 4: {
                //Display the DiceSets Fragment.
                DiceSetsFragment fragment = new DiceSetsFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "diceSetsFragment");
                fragmentTransaction.commit();
            } break; case 5: {
                //Display the Settings Fragment.
                SettingsFragment fragment = new SettingsFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "settingsFragment");
                fragmentTransaction.commit();
            } break; case 6: {
                //Display the About Fragment.
                AboutFragment fragment = new AboutFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "aboutFragment");
                fragmentTransaction.commit();
            } break; case 7: {
                //Display the Help Fragment.
                Intent webLink = new Intent(Intent.ACTION_VIEW);
                webLink.setData(Uri.parse("https://docs.google.com/document/d/1-7EwIJYdVmmivkyp-dV0eznPsDFqz100nZpOeoZEFF0/edit"));
                startActivity(webLink);
                //https://docs.google.com/document/d/1-7EwIJYdVmmivkyp-dV0eznPsDFqz100nZpOeoZEFF0/edit
            } break; default: {
                //Display the Adventures Fragment by default.
                AdventuresFragment fragment = new AdventuresFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "adventuresFragment");
                fragmentTransaction.commit();
            } break;
        }
    }

    //setGridData. Populates text/SVG's for the DashboardTiles.
    private void setGridData() {

        db = new DataHelper(getContext());

        //Init DashboardFields.
        dg1 = db.getGridByID(1);
        ds1 = db.getDSByDSID(dg1.DSID);

        dg2 = db.getGridByID(2);
        ds2 = db.getDSByDSID(dg2.DSID);

        dg3 = db.getGridByID(3);
        ds3 = db.getDSByDSID(dg3.DSID);

        dg4 = db.getGridByID(4);
        ds4 = db.getDSByDSID(dg4.DSID);

        dg5 = db.getGridByID(5);
        ds5 = db.getDSByDSID(dg5.DSID);

        dg6 = db.getGridByID(6);
        ds6 = db.getDSByDSID(dg6.DSID);

        tvD1 = (TextView) getView().findViewById(R.id.tvD1);
        tvD2 = (TextView) getView().findViewById(R.id.tvD2);
        tvD3 = (TextView) getView().findViewById(R.id.tvD3);
        tvD4 = (TextView) getView().findViewById(R.id.tvD4);
        tvD5 = (TextView) getView().findViewById(R.id.tvD5);
        tvD6 = (TextView) getView().findViewById(R.id.tvD6);

        //Populate TextViews.
        tvD1.setText(ds1.Name);
        tvD2.setText(ds2.Name);
        tvD3.setText(ds3.Name);
        tvD4.setText(ds4.Name);
        tvD5.setText(ds5.Name);
        tvD6.setText(ds6.Name);

    }
}
