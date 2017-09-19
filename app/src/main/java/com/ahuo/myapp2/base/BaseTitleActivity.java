package com.ahuo.myapp2.base;

import android.view.View;

import com.ahuo.myapp2.ui.widget.MyAppBar;

/**
 * description :
 * author : LiuHuiJie
 * created on : 2017-8-3
 */
public abstract class BaseTitleActivity<P extends BasePresenter> extends BaseActivity<P> {

    @Override
    protected void initWindows() {
        super.initWindows();
        mLLAppbar.setVisibility(View.VISIBLE);
        mToolbar.setTitleConfig(getTitleViewConfig());
        setSupportActionBar(mToolbar);
        //不显示Toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
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
        config.leftViewListener = mBackOnClickListener;
        return config;
    }


    /**
     * 默认左侧按钮点击事件
     */
    public View.OnClickListener mBackOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

}
