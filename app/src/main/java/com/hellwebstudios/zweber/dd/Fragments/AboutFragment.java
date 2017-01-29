package com.hellwebstudios.zweber.dd.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hellwebstudios.zweber.dd.DataHelper;
import com.hellwebstudios.zweber.dd.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView txtGMS = (TextView) getView().findViewById(R.id.txtGMS);

        txtGMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent webLink = new Intent(Intent.ACTION_VIEW);
                webLink.setData(Uri.parse("https://www.twitch.tv/gentlemenssword"));
                startActivity(webLink);
                //https://www.twitch.tv/gentlemenssword

            }
        });


    }

}
