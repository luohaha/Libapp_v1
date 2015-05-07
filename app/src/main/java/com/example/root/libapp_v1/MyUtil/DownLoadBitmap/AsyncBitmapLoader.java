package com.example.root.libapp_v1.MyUtil.DownLoadBitmap;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.os.Handler;
import android.widget.ImageView;


import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * Created by root on 15-5-6.
 */
public class AsyncBitmapLoader {
    private HashMap<String, SoftReference<Bitmap>> imageCache = null;

    public AsyncBitmapLoader() {
        imageCache = new HashMap<String, SoftReference<Bitmap>>();
    }

    public Bitmap loadBitmap(final ImageView imageView, final String imageUrl, final ImageCallBack imageCallBack) {
        /**
         * first step, find this image in cache
         * */
        if (imageCache.containsKey(imageUrl)) {
            SoftReference<Bitmap> reference = imageCache.get(imageUrl);
            Bitmap bitmap = reference.get();
            if (bitmap != null) {
                return bitmap;
            }
        } else {
            /**
             * second step, find this image in sdcard
             * */
            String bitmapName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
            File cacheDir = new File("/mnt/sdcard/feishu/");
            File[] cacheFiles = cacheDir.listFiles();
            if (cacheFiles != null) {
                int i = 0;
                for (; i < cacheFiles.length; i++) {
                    if (bitmapName.equals(cacheFiles[i].getName())) {
                        break;
                    }
                }
                /**
                 * if the image found in dir
                 * */
                if (i < cacheFiles.length) {
                    return BitmapFactory.decodeFile("/mnt/sdcard/feishu/"+bitmapName);
                }
            }
        }

        final Handler handler = new Handler() {

            public void handleMessage(Message msg) {
                imageCallBack.imageLoad(imageView, (Bitmap)msg.obj);
            }
        };

        /**
         * third step, if the image not found in cache and sdcard dir,
         *          then down load it from http
         * */
        new Thread() {
            public void run() {
                try {

                    Bitmap httpBitmap = DownLoadBitmap.downloadBitmap(imageUrl);
                    imageCache.put(imageUrl, new SoftReference<Bitmap>(httpBitmap));
                    Message message = handler.obtainMessage(0, httpBitmap);
                    handler.sendMessage(message);

                    File dir = new File("/mnt/sdcard/feishu/");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    File bitmapFile = new File("/mnt/sdcard/feishu/"+imageUrl.substring(imageUrl.lastIndexOf("/")+1));
                    if (!bitmapFile.exists()) {
                        try {
                            bitmapFile.createNewFile();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    FileOutputStream fileOutputStream;
                    fileOutputStream = new FileOutputStream(bitmapFile);
                    httpBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return null;
    }


    public interface ImageCallBack
    {
        public void imageLoad(ImageView imageView, Bitmap bitmap);
    }
}
