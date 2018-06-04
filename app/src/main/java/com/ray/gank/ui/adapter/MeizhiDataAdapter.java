package com.ray.gank.ui.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.ray.gank.R;
import com.ray.gank.bean.Gank;
import com.ray.gank.util.StringStyles;
import com.ray.gank.view.RatioImageView;
import com.ray.gank.widget.LookBigPicManager;
import com.ray.library.utils.GlideUtils;
import com.ray.library.utils.ImageUtil;
import com.ray.library.utils.TimeFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeizhiDataAdapter extends RecyclerArrayAdapter<Gank> {

    private static final String TAG = MeizhiDataAdapter.class.getSimpleName();

    public MeizhiDataAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new GankDataAdapterHolder(parent);
    }

    public static class GankDataAdapterHolder extends BaseViewHolder<Gank> {
        @BindView(R.id.meizhi)
        RatioImageView meizhiView;
        @BindView(R.id.title) TextView titleView;
        @BindView(R.id.meizhi_card)
        LinearLayout meizhi_card;

        public GankDataAdapterHolder(ViewGroup parent) {
            super(parent, R.layout.fragment_item_meizhi);
            ButterKnife.bind(this, itemView);
            meizhiView.setOriginalSize(50, 50);
          /*  meizhiView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });*/
        }

        @Override
        public void setData(final Gank meizhi) {
            int limit = 48;
            String content=meizhi.getDesc()+" "+meizhi.getVedioStr();
            String text = content.length() > limit ? content.substring(0, limit) +
                    "..." : content;
            titleView.setText(text);
            Glide.with(getContext())
                    .load(meizhi.getUrl())
                    .centerCrop()
                    .into(meizhiView)
                    .getSize((width, height) -> {
                        if (!meizhi_card.isShown()) {
                            meizhi_card.setVisibility(View.VISIBLE);
                        }
                    });
        }
    }
}