package com.example.user.enggmart.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.user.enggmart.wrost_activities.Novel;
import com.example.user.enggmart.R;
import com.example.user.enggmart.wrost_activities.Semester;
import com.example.user.enggmart.wrost_activities.SkillDevlopment;
import com.example.user.enggmart.wrost_activities.CurrentAffairs;


public class FourFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_four, container, false);
        LinearLayout CSsem = (LinearLayout)rootView.findViewById(R.id.cssem);
        LinearLayout ECsem = (LinearLayout)rootView.findViewById(R.id.ecsem);
        LinearLayout EEsem = (LinearLayout)rootView.findViewById(R.id.eesem);
        LinearLayout ITsem = (LinearLayout)rootView.findViewById(R.id.itsem);


        CSsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),Semester.class);
                startActivity(i);
            }
        });
        ECsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),CurrentAffairs.class);
                startActivity(i);
            }
        });
        EEsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),Novel.class);
                startActivity(i);
            }
        });
        ITsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),SkillDevlopment.class);
                startActivity(i);
            }
        });
        return rootView;
    }
}
