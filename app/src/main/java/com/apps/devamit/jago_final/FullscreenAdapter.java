package com.apps.devamit.jago_final;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FullscreenAdapter extends PagerAdapter {
    static int key;

    @Override
    public int getCount() {
        if(MetadataHolder.latestPages[key]!=0) {
            return MetadataHolder.latestPages[key];
        }
        return MetadataHolder.oldPages[key];
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View myImageLayout= LayoutInflater.from(container.getContext()).inflate(R.layout.imageview_fullscreen,
                                                container, false);
        TouchImageView myImage=myImageLayout.findViewById(R.id.touchImageView);

        int currDate=MetadataHolder.oldDates[key];
        if(MetadataHolder.latestDates[key]!=0)
            currDate=MetadataHolder.latestDates[key];
        ++position;
        String image_key=currDate+((position<10)?"0":"")+position;
        Bitmap bitmap=MainActivity.databaseHelper.retrieveImage(MainActivity.databaseHelper, image_key);
        if(bitmap!=null) {
            FullscreenActivity.imageToShare=bitmap;
            myImage.setImageBitmap(bitmap);
        }

        container.addView(myImageLayout);
        return myImageLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}