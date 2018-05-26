package com.ray.gank.api;


import com.ray.gank.bean.Gank;
import com.ray.gank.bean.GankData;
import com.ray.gank.bean.VedioData;
import com.ray.gank.common.Const;
import com.ray.library.bean.BaseModel;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ray on 18-5-24
 * gank.io api
 */
public interface ApiService {

    @GET("data/福利/" + Const.meizhiSize + "/{page}")
    Observable<BaseModel<ArrayList<Gank>>> getMeizhiData(@Path("page") int page);

    @GET("data/休息视频/" + Const.meizhiSize + "/{page}")
    Observable<BaseModel<ArrayList<VedioData>>> getVedioData(@Path("page") int page);

    @GET("day/{year}/{month}/{day}")
    Observable<BaseModel<GankData>> getGankData(@Path("year") String year, @Path("month") String month, @Path("day") String day);

    @GET("data/{type}/"+ Const.meizhiSize+"/{page}")
    Observable<BaseModel<ArrayList<Gank>>> getGankTypeData(@Path("type") String type, @Path("page") int page);

    @GET("day/history")
    Observable<BaseModel<ArrayList<String>>> getHistoryDay();

}





