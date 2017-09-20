package com.ahuo.myapp2.manager;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ahuo.myapp2.MyApp;
import com.ahuo.myapp2.R;
import com.ahuo.myapp2.base.BaseResponse;
import com.ahuo.myapp2.net.HttpExceptionUtils;
import com.ahuo.myapp2.ui.widget.dialog.LoadingDialog;
import com.ahuo.tool.util.NetWorkUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * description : 网络请求入口
 * author : LiuHuiJie
 * created on : 2017-8-3
 */
public class HttpManager {

    public static LoadingDialog mLoadingDialog;

    public static <T extends BaseResponse> Disposable normalRequest(Context context, Observable<T> observable, final HttpListener listener) {
        if (!NetWorkUtils.isNetConnect(MyApp.getInstance())) {
            listener.fail(MyApp.getInstance().getString(R.string.public_net_no_use));
            return null;
        }
        showLoading(context);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<T>() {
                            @Override
                            public void accept(@NonNull T response) throws Exception {
                                dismissLoading();
                                listener.success(response);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                dismissLoading();
                                listener.fail(HttpExceptionUtils.handleException(throwable));
                            }
                        }
                );
    }

    public static void showLoading(Context context) {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismissLoading();
            mLoadingDialog = null;
        }
        mLoadingDialog = new LoadingDialog(context);
        mLoadingDialog.showLoading(MyApp.getInstance().getString(R.string.public_hint_data_loading));
    }

    public static void dismissLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismissLoading();
            mLoadingDialog = null;
        }
    }

    public interface HttpListener {
        void success(BaseResponse response);

        void fail(String message);
    }

}
