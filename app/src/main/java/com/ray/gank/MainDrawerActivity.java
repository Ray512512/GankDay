package com.ray.gank;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.ray.gank.bean.Gank;
import com.ray.gank.bean.GankType;
import com.ray.gank.mvp.presenter.MainPresenter;
import com.ray.gank.mvp.view.MainIView;
import com.ray.gank.ui.activity.MeiZhiActivity;
import com.ray.gank.ui.activity.WebActivity;
import com.ray.gank.ui.adapter.ViewPagerAdapter;
import com.ray.gank.ui.fragment.GankDataFragment;
import com.ray.library.base.ui.BaseActivity;
import com.ray.library.rxbus.Event;
import com.ray.library.utils.AppManager;
import com.ray.library.view.view.banner.BannerLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainDrawerActivity extends BaseActivity<MainPresenter> implements NavigationView.OnNavigationItemSelectedListener,MainIView {
    private static final String TAG = "MainDrawerActivity";
    @BindView(R.id.toolbar_layout_main)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.app_layout_main)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.tab_layout__main)
    TabLayout mTabLayout;
    @BindView(R.id.vp_main)
    ViewPager mViewPager;
    @BindView(R.id.head_banner)
    BannerLayout head_banner;

    private AppBarLayout.OnOffsetChangedListener mOnOffsetChangedListener;
    private ArrayList<Gank> headImage=new ArrayList<>(); //头部轮播数据

    @Override
    protected int inflateContentView() {
        return R.layout.activity_main_drawer;
    }

    @Override
    protected void initPresenter() {
        mPresenter=new MainPresenter(this,this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        setDrawerLayout();
        setViewPager();
        setAppBarLayout();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void setViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(GankDataFragment.newInstance(1), GankType.TYPE_ANDROID);
        adapter.addFragment(GankDataFragment.newInstance(2), GankType.TYPE_IOS);
        adapter.addFragment(GankDataFragment.newInstance(3), GankType.TYPE_WEB);
        adapter.addFragment(GankDataFragment.newInstance(5), GankType.TYPE_EXTRA);
        adapter.addFragment(GankDataFragment.newInstance(6), GankType.TYPE_NOTHINGS);
        adapter.addFragment(GankDataFragment.newInstance(4), GankType.TYPE_APP);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(adapter.getCount());
        final int colors[] = {R.color.colorPrimary, R.color.color_a, R.color.color_b,
                R.color.color_c, R.color.color_d, R.color.color_e};

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(colors[position]));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    protected void initEvents() {
        mPresenter.getData();
    }

    private void setAppBarLayout() {
        mOnOffsetChangedListener = new AppBarLayout.OnOffsetChangedListener() {

            private State state;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (state != State.EXPANDED) {
                        mTabLayout.setBackgroundColor(Color.parseColor("#8F000000"));
                        Log.d(TAG, "EXPANDED: ");
                    }
                    state = State.EXPANDED;
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != State.COLLAPSED) {
                        mTabLayout.setBackgroundColor(Color.parseColor("#00000000"));
                        Log.d(TAG, "COLLAPSED: ");
                    }
                    state = State.COLLAPSED;
                } else {
                    if (state != State.IDLE) {
                        Log.d(TAG, "IDLE: ");

                    }
                    state = State.IDLE;
                }
            }
        };

        mAppBarLayout.addOnOffsetChangedListener(mOnOffsetChangedListener);
        head_banner.setOnBannerItemClickListener(position -> {
            try {
                Intent intent = WebActivity.newIntent(mContext, headImage.get(position).getUrl(), headImage.get(position).getDesc());
                startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    @Override
    public void getLatelyData(ArrayList<Gank> gankData) {
        headImage=gankData;
        head_banner.setViewUrls(getImages(gankData));
    }

    private ArrayList<String > getImages(ArrayList<Gank> gankData){
        ArrayList<String>  r=new ArrayList<>();
        for (Gank g:gankData) {
            r.add(g.getImage());
        }
        return r;
    }

    @Override
    public void getDataError() {

    }


    private enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int navId = item.getItemId();
        if (navId == R.id.nav_mz) {
            AppManager.start(this,MeiZhiActivity.class);
        }/*else if (navId == R.id.nav_local_collect) {
            intent2(CollectActivity.class);
        } else if (navId == R.id.nav_clear) {
            clearLocalCache();
        } else if (navId == R.id.nav_about) {
            intent2(AboutActivity.class);
        }*/
        item.setCheckable(false);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAppBarLayout.addOnOffsetChangedListener(mOnOffsetChangedListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppBarLayout.removeOnOffsetChangedListener(mOnOffsetChangedListener);
    }


    @Subscribe(tags = {@Tag(Event.TAG.SHOW_HEAD)})
    public void showHeadBanner(ArrayList<String> headImage) {
//        if(headImage!=null)
//        head_banner.setViewUrls(headImage);
    }
}
