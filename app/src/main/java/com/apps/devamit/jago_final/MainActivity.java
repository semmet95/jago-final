package com.apps.devamit.jago_final;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.support.v7.widget.ShareActionProvider;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private ShareActionProvider mShareActionProvider;

    static ImageDatabaseHelper databaseHelper;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    static int selectedItem=0;
    private ActionBar actionBar;
    private static final String[] actionBarTitles={"Home", "Contact Us", "About Us", "Advertisement Policy",
            "Privacy Policy", "FAQ", "Disclaimer"};
    static SharedPreferences DatesAndPages;
    static SharedPreferences.Editor editor;
    private DownloadManager downloadManager;

    static AdRequest adRequest;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, getResources().getString(R.string.appid));
        adRequest = new AdRequest.Builder().build();

        DatesAndPages= getSharedPreferences("DatesAndPages", MODE_PRIVATE);
        editor=DatesAndPages.edit();
        databaseHelper=new ImageDatabaseHelper(this);
        downloadManager=new DownloadManager(this);
        downloadManager.execute();

        actionBar=getSupportActionBar();
        mDrawerLayout= findViewById(R.id.drawer_layout);

        navigationView=findViewById(R.id.navigation);
        navigationView.setItemIconTintList(null);

        mDrawerToggle=new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_opened, R.string.drawer_closed) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawer(Gravity.START);
                navigationView.setCheckedItem(item.getItemId());
                FragmentManager fragmentManager = getSupportFragmentManager();
                if(item.getItemId()==R.id.nav_item_home&&selectedItem!=0) {
                    selectedItem=0;
                    actionBar.setTitle(actionBarTitles[0]);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
                } else if(item.getItemId()==R.id.nav_item_contactus&&selectedItem!=1) {
                    selectedItem=1;
                    actionBar.setTitle(actionBarTitles[1]);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new ContactFragment()).commit();
                } else if(item.getItemId()==R.id.nav_item_aboutus&&selectedItem!=2) {
                    selectedItem=2;
                    actionBar.setTitle(actionBarTitles[2]);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new AboutFragment()).commit();
                } else if(item.getItemId()==R.id.nav_item_advertisement&&selectedItem!=3) {
                    selectedItem=3;
                    actionBar.setTitle(actionBarTitles[3]);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new AdPolicyFragment()).commit();
                } else if(item.getItemId()==R.id.nav_item_privacy&&selectedItem!=4) {
                    selectedItem=4;
                    actionBar.setTitle(actionBarTitles[4]);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new PrivacyPolicyFragment()).commit();
                } else if(item.getItemId()==R.id.nav_item_faq&&selectedItem!=5) {
                    selectedItem=5;
                    actionBar.setTitle(actionBarTitles[5]);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new FaqFragment()).commit();
                } else if(item.getItemId()==R.id.nav_item_disclaimer&&selectedItem!=6) {
                    selectedItem=6;
                    actionBar.setTitle(actionBarTitles[6]);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new DisclaimerFragment()).commit();
                }
                return false;
            }
        });

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            mDrawerToggle.syncState();
        }

        navigationView.setCheckedItem(R.id.nav_item_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
        actionBar.setTitle(actionBarTitles[0]);
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                "http://play.google.com/store/apps/details?id=" + getPackageName());
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Jago Haridwar Android app");
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        MenuItem item = menu.findItem(R.id.shareApp);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        setShareIntent(createShareIntent());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        mDrawerToggle.syncState();
        if(mDrawerToggle.onOptionsItemSelected(item))
            return true;
        if(item.getItemId()==R.id.refresh) {
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            @SuppressLint("InflateParams") ImageView iv = (ImageView)inflater.inflate(R.layout.refresh_action_view, null);
            Animation rotation = AnimationUtils.loadAnimation(this, R.anim.refresh_anim);
            rotation.setRepeatCount(1);
            iv.startAnimation(rotation);
            item.setActionView(iv);
            rotation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    item.setActionView(null);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            if(downloadManager.getStatus()!= AsyncTask.Status.RUNNING)
                new DownloadManager(this).execute();
        } else if(item.getItemId()==R.id.rateUs) {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            }
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        mDrawerToggle.syncState();
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        mDrawerToggle.syncState();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(Gravity.START))
            mDrawerLayout.closeDrawer(Gravity.START);
        else if(selectedItem!=0) {
            navigationView.setCheckedItem(R.id.nav_item_home);
            actionBar.setTitle(actionBarTitles[0]);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
            selectedItem=0;
        }
        else
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        //Log.e("Main activity :", "old images deletion");
        //MainActivity.databaseHelper.deleteOldImages(MainActivity.databaseHelper, ""+MetadataHolder.oldDates[4]);
        super.onDestroy();
    }
}
