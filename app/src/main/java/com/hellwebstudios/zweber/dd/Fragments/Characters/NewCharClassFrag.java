package com.hellwebstudios.zweber.dd.Fragments.Characters;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.DataObjects.CharClass;
import com.hellwebstudios.zweber.dd.DataObjects.DDCharacter;
import com.hellwebstudios.zweber.dd.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewCharClassFrag extends Fragment {

    //Global vars
    DataHelper db;
    int classID = 0;
    Button btnSave;

    TextView tvTitle;
    TextView tvClassName;

    public NewCharClassFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_char_class, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnSave = (Button) getView().findViewById(R.id.btnSaveClass);

        //Bundle it.
        Bundle bundle = getArguments();
        if (bundle != null) {
            classID = bundle.getInt("ClassID", 0);
        }

        //Initialize TextViews.
        tvClassName = (TextView) getView().findViewById(R.id.txtClassName);

        //Handle Adds/Edits here. (Look @ sClassID passed in via the Intent.)
        if (classID == 0) { //New CharClass
            Toast.makeText(getActivity(), "Create a new Class to your liking.", Toast.LENGTH_SHORT).show();
            tvClassName.setEnabled(true);
        } else { //Existing Class

            db = new DataHelper(getActivity());
            btnSave.setText("Update");

            CharClass classFromDB = db.getClass(classID);

            //Load data.
            tvClassName.setText(classFromDB.getClassName());

            //Change the heading text to say "Edit Class".
            tvTitle = (TextView) getView().findViewById(R.id.txtAddEditChar);

            //Check to see if the ClassID is between 1 & 12. If so, disable being able to edit.
            if (classID > 12) {
                tvTitle.setText("Edit Class");
                tvClassName.setEnabled(true);
            }
            else {
                tvTitle.setText("Default Class - Can't Edit.");
                tvClassName.setEnabled(false);
                btnSave.setVisibility(View.GONE);
            }
        }

        //BtnCancel
        Button btnCancel = (Button) getView().findViewById(R.id.btnCancelClass);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CCF();
            }
        });

        //BtnSave
        Button btnSave = (Button) getView().findViewById(R.id.btnSaveClass);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                SaveClass();
            }
        });
    }

    //CCF
    public void CCF()
    {
        //Take the user to the ClassesFragment.
        CharClassesFrag fragment = new CharClassesFrag();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    //SaveClass()
    public void SaveClass()
    {
        db = new DataHelper(getActivity());
        CharClass classObj = new CharClass();

        if (classID == 0) //New Class
            classObj.ClassID = 0;
        else //Existing
            classObj.ClassID = classID;

        classObj.ClassName = tvClassName.getText().toString();
        CheckFields(classObj);
    }

    //Method that does the error handling and then sends off to DB.
    //Accepts cToSave as parameter, which either a new/existing Class object.
    public void CheckFields(CharClass ccToSave)
    {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());
        if (tvClassName.length() == 0) {
            myAlert.setMessage("Please enter a Class Name.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        } else if (tvClassName.length() > 30) {
            myAlert.setMessage("Please enter a Class Name under 30 characters.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        } else {
            if (classID == 0) { //New
                if (db.addClass(ccToSave)) {
                    Toast.makeText(getActivity(), "Class added successfully.", Toast.LENGTH_SHORT).show();
                    CCF();
                }
                else
                    Toast.makeText(getActivity(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
            }
            else { //Existing
                if (db.updateClass(ccToSave)) {
                    Toast.makeText(getActivity(), "Class updated successfully.", Toast.LENGTH_SHORT).show();
                    CCF();
                }
                else
                    Toast.makeText(getActivity(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
