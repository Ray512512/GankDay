package com.ray.gank.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.ray.gank.R;
import com.ray.gank.bean.Gank;
import com.ray.gank.bean.GankType;
import com.ray.gank.mvp.presenter.GankPresenter;
import com.ray.gank.mvp.view.GankIView;
import com.ray.gank.ui.activity.WebActivity;
import com.ray.gank.ui.adapter.GankDataAdapter;
import com.ray.library.base.ui.BaseFragment;
import com.ray.library.common.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ray on 2018/5/25.
 */

public class GankDataFragment extends BaseFragment<GankPresenter> implements GankIView {
    private static final String TAG = "GankDataFragment";
    private static final String INDEX = "args_index";

    @BindView(R.id.easy_rv_gank)
    EasyRecyclerView mEasyRecyclerView;

    private int index;
    private int page=1;
    private List<Gank> mBeanList=new ArrayList<>();
    private GankDataAdapter mGankDataAdapter;

    public static GankDataFragment newInstance(int index) {
        Bundle args = new Bundle();
        GankDataFragment fragment = new GankDataFragment();
        args.putInt(INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void getGankDataList(ArrayList<Gank> meiZhis) {
        mEasyRecyclerView.setRefreshing(false);
        if(page==1){
                mBeanList.clear();
                mGankDataAdapter.clear();

                mBeanList.addAll(meiZhis);
                mGankDataAdapter.addAll(mBeanList);
            }else {
                mBeanList.addAll(meiZhis);
                mGankDataAdapter.addAll(meiZhis);
            }
            if(mBeanList.size()==0)showErrorView();
    }

    @Override
    public void showErrorView() {
        mEasyRecyclerView.setRefreshing(false);
        Snackbar.make(mEasyRecyclerView, page==1?"本地还没有干货哦"+"ヾ(￣▽￣)":"已加载本地所有干货"+"(*╹▽╹*)", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected int inflateContentView() {
        return R.layout.fragment_gank_data;
    }

    @Override
    protected void initView(View v) {
        ButterKnife.bind(this,v);
        index = getArguments().getInt(INDEX);
        mEasyRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mEasyRecyclerView.setItemAnimator(new DefaultItemAnimator());

        int colors[] = {R.color.colorPrimary, R.color.md_lime_500, R.color.md_red_500, R.color.md_green_600};
        mEasyRecyclerView.setRefreshingColorResources(colors);

        mEasyRecyclerView.setRefreshListener(() -> {
            page=1;
            mPresenter.getData(GankType.index2Type.get(index),page);
        });
        setAdapter();
    }

    @Override
    protected void initPresenter() {
       mPresenter=new GankPresenter(mActivity,this);
    }

    @Override
    protected void initEvents() {
        mPresenter.getData(GankType.index2Type.get(index),page);
    }

    private void setAdapter() {
        mGankDataAdapter = new GankDataAdapter(mActivity);

        mEasyRecyclerView.setAdapter(mGankDataAdapter);
        mGankDataAdapter.setOnItemClickListener(position -> {
            Gank dataBean = mBeanList.get(position);
            Intent intent = WebActivity.newIntent(getContext(), dataBean);
            startActivity(intent);
        });

        if(index==7)return;
            mGankDataAdapter.setMore(R.layout.layout_load_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                mPresenter.getData(GankType.index2Type.get(index),++page);
            }

            @Override
            public void onMoreClick() {
                mPresenter.getData(GankType.index2Type.get(index),++page);
            }
        });

        mGankDataAdapter.setNoMore(R.layout.layout_no_more);
    }
}
