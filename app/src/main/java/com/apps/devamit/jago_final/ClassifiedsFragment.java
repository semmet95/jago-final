package com.apps.devamit.jago_final;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class ClassifiedsFragment extends Fragment {
    private ViewPager mPager;

    public ClassifiedsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_classifieds, container, false);
        mPager= layout.findViewById(R.id.pager);
        TabLayout tabLayout = layout.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Current Edition"));
        tabLayout.addTab(tabLayout.newTab().setText("Last Four Editions"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mPager.setAdapter(new PagerAdapter(getChildFragmentManager()));
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        setHasOptionsMenu(true);
        return layout;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.shareApp);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        super.onPrepareOptionsMenu(menu);
    }

    private static class PagerAdapter extends FragmentPagerAdapter {

        PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment temp;
            if(position==0)
                temp=new CurrentEditionFragment();
            else
                temp=new PreviousEditionsFragment();
            return temp;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
