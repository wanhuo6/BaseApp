package com.ahuo.myapp2.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.ahuo.myapp2.R;
import com.ahuo.myapp2.base.BaseActivity;
import com.ahuo.tool.util.MyLog;
import com.ahuo.tool.util.ToastUtil;

import butterknife.BindView;

/**
 * Created on 2017-6-16
 *
 * @author LiuHuiJie
 */
public class MyWebViewActivity extends BaseActivity {
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private WebView mWebView;
    private WebSettings mSettings;
    private String mUrl = null;

    private final static String INTENT_URL = "intent_url";

    public static void startActivity(Activity activity, String url) {

        Intent intent = new Intent(activity, MyWebViewActivity.class);
        intent.putExtra(INTENT_URL, url);
        activity.startActivity(intent);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_web;
    }


    @Override
    public void initData() {
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mSwipeRefresh.addView(mWebView);
        mSettings = mWebView.getSettings();
        mWebView.setWebViewClient(new MyWebViewClient());
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.loadUrl(mWebView.getUrl());
            }
        });
        //设置进度条
        mWebView.setWebChromeClient(new WebChromeClient() {
                                       @Override
                                       public void onProgressChanged(WebView view, int newProgress) {
                                           if (newProgress == 100) {
                                               //隐藏进度条
                                               mSwipeRefresh.setRefreshing(false);
                                           } else {
                                               if (!mSwipeRefresh.isRefreshing())
                                                   mSwipeRefresh.setRefreshing(true);
                                           }

                                           super.onProgressChanged(view, newProgress);
                                       }

                                       @Override
                                       public void onReceivedTitle(WebView view, String title) {
                                           super.onReceivedTitle(view, title);
                                       }
                                   }
        );
        if (getIntent() != null) {
            mUrl = getIntent().getStringExtra(INTENT_URL);
        }
        if (TextUtils.isEmpty(mUrl)) {
            ToastUtil.showToast("网络异常");
            finish();
        }
    }

    @Override
    public void refresh() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSettings.setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.loadUrl(mUrl);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSettings.setJavaScriptEnabled(false);
    }

    @Override
    protected void onDestroy() {
        try {
            if (mWebView != null) {
                mWebView.stopLoading();
                mWebView.removeAllViews();
                mWebView.destroy();
            }
            if (mSwipeRefresh != null) {
                mSwipeRefresh.removeAllViews();
            }
        } catch (Exception e) {
            MyLog.e(e.getMessage());
            e.printStackTrace();
        } finally {
            mWebView = null;
            mSwipeRefresh = null;
            android.os.Process.killProcess(android.os.Process.myPid());
            super.onDestroy();
        }
    }

    @Override
    public void setPresenter() {

    }


    private final class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url2) {
            view.loadUrl(url2);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //view.loadUrl(url);
            // pb_loading.setVisibility(IView.GONE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }
}
