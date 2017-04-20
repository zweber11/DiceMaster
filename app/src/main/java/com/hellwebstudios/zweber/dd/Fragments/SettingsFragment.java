package com.hellwebstudios.zweber.dd.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hellwebstudios.zweber.dd.Fragments.Adventures.AdventuresFragment;
import com.hellwebstudios.zweber.dd.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    Button btnAppSettings;
    Button btnDashTileSettings;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //button.onclicks
        btnAppSettings = (Button) getView().findViewById(R.id.btnAppSettings);
        btnAppSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Coming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        btnDashTileSettings = (Button) getView().findViewById(R.id.btnDashTileSettings);
        btnDashTileSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //DashTileFragment.
                DashTileFragment fragment = new DashTileFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "dashTileFragment");
                fragmentTransaction.commit();
            }
        });

    }
}
