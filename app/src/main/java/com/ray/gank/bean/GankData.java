package com.ray.gank.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ray on 2018/5/25.
 */

public class GankData {

    public Result results;
    public List<String> category;

    public class Result {
        @SerializedName("Android")
        public List<Gank> androidList;
        @SerializedName("休息视频")
        public List<Gank> vedioList;
        @SerializedName("iOS")
        public List<Gank> iOSList;
        @SerializedName("福利")
        public List<Gank> meiZhiList;
        @SerializedName("拓展资源")
        public List<Gank> expandList;
        @SerializedName("瞎推荐")
        public List<Gank> blideList;
        @SerializedName("App")
        public List<Gank> appList;
    }
}
