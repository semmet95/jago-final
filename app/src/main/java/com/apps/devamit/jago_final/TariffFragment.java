package com.apps.devamit.jago_final;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TariffFragment extends Fragment {
    private TouchImageViewGeneral tariffImage;
    private TextView retryTariff;
    private ProgressBar tariffProgress;

    public TariffFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_tariff, container, false);
        AdView mAdView = layout.findViewById(R.id.adView);
        mAdView.loadAd(MainActivity.adRequest);

        tariffImage=layout.findViewById(R.id.tariffImage);
        tariffImage.setImageDrawable(getResources().getDrawable(R.drawable.tariff));
        retryTariff=layout.findViewById(R.id.retryTariff);
        tariffProgress=layout.findViewById(R.id.tariffProgress);
        retryTariff.setVisibility(View.INVISIBLE);
        tariffProgress.setVisibility(View.INVISIBLE);
        tariffImage.setVisibility(View.VISIBLE);
        retryTariff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retryTariff.setVisibility(View.INVISIBLE);
                tariffProgress.setVisibility(View.VISIBLE);
                startDownload();
            }
        });
        startDownload();
        return layout;
    }

    private void startDownload() { //new TariffDownload(this).execute();
         }

    private static class TariffDownload extends AsyncTask<Void, Integer, Bitmap> {
        private TariffFragment context;
        TariffDownload(TariffFragment c) {
            context=c;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Log.e("Tariff :", "trying to download");
            Bitmap bitmap = null;
            HttpURLConnection connection = null;
            InputStream is = null;
            try {
                connection = (HttpURLConnection) new URL("http://jagoharidwar.com/downloads/JAGO-QUOTATION.jpg")
                                                        .openConnection();

                is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
            } catch (Throwable e) {
                Handler mainHandler= new Handler(context.getContext().getMainLooper());
                Runnable myRunnable=new Runnable() {
                    @Override
                    public void run() {
                        Bitmap img=MainActivity.databaseHelper.retrieveImage(MainActivity.databaseHelper,
                                "1111111111");
                        if(img!=null) {
                            Log.e("Tariff :", "downloaded");
                            context.tariffProgress.setVisibility(View.INVISIBLE);
                            context.tariffImage.setVisibility(View.VISIBLE);
                            context.retryTariff.setVisibility(View.INVISIBLE);
                            context.tariffImage.setImageBitmap(img);
                        }
                        else {
                            context.tariffProgress.setVisibility(View.INVISIBLE);
                            context.tariffImage.setVisibility(View.INVISIBLE);
                            //context.retryTariff.setVisibility(View.VISIBLE);
                        }
                    }
                };
                mainHandler.post(myRunnable);
                if (!this.isCancelled()) {
                    this.cancel(true);
                }
            } finally {
                try {
                    if (connection != null)
                        connection.disconnect();
                    if (is != null)
                        is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.e("Tariff :", "onPostExecute");
            if(bitmap!=null) {
                MainActivity.databaseHelper.insertImage(MainActivity.databaseHelper, 1111111111, bitmap);
                context.tariffProgress.setVisibility(View.INVISIBLE);
                context.tariffImage.setVisibility(View.VISIBLE);
                context.retryTariff.setVisibility(View.INVISIBLE);
                context.tariffImage.setImageBitmap(bitmap);
            }
            else {
                context.tariffProgress.setVisibility(View.INVISIBLE);
                context.tariffImage.setVisibility(View.INVISIBLE);
                //context.retryTariff.setVisibility(View.VISIBLE);
            }
        }


    }
}
