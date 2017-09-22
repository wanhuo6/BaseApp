package com.ahuo.myapp2.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ahuo.myapp2.R;
import com.ahuo.myapp2.manager.HttpManager;
import com.ahuo.myapp2.ui.widget.MyAppBar;
import com.ahuo.tool.util.MyOnClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 2017-8-2
 *
 * @author LiuHuiJie
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    protected P mPresenter;

    protected View mRootView;
    @BindView(R.id.appBar)
    MyAppBar mAppBar;
    @BindView(R.id.split_line)
    View mSplitLine;
    @BindView(R.id.ll_appbar)
    LinearLayout mLLAppbar;
    @BindView(R.id.content)
    FrameLayout mContentLayout;
    @BindView(R.id.btn_refresh)
    Button mBtnRefresh;
    @BindView(R.id.ll_net_error)
    LinearLayout mLLNetError;
    Unbinder mUnBinder;
    protected boolean mHasInit;
    protected LayoutInflater mLayoutInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_base, container, false);
        }
      /*  mRootView = inflater.inflate(getLayoutId(), base, true);*/
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!mHasInit) {
            mContentLayout = mRootView.findViewById(R.id.content);
            mLayoutInflater.inflate(getLayoutId(), mContentLayout, true);
            mUnBinder = ButterKnife.bind(this, mRootView);
            mLLNetError = mRootView.findViewById(R.id.ll_net_error);
            mBtnRefresh.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onMyClick(View v) {
                    refresh();
                }
            });
            setPresenter();
            if (mPresenter != null) {
                mPresenter.setView(this);
            }

            initData();
        }
        mHasInit = true;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mRootView != null) {
            ViewGroup parent = ((ViewGroup) mRootView.getParent());
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }
    }

    public void setTipLayout(String tip) {
        if (TextUtils.isEmpty(tip)) {
            tip = getString(R.string.public_str_retry);
        }
        mBtnRefresh.setEnabled(false);
        mBtnRefresh.setText(tip);
        mLLNetError.setVisibility(View.VISIBLE);
        mContentLayout.setVisibility(View.GONE);
    }

    public void setNetErrorLayout() {
        mBtnRefresh.setEnabled(true);
        mBtnRefresh.setText(getString(R.string.public_str_retry));
        mLLNetError.setVisibility(View.VISIBLE);
        mContentLayout.setVisibility(View.GONE);
    }

    public void setNormalLayout() {
        mLLNetError.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.VISIBLE);
    }

    protected abstract int getLayoutId();


    public abstract void initData();

    protected MyOnClickListener mClickListener = new MyOnClickListener() {
        @Override
        protected void onMyClick(View v) {
            onViewClick(v);
        }
    };

    public abstract void refresh();

    protected void onViewClick(View v) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.removeView();
            mPresenter = null;
        }
        HttpManager.dismissLoading();
        mUnBinder.unbind();
    }


}
