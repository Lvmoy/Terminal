package com.example.administrator.terminal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import utils.DensityUtils;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

public class OpenningActivity extends Activity{
    private static final String TAG = "openActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_openning_load);

        ImageView imageView = (ImageView) findViewById(R.id.iv_loading);

        WindowManager windowManager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int windowHeight = metrics.heightPixels;
        int windowWidth = (int)DensityUtils.px2dp(this, metrics.widthPixels);
        Log.d(TAG, "windowHeight :" + windowHeight);
        Log.d(TAG, "windowWidth: " + windowWidth);

        //图片原始大小
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        int height = options.outHeight;
        int width = options.outWidth;

        int heightSample = height < windowHeight ? 1 : height/windowHeight;
        int widthSample = width < windowWidth ? 1 : width/windowWidth;
        int sampleSize = (heightSample >= widthSample ? widthSample : heightSample);

        Log.d(TAG, "height :" + height);
        Log.d(TAG, "width: " + width);

        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_loading_background, options);
        if(sampleSize == 1){
            Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, windowWidth, windowHeight, true);
            imageView.setImageBitmap(newBitmap);

        }else {
            imageView.setImageBitmap(bitmap);

        }
        bitmap = null;
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return false;
            }
        }).sendEmptyMessageDelayed(0,1500);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
