package com.ray.gank.ui.activity;

import android.Manifest;
import android.os.Handler;

import com.ray.gank.MainDrawerActivity;
import com.ray.gank.R;
import com.ray.library.base.ui.CheckPermissionsActivity;
import com.ray.library.utils.AppManager;

public class SplashActivity extends CheckPermissionsActivity {
    protected String[] needPermissions = {
//			Manifest.permission.ACCESS_COARSE_LOCATION,
//			Manifest.permission.ACCESS_FINE_LOCATION,
//			Manifest.permission.READ_PHONE_STATE,
//			Manifest.permission.WRITE_EXTERNAL_STORAGE,
//			Manifest.permission.CAMERA,
    };
    /**
     * 获取需要检测的权限列表，返回null 直接回调startApp()
     * @return
     */
    @Override
    protected String[] getPermissions() {
        return new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    @Override
    protected void startApp() {
        new Handler().postDelayed(() -> {
            AppManager.start(mContext,MainDrawerActivity.class);
            finish();
        }, 0);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.activity_splash;
    }

}
