package com.enggmartservices.enggmart.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.enggmartservices.enggmart.R;

public class StoreFragment extends Fragment {

    private LinearLayout booksStore, toolsStore, notesStore, searchStore;
    private View v1, v2, v3;

    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_store, container, false);
        booksStore = rootView.findViewById(R.id.books_store);
        toolsStore = rootView.findViewById(R.id.tools_store);
        notesStore = rootView.findViewById(R.id.notes_store);
        searchStore = rootView.findViewById(R.id.search_store);
        v1 = rootView.findViewById(R.id.books_store_view);
        v2 = rootView.findViewById(R.id.tools_store_view);
        v3 = rootView.findViewById(R.id.notes_store_view);
        v1.setVisibility(View.VISIBLE);
        v2.setVisibility(View.GONE);
        v3.setVisibility(View.GONE);
        changeFragment(new ThreeFragment(), "BookS");
        booksStore.setEnabled(false);
        toolsStore.setEnabled(true);
        booksStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booksStore.setEnabled(false);
                toolsStore.setEnabled(true);
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.GONE);
                v3.setVisibility(View.GONE);
                changeFragment(new ThreeFragment(), "Books");
            }
        });
        toolsStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booksStore.setEnabled(true);
                toolsStore.setEnabled(false);
                v1.setVisibility(View.GONE);
                v2.setVisibility(View.VISIBLE);
                v3.setVisibility(View.GONE);
                changeFragment(new ToolsFragment(), "Blank");
            }
        });
        return rootView;
    }

    public void changeFragment(Fragment fragment, String title) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_store, fragment, title).addToBackStack(null).commit();
    }
}
