package com.example.root.libapp_v1.GetPictureActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.MyUtil.DownLoadBitmap.ImageTools;
import com.example.root.libapp_v1.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.FileNameMap;

/**
 * Created by Yixin on 15-5-10.
 */
public class GetPictureActivity extends Activity {
    private HeadBar mHeadBar;
    private Button mTakepic;
    private Button mChoosepic;
    private ImageView mImageView;

    private static final int TAKE_PICTURE = 0;
    private static final int CHOOSE_PICTURE = 1;

    private static final int SCALE = 5;//照片缩小比例
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getpic);
        initHeadBar();
        initView();
    }

    private void initHeadBar() {
        mHeadBar = (HeadBar) findViewById(R.id.getpic_head_bar);
        mHeadBar.setTitleText("获取图片");
        mHeadBar.setRightButtonNoused();
        mHeadBar.setRightSecondButtonNoused();
        mHeadBar.setLeftSecondButtonNoused();
        mHeadBar.setLeftButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mTakepic = (Button) findViewById(R.id.getpic_takepic_button);
        mChoosepic = (Button) findViewById(R.id.getpic_choosepic_button);
        mTakepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri imageUri = Uri.fromFile(new File("/mnt/sdcard/feishu/image.jpg"));
                //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(openCameraIntent, TAKE_PICTURE);
            }
        });
        mChoosepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType("image/*");
                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    //将保存在本地的图片取出并缩小后显示在界面上
                    Bitmap bitmap = BitmapFactory.decodeFile("/mnt/sdcard/feishu/image.jpg");
                    Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
                    //由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
                    bitmap.recycle();

                    //将处理过的图片显示在界面上，并保存到本地
                    mImageView.setImageBitmap(newBitmap);
                    Intent intent = getIntent();
                    ImageTools.savePhotoToSDCard(newBitmap, "/mnt/sdcard/feishu/", intent.getStringExtra("name"));
                    break;

                case CHOOSE_PICTURE:
                    ContentResolver resolver = getContentResolver();
                    //照片的原始资源地址
                    Uri originalUri = data.getData();
                    try {
                        //使用ContentProvider通过URI获取原始图片
                        Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                        if (photo != null) {
                            //为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                            Bitmap smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
                            //释放原始图片占用的内存，防止out of memory异常发生
                            photo.recycle();

                            mImageView.setImageBitmap(smallBitmap);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
        }
    }
}
