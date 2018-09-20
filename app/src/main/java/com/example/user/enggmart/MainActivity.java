package com.example.user.enggmart;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.felipecsl.gifimageview.library.GifImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements /*NavigationView.OnNavigationItemSelectedListener,*/ View.OnClickListener {
    private TabLayout tabLayout;
    private TextView uname,uemail;
    private ViewPager viewPager;
    private DrawerLayout drawer;
    private LinearLayout semesterWiseBooks,tools,myCart,myOrder,myChat,sellOnEnggMart,accountSetting,helpCentre,share;
    private CircleImageView imageProfile;
    private GifImageView  notifiacationa;
    private DatabaseReference mdDatabase;
    private FirebaseAuth userAuth;


    private int[] tabIcons = {
            R.drawable.newspaper,
            R.drawable.oldfashionbriefcase,
            R.drawable.bookstore,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notifiacationa=(GifImageView) findViewById(R.id.nofication);
        drawer=findViewById(R.id.drawer_layout);
        semesterWiseBooks=findViewById(R.id.semesterwisebook);
        tools=findViewById(R.id.tools);
        myCart=findViewById(R.id.mycart);
        myChat=findViewById(R.id.mychat);
        myOrder=findViewById(R.id.myorder);
        sellOnEnggMart=findViewById(R.id.sellonenggmart);
        accountSetting=findViewById(R.id.accountsetting);
        share=findViewById(R.id.share);
        helpCentre=findViewById(R.id.help);

        try {
            InputStream inputStream=getAssets().open("bellmmf.gif");
            byte[] bytes= IOUtils.toByteArray(inputStream);
            notifiacationa.setBytes(bytes);
            notifiacationa.startAnimation();
            notifiacationa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(MainActivity.this,Notification.class);
                    startActivity(i);
                }
            });
        } catch (IOException e) {

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(MainActivity.this,WritePost.class);
                startActivity(i);

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    //    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        uname=(TextView) findViewById(R.id.namedr);
        uemail=(TextView) findViewById(R.id.emaildr);
        init();
        //navigationView.setNavigationItemSelectedListener(this);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        setupViewPager();
    }

    private void init() {
        userAuth=FirebaseAuth.getInstance();
        String uid=userAuth.getCurrentUser().getUid().toString();
        mdDatabase=FirebaseDatabase.getInstance().getReference().child("users");
        mdDatabase.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String,Object> userid=(Map<String, Object>) dataSnapshot.getValue();
                Log.e("uid :",""+userid);
                final String name=userid.get("name").toString();
                final String email=userid.get("email").toString();
                final String imageurl=userid.get("image").toString();
               uname.setText(name+"");
               uemail.setText(email+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }





    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "Newfeed");
        adapter.addFragment(new TwoFragment(), "Work/Job ");
        adapter.addFragment(new ThreeFragment(), "Store");
        adapter.addFragment(new FourFragment(), "STUDY");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View view) {
       /* if (view == myChat) {

            startActivity(new Intent(MainActivity.this,ChatActivity.class));
        }*/
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position)  {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            Intent i =new Intent(MainActivity.this,UserProfile.class);
            startActivity(i);
            return true;
        }else if(id == R.id.action_settings3) {
            customDialog();

        }*/

        return super.onOptionsItemSelected(item);
    }

   /* @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        drawer.closeDrawer(GravityCompat.START);
        return true;

    }*/
    private void customDialog() {
        final Dialog openDialog = new Dialog(MainActivity.this);
        openDialog.setContentView(R.layout.activity_logout);
        openDialog.setCancelable(false);

        TextView cancel = (TextView) openDialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialog.dismiss();

            }
        });
        openDialog.show();
    }
}
