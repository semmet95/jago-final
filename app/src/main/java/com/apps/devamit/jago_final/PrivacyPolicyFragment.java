package com.apps.devamit.jago_final;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.google.android.gms.ads.AdView;

public class PrivacyPolicyFragment extends Fragment {

    public PrivacyPolicyFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        AdView mAdView = layout.findViewById(R.id.adView);
        mAdView.loadAd(MainActivity.adRequest);
        setHasOptionsMenu(true);
        WebView privacyWebView = layout.findViewById(R.id.privacyWebView);
        privacyWebView.getSettings().setJavaScriptEnabled(true);
        privacyWebView.loadUrl("file:///android_asset/privacy_policy.html");
        return layout;
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.shareApp);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        super.onPrepareOptionsMenu(menu);
    }
}
