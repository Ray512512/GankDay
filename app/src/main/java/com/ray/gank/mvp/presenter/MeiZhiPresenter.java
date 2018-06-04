package com.ray.gank.mvp.presenter;

import android.app.Activity;

import com.google.gson.Gson;
import com.ray.gank.api.Api;
import com.ray.gank.bean.Gank;
import com.ray.gank.bean.GankType;
import com.ray.gank.bean.VedioData;
import com.ray.gank.common.Const;
import com.ray.gank.greendao.GankDaoImp;
import com.ray.gank.mvp.view.MeiZhiIView;
import com.ray.gank.util.DataUtils;
import com.ray.gen.GankDao;
import com.ray.library.base.mvp.BasePresenter;
import com.ray.library.utils.L;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ray on 2018/5/24.
 */

public class MeiZhiPresenter extends BasePresenter<MeiZhiIView>{
    private static final String TAG = "MeiZhiPresenter";
    private ArrayList<Gank> localData;

    public MeiZhiPresenter(Activity mContext, MeiZhiIView mView) {
        super(mContext, mView);
    }


    public void getLocalMeiZhi(int page){
        localData = (ArrayList<Gank>) GankDaoImp.where(GankDao.Properties.LocalType.eq(GankType.LOCAL_LIKE),
                GankDao.Properties.Type.eq(GankType.TYPE_MEIZHI),GankDao.Properties.IsLike.eq(true))./*offset((page-1)* Const.meizhiSize).limit(Const.meizhiSize).*/list();
        if (localData != null ) {
            L.v(TAG, "加载本地妹纸\n" + new Gson().toJson(localData));
            mView.getMeiZhiDataList(localData);
        }else {
            mView.showErrorView();
        }
    }

    public void getMeizhiAndVedioData(int page){
        if(page==1) {
            localData = (ArrayList<Gank>) GankDaoImp.where(GankDao.Properties.LocalType.eq(GankType.LOCAL_MEIZHI)).list();
            if (localData != null && localData.size() != 0) {
                L.v(TAG, "加载本地妹纸数据\n" + new Gson().toJson(localData));
                mView.getMeiZhiDataList(localData);
            }
        }
        mLastVideoIndex = 0;
        Observable.zip(Api.get().getMeizhiData(page), Api.get().getVedioData(page),
                (meizhiData, vedioData) -> createMeizhiDataWithVedioDesc(meizhiData.results, vedioData.results)).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(arrayListBaseModel -> {
                    mView.getMeiZhiDataList(arrayListBaseModel);
                    if(page==1)
                    saveDataToDb(arrayListBaseModel);
                }, throwable -> {
                    mView.showErrorView();
                    L.v(TAG, throwable.toString());
                });
    }

    /**
     * 组合数据
     * @param meizhis
     * @param vedios
     * @return
     */
    private ArrayList<Gank> createMeizhiDataWithVedioDesc(ArrayList<Gank> meizhis, ArrayList<VedioData> vedios) {
        for (Gank meizhi : meizhis) {
            meizhi.setVedioStr(getFirstVideoDesc(meizhi.publishedAt, vedios));
        }
        Collections.reverse(meizhis);
        return meizhis;
    }

    private int mLastVideoIndex = 0;
    private String getFirstVideoDesc(Date publishedAt, List<VedioData> results) {
        String videoDesc = "";
        for (int i = mLastVideoIndex; i < results.size(); i++) {
            VedioData video = results.get(i);
            if (video.publishedAt == null) video.publishedAt = video.createdAt;
            if (DataUtils.isTheSameDay(publishedAt, video.publishedAt)) {
                videoDesc = video.getDesc();
                mLastVideoIndex = i;
                break;
            }
        }
        return videoDesc;
    }

    private void saveDataToDb(ArrayList<Gank> Datas){
        GankDaoImp.delete(localData);
        for (Gank g : Datas) {
            g.setLocalType(GankType.LOCAL_MEIZHI);
        }
        GankDaoImp.insert(Datas);
    }
}


