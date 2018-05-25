package com.ray.gank.mvp.presenter;

import android.app.Activity;

import com.ray.gank.api.Api;
import com.ray.gank.bean.Gank;
import com.ray.gank.bean.VedioData;
import com.ray.gank.mvp.view.MeiZhiIView;
import com.ray.gank.util.DataUtils;
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

    public MeiZhiPresenter(Activity mContext, MeiZhiIView mView) {
        super(mContext, mView);
    }


    public void getMeizhiAndVedioData(int page){
        mLastVideoIndex = 0;
        Observable.zip(Api.get().getMeizhiData(page), Api.get().getVedioData(page),
                (meizhiData, vedioData) -> createMeizhiDataWithVedioDesc(meizhiData.results, vedioData.results)).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(arrayListBaseModel -> mView.getMeiZhiDataList(arrayListBaseModel), throwable -> L.v(TAG,throwable.toString()));
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
}


