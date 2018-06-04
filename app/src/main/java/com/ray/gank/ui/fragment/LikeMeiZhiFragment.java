package com.ray.gank.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.ray.gank.R;
import com.ray.gank.bean.Gank;
import com.ray.gank.bean.GankType;
import com.ray.gank.mvp.presenter.MeiZhiPresenter;
import com.ray.gank.mvp.view.MeiZhiIView;
import com.ray.gank.ui.activity.WebActivity;
import com.ray.gank.ui.adapter.MeizhiDataAdapter;
import com.ray.gank.widget.LookBigPicManager;
import com.ray.library.base.ui.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ray on 2018/5/30.
 */

public class LikeMeiZhiFragment extends BaseFragment<MeiZhiPresenter> implements MeiZhiIView {

    @BindView(R.id.easy_rv_meizhi)
    EasyRecyclerView mEasyRecyclerView;
    @BindView(R.id.main_fab)
    FloatingActionButton main_fab;

    private MeizhiDataAdapter meizhiDataAdapter;
    private List<Gank> mBeanList=new ArrayList<>();
    private int page=1;

    public static LikeMeiZhiFragment newInstance() {
        LikeMeiZhiFragment fragment = new LikeMeiZhiFragment();
        return fragment;
    }

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_like_meizhi;
    }

    @Override
    protected void initView(View v) {
        ButterKnife.bind(this,v);
        setupRecyclerView();
    }

    @Override
    protected void initPresenter() {
        mPresenter=new MeiZhiPresenter(mActivity,this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getLocalMeiZhi(page);
    }

    @Override
    protected void initEvents() {
    }

    private void setupRecyclerView() {
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mEasyRecyclerView.setLayoutManager(layoutManager);
        meizhiDataAdapter = new MeizhiDataAdapter(mContext);
        mEasyRecyclerView.setAdapter(meizhiDataAdapter);

        meizhiDataAdapter.setOnItemClickListener((int position) -> LookBigPicManager.getInstance().lookBigPic(mContext,position, (ArrayList<Gank>) mBeanList,mEasyRecyclerView.getRecyclerView().getChildAt(position)
                ,new ViewPager.SimpleOnPageChangeListener(){
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        mEasyRecyclerView.getRecyclerView().smoothScrollToPosition(position);
                    }
                }));
        mEasyRecyclerView.setRefreshListener(() -> {
            page=1;
            mPresenter.getLocalMeiZhi(page);
        });
       /* meizhiDataAdapter.setMore(R.layout.layout_load_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                mPresenter.getLocalMeiZhi(++page);
            }

            @Override
            public void onMoreClick() {
                mPresenter.getLocalMeiZhi(++page);
            }
        });

        meizhiDataAdapter.setNoMore(R.layout.layout_no_more);*/

        main_fab.setVisibility(View.GONE);
        main_fab.setOnClickListener(v -> mEasyRecyclerView.getRecyclerView().smoothScrollToPosition(0));
    }

    @Override
    public void getMeiZhiDataList(ArrayList<Gank> meiZhis) {
        mEasyRecyclerView.setRefreshing(false);
        if(page==1){
            mBeanList.clear();
            meizhiDataAdapter.clear();
        }
        mBeanList.addAll(meiZhis);
        meizhiDataAdapter.addAll(mBeanList);
        main_fab.setVisibility(mBeanList.size()>5?View.VISIBLE:View.GONE);
        if(mBeanList.size()==0)showErrorView();
    }

    @Override
    public void showErrorView() {
        mEasyRecyclerView.setRefreshing(false);
        Snackbar.make(mEasyRecyclerView, page==1?"本地还没有妹纸哦"+"ヾ(￣▽￣)":"已加载本地所有妹纸"+"(*╹▽╹*)", Snackbar.LENGTH_SHORT).show();
    }
}
