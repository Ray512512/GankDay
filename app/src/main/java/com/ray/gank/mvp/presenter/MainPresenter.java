package com.ray.gank.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.google.gson.Gson;
import com.ray.gank.api.Api;
import com.ray.gank.bean.Gank;
import com.ray.gank.bean.GankData;
import com.ray.gank.mvp.view.GankIView;
import com.ray.gank.mvp.view.MainIView;
import com.ray.library.base.mvp.BasePresenter;
import com.ray.library.bean.BaseModel;
import com.ray.library.bean.DemoUser;
import com.ray.library.rxjava.RxHelper;
import com.ray.library.rxjava.RxSubscribe;
import com.ray.library.utils.L;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import rx.functions.Action1;

/**
 * Created by Ray on 2018/5/25.
 */

public class MainPresenter extends BasePresenter<MainIView> {
    private static final String TAG = "MainPresenter";
    public MainPresenter(Activity mContext, MainIView mView) {
        super(mContext, mView);
    }

    @SuppressLint("CheckResult")
    public void getData(){
        Api.get().getHistoryDay().flatMap((Function<BaseModel<ArrayList<String>>, ObservableSource<BaseModel<GankData>>>) days -> {
            String date[] = new String[3];
            if (days != null && days.results.size() != 0) {
                date = days.results.get(0).split("-");
                L.v(TAG, "最近有货日期：" + date[0] + date[1] + date[2]);
            }
            return Api.get().getGankData(date[0], date[1], date[2]);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gankDataBaseModel -> mView.getLatelyData(getHeadData(gankDataBaseModel.results)));
    }

    /**
     * 获取有图片的数据且只去图片第一张
     * @return
     */
    private ArrayList<Gank>  getHeadData(GankData gankData){
        ArrayList<Gank> gankArrayList=new ArrayList<>();
       if(gankData==null)return gankArrayList;
        gankArrayList.addAll(getPicData(gankData.androidList));
        gankArrayList.addAll(getPicData(gankData.iOSList));
        gankArrayList.addAll(getPicData(gankData.h5List));
        gankArrayList.addAll(getPicData(gankData.expandList));
        gankArrayList.addAll(getPicData(gankData.blideList));
        gankArrayList.addAll(getPicData(gankData.appList));
        L.v(TAG,new Gson().toJson(gankArrayList));

        return gankArrayList;
    }

    private ArrayList<Gank> getPicData(List<Gank> ganks){
        ArrayList<Gank> result=new ArrayList<>();
        if(ganks==null)return result;
        for (Gank g:ganks) {
            if(g.getImages().size()!=0){
                g.setImage(g.getImages().get(0));
                result.add(g);
            }
        }
        return result;
    }

}
