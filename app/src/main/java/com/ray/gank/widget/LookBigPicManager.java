package com.ray.gank.widget;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.maning.imagebrowserlibrary.ImageEngine;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.maning.imagebrowserlibrary.model.ImageBrowserConfig;
import com.ray.gank.R;
import com.ray.gank.util.RxMeizhi;
import com.ray.library.utils.AlertDialogUtil;
import com.ray.library.utils.L;
import com.ray.library.utils.T;

import java.io.File;
import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Ray on 2018/5/29.
 */

public class LookBigPicManager {
    private static final String TAG = "LookBigPicManager";
    private ImageBrowserConfig.TransformType transformType = ImageBrowserConfig.TransformType.Transform_ZoomOut;
    private ImageBrowserConfig.IndicatorType indicatorType = ImageBrowserConfig.IndicatorType.Indicator_Number;
    private ImageBrowserConfig.ScreenOrientationType screenOrientationType = ImageBrowserConfig.ScreenOrientationType.Screenorientation_Default;

    private static LookBigPicManager mLookBigPicManager = null ;
    private LookBigPicManager(){}
    public static LookBigPicManager getInstance(){
        if(mLookBigPicManager==null)
         synchronized (LookBigPicManager.class){
              if(mLookBigPicManager==null){
                  mLookBigPicManager=new LookBigPicManager();
              }
         }
         return mLookBigPicManager;
    }

    int testNum=0;
    private MNImageBrowser init(Context context, int position,ViewPager.SimpleOnPageChangeListener onPageChangeListener){
       /* if(testNum>=7)testNum=0;
        transformType=ImageBrowserConfig.TransformType.values()[testNum++];*/
//        L.v(TAG,transformType.name());
        return MNImageBrowser.with(context)
                .setCurrentPosition(position)
                .setImageEngine(new GlideImageEngine())
                .setTransformType(transformType)
                .setIndicatorType(indicatorType)
                .setScreenOrientationType(screenOrientationType)
                //非必须-图片长按监听
                .setOnLongClickListener((activity, imageView, position12, url) -> {
                    //长按监听
                    AlertDialogUtil.AlertDialog(activity, "保存到手机？", "确定","取消", (dialog, which) ->
                            saveImageToGallery(activity,url));
                })
                .setOnPageChangeListener(onPageChangeListener);
    }
    public void lookBigPic(Context context, int position, ArrayList<String> sourceImageList, View view,ViewPager.SimpleOnPageChangeListener listener){
        MNImageBrowser imageBrowser=init(context,position,listener);
        imageBrowser.setImageList(sourceImageList).show(view);
    }

    private void saveImageToGallery(Context context,String mImageUrl) {
        // @formatter:off
        String mImageTitle=mImageUrl.substring(mImageUrl.lastIndexOf("/")+1,mImageUrl.lastIndexOf("."));
        Subscription s = RxMeizhi.saveImageAndGetPathObservable(context, mImageUrl, mImageTitle)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> {
                    File appDir = new File(Environment.getExternalStorageDirectory(), "Meizhi");
                    String msg = String.format(context.getString(R.string.picture_has_save_to),
                            appDir.getAbsolutePath());
                    T.show(msg);
                }, error -> T.show(error.getMessage() + "\n再试试..."));
        // @formatter:on
//        addSubscription(s);
    }

  /*  public void lookBigPic(Context context, int position, String imgUrl, View view){
        MNImageBrowser imageBrowser=init(context,position);
        imageBrowser.setImageUrl(imgUrl).show(view);
    }*/

    //Glide
    public class GlideImageEngine implements ImageEngine {
        @Override
        public void loadImage(Context context, String url, ImageView imageView) {
            Glide.with(context).load(url).into(imageView);
        }
    }

}
