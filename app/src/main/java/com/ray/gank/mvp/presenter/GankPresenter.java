package com.ray.gank.mvp.presenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.ray.gank.api.Api;
import com.ray.gank.bean.Gank;
import com.ray.gank.bean.GankType;
import com.ray.gank.greendao.GankDaoImp;
import com.ray.gank.mvp.view.GankIView;
import com.ray.gen.GankDao;
import com.ray.library.base.mvp.BasePresenter;
import com.ray.library.rxjava.RxHelper;
import com.ray.library.rxjava.RxSubscribe;
import com.ray.library.utils.L;

import java.util.ArrayList;

/**
 * Created by Ray on 2018/5/25.
 */

public class GankPresenter extends BasePresenter<GankIView> {
    private static final String TAG = "GankPresenter";
    private ArrayList<Gank> localData;

    public GankPresenter(Activity mContext, GankIView mView) {
        super(mContext, mView);
    }

    public void getData(String  type,int page){
        if(page==1) {
            localData = (ArrayList<Gank>) GankDaoImp.where(GankDao.Properties.LocalType.eq(GankType.LOCAL_NEWEST), GankDao.Properties.Type.eq(type)).list();
            if (localData != null && localData.size() != 0) {
                L.v(TAG, type + "加载本地最新数据\n" + new Gson().toJson(localData));
                mView.getGankDataList(localData);
            }
        }
        Api.get().getGankTypeData(type,page).compose(RxHelper.handleResult()).subscribe(new RxSubscribe<ArrayList<Gank>>(mContext,mView) {
            @Override
            public void _onNext(ArrayList<Gank> result) {
                 mView.getGankDataList(result);
                 if(page==1)
                saveDataToDb(result);
            }

            @Override
            public void _onError(String message) {
                mView.showErrorView();
            }
        });
    }

    private void saveDataToDb(ArrayList<Gank> Datas){
        GankDaoImp.delete(localData);
        for (Gank g : Datas) {
            g.setLocalType(GankType.LOCAL_NEWEST);
        }
        GankDaoImp.insert(Datas);
    }
}
