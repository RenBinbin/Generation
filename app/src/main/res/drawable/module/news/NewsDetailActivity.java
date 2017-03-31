package com.weiruanit.lifepro.module.news;

import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.weiruanit.lifepro.R;
import com.weiruanit.lifepro.base.BaseBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends BaseBackActivity {

    @BindView(R.id.news_webview)
    WebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }


    @Override
    public void initData() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        mWebView.loadUrl(url);
    }

    @Override
    protected void getData() {

    }

}
