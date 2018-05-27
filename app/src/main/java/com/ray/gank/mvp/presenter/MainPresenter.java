package com.ray.gank.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.google.gson.Gson;
import com.ray.gank.api.Api;
import com.ray.gank.bean.Gank;
import com.ray.gank.bean.GankData;
import com.ray.gank.bean.GankType;
import com.ray.gank.greendao.GankDaoImp;
import com.ray.gank.mvp.view.MainIView;
import com.ray.gen.GankDao;
import com.ray.library.base.mvp.BasePresenter;
import com.ray.gank.greendao.MyDaoMaster;
import com.ray.library.bean.BaseModel;
import com.ray.library.bean.DemoUser;
import com.ray.library.utils.L;
import com.ray.library.utils.NetUtils;
import com.ray.library.utils.SPUtils;
import com.ray.library.utils.T;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ray on 2018/5/25.
 */

public class MainPresenter extends BasePresenter<MainIView> {
    private static final String TAG = "MainPresenter";
    private GankDao gankDao;
    private ArrayList<Gank> localData;

    public MainPresenter(Activity mContext, MainIView mView) {
        super(mContext, mView);
        gankDao = MyDaoMaster.getInstance(mContext).getDaoSession().getGankDao();
    }

    @SuppressLint("CheckResult")
    public void getData(){
        localData= (ArrayList<Gank>) GankDaoImp.where(GankDao.Properties.LocalType.eq(GankType.LOCAL_HEAD)).list();
        if(localData.size()!=0){
            L.v(TAG,"加载本地banner数据\n"+new Gson().toJson(localData));
            mView.getLatelyData(localData);
        }
        if(!NetUtils.checkNetWork(mContext)){
            mView.getDataError("好像没网了");
            return;
        }
        Api.get().getHistoryDay().flatMap(days -> {
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
        saveDataToDb(gankArrayList);
        return gankArrayList;
    }

    private ArrayList<Gank> getPicData(List<Gank> ganks){
        ArrayList<Gank> result=new ArrayList<>();
        if(ganks==null)return result;
        for (Gank g:ganks) {
            if(g.getImages().size()!=0){
                g.setImage(g.getImages().get(0));
                g.setLocalType(GankType.LOCAL_HEAD);
                result.add(g);
            }
        }
        return result;
    }

    private void saveDataToDb(ArrayList<Gank> Datas){
        GankDaoImp.delete(localData);
        GankDaoImp.insert(Datas);
    }
}
