package com.hellwebstudios.zweber.dd.Fragments.Skills;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.Skill;
import com.hellwebstudios.zweber.dd.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewSkillFrag extends Fragment {

    //Global vars
    DataHelper db;
    Skill skillFromDB;
    int skillID = 0;
    TextView tvTitle, tvName;
    Button btnCancel;

    public NewSkillFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_skill, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        //Bundle it.
        Bundle bundle = getArguments();
        if (bundle != null) {
            skillID = bundle.getInt("SkillID", 0);
        }

        db = new DataHelper(getActivity());

        //Initialize TextViews.
        tvName = (TextView) getView().findViewById(R.id.txtSkillName);

        //Change the heading text to say "Skill Details".
        tvTitle = (TextView) getView().findViewById(R.id.txtAddEditSkill);
        tvTitle.setText("Skill Details");

        skillFromDB = db.getSkill(skillID);

        //Load data.
        tvName.setText(skillFromDB.getSkillName());

        //Favorite checkbox.
//        final CheckBox chkFav = (CheckBox) getView().findViewById(R.id.chkFav);
//        chkFav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (chkFav.isChecked()) {
//                    db.updateSkillFavFlag(skillID, 1);
//                    Toast.makeText(getActivity(), "Skill set as a Favorite.", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    db.updateSkillFavFlag(skillID, 0);
//                    Toast.makeText(getActivity(), "Skill is no longer a Favorite.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        //set flag value from SkillObject.
//        if (skillFromDB.getFavorite() == 0)
//            chkFav.setChecked(false);
//        else
//            chkFav.setChecked(true);

        //Cancel button
        btnCancel = (Button) getView().findViewById(R.id.btnCancelSkill);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SF();
            }
        });
    }

    //SF
    public void SF() {
        //Take the user to the SkillsFragment.
        SkillsFragment fragment = new SkillsFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, "skillsFragment");
        fragmentTransaction.commit();
    }
}
