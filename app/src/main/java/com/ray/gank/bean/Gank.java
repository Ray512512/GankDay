package com.ray.gank.bean;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Ray on 2018/5/24.
 */
@Entity
public class Gank implements Serializable{


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
    @Id
    private String _id;
    @Property
    public Date createdAt;
    @Property
    private String desc;
    @Property
    private String vedioStr;
    @Property
    public Date publishedAt;
    @Property
    private String source;
    @Property
    private String type;
    @Property
    private String url;
    @Property
    private boolean used;
    @Property
    private String who;
    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> images;
    @Property
    private String image;
    @Property
    private int localType;
    @Property
    private boolean isLike;

    @Generated(hash = 213065472)
    public Gank(String _id, Date createdAt, String desc, String vedioStr,
            Date publishedAt, String source, String type, String url, boolean used,
            String who, List<String> images, String image, int localType,
            boolean isLike) {
        this._id = _id;
        this.createdAt = createdAt;
        this.desc = desc;
        this.vedioStr = vedioStr;
        this.publishedAt = publishedAt;
        this.source = source;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
        this.images = images;
        this.image = image;
        this.localType = localType;
        this.isLike = isLike;
    }

    @Generated(hash = 116302247)
    public Gank() {
    }

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

    public ArrayList<String> getImages() {
        if (images == null) {
            return new ArrayList<>();
        }else
        return new ArrayList<>(images);
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

    public boolean getUsed() {
        return this.used;
    }

    public int getLocalType() {
        return this.localType;
    }

    public void setLocalType(int localType) {
        this.localType = localType;
    }

    public boolean getIsLike() {
        return this.isLike;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }
}
