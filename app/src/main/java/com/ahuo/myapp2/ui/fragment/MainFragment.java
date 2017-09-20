package com.ahuo.myapp2.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.ahuo.myapp2.R;
import com.ahuo.myapp2.base.BaseTitleFragment;
import com.ahuo.myapp2.contract.MainFragmentContract;
import com.ahuo.myapp2.entity.response.GetUserResponse;
import com.ahuo.myapp2.presenter.MainFragmentPresenter;
import com.ahuo.myapp2.ui.widget.MyAppBar;
import com.ahuo.myapp2.util.MyLog;
import com.ahuo.myapp2.util.ToastUtil;
import com.alibaba.fastjson.JSONObject;

import butterknife.BindView;

/**
 * Created by ahuo on 17-9-19.
 */

public class MainFragment extends BaseTitleFragment<MainFragmentPresenter> implements MainFragmentContract.MainFragmentView {

    @BindView(R.id.tvLookUsers)
    TextView mTvLookUsers;
    @BindView(R.id.tvUserMessage)
    TextView mTvUserMessage;

    @Override
    public void setPresenter() {
        mPresenter = new MainFragmentPresenter();
    }

    @Override
    public MyAppBar.TitleConfig getTitleViewConfig() {
        return buildDefaultConfig(getString(R.string.fragment_title_main));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initData() {
        MyLog.e("init==========");
        mTvLookUsers.setOnClickListener(mClickListener);
    }

    @Override
    public void refresh() {
        mPresenter.getUsers(getActivity());
    }

    @Override
    public void getUsersSuccess(GetUserResponse response) {
        setNormalLayout();
        mTvUserMessage.setText(JSONObject.toJSONString(response));
    }

    @Override
    public void getUsersFail(String message) {
        ToastUtil.showToast(message);
        setNetErrorLayout();
    }

    @Override
    protected void onViewClick(View v) {
        super.onViewClick(v);
        switch (v.getId()) {
            case R.id.tvLookUsers:
                refresh();
                break;

            default:
                break;
        }
    }
}
