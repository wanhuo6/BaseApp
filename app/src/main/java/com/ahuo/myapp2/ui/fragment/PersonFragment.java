package com.ahuo.myapp2.ui.fragment;

import android.widget.ImageView;

import com.ahuo.myapp2.R;
import com.ahuo.myapp2.base.BaseTitleFragment;
import com.ahuo.myapp2.core.AppConfig;
import com.ahuo.myapp2.ui.widget.MyAppBar;
import com.ahuo.util.imageloader.GlideLoaderUtil;

import butterknife.BindView;

/**
 * Created by ahuo on 17-9-19.
 */

public class PersonFragment extends BaseTitleFragment {
    @BindView(R.id.ivUserPhoto)
    ImageView mIvUserPhoto;

    @Override
    public void setPresenter() {

    }

    @Override
    public MyAppBar.TitleConfig getTitleViewConfig() {
        return buildDefaultConfig(getString(R.string.fragment_title_person));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_person;
    }

    @Override
    public void initData() {
        GlideLoaderUtil.loadNormalImage(getActivity(), AppConfig.APP_LOGO, GlideLoaderUtil.LOAD_IMAGE_DEFAULT_ID, mIvUserPhoto);
    }

    @Override
    public void refresh() {

    }


}
