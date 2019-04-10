package com.apps.devamit.jago_final;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class ClientsFragment extends Fragment {
    private ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] XMEN= {
            R.drawable.braviaimage,
            R.drawable.deepgangaimage,
            R.drawable.hecimage,
            R.drawable.lgimage,
            R.drawable.nricityimage,
            R.drawable.sdimtimage};
    private ArrayList<Integer> XMENArray = new ArrayList<>();
    private View layout;

    public ClientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout=inflater.inflate(R.layout.fragment_clients, container, false);
        init();
        setHasOptionsMenu(true);
        AdView mAdView = layout.findViewById(R.id.adView);
        mAdView.loadAd(MainActivity.adRequest);
        return layout;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.shareApp);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        super.onPrepareOptionsMenu(menu);
    }

    private void init() {
        XMENArray.addAll(Arrays.asList(XMEN));

        mPager = layout.findViewById(R.id.pager);
        mPager.setAdapter(new MyAdapter(getContext(), XMENArray));
        CircleIndicator indicator = layout.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }

    static public class MyAdapter extends PagerAdapter {

        private ArrayList<Integer> images;
        private LayoutInflater inflater;

        MyAdapter(Context context, ArrayList<Integer> images) {
            this.images=images;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View myImageLayout = inflater.inflate(R.layout.slide, view, false);
            ImageView myImage = myImageLayout
                    .findViewById(R.id.image);
            myImage.setImageResource(images.get(position));
            view.addView(myImageLayout, 0);
            return myImageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }
}