package com.apps.devamit.jago_final;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

public class ContactFragment extends Fragment {

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_contact, container, false);
        AdView mAdView = layout.findViewById(R.id.adView);
        mAdView.loadAd(MainActivity.adRequest);

        TextView branch_office = layout.findViewById(R.id.branch_office);
        branch_office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?q=loc:" + 29.933301 + "," + 78.132577 + " (" + "Jago Haridwar" + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                getContext().startActivity(intent);
            }
        });

        setHasOptionsMenu(true);
        return layout;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.shareApp);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        super.onPrepareOptionsMenu(menu);
    }
}
