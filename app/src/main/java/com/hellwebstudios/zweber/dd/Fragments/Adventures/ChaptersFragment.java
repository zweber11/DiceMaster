package com.hellwebstudios.zweber.dd.Fragments.Adventures;


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
import com.hellwebstudios.zweber.dd.DataObjects.Chapter;
import com.hellwebstudios.zweber.dd.ListAdapters.AdvListAdapter;
import com.hellwebstudios.zweber.dd.ListAdapters.ChapsListAdapter;
import com.hellwebstudios.zweber.dd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChaptersFragment extends Fragment {

    //Global vars
    private ListView lvChaps;
    private ChapsListAdapter adapter;
    private List<Chapter> mChapList;
    DataHelper db;

    int advID = 0;
    TextView advTitle;
    TextView tvChapName;
    TextView tvAddChap;

    Spinner spinRTH;
    Spinner spinRTH2;
    List<Integer> sRTH;
    int number;
    int maxNumb;

    public ChaptersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chapters, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Give the user some instruction.
        Toast.makeText(getActivity(), "Select a Chapter to Roll, or press and hold to edit the Chapter details.", Toast.LENGTH_LONG).show();

        db = new DataHelper(getActivity());
        lvChaps = (ListView) getView().findViewById(R.id.lvChaps);

        //Bundle it.
        Bundle bundle = getArguments();
        if (bundle != null) {
            advID = bundle.getInt("AdvID", 0);
        }

        //Initialize TextViews
        advTitle = (TextView) getView().findViewById(R.id.txtAdvTitle);
        advTitle.setText(db.getAdvTitle(advID));

        //Call setChaps()
        setChaps(advID);

        //region **AddChap/ItemClick events.

        //AddChap button
        tvAddChap = (TextView) getView().findViewById(R.id.txtAddChap);
        tvAddChap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder abAddChap = new AlertDialog.Builder(getActivity());
                abAddChap.setTitle("Please enter Chapter Details.");

                View view = (LayoutInflater.from(getActivity()).inflate(R.layout.view_add_chapter, null));

                tvChapName = (TextView) view.findViewById(R.id.txtChapName);
//                spinRTH = (Spinner) view.findViewById(R.id.spinRTH);
//
//                sRTH = new ArrayList<>();
//                number = 1;
//                maxNumb = 21;
//
//                //Loop through and generate a list of possible rolls.
//                while (number < maxNumb) {
//                    sRTH.add(number);
//                    number++;
//                }
//
//                //Adapter
//                ArrayAdapter<Integer> ad = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, sRTH);
//                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinRTH.setAdapter(ad);
//                spinRTH.setSelection(0);

                abAddChap.setView(view);

                //Save action button.
                abAddChap.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Save logic here, or validate logic...
                        db = new DataHelper(getActivity());
                        Chapter c = new Chapter();
                        c.ChapID = 0;
                        c.AdvID = advID;
                        c.Name = tvChapName.getText().toString();

                        valFields(c);
                    }
                });

                AlertDialog a = abAddChap.create();
                a.show();
            }
        });

        //List onItemClick
        lvChaps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Grab the selectedIndex
                Integer sChapID = (Integer) view.getTag();

                //Take the use to the ChapMenuFragment.
                ChapMenuFragment fragment = new ChapMenuFragment();

                //Create a bundle to save the chapID.
                Bundle bundle = new Bundle();
                bundle.putInt("ChapID", sChapID);
                fragment.setArguments(bundle);

                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "chapMenuFragment");
                fragmentTransaction.commit();
            }
        });

        //lvChaps.setOnItemLongClickListener
        lvChaps.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //Grab the selectedIndex.
                final Integer sChapID = (Integer) view.getTag();

                Chapter c = db.getChap(sChapID);

                AlertDialog.Builder abAddChap = new AlertDialog.Builder(getActivity());
                abAddChap.setTitle("Please enter Chapter details.");

                View view2 = (LayoutInflater.from(getActivity()).inflate(R.layout.view_add_chapter, null));

                tvChapName = (TextView) view2.findViewById(R.id.txtChapName);
                tvChapName.setText(c.getName());

                abAddChap.setView(view2);

                //Save action button.
                abAddChap.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Save logic here, or validate logic...
                        db = new DataHelper(getActivity());
                        Chapter c = new Chapter();
                        c.ChapID = sChapID;
                        c.AdvID = advID;
                        c.Name = tvChapName.getText().toString();

                        valFields(c);
                    }
                });

                AlertDialog a = abAddChap.create();
                a.show();

                return true;
            }
        });

        //endregion

    }

    //setChaps(), grabs Chapters based on the AdvID passed in...
    private void setChaps(int a)
    {
        mChapList = new ArrayList<>();
        Cursor res = db.getChapsByAdv(a);

        //Loop to populate the Chapter list.
        while (res.moveToNext())
            mChapList.add(new Chapter(res.getInt(0), res.getInt(1), res.getString(2)));

        //init adapter
        adapter = new ChapsListAdapter(getActivity(), mChapList);
        lvChaps.setAdapter(adapter);
    }

    //valFields, will check fields for adding a new Adventure.
    private void valFields(Chapter c)
    {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(getActivity());

        if (tvChapName.length() == 0) {
            myAlert.setMessage("Please enter a Chapter Name.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        } else if (tvChapName.length() > 30) {
            myAlert.setMessage("Please enter a Chapter Name under 30 characters.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            myAlert.show();
        } else {
            if (c.ChapID == 0) { //New Chapter
                db.addChap(c);
                Toast.makeText(getActivity(), c.Name + " added.", Toast.LENGTH_SHORT).show();
            }
            else { //Existing Chapter
                db.updateChap(c);
                Toast.makeText(getActivity(), c.Name + " updated.", Toast.LENGTH_SHORT).show();
            }

            setChaps(advID);
        }
    }

}