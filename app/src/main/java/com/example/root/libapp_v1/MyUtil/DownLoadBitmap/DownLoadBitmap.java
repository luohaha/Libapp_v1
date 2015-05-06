package com.example.root.libapp_v1.MyUtil.DownLoadBitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Yixin on 15-5-6.
 */
public class DownLoadBitmap {
    /**
     * open a http connection and down load a picture from internet
     * @param imageUrl the url of picture we want to download
     * @return the bitmap what we get
     */
    public static Bitmap downloadBitmap(String imageUrl) {
        Bitmap bitmap = null;
        HttpURLConnection httpURLConnection = null;
        InputStream is = null;
        InputStream iss = null;
        try {
            //create a new input stream
            // InputStream is = getInputStreamFromHttp(imageUrl, 5000, 10000);
            URL url = new URL(imageUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(10000);
            is = httpURLConnection.getInputStream();
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;


            BitmapFactory.decodeStream(is, null, options);
            options.inSampleSize = calculateInSampleSize(options, 100, 100);
            options.inJustDecodeBounds = false;
            is.close();
            //create a new input stream again, or you can not down load this picture
            //  InputStream iss = getInputStreamFromHttp(imageUrl, 5000, 10000);
            url = new URL(imageUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(10000);
            iss = httpURLConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(iss, null, options);
            iss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    /**
     * calculate the radio using request width and height
     * @param options which we want to get the old height and width from
     * @param requestWidth
     * @param requestHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int requestWidth,
                                      int requestHeight) {
        //get the old height and width
        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize = 1;
        //not suitable
        if (height > requestHeight || width > requestWidth) {
            final int heightRadio = Math.round((float) height / (float) requestHeight);
            final int widthRadio = Math.round((float) width / (float) requestWidth);
            inSampleSize = (heightRadio < widthRadio) ? heightRadio : widthRadio;
        }
        return inSampleSize;
    }
}
