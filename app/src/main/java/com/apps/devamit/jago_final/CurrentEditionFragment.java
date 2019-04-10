package com.apps.devamit.jago_final;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CurrentEditionFragment extends Fragment {
    private ImageView currentEditionThumbnail;
    private TextView currentEditionDate;

    public CurrentEditionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_current_edition, container, false);
        currentEditionThumbnail=layout.findViewById(R.id.currentEditionThumbnail);
        currentEditionDate=layout.findViewById(R.id.currentEditionDate);
        if(MetadataHolder.oldDates[0]!=0) {
            String date=String.valueOf(MetadataHolder.oldDates[0]);
            date=date.substring(6)+"-"+date.substring(4, 6)+"-"+date.substring(0, 4);
            currentEditionDate.setText(date);
            currentEditionThumbnail.setImageBitmap(MainActivity.databaseHelper.retrieveImage(MainActivity.databaseHelper,
                                                                    String.valueOf(MetadataHolder.oldDates[0])+"01"));
        }
        Bitmap first=MainActivity.databaseHelper.retrieveImage(MainActivity.databaseHelper,
                String.valueOf(MetadataHolder.latestDates[0])+"01");
        if(MetadataHolder.latestDates[0]!=0) {
            refreshUI();
        }
        currentEditionThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentEditionThumbnail.setBackground(getResources().getDrawable(R.drawable.ripple_selector));
                FullscreenAdapter.key=0;
                Intent intent=new Intent(getContext(), FullscreenActivity.class);
                getContext().startActivity(intent);
            }
        });

        return layout;
    }

    void refreshUI() {
        String date=String.valueOf(MetadataHolder.latestDates[0]);
        date=date.substring(6)+"-"+date.substring(4, 6)+"-"+date.substring(0, 4);
        Log.e("Current Edition :", "Setting date");
        currentEditionDate.setText(date);
        currentEditionThumbnail.setImageBitmap(MainActivity.databaseHelper.retrieveImage(MainActivity.databaseHelper,
                                                            String.valueOf(MetadataHolder.latestDates[0])+"01"));
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
            Log.e("setting :", "current first image");
            refreshUI();
        }
    };
}