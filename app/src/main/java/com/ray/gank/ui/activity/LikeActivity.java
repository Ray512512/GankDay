package com.ray.gank.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.ray.gank.R;
import com.ray.gank.ui.fragment.GankDataFragment;
import com.ray.gank.ui.fragment.LikeMeiZhiFragment;
import com.ray.gank.util.Shares;
import com.ray.library.base.ui.BaseActivity;
import com.ray.library.common.adapter.IndicatorAdapter;
import com.ray.library.common.adapter.PagerFragmentAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import magicindicator.MagicIndicator;
import magicindicator.ViewPagerHelper;
import magicindicator.buildins.commonnavigator.CommonNavigator;

public class LikeActivity extends BaseActivity {

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.title)
    TextSwitcher mTextSwitcher;
    protected AppBarLayout mAppBar;
    protected Toolbar mToolbar;

    ArrayList<Fragment> fragments = new ArrayList<>(2);
    private String[] mTitleDataList = new String[]{"干货", "妹纸"};
    private IndicatorAdapter tabAdapter;


    @Override
    protected int inflateContentView() {
        return R.layout.activity_like;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mAppBar = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            mAppBar.setElevation(10.6f);
        }
        mTextSwitcher.setFactory(() -> {
            TextView textView = new TextView(this);
            textView.setTextAppearance(this, R.style.WebTitle);
            textView.setSingleLine(true);
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.postDelayed(() -> textView.setSelected(true), 1738);
            return textView;
        });
        setTitle("本地收藏");

        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setAdjustMode(true);
        tabAdapter = new IndicatorAdapter(mTitleDataList, viewPager);
        commonNavigator.setAdapter(tabAdapter);
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTextSwitcher.setText(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initEvents() {
        fragments.add(GankDataFragment.newInstance(7));
        fragments.add(LikeMeiZhiFragment.newInstance());

        PagerFragmentAdapter adapter = new PagerFragmentAdapter(getSupportFragmentManager(), fragments, mTitleDataList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(mTitleDataList.length);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gank, menu);
        return true;
    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                Shares.share(mContext, R.string.share_text);
                return true;
            case android.R.id.home:
                onBackPressed();
                break;
          /*  case R.id.action_subject:
                openTodaySubject();
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }

}
