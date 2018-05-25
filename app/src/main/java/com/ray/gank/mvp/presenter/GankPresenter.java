package com.ray.gank.mvp.presenter;

import android.app.Activity;

import com.ray.gank.api.Api;
import com.ray.gank.bean.Gank;
import com.ray.gank.bean.GankData;
import com.ray.gank.mvp.view.GankIView;
import com.ray.library.base.mvp.BasePresenter;
import com.ray.library.bean.DemoUser;
import com.ray.library.rxjava.RxHelper;
import com.ray.library.rxjava.RxSubscribe;

import java.util.ArrayList;

/**
 * Created by Ray on 2018/5/25.
 */

public class GankPresenter extends BasePresenter<GankIView> {

    public GankPresenter(Activity mContext, GankIView mView) {
        super(mContext, mView);
    }

    public void getData(String  type,int page){
        Api.get().getGankTypeData(type,page).compose(RxHelper.handleResult()).subscribe(new RxSubscribe<ArrayList<Gank>>(mContext,mView) {
            @Override
            public void _onNext(ArrayList<Gank> result) {
                 mView.getGankDataList(result);
            }

            @Override
            public void _onError(String message) {
                mView.showErrorView();
            }
        });
    }
}
