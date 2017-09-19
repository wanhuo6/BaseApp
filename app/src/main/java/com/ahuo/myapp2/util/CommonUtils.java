package com.ahuo.myapp2.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * description :
 * author : LiuHuiJie
 * created on : 2017-8-7
 */
public class CommonUtils {
    /**
     * 隐藏当前view下的软键盘,适用dialog
     * @param context
     * @param view
     */
    public static  void dismissSoftKeyBoardByView(Context context, View view){
        InputMethodManager inputMethodManager =
                (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidthpx(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeightpx(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * dp转化px
     *
     * @param context
     * @param dpValue
     * @return
     */

    public static int dpTopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转化dp
     *
     * @param context
     * @param pxValue
     * @return
     */

    public static int pxTodp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
//    public static int sp2px(Context context, float spValue) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
//    }
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
