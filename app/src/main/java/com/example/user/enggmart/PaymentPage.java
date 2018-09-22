package com.example.user.enggmart;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaymentPage extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setTitle("FAQ");
        listView=(ExpandableListView)findViewById(R.id.ivExp);
        initData();

    }

    private void initData() {
        listDataHeader=new ArrayList<>();
        listHash = new HashMap<>();
        listDataHeader.add("e-Wallet");
        listDataHeader.add("Cash On Delivery");

        List<String> edmDev = new ArrayList<>();
        listHash.put("e-Wallet",edmDev);
        edmDev.add("we have to add here payment process");
        edmDev.add("if consumer click on e-wallet it expand and show the field to enter paytm/phonepe no.");

        List<String> androidStudio = new ArrayList<>();
        listHash.put("Cash On Delivery",androidStudio);
        androidStudio.add("after clicking on this field customer will get the additional money for cash on delivery");
        androidStudio.add("");



        listAdapter =new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);

    }
}