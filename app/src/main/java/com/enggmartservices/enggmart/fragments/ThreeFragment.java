package com.enggmartservices.enggmart.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.models.StoreModel;
import com.enggmartservices.enggmart.adapers.CustomAdapterStore;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ThreeFragment extends Fragment {

    private CardView cardBooks, cardHandBooks, cardTools;
    private RecyclerView mAllItemsListView, mListViewBooks, mListViewHandbooks, mListViewTools;
    private RecyclerView mAllItemListView;
    private List<StoreModel> listItemsStore, listItemBooks, listItemHandbooks, listItemTools;
    private DatabaseReference mDatabase;
    private SearchView searchView, searchViewBooks, searchViewHandBooks, searchViewTools;
    private CustomAdapterStore customAdapterBooks, customAdapterHandbooks, customAdapterTools;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // get the reference of RecyclerView
        View rootView = inflater.inflate(R.layout.fragment_three, container, false);
        cardBooks = rootView.findViewById(R.id.books_card);
        cardHandBooks = rootView.findViewById(R.id.handbooks_card);
        cardTools = rootView.findViewById(R.id.tools_card);

        mListViewBooks = rootView.findViewById(R.id.recycler_view_books);
        mListViewHandbooks = rootView.findViewById(R.id.recycler_view_hb);
        mListViewTools = rootView.findViewById(R.id.recycler_view_tools);

        searchViewBooks = rootView.findViewById(R.id.action_search_books);
        searchViewHandBooks = rootView.findViewById(R.id.action_search_hb);
        searchViewTools = rootView.findViewById(R.id.action_search_tools);

        listItemBooks = new ArrayList<>();
        listItemHandbooks = new ArrayList<>();
        listItemTools = new ArrayList<>();
        listItemBooks.clear();
        listItemHandbooks.clear();
        listItemTools.clear();


        mListViewBooks.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));
        mListViewHandbooks.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));
        mListViewTools.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("storeDetails");

        updateBooks();
        updateHandBooks();
        updateTools();

        return rootView;
    }

    private void updateTools() {
        DatabaseReference databaseReference = mDatabase.child("tools");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.hasChildren()) {
                    Log.e("hello", dataSnapshot.toString());
                    String itemID = dataSnapshot.getKey();
                    Map map = (Map) dataSnapshot.getValue();
                    String itemName = map.get("itemName").toString();
                    String itemPrice = map.get("itemPrice").toString();
                    String itemDescription = map.get("itemDescription").toString();
                    String itemImage = map.get("itemImage").toString();
                    StoreModel storeModel = new StoreModel();
                    storeModel.setItemID(itemID);
                    storeModel.setItemType("tools");
                    storeModel.setItemName(itemName);
                    storeModel.setItemPrice(itemPrice);
                    storeModel.setItemDescription(itemDescription);
                    storeModel.setItemImage(itemImage);
                    listItemTools.add(storeModel);
                    customAdapterTools = new CustomAdapterStore(getActivity(), listItemTools);
                    mListViewTools.setHasFixedSize(true);
                    mListViewTools.setAdapter(customAdapterTools);
                } else {
                    Toast.makeText(getActivity(), "no Items", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        searchViewTools.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchViewTools.setMaxWidth(Integer.MAX_VALUE);
        searchViewTools.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                customAdapterTools.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                customAdapterTools.getFilter().filter(query);
                return false;
            }
        });

    }

    private void updateHandBooks() {
        DatabaseReference databaseReference = mDatabase.child("handbooks");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.hasChildren()) {
                    Log.e("hello", dataSnapshot.toString());
                    String itemID = dataSnapshot.getKey();
                    Map map = (Map) dataSnapshot.getValue();
                    String itemName = map.get("itemName").toString();
                    String itemPrice = map.get("itemPrice").toString();

                    String itemDescription = map.get("itemDescription").toString();
                    String itemImage = map.get("itemImage").toString();
                    StoreModel storeModel = new StoreModel();
                    storeModel.setItemID(itemID);
                    storeModel.setItemName(itemName);
                    storeModel.setItemType("handbooks");
                    storeModel.setItemPrice(itemPrice);
                    storeModel.setItemDescription(itemDescription);
                    storeModel.setItemImage(itemImage);
                    listItemHandbooks.add(storeModel);
                    //  call the constructor of CustomAdapterStore to send the reference and data to Adapter
                    customAdapterHandbooks = new CustomAdapterStore(getActivity(), listItemHandbooks);
                    mListViewHandbooks.setHasFixedSize(true);
                    mListViewHandbooks.setAdapter(customAdapterHandbooks);
                } else {
                    Toast.makeText(getActivity(), "no Items", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);


        searchViewHandBooks.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchViewHandBooks.setMaxWidth(Integer.MAX_VALUE);
        searchViewHandBooks.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                customAdapterHandbooks.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                customAdapterHandbooks.getFilter().filter(query);
                return false;
            }
        });


    }

    private void updateBooks() {
        DatabaseReference databaseReference = mDatabase.child("books");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.hasChildren()) {
                    Log.e("hello", dataSnapshot.toString());
                    String itemID = dataSnapshot.getKey();
                    Map map = (Map) dataSnapshot.getValue();
                    String itemName = map.get("itemName").toString();
                    String itemPrice = map.get("itemPrice").toString();
                    String itemDescription = map.get("itemDescription").toString();
                    String itemImage = map.get("itemImage").toString();
                    StoreModel storeModel = new StoreModel();
                    storeModel.setItemID(itemID);
                    storeModel.setItemType("books");
                    storeModel.setItemName(itemName);
                    storeModel.setItemPrice(itemPrice);
                    storeModel.setItemDescription(itemDescription);
                    storeModel.setItemImage(itemImage);
                    listItemBooks.add(storeModel);
                    customAdapterBooks = new CustomAdapterStore(getActivity(), listItemBooks);
                    mListViewBooks.setHasFixedSize(true);
                    mListViewBooks.setAdapter(customAdapterBooks);
                } else {
                    Toast.makeText(getActivity(), "no Items", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchViewBooks.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchViewBooks.setMaxWidth(Integer.MAX_VALUE);
        searchViewBooks.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                customAdapterBooks.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                customAdapterBooks.getFilter().filter(query);
                return false;
            }
        });


    }


}

