package com.ray.gank.bean;

import java.util.HashMap;

public class GankType {

    public static final HashMap<Integer, String> index2Type = new HashMap<>();
    public static final HashMap<Integer, Integer> localType = new HashMap<>();

    public static final String TYPE_ALL = "all";
    public static final String TYPE_ANDROID = "Android";
    public static final String TYPE_IOS = "iOS";
    public static final String TYPE_WEB = "前端";
    public static final String TYPE_APP = "App";
    public static final String TYPE_EXTRA = "拓展资源";
    public static final String TYPE_NOTHINGS = "瞎推荐";

    public static final String TYPE_FULI = "福利";
    public static final String TYPE_VIDEO = "休息视频";

    public static final int LOCAL_HEAD=1;//头部banner数据
    public static final int LOCAL_NEWEST=2;//最新数据
    public static final int LOCAL_MEIZHI=3;//妹纸数据

    static {
        index2Type.put(1, GankType.TYPE_ANDROID);
        index2Type.put(2, GankType.TYPE_IOS);
        index2Type.put(3, GankType.TYPE_WEB);
        index2Type.put(4, GankType.TYPE_APP);
        index2Type.put(5, GankType.TYPE_EXTRA);
        index2Type.put(6, GankType.TYPE_NOTHINGS);

    }

    public static final String GANK_PIC_400 = "?imageView2/0/w/400/format/jpg";
    public static final String GANK_PIC_1000 = "?imageView2/0/w/1280/format/jpg";
}
