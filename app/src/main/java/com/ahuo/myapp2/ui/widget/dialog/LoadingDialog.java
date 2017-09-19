package com.ahuo.myapp2.ui.widget.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

/**
 * description :
 * author : LiuHuiJie
 * created on : 2017-8-3
 */
public class LoadingDialog extends ProgressDialog {

    public LoadingDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        setProgressStyle(STYLE_SPINNER);
    }
    public void showLoading(String content){
        this.setMessage(content);
        this.show();
    }

    public void dismissLoading(){
        if (this.isShowing()){
            this.dismiss();
        }
    }
}
