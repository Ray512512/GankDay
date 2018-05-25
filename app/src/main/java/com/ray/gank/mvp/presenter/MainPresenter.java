package com.ray.gank.mvp.presenter;

import android.app.Activity;

import com.ray.gank.api.Api;
import com.ray.gank.bean.Gank;
import com.ray.gank.mvp.view.GankIView;
import com.ray.gank.mvp.view.MainIView;
import com.ray.library.base.mvp.BasePresenter;
import com.ray.library.rxjava.RxHelper;
import com.ray.library.rxjava.RxSubscribe;

import java.util.ArrayList;

/**
 * Created by Ray on 2018/5/25.
 */

public class MainPresenter extends BasePresenter<MainIView> {

    public MainPresenter(Activity mContext, MainIView mView) {
        super(mContext, mView);
    }

    public void getData(String  type,int page){

    }
}
