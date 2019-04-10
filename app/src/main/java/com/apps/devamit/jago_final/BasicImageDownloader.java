package com.apps.devamit.jago_final;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

class BasicImageDownloader {

    private OnImageLoaderListener mImageLoaderListener;
    private Set<String> mUrlsInProgress = new HashSet<>();
    private final String TAG = this.getClass().getSimpleName();

    BasicImageDownloader(@NonNull OnImageLoaderListener listener) {
        this.mImageLoaderListener = listener;
    }

    public interface OnImageLoaderListener {
        void onError(ImageError error);
        void onProgressChange(int percent);
        void onComplete(Bitmap result, int key);
    }


    void download(@NonNull final String imageUrl, final int image_key) {
        if (mUrlsInProgress.contains(imageUrl)) {
            Log.w(TAG, "a download for this url is already running, " +
                    "no further download will be started");
            return;
        }

        new AsyncTask<Void, Integer, Bitmap>() {

            private ImageError error;

            @Override
            protected void onPreExecute() {
                mUrlsInProgress.add(imageUrl);
                Log.d(TAG, "starting download");
            }

            @Override
            protected void onCancelled() {
                mUrlsInProgress.remove(imageUrl);
                mImageLoaderListener.onError(error);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                mImageLoaderListener.onProgressChange(values[0]);
            }

            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap bitmap = null;
                HttpURLConnection connection = null;
                InputStream is = null;
                ByteArrayOutputStream out = null;
                try {
                    connection = (HttpURLConnection) new URL(imageUrl).openConnection();

                    is = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (Throwable e) {
                    if (!this.isCancelled()) {
                        error = new ImageError(e).setErrorCode();
                        this.cancel(true);
                    }
                } finally {
                    try {
                        if (connection != null)
                            connection.disconnect();
                        if (out != null) {
                            out.flush();
                            out.close();
                        }
                        if (is != null)
                            is.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                if (result == null) {
                    Log.e(TAG, "factory returned a null result");
                    mImageLoaderListener.onError(new ImageError("downloaded file could not be decoded as bitmap")
                            .setErrorCode());
                } else {
                    Log.d(TAG, "download complete, " + result.getByteCount() +
                            " bytes transferred");
                    mImageLoaderListener.onComplete(result, image_key);
                }
                mUrlsInProgress.remove(imageUrl);
                System.gc();
            }
        }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    static final class ImageError extends Throwable {

        ImageError(@NonNull String message) {
            super(message);
        }

        ImageError(@NonNull Throwable error) {
            super(error.getMessage(), error.getCause());
            this.setStackTrace(error.getStackTrace());
        }

        ImageError setErrorCode() {
            return this;
        }

    }
}