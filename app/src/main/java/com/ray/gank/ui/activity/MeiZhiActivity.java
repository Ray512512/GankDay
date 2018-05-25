package com.ray.gank.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.dingmouren.layoutmanagergroup.echelon.EchelonLayoutManager;
import com.ray.gank.R;
import com.ray.gank.bean.Gank;
import com.ray.gank.mvp.presenter.MeiZhiPresenter;
import com.ray.gank.mvp.view.MeiZhiIView;
import com.ray.library.base.ui.BaseActivity;
import com.ray.library.utils.GlideUtils;
import com.ray.library.view.view.swipefreshReccycleview.MySwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import multiitem.adapter.BaseItemAdapter;
import multiitem.adapter.holder.BaseViewHolder;
import multiitem.adapter.holder.BaseViewHolderManager;

public class MeiZhiActivity extends BaseActivity<MeiZhiPresenter> implements MeiZhiIView {
    private static final String TAG = "MeiZhiActivity";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.meizhi_swiperefresh)
    MySwipeRefreshLayout meizhi_swiperefresh;

    private EchelonLayoutManager mLayoutManager;
    private BaseItemAdapter mAdapter;

    private int page=1;
    @Override
    protected int inflateContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MeiZhiPresenter(this, this);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mLayoutManager = new EchelonLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new BaseItemAdapter();
        mAdapter.register(Gank.class, new BaseViewHolderManager<Gank>() {
            @Override
            public void onBindViewHolder(BaseViewHolder baseViewHolder, Gank item) {
                GlideUtils.load(mContext,item.getUrl(),getView(baseViewHolder,R.id.img_meizhi));
                GlideUtils.load(mContext,item.getUrl(),getView(baseViewHolder,R.id.img_avatar));
                TextView tv_name = getView(baseViewHolder, R.id.tv_nickname);
                TextView tv_content = getView(baseViewHolder, R.id.tv_desc);
                tv_name.setText(item.getDesc());
                tv_content.setText(item.getVedioStr());
               /* HeartLayout mHeartLayout = getView(baseViewHolder, R.id.layout);
                mHeartLayout.setOnTouchListener((v, event) -> {
                    mHeartLayout.post(() -> mHeartLayout.addHeart(randomColor()));
                    return false;
                });*/
            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.item_meizhi;
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        meizhi_swiperefresh.setOnRefreshListener(() -> mPresenter.getMeizhiAndVedioData(page++));

    }

    @Override
    protected void initEvents() {
        meizhi_swiperefresh.autoRefresh();
    }

    @Override
    public void getMeiZhiDataList(ArrayList<Gank> meiZhis) {
        meizhi_swiperefresh.setRefreshing(false);
        if(mAdapter.getDataList().size()!=0){
            mAdapter.addDataItems(0,meiZhis);
        }else {
            mAdapter.addDataItems(meiZhis);
        }
        mRecyclerView.scrollBy(0,mLayoutManager.getVerticalSpace()*8);
    }

    private int randomColor() {
        return Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
    }
}
