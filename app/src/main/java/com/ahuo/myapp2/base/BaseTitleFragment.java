package com.ahuo.myapp2.base;

import android.os.Bundle;
import android.view.View;

import com.ahuo.myapp2.ui.widget.MyAppBar;

/**
 * Created by ahuo on 17-9-19.
 */

public abstract class BaseTitleFragment<P extends BasePresenter> extends BaseFragment<P> {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLLAppbar.setVisibility(View.VISIBLE);
        mToolbar.setTitleConfig(getTitleViewConfig());
    }


    public abstract MyAppBar.TitleConfig getTitleViewConfig();

    /**
     * 1> 设置左边返回按钮的整件
     * 2> 设置标题文本
     *
     * @param title
     * @return
     */
    public MyAppBar.TitleConfig buildDefaultConfig(String title) {
        MyAppBar.TitleConfig config = new MyAppBar.TitleConfig(title);
        return config;
    }



}
