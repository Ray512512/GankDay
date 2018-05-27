package com.ray.gank.greendao;

import com.ray.gank.GankApplication;
import com.ray.gank.bean.Gank;
import com.ray.gen.GankDao;
import com.ray.library.utils.L;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;

/**
 * Created by Ray on 2018/5/27.
 */

public class GankDaoImp{
    private static final String TAG = "GankDaoImp";
    private static GankDao gankDao;
    static {
        gankDao=MyDaoMaster.getInstance(GankApplication.getInstance()).getDaoSession().getGankDao();
    }

    public static QueryBuilder<Gank> where(WhereCondition cond, WhereCondition... condMore){
        return gankDao.queryBuilder().where(cond,condMore);
    }



    public static void delete(ArrayList<Gank> Datas){
        if(Datas==null||Datas.size()==0)return;
        for (Gank g : Datas) {
            gankDao.delete(g);
        }
    }

    public static void insert(ArrayList<Gank> Datas){
        for (Gank s : Datas) {
          long id = gankDao.insertOrReplace(s);
            L.v(TAG,"插入数据："+id);
        }
    }
}
