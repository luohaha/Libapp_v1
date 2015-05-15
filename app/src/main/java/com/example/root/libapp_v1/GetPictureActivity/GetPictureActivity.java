package com.example.root.libapp_v1.GetPictureActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.root.libapp_v1.HeadBar.HeadBar;
import com.example.root.libapp_v1.MyUtil.DownLoadBitmap.DownLoadBitmap;
import com.example.root.libapp_v1.MyUtil.DownLoadBitmap.ImageTools;
import com.example.root.libapp_v1.MyUtil.DownLoadBitmap.UploadFile;
import com.example.root.libapp_v1.R;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by Yixin on 15-5-10.
 */
public class GetPictureActivity extends Activity {
    private HeadBar mHeadBar;
    private Button mTakepic;
    private Button mChoosepic;
    private Button mSubmitpic;
    private ImageView mImageView;

    private String mPictureName;

    private static final int TAKE_PICTURE = 0;
    private static final int CHOOSE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getpic);
        getPicName();
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

    private void getPicName() {
        Intent intent = getIntent();
        if (intent.getStringExtra("flag").equals("personpic")) {
            mPictureName = "personimg_" + intent.getStringExtra("getpic_name");
        } else if (intent.getStringExtra("flag").equals("bookpic")) {
            mPictureName = "bookimg_" + intent.getStringExtra("getpic_name");
        } else {
            Dialog dialog = new AlertDialog.Builder(GetPictureActivity.this).setTitle("失败")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
            dialog.show();
        }
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.getpic_imageview);
        mImageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mTakepic = (Button) findViewById(R.id.getpic_takepic_button);
        mChoosepic = (Button) findViewById(R.id.getpic_choosepic_button);
        mSubmitpic = (Button) findViewById(R.id.getpic_submit_button);
        mTakepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri imageUri = Uri.fromFile(new File("/mnt/sdcard/feishu/image.jpg"));
                //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                mSubmitpic.setVisibility(View.VISIBLE);
                startActivityForResult(openCameraIntent, TAKE_PICTURE);
            }
        });
        mChoosepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType("image/*");
                mSubmitpic.setVisibility(View.VISIBLE);
                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);

            }
        });
        mSubmitpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpTask httpTask = new HttpTask();
                httpTask.execute();
                finish();
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
                    try {
                        Bitmap bitmap = DownLoadBitmap.loadBitmapFromFile("/mnt/sdcard/feishu/image.jpg");


                        //将处理过的图片显示在界面上，并保存到本地
                        mImageView.setImageBitmap(bitmap);
                        ImageTools.savePhotoToSDCard(bitmap, "/mnt/sdcard/feishu/", "haha");
                        /**
                         * can not recycle yet
                         * */
                    //    bitmap.recycle();
                    } catch (Exception e) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    break;

                case CHOOSE_PICTURE:
                    try {
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor cursor = managedQuery(data.getData(), proj, null, null, null);
                        int photocolumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        /**
                        * get the file's path
                        * */
                        String filePath = cursor.getString(photocolumn);

                        Bitmap photo = DownLoadBitmap.loadBitmapFromFile(filePath);
                        if (photo != null) {

                            ImageTools.savePhotoToSDCard(photo, "/mnt/sdcard/feishu/", "haha");
                            mImageView.setImageBitmap(photo);
                            /**
                             * can not recycle
                             * */
                    //        photo.recycle();
                        }
                     //   cursor.close();
                    }catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * clss HttpTask : which using AsyncTask to open a new thread to download data in back.
     */
    private class HttpTask extends AsyncTask<String, Integer, JSONObject> {
        private HttpTask() {
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                UploadFile.upLoadFile(GetPictureActivity.this, getResources().getString(R.string.app_url)+"upload.php", "/mnt/sdcard/feishu/haha.png",mPictureName+".png");
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(JSONObject s) {

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }
    }
}
