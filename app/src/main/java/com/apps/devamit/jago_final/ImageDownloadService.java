package com.apps.devamit.jago_final;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class ImageDownloadService extends IntentService implements BasicImageDownloader.OnImageLoaderListener {

    public ImageDownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        BasicImageDownloader basicImageDownloader=new BasicImageDownloader(this);
        String[] folderNames=getResources().getStringArray(R.array.folderNames);
        for(int i=0;i<5;i++) {
            Log.e("in service :", "starting download");
            for(int j=1;j<=MetadataHolder.latestPages[i];j++) {
                Bitmap image=MainActivity.databaseHelper.retrieveImage(MainActivity.databaseHelper,
                        ""+MetadataHolder.latestDates[i]+(j<10?"0":"")+j);
                if(image==null) {
                    int image_key=Integer.parseInt(MetadataHolder.latestDates[i]+(j<10?"0":"")+j);
                    //used to use folder name in the url
                    String url="http://res.cloudinary.com/devamit/image/upload/"+image_key+".jpg";
                    Log.e("Downloading with url :", url+" and key :: "+image_key);
                    basicImageDownloader.download(url, image_key);
                }
            }
        }
    }

    @Override
    public void onError(BasicImageDownloader.ImageError error) {
        Log.e("Download status :", error.getMessage());
    }

    @Override
    public void onProgressChange(int percent) {
        Log.e("Download status :", percent+"%");
    }

    @Override
    public void onComplete(Bitmap result, int image_key) {
        Log.e("Download status :", "completed");
        MainActivity.databaseHelper.insertImage(MainActivity.databaseHelper, image_key, result);
        if(String.valueOf(image_key).endsWith("01")||image_key%100==1) {
            Intent intent=new Intent("first image downloaded");
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
        //MainActivity.databaseHelper.deleteOldImages(MainActivity.databaseHelper, ""+MetadataHolder.oldDates[4]);
    }
}
