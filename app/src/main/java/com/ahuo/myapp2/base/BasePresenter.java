package com.ahuo.myapp2.base;


import com.ahuo.myapp2.net.client.ApiManager;
import com.ahuo.myapp2.net.client.ApiService;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created on 17-8-1
 *
 * @author liuhuijie
 */

public abstract class BasePresenter<V extends BaseView> {

    protected ApiService mApiService;

    protected CompositeDisposable mCompositeDisposable;

    protected V mView;

    public void setView(V view) {
        mApiService = ApiManager.getInstance().getApiService();
        mView = view;
    }

    public void removeView() {
        removeAllDisposable();
        mView = null;
    }

    public void addDisposable(Disposable disposable) {
        if (disposable == null) {
            return;
        }
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void removeAllDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

}
