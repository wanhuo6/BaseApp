package com.ahuo.myapp2.ui.fragment;

import com.ahuo.myapp2.R;
import com.ahuo.myapp2.base.BaseTitleFragment;
import com.ahuo.myapp2.ui.widget.MyAppBar;

/**
 * Created by ahuo on 17-9-19.
 */

public class DiscoveryFragment extends BaseTitleFragment {
    @Override
    public void setPresenter() {

    }

    @Override
    public MyAppBar.TitleConfig getTitleViewConfig() {
        return buildDefaultConfig(getString(R.string.fragment_title_discovery));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discovery;
    }

    @Override
    public void initData() {

    }

    @Override
    public void refresh() {

    }
}
