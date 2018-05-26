package com.ray.gank.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ray on 2018/5/24.
 */

public class Gank {


    /**
     * _id : 5aff4645421aa95f55cab5e7
     * createdAt : 2018-05-15T00:00:00.0Z
     * desc : 2018-05-15
     * publishedAt : 2018-05-15T00:00:00.0Z
     * source : web
     * type : 福利
     * url : http://ww1.sinaimg.cn/large/0065oQSqly1frepqtwifwj30no0ti47n.jpg
     * used : true
     * who : lijinshanmx
     */

    private String _id;
    public Date createdAt;
    private String desc;
    private String vedioStr;
    public Date publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public List<String> getImages() {
        if (images == null) {
            return new ArrayList<>();
        }
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getVedioStr() {
        return vedioStr == null ? "" : vedioStr;
    }

    public void setVedioStr(String vedioStr) {
        this.vedioStr = vedioStr;
    }

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
