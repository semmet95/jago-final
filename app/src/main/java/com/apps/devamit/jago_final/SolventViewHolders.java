package com.apps.devamit.jago_final;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


class SolventViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView imagelabel;
    ImageView thumbnail;

    SolventViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        imagelabel= itemView.findViewById(R.id.imagelabel);
        thumbnail=itemView.findViewById(R.id.thumbnail);
    }

    @Override
    public void onClick(View v) {
        MainActivity mainActivity=(MainActivity)imagelabel.getContext();
        if(imagelabel.getText().equals("Classifieds")) {
            mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                                                                                new ClassifiedsFragment()).commit();
            MainActivity.selectedItem=-1;
            if(mainActivity.getSupportActionBar()!=null)
                mainActivity.getSupportActionBar().setTitle("Classifieds");
        } else if(imagelabel.getText().equals("Online Booking")) {
            mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                    new OnlineBookingFragment()).commit();
            MainActivity.selectedItem=-1;
            if(mainActivity.getSupportActionBar()!=null)
                mainActivity.getSupportActionBar().setTitle("Online Booking");
        } else if(imagelabel.getText().equals("Tariff")) {
            mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                    new TariffFragment()).commit();
            MainActivity.selectedItem=-1;
            if(mainActivity.getSupportActionBar()!=null)
                mainActivity.getSupportActionBar().setTitle("Tariff");
        }else if(imagelabel.getText().equals("Our Clients")) {
            mainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,
                    new ClientsFragment()).commit();
            MainActivity.selectedItem=-1;
            if(mainActivity.getSupportActionBar()!=null)
                mainActivity.getSupportActionBar().setTitle("Our Clients");
        }
    }
}
