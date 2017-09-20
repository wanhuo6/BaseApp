package com.ahuo.myapp2.presenter;

import android.content.Context;

import com.ahuo.myapp2.base.BasePresenter;
import com.ahuo.myapp2.base.BaseResponse;
import com.ahuo.myapp2.contract.MainFragmentContract;
import com.ahuo.myapp2.entity.response.GetUserResponse;
import com.ahuo.myapp2.manager.HttpManager;
import com.ahuo.tool.util.MyLog;

/**
 * Created by ahuo on 17-9-19.
 */

public class MainFragmentPresenter extends BasePresenter<MainFragmentContract.MainFragmentView> implements MainFragmentContract.MainFragmentBiz {

    @Override
    public void getUsers(Context context) {
        addDisposable(HttpManager.normalRequest(context, mApiService.getUsers(), new HttpManager.HttpListener() {
            @Override
            public void success(BaseResponse response) {
                mView.getUsersSuccess((GetUserResponse) response);
            }

            @Override
            public void fail(String message) {
                mView.getUsersFail(message);
                MyLog.e(message+"ddddd");
            }
        }));
    }
}
