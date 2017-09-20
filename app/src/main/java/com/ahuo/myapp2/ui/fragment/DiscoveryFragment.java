package com.ahuo.myapp2.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.ahuo.myapp2.R;
import com.ahuo.myapp2.base.BaseTitleFragment;
import com.ahuo.myapp2.core.AppConfig;
import com.ahuo.myapp2.ui.activity.MyWebViewActivity;
import com.ahuo.myapp2.ui.widget.MyAppBar;

import butterknife.BindView;

/**
 * Created by ahuo on 17-9-19.
 */

public class DiscoveryFragment extends BaseTitleFragment {
    @BindView(R.id.tvDiscovery)
    TextView mTvDiscovery;

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
        mTvDiscovery.setOnClickListener(mClickListener);

    }

    @Override
    protected void onViewClick(View v) {
        super.onViewClick(v);
        switch (v.getId()){
            case R.id.tvDiscovery:

                MyWebViewActivity.startActivity(getActivity(), AppConfig.API_HOST_WEB);

                break;

            default:
                break;
        }
    }

    @Override
    public void refresh() {

    }

}
