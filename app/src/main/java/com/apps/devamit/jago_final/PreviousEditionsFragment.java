package com.apps.devamit.jago_final;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PreviousEditionsFragment extends Fragment {
    private RecyclerView mRecyclerView;

    public PreviousEditionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout= inflater.inflate(R.layout.fragment_previous_editions, container, false);
        mRecyclerView = layout.findViewById(R.id.previous_editions_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(MetadataHolder.oldDates[0]!=0)
            mRecyclerView.setAdapter(new PreviousEditions_Adapter(true));
        if(MetadataHolder.latestDates[4]!=0)
            refreshUI();
        return layout;
    }

    void refreshUI() {
        if(MetadataHolder.latestDates[4]!=0)
            mRecyclerView.setAdapter(new PreviousEditions_Adapter(false));
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(downloadMessageReceiver,
                new IntentFilter("image downloaded"));
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(downloadMessageReceiver);
        super.onPause();
    }

    private BroadcastReceiver downloadMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshUI();
            Log.d("previouseditionfragment", "received image downloaded message");
        }
    };
}