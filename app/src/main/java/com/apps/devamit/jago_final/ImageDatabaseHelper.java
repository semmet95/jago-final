package com.apps.devamit.jago_final;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * The images are stored with their edition date and serial number
 * concatenating to form a unique key, its primary key
 */

public class ImageDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="ImagesAndMetadata";
    private static final int DB_VERSION=10;
    private static final String TABLE_1_NAME="Images";
    private SQLiteDatabase sqLiteDatabase;

    ImageDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sqLiteDatabase=db;
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_1_NAME+" (IMAGEKEY INTEGER UNIQUE, "+"IMAGE BLOB);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_1_NAME);
        onCreate(sqLiteDatabase);
    }

    void insertImage(ImageDatabaseHelper helper, int image_key, Bitmap image) {
        Log.e("inserting image :", "with key = "+image_key);
        byte[] imageByte=filetoByte(image);
        ContentValues imageValues=new ContentValues();
        imageValues.put("IMAGEKEY", image_key);
        imageValues.put("IMAGE", imageByte);
        helper.getWritableDatabase().insertWithOnConflict(TABLE_1_NAME, null, imageValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    Bitmap retrieveImage(ImageDatabaseHelper helper, String key) {
        Cursor cursor=helper.getReadableDatabase().query(TABLE_1_NAME, new String[]{"IMAGE"}, "IMAGEKEY=?", new String[]{key}, null, null, null);
        if(cursor.moveToFirst()) {
            byte[] blob=cursor.getBlob(cursor.getColumnIndex("IMAGE"));
            cursor.close();
            try {
                return BitmapFactory.decodeByteArray(blob, 0, blob.length);
            }
            catch(OutOfMemoryError e) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize=8;
                return BitmapFactory.decodeByteArray(blob, 0, blob.length, options);
            }
        }
        cursor.close();
        return null;
    }

    void deleteOldImages(ImageDatabaseHelper helper, String key) {
        int deleted=helper.getWritableDatabase().delete(TABLE_1_NAME, "IMAGEKEY<?", new String[]{key+"00"});
        Log.e("deleted :", deleted+" rows for IMAGEKEY<"+key+"00");
    }

    private static byte[] filetoByte(Bitmap bitmap) {
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, blob);
        return blob.toByteArray();
    }
}
