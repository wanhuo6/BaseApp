package com.ahuo.myapp2.contract;

import android.content.Context;

import com.ahuo.myapp2.base.BaseView;
import com.ahuo.myapp2.entity.response.GetUserResponse;

/**
 * Created by ahuo on 17-9-19.
 */

public interface MainFragmentContract {

    interface MainFragmentView extends BaseView {

        void getUsersSuccess(GetUserResponse response);

        void getUsersFail(String message);
    }

    interface MainFragmentBiz {

        void getUsers(Context context);

    }

}
