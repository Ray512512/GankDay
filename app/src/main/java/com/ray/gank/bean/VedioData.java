package com.ray.gank.bean;

import java.util.Date;

/**
 * Created by Ray on 2018/5/24.
 */

public class VedioData {

    /**
     * _id : 5b0626b4421aa97efda86522
     * createdAt : 2018-05-24T10:43:00.322Z
     * desc : 见过弹簧的诞生吗😳
     * publishedAt : 2018-05-24T11:03:54.588Z
     * source : web
     * type : 休息视频
     * url : https://www.iesdouyin.com/share/video/6556013752913759491/?region=CN&mid=6556013796022979342&titleType=title&utm_source=copy_link&utm_campaign=client_share&utm_medium=android&app=aweme&iid=32312367085&timestamp=1527129755
     * used : true
     * who : lijinshanmx
     */

    private String _id;
    public Date createdAt;
    public Date publishedAt;
    private String desc;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
