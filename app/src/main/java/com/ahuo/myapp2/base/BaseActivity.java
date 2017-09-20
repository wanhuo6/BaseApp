package com.ahuo.myapp2.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ahuo.myapp2.R;
import com.ahuo.myapp2.manager.HttpManager;
import com.ahuo.myapp2.ui.widget.MyAppBar;
import com.ahuo.tool.util.MyOnClickListener;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 17-8-1
 *
 * @author liuhuijie
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    private FrameLayout mContentLayout;
    protected MyAppBar mToolbar;
    protected LinearLayout mLLAppbar;
    protected View mSplitLine;
    protected LinearLayout mLLNetError;
    protected Button mBtnRefresh;
    protected P mPresenter;

    protected Unbinder mUnBinder = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_MODE_OVERLAY);  //防止在WebView中长按复制出现标题栏显示错误
        initWindows();
        setContentView(getLayoutId());
        setPresenter();
        if (mPresenter != null) {
            mPresenter.setView(this);
        }
        mUnBinder = ButterKnife.bind(this);
        initData();
    }

    protected void initWindows() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {//4.4到5.0
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        getDelegate().setContentView(R.layout.activity_base);
        mLLAppbar = (LinearLayout) findViewById(R.id.ll_appbar);
        mContentLayout = (FrameLayout) findViewById(R.id.content);
        mSplitLine = findViewById(R.id.split_line);
        mLLNetError = (LinearLayout) findViewById(R.id.ll_net_error);
        mBtnRefresh = (Button) findViewById(R.id.btn_refresh);
        mBtnRefresh.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onMyClick(View v) {
                refresh();
            }
        });
        mToolbar = (MyAppBar) findViewById(R.id.kk_toolbar);
    }

    @Override
    public void setContentView(View view) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID != 0) {
            mContentLayout.removeAllViews();
            getLayoutInflater().inflate(layoutResID, mContentLayout, true);
        }
    }

    public void setNetErrorLayout() {
        mLLNetError.setVisibility(View.VISIBLE);
        mContentLayout.setVisibility(View.GONE);
    }

    public void setNormalLayout() {
        mLLNetError.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.VISIBLE);
    }

    protected abstract int getLayoutId();


    public abstract void initData();

    protected View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onViewClick(v);
        }
    };

    public abstract void refresh();

    protected void onViewClick(View v) {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.removeView();
            mPresenter = null;
        }
        HttpManager.dismissLoading();
        mUnBinder.unbind();
    }
}
