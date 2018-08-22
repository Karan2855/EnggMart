package com.example.user.enggmart;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class FourFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_four, container, false);
        ImageView CSsem = (ImageView)rootView.findViewById(R.id.cssem);
        ImageView ECsem = (ImageView)rootView.findViewById(R.id.ecsem);
        ImageView EEsem = (ImageView)rootView.findViewById(R.id.eesem);
        ImageView ITsem = (ImageView)rootView.findViewById(R.id.itsem);
        ImageView MEsem = (ImageView)rootView.findViewById(R.id.mesem);
        ImageView AEsem = (ImageView)rootView.findViewById(R.id.aesem);
        ImageView CIVILsem = (ImageView)rootView.findViewById(R.id.civilsem);

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
                Intent i=new Intent(getActivity(),Semester.class);
                startActivity(i);
            }
        });
        EEsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),Semester.class);
                startActivity(i);
            }
        });
        ITsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),Semester.class);
                startActivity(i);
            }
        });
        MEsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),Semester.class);
                startActivity(i);
            }
        });
        AEsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),Semester.class);
                startActivity(i);
            }
        });
        CIVILsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),Semester.class);
                startActivity(i);
            }
        });
        return rootView;
    }
}
