package com.apps.devamit.jago_final;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

class MetadataDownloader {
    private Context context;
    private boolean downloadSucceeded;
    private StringBuilder oldDatesAndPages;

    MetadataDownloader(Context c) {
        context=c;
        downloadSucceeded=true;
    }

    void downloadMetadata() {
        String[] folderNames=context.getResources().getStringArray(R.array.folderNames);
        for(int i=0;i<5;i++) {
            try {
                URL url = new URL("https://s3.amazonaws.com/jagofinal/" + folderNames[i] + "/Metadata.txt?versionId=null");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                MetadataHolder.latestDates[i] = Integer.parseInt(in.readLine());
                MetadataHolder.latestPages[i] = Integer.parseInt(in.readLine());
                Log.e("Yosha :", "s3 is working");
            } catch (IOException e) {
                Log.e("Crap :", "s3 not working");
                downloadSucceeded=false;
                e.printStackTrace();
            }
        }
    }

    void initializeOldDates() {
        oldDatesAndPages= new StringBuilder(MainActivity.DatesAndPages.getString("oldDatesAndPages", "NA"));
        if(oldDatesAndPages.toString().equals("NA")) {
            oldDatesAndPages = new StringBuilder("00000000 0,00000000 0,00000000 0,00000000 0,00000000 0");
        }
        Log.e("here :", "what the hell "+oldDatesAndPages);
        String[] editionWise= oldDatesAndPages.toString().split(",");
        for(int i=0;i<5;i++) {
            String[] perEdition=editionWise[i].split(" ");
            Log.e("here :", "in adding old dates "+perEdition[0]);
            MetadataHolder.oldDates[i]=Integer.parseInt(perEdition[0]);
            MetadataHolder.oldPages[i]=Integer.parseInt(perEdition[1]);
        }
    }

    void refreshOldDates() {
        if(downloadSucceeded&&MetadataHolder.latestDates[0]!=0) {

            Intent intent=new Intent("image downloaded");
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            oldDatesAndPages = new StringBuilder(MetadataHolder.latestDates[0] + " " + MetadataHolder.latestPages[0]);
            for(int i=1;i<5;i++)
                oldDatesAndPages.append(",").append(MetadataHolder.latestDates[i]).append(" ").append(MetadataHolder.latestPages[i]);
            Log.e("MetadataDownloader:", "old images deletion");
            MainActivity.databaseHelper.deleteOldImages(MainActivity.databaseHelper, ""+MetadataHolder.oldDates[4]);
        }
        Log.e("here :", "putting string "+oldDatesAndPages.toString());
        MainActivity.editor.putString("oldDatesAndPages", oldDatesAndPages.toString()).apply();
    }
}
