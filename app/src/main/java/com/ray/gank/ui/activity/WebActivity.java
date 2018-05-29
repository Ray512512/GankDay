/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ray.gank.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.ray.gank.R;
import com.ray.gank.bean.Gank;
import com.ray.gank.bean.GankType;
import com.ray.gank.greendao.GankDaoImp;
import com.ray.gank.util.Androids;
import com.ray.gen.GankDao;
import com.ray.library.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WebActivity extends ToolbarActivity {

    private static final String EXTRA_DATA = "extra_data";
    private static final String EXTRA_URL = "extra_url";
    private static final String EXTRA_TITLE = "extra_title";

    @BindView(R.id.progressbar)
    NumberProgressBar mProgressbar;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.title)
    TextSwitcher mTextSwitcher;
    @BindView(R.id.fab_like)
    FloatingActionButton fab_like;

    private String mUrl, mTitle;

    private Gank gank ;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web;
    }


    @Override
    public boolean canBack() {
        return true;
    }


    /**
     * Using newIntent trick, return WebActivity Intent, to avoid `public static`
     * constant
     * variable everywhere
     *
     * @return Intent to start WebActivity
     */
    public static Intent newIntent(Context context, String extraURL, String extraTitle) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(EXTRA_URL, extraURL);
        intent.putExtra(EXTRA_TITLE, extraTitle);
        return intent;
    }

    public static Intent newIntent(Context context, Gank gank) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(EXTRA_DATA, gank);
        intent.putExtra(EXTRA_URL, gank.getUrl());
        intent.putExtra(EXTRA_TITLE, gank.getDesc());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mUrl = getIntent().getStringExtra(EXTRA_URL);
        mTitle = getIntent().getStringExtra(EXTRA_TITLE);
        gank = (Gank) getIntent().getSerializableExtra(EXTRA_DATA);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        mWebView.setWebChromeClient(new ChromeClient());
        mWebView.setWebViewClient(new LoveClient());

        mWebView.loadUrl(mUrl);

        mTextSwitcher.setFactory(() -> {
            TextView textView = new TextView(this);
            textView.setTextAppearance(this, R.style.WebTitle);
            textView.setSingleLine(true);
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.postDelayed(() -> textView.setSelected(true), 1738);
            return textView;
        });
        mTextSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mTextSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        if (mTitle != null) setTitle(mTitle);

    }


    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTextSwitcher.setText(title);
    }


    private void refresh() {
        mWebView.reload();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                refresh();
                return true;
            case R.id.action_copy_url:
                String copyDone = getString(R.string.tip_copy_done);
                Androids.copyToClipBoard(this, mWebView.getUrl(), copyDone);
                return true;
            case R.id.action_open_url:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(mUrl);
                intent.setData(uri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    T.show(R.string.tip_open_fail);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) mWebView.destroy();
    }


    @Override
    protected void onPause() {
        if (mWebView != null) mWebView.onPause();
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) mWebView.onResume();
    }

    @OnClick(R.id.fab_like)
    public void onViewClicked() {
        if(gank!=null){
            Gank g=GankDaoImp.where(GankDao.Properties.LocalType.eq(GankType.LOCAL_LIKE),GankDao.Properties._id.eq(gank.get_id())).unique();
            if(g!=null) {
                Snackbar.make(mWebView, "已经收藏过了"+"(〃'▽'〃)", Snackbar.LENGTH_SHORT).show();
                return;
            }
            gank.setLocalType(GankType.LOCAL_LIKE);
            GankDaoImp.insert(gank);
            Snackbar.make(mWebView, "收藏成功"+"(〃'▽'〃)", Snackbar.LENGTH_SHORT).show();
        }
    }


    private class ChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mProgressbar.setProgress(newProgress);
            if (newProgress == 100) {
                mProgressbar.setVisibility(View.GONE);
            } else {
                mProgressbar.setVisibility(View.VISIBLE);
            }
        }


        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    }

    private boolean isLoadUrl = false;

    private class LoveClient extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.v("LoveClient1", url);
            if (url.startsWith("http:") || url.startsWith("https:")) {
                view.loadUrl(url);
                return false;
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        }

    }
}
