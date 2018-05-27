package com.ray.gank.ui.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.ray.gank.R;
import com.ray.gank.bean.Gank;
import com.ray.gank.util.StringStyles;
import com.ray.library.utils.DateUtils;
import com.ray.library.utils.ImageUtil;
import com.ray.library.utils.TimeFormat;

import java.text.DateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GankDataAdapter extends RecyclerArrayAdapter<Gank> {

    private static final String TAG = GankDataAdapter.class.getSimpleName();

    public GankDataAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new GankDataAdapterHolder(parent);
    }

    public static class GankDataAdapterHolder extends BaseViewHolder<Gank> {

        @BindView(R.id.tv_gank_desc)
        TextView mDescTv;
        @BindView(R.id.tv_gank_info)
        TextView mInfoTv;
        @BindView(R.id.iv_gank_image)
        ImageView mImageView;

        public GankDataAdapterHolder(ViewGroup parent) {
            super(parent, R.layout.item_rv_gank_data);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(final Gank itemData) {
            mDescTv.setText(itemData.getDesc());

            String who = TextUtils.isEmpty(itemData.getWho()) ? "via. 某大神" : ("via. " + itemData.getWho());
            String date = TimeFormat.getDateTime2(itemData.getPublishedAt().getTime());

            SpannableStringBuilder builder = new SpannableStringBuilder(who)
                    .append("  ")
                    .append(StringStyles.format(getContext(), date, R.style.TextDate));

            CharSequence charSequence = builder.subSequence(0, builder.length());
            mInfoTv.setText(charSequence);

            if (itemData.getImages() != null && itemData.getImages().size() > 0) {
                mImageView.setVisibility(View.VISIBLE);
                if (itemData.getImages().size() == 1) {
                    mImageView.setImageResource(R.drawable.ic_image3);
                } else {
                    mImageView.setImageResource(R.drawable.ic_image4);
                }
                mImageView.setOnClickListener(view -> ImageUtil.lookBigPic(getContext(),itemData.getImages(), 0));
            } else {
                mImageView.setVisibility(View.GONE);
            }
        }
    }
}