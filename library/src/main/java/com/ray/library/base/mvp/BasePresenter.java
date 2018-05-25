package com.ray.library.base.mvp;

import android.app.Activity;
import android.widget.Toast;

import com.ray.library.retrofit.BaseApiManager;


/**
 * Created by ray on 17/5/11.
 */
public abstract class BasePresenter<I extends BaseIView> {
    private static final String TAG = "BasePresenter";
    protected I mView;//view接口
    protected Activity mContext;

    public BasePresenter(Activity mContext, I mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    public void onDetachView() { //销毁的时候
        if (mView != null) {
            mView = null;
        }
    }
}
