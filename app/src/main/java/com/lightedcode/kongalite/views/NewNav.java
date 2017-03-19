package com.lightedcode.kongalite.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.service.chooser.ChooserTargetService;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.lightedcode.kongalite.R;
import com.lightedcode.kongalite.fragments.Homeviews;
import com.lightedcode.kongalite.fragments.News;
import com.lightedcode.kongalite.fragments.Promotion;
import com.lightedcode.kongalite.fragments.Settings;

public class NewNav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PROMOTIONS = "Promotions";
    private static final String TAG_NEWS = "News";
    private static final String TAG_FEED = "Feed back";
    private static final String TAG_Share= "Share";
    private static final String TAG_Help= "How to shop";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    private static final String TAG_SIGNOUT= "Sign out";
    public static String CURRENT_TAG = TAG_HOME;
    FloatingActionButton fab;
    private String[] activityTitles;


    // flag to load home fragment when user presses back key
    private boolean doubleBackToExitPressedOnce = true;
    private Handler mHandler;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawer;
    Intent intent, chooser;
    FirebaseAuth auth;
    DatabaseReference dref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_nav);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         mHandler = new Handler();
        fab = (FloatingActionButton)findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);


        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }else {
           loadHomeFragment();
        }
    }

    @Override
    public void onBackPressed() {
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
       // if (this.doubleBackToExitPressedOnce == true){
         //   setFragments(new MainFragment());
       // }
        if (doubleBackToExitPressedOnce) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewNav.this);
                builder.setMessage("Are you sure you want to exit?");
                builder.setTitle("Exit");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        NewNav.this.finishAffinity();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.new_nav, menu);
        }
        // show menu only when profile fragment is selected
        if (navItemIndex == 1) {
            getMenuInflater().inflate(R.menu.new_nav, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 3) {
            getMenuInflater().inflate(R.menu.notification, menu);
        }
        if (navItemIndex == 4) {
            getMenuInflater().inflate(R.menu.help_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.shop) {
            Intent intent = new Intent(getApplicationContext(), Others.class);
            startActivity(intent);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (item.getItemId()){

            case R.id.nav_home:
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                break;
            case R.id.nav_promo:
                navItemIndex = 1;
                CURRENT_TAG = TAG_PROMOTIONS;
                break;
            case R.id.nav_news:
                navItemIndex = 2;
                CURRENT_TAG = TAG_NEWS;
                break;
            case R.id.nav_notifications:
                drawer.closeDrawers();
                Toast.makeText(getApplicationContext(),"No action for this yet", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_help:
                drawer.closeDrawers();
                break;
            case R.id.action_feed:
                getfeedback();
                break;
            case R.id.action_share:
                drawer.closeDrawers();
                break;
            case R.id.nav_settings:
                navItemIndex = 7;
                CURRENT_TAG = TAG_SETTINGS;
                break;
            case R.id.sign_out:
                navItemIndex = 8;
                CURRENT_TAG = TAG_SIGNOUT;
                SigningOut();
                break;
            default:
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
        }

        item.setChecked(false);
        loadHomeFragment();
        return true;
    }



    FragmentManager.OnBackStackChangedListener back = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {


        }

    };

    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set cancel_toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            getHomeFragment(0);
            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment(navItemIndex);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.containerView, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        //  toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh cancel_toolbar menu
        invalidateOptionsMenu();
    }




    private Fragment getHomeFragment(int position) {

        Fragment myfragmen = null;
        switch (position) {
            case 0:
                myfragmen = new Homeviews();
                break;
            case 1:
                myfragmen = new Promotion();
                break;
            case 2:
                myfragmen = new News();
                break;
            case 7:
                myfragmen = new Settings();
                break;

            default:
                myfragmen = new Homeviews();
        }
        return myfragmen;
    }

    private void setToolbarTitle() {
        if (navItemIndex == 0){
            toolbar.setTitle(activityTitles[0]);
        }else {
            toolbar.setTitle(activityTitles[navItemIndex]);
        }
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }
    private void toggleFab() {
        if (navItemIndex == 0) {
            fab.show();
        } else{
            fab.hide();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), Categories.class);
                startActivity(intent);
            }
        });
    }
    private void getfeedback(){
        intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        String[] us = {"josephookoedo@gmail.com", "lightedcode@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL,us);
        intent.putExtra(Intent.EXTRA_SUBJECT,"My Feed Back");
        intent.putExtra(Intent.EXTRA_TEXT," Please you are to type here");
        intent.setType("message/rfc822");
       chooser = Intent.createChooser(intent,"Send Email");
        startActivity(chooser);

    }

    private void authlistner(){
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){

                }
            }
        });
    }
    private void SigningOut(){
        AlertDialog.Builder builder = new AlertDialog.Builder(NewNav.this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setTitle("Exit");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                auth.signOut();
                dialogInterface.cancel();
                Toast.makeText(getApplicationContext(),"No action for this yet", Toast.LENGTH_LONG).show();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
