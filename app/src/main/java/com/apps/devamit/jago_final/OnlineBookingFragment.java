package com.apps.devamit.jago_final;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdView;

public class OnlineBookingFragment extends Fragment {

    private EditText date, name, address, city, country, contact, mail, adText;
    private AppCompatCheckBox full5, full4, two5, two4, four2, eight1, sixteen1;

    public OnlineBookingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_online_booking, container, false);
        AdView mAdView = layout.findViewById(R.id.adView);
        mAdView.loadAd(MainActivity.adRequest);

        setHasOptionsMenu(true);

        date=layout.findViewById(R.id.date);
        name=layout.findViewById(R.id.name);
        address=layout.findViewById(R.id.address);
        city=layout.findViewById(R.id.city);
        country=layout.findViewById(R.id.country);
        contact=layout.findViewById(R.id.contact);
        mail=layout.findViewById(R.id.mail);
        adText=layout.findViewById(R.id.adText);
        Button submit = layout.findViewById(R.id.submit);
        full5=layout.findViewById(R.id.full5);
        full4=layout.findViewById(R.id.full4);
        two5=layout.findViewById(R.id.two5);
        two4=layout.findViewById(R.id.two4);
        four2=layout.findViewById(R.id.four2);
        eight1=layout.findViewById(R.id.eight1);
        sixteen1=layout.findViewById(R.id.sixteen1);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mailBody="Insertion Date: "+date.getText()+"\n"
                                    +"Full Name: "+name.getText()+"\n"
                                    +"Address: "+address.getText()+"\n"
                                    +"City/State: "+city.getText()+"\n"
                                    +"Country: "+country.getText()+"\n"
                                    +"Contact number: "+contact.getText()+"\n"
                                    +"E-mail: "+mail.getText()+"\n"
                                    +"Advertisement type: \n"
                                    +(full5.isChecked()?"Full Page (Inside) - 05 Columns\n":"")
                                    +(full4.isChecked()?"Full Page (Inside) - 04 Columns\n":"")
                                    +(two5.isChecked()?"1/2 Page - 05 Columns\n":"")
                                    +(two4.isChecked()?"1/2 Page - 04 Columns\n":"")
                                    +(four2.isChecked()?"1/4 Page - 02 Columns\n":"")
                                    +(eight1.isChecked()?"1/8 Page - 01 Column\n":"")
                                    +(sixteen1.isChecked()?"1/16 Page - 01 Column\n":"")
                                    +"\n"+"Advertisement Text: \n"+adText.getText()+"\n";

                Intent send = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("jagoharidwar@gmail.com") +
                        "?subject=" + Uri.encode("Online Booking") +
                        "&body=" + Uri.encode(mailBody);
                Uri uri = Uri.parse(uriText);
                send.setData(uri);
                startActivity(Intent.createChooser(send, "Send mail..."));
            }
        });
        return layout;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.shareApp);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        super.onPrepareOptionsMenu(menu);
    }
}
