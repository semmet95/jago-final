package com.apps.devamit.jago_final;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdView;

import at.blogc.android.views.ExpandableTextView;

public class FaqFragment extends Fragment {
    private ExpandableTextView ans1, ans2, ans3, ans4, ans5;

    public FaqFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_faq, container, false);
        AdView mAdView = layout.findViewById(R.id.adView);
        mAdView.loadAd(MainActivity.adRequest);

        ans1=layout.findViewById(R.id.ans1);
        ans2=layout.findViewById(R.id.ans2);
        ans3=layout.findViewById(R.id.ans3);
        ans4=layout.findViewById(R.id.ans4);
        ans5=layout.findViewById(R.id.ans5);

        ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans1.toggle();
            }
        });
        ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans2.toggle();
            }
        });
        ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans3.toggle();
            }
        });
        ans4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans4.toggle();
            }
        });
        ans5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ans5.toggle();
            }
        });

        return layout;
    }
}