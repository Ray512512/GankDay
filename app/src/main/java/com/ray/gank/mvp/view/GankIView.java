package com.ray.gank.mvp.view;


import com.ray.gank.bean.Gank;
import com.ray.library.base.mvp.BaseIView;

import java.util.ArrayList;

/**
 * Created by xy on 16/5/16.
 */
public interface GankIView extends BaseIView {
    void getGankDataList(ArrayList<Gank> meiZhis);
    void showErrorView();
}
