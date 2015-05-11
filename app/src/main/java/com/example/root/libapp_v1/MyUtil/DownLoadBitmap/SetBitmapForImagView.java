package com.example.root.libapp_v1.MyUtil.DownLoadBitmap;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.root.libapp_v1.R;

/**
 * Created by Yixin on 15-5-11.
 */
public class SetBitmapForImagView {
    /**
     * get img from db or http
     * */
    public static void setBitmapForImageView(AsyncBitmapLoader loader, ImageView imageView, String url) {
        Bitmap bitmap = loader.loadBitmap(imageView,
                url, new AsyncBitmapLoader.ImageCallBack() {
                    @Override
                    public void imageLoad(ImageView imageView, Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                });
        if (bitmap == null) {
            imageView.setImageResource(R.drawable.ic_launcher);
        } else {
            imageView.setImageBitmap(bitmap);
        }

    }
}
