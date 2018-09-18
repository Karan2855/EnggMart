package com.example.user.enggmart;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.felipecsl.gifimageview.library.GifImageView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    TextView uname, uemail;
    private ViewPager viewPager;
    DrawerLayout drawer;
    LinearLayout semesterWiseBooks, tools, myCart, myOrder, myChat, sellOnEnggMart, accountSetting, helpCentre, share;
    CircleImageView imageProfile;
    private GifImageView notifiacationa;
    private DatabaseReference mdDatabase;
    private FirebaseAuth userAuth;
    Toolbar toolbar;
    private GoogleSignInClient mGoogleSignInClient;
    FloatingActionButton fab;
    ActionBarDrawerToggle toggle;
    String uid;
    boolean doubleBackToExitPressedOnce = false;
    private int[] tabIcons = {
            R.drawable.newspaper,
            R.drawable.oldfashionbriefcase,
            R.drawable.bookstore,
    };
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findIds();
        userAuth = FirebaseAuth.getInstance();
        uid = userAuth.getCurrentUser().getUid().toString();
        UserDetails.uid = uid;
        mdDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        mStorageRef = FirebaseStorage.getInstance().getReference().child("profileImages").child(uid);


        init();
        onClicking();
        try {
            InputStream inputStream = getAssets().open("bellmmf.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            notifiacationa.setBytes(bytes);
            notifiacationa.startAnimation();
            notifiacationa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(HomeActivity.this, Notification.class);
                    startActivity(i);
                }
            });
        } catch (IOException e) {

        }

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setupViewPager();
    }

    private void onClicking() {
        sellOnEnggMart.setOnClickListener(this);
        semesterWiseBooks.setOnClickListener(this);
        tools.setOnClickListener(this);
        myCart.setOnClickListener(this);
        myChat.setOnClickListener(this);
        myOrder.setOnClickListener(this);
        accountSetting.setOnClickListener(this);
        helpCentre.setOnClickListener(this);
        share.setOnClickListener(this);
        imageProfile.setOnClickListener(this);
    }

    private void init() {

        mdDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Map<String, Object> userid = (Map<String, Object>) dataSnapshot.getValue();
                    Log.e("uid :", "" + userid);
                    String name = userid.get("name").toString();
                    String email = userid.get("email").toString();
                    String phone = userid.get("phone").toString();
                    String imageurl = userid.get("image").toString();
                    UserDetails.name = name;
                    UserDetails.phoneno = phone;
                    UserDetails.email = email;
                    UserDetails.img = imageurl;
                    Glide.with(getApplicationContext()).load(imageurl).into(imageProfile);
                    uname.setText(name + "");
                    uemail.setText(email + "");
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setupViewPager() {
        HomeActivity.ViewPagerAdapter adapter = new HomeActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "Newfeed");
        adapter.addFragment(new TwoFragment(), "Work/Job ");
        adapter.addFragment(new ThreeFragment(), "Store");
        adapter.addFragment(new FourFragment(), "Engg Lib");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void findIds() {
        notifiacationa = (GifImageView) findViewById(R.id.nofication);
        drawer = (DrawerLayout) findViewById(R.id.dr_layout);
        semesterWiseBooks = (LinearLayout) findViewById(R.id.semesterwisebook);
        tools = (LinearLayout) findViewById(R.id.tools);
        myCart = (LinearLayout) findViewById(R.id.mycart);
        myChat = (LinearLayout) findViewById(R.id.mychat);
        myOrder = (LinearLayout) findViewById(R.id.myorder);
        sellOnEnggMart = (LinearLayout) findViewById(R.id.sellonenggmart);
        accountSetting = (LinearLayout) findViewById(R.id.accountsetting);
        share = (LinearLayout) findViewById(R.id.share);
        helpCentre = (LinearLayout) findViewById(R.id.help);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        uname = (TextView) findViewById(R.id.namedr);
        uemail = (TextView) findViewById(R.id.emaildr);
        imageProfile = (CircleImageView) findViewById(R.id.img_pdr);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
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
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        }


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Toast.makeText(HomeActivity.this, "Press again back to exit", Toast.LENGTH_LONG).show();
            this.doubleBackToExitPressedOnce = true;
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1500);
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
        if (id == R.id.profilemenu) {
            Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if (id == R.id.logoutmenu) {
            customDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void customDialog() {
        final Dialog openDialog = new Dialog(HomeActivity.this);
        openDialog.setContentView(R.layout.activity_logout);
        openDialog.setCancelable(false);

        TextView cancel = (TextView) openDialog.findViewById(R.id.cancel);
        TextView logout = (TextView) openDialog.findViewById(R.id.logout);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialog.dismiss();

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog.dismiss();


                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                // [END config_signin]

                mGoogleSignInClient = GoogleSignIn.getClient(HomeActivity.this, gso);

                mGoogleSignInClient.signOut().addOnCompleteListener(HomeActivity.this,
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                userAuth.signOut();
                                startActivity(new Intent(HomeActivity.this, SignInSignUp.class));
                                finish();
                            }
                        });


            }
        });
        openDialog.show();
    }


    public void onClick(View v) {
        if (v == semesterWiseBooks) {
            Toast.makeText(this, "clicked On Semester Wise Book", Toast.LENGTH_SHORT).show();
            drawer.closeDrawers();
        } else if (v == tools) {
            Toast.makeText(this, "clicked On tools", Toast.LENGTH_SHORT).show();
            drawer.closeDrawers();
        } else if (v == myCart) {
            Toast.makeText(this, "clicked On My Cart", Toast.LENGTH_SHORT).show();
            drawer.closeDrawers();
        } else if (v == myOrder) {
            Toast.makeText(this, "clicked On My Order", Toast.LENGTH_SHORT).show();
            drawer.closeDrawers();
        } else if (v == myChat) {
            Intent i = new Intent(HomeActivity.this, UsersActivity.class);
            startActivity(i);
            finish();
            drawer.closeDrawers();
        } else if (v == sellOnEnggMart) {
            Toast.makeText(this, "clicked On Sell On Enggmart", Toast.LENGTH_SHORT).show();
            drawer.closeDrawers();
        } else if (v == accountSetting) {
            Toast.makeText(this, "clicked On Account Setting", Toast.LENGTH_SHORT).show();
            drawer.closeDrawers();
        } else if (v == helpCentre) {
            Toast.makeText(this, "clicked On Help Centre", Toast.LENGTH_SHORT).show();
            drawer.closeDrawers();
        } else if (v == share) {
            Toast.makeText(this, "clicked On Share", Toast.LENGTH_SHORT).show();

                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                    myIntent.setType("text/plain");
                    String shareBody = "";
                    String shareSub = "Your Subject here";
                    myIntent.putExtra(Intent.EXTRA_SUBJECT,shareBody);
                    myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                    startActivity(Intent.createChooser(myIntent,"Share using"));


            drawer.closeDrawers();
        } else if (v == imageProfile) {
            Toast.makeText(this, "clicked On Image Profile", Toast.LENGTH_SHORT).show();
            drawer.closeDrawers();
        }
    }


}
