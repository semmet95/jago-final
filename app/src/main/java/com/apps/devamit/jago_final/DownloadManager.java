package com.apps.devamit.jago_final;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

class DownloadManager extends AsyncTask<Void, Void, Void> {
    @SuppressLint("StaticFieldLeak")
    private Context context;

    DownloadManager(Context c) {
        context=c;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        MetadataDownloader metadataDownloader=new MetadataDownloader(context);
        metadataDownloader.initializeOldDates();
        metadataDownloader.downloadMetadata();
        metadataDownloader.refreshOldDates();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.e("DownloadManager :", "gonna start the service");
        context.startService(new Intent(context, ImageDownloadService.class));
    }

    //clear database of old images
}
