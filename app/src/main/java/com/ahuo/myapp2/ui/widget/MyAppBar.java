package com.ahuo.myapp2.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.CheckResult;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleableRes;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahuo.myapp2.R;
import com.ahuo.myapp2.util.CommonUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MyAppBar extends Toolbar {

    private String mTitle = null;
    private int mTitleSize = 20;
    private int mTitleColor = Color.BLACK;
    private static final String TAG = "AppBar";

    private LayoutParams MENU_LP;

    private final Context context;
    private View mLeftView = null;
    private View mRightView = null;
    private View mCenterView = null;

    private TextView mToolbarTitle;

    public MyAppBar(Context context) {
        this(context, null);
    }

    public MyAppBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.toolbarStyle);
    }

    public MyAppBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        MENU_LP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, Gravity.CENTER);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyAppBar, defStyleAttr, 0);
        final String navBtnGravity = a.getString(R.styleable.MyAppBar_navigationGravity);
        mTitle = a.getString(R.styleable.MyAppBar_center_title);
        mTitleSize = a.getDimensionPixelSize(R.styleable.MyAppBar_center_title_size, mTitleSize);
        mTitleColor = a.getColor(R.styleable.MyAppBar_center_title_color, mTitleColor);

        final int[] styleableResIds = {
                R.styleable.MyAppBar_menu_left,
                R.styleable.MyAppBar_menu_right,
                R.styleable.MyAppBar_menu_center,
        };
        List<Integer> menuIds = getResIds(styleableResIds, a);
        a.recycle();

        if (!TextUtils.isEmpty(mTitle)) {
            setToolbarTitle(mTitle);
        }

        // 1.set nav button
        ImageButton navButton = getNavButton();
        if (navButton != null) {
            LayoutParams lp = (LayoutParams) navButton.getLayoutParams();
            if (!TextUtils.equals(navBtnGravity, "0")) {
                lp.gravity = Gravity.CENTER_VERTICAL;
            }
            getNavButton().setLayoutParams(lp);
        }

        // 2.set menu views
        for (int i = 0; i < menuIds.size(); i++) {
            int menuId = menuIds.get(i);
            if (menuId == 0) {
                continue;
            }
            if (i == 0) {
                setLeftMenu(context, menuId);
                continue;
            }
            if (i == 1) {
                setRightMenu(context, menuId);
                continue;
            }

            if (i == 2) {
                setCenterMenu(context, menuId);
            }
        }
    }


    /**
     * 设置中间的Menu
     *
     * @param context
     * @param menuId
     */
    public void setCenterMenu(Context context, int menuId) {
        setMenu(context, Gravity.CENTER, menuId);
    }


    /**
     * 设置右边的Menu
     *
     * @param context
     * @param menuId
     */
    public void setRightMenu(Context context, int menuId) {
        setMenu(context, Gravity.RIGHT, menuId);
    }


    /**
     * 设置左边的Menu
     *
     * @param context
     * @param menuId
     */
    public void setLeftMenu(Context context, int menuId) {
        setMenu(context, Gravity.LEFT, menuId);
    }

    private void setMenu(Context context, int gravity, int menuId) {
//        ActionBar.LayoutParams lp = new LayoutParams(CommonUtils.dpTopx(getContext(), 35), CommonUtils.dpTopx(getContext(), 35), gravity | Gravity.CENTER_VERTICAL);
        ActionBar.LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, gravity | Gravity.CENTER_VERTICAL);
        switch (gravity) {
            case Gravity.LEFT:
                mLeftView = initMenuVIew(context, menuId);
                addView(mLeftView, lp);
                break;
            case Gravity.RIGHT:
                mRightView = initMenuVIew(context, menuId);
                addView(mRightView, lp);
                break;
            case Gravity.CENTER:
                mCenterView = initMenuVIew(context, menuId);
                addView(mCenterView, lp);
                break;
        }
    }

    public String getToolbarTitle() {
        return mTitle;
    }

    //设置居中的Title
    public void setToolbarTitle(int string_id) {
        setToolbarTitle(context.getResources().getString(string_id));
    }

    public void setToolbarTitle(String title) {
        mTitle = title;
        if (mToolbarTitle == null) {
            mToolbarTitle = new TextView(context);
            mToolbarTitle.setTextColor(mTitleColor);
            mToolbarTitle.setTextSize(mTitleSize);
            mToolbarTitle.setGravity(Gravity.CENTER);
            mToolbarTitle.setMaxEms(12);
            mToolbarTitle.setMaxLines(1);
            mToolbarTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            // mToolbarTitle.setHorizontallyScrolling(true);
            mToolbarTitle.setFocusableInTouchMode(true);
//            TextPaint tp = mToolbarTitle.getPaint();
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT, Gravity.CENTER);
            addView(mToolbarTitle, layoutParams);
        }
        mToolbarTitle.setText(title);
    }

    public MyAppBar addMenu(View v) {
        addView(v, MENU_LP);
        return this;
    }

    /**
     * 通过资源的id添加menu
     *
     * @param menuId [layoutResId,DrawableResId,StringResId]
     */
    public <T extends View> T addMenu(@LayoutRes @DrawableRes @StringRes int menuId) {
        final View menuV = initMenuVIew(context, menuId);
        addView(menuV, MENU_LP);
        return (T) menuV;
    }

    @NonNull
    private View initMenuVIew(Context context, int menuId) {
        final View menuV;
        String str = getResources().getString(menuId);
        if (str.startsWith("res/drawable") || str.startsWith("res/mipmap")) {
            // 是图片
            menuV = new ImageView(context, null, R.attr.toolbarMenuImageStyle);
            ((ImageView) menuV).setImageResource(menuId);
        } else if (str.startsWith("res/layout")) {
            // 是view的布局文件
            menuV = LayoutInflater.from(context).inflate(menuId, null);
        } else {
            // 是文本
            menuV = new TextView(context, null, R.attr.toolbarMenuTextStyle);
            ((TextView) menuV).setText(str);
        }
        return menuV;
    }

    /**
     * @return menu视图的id数组
     */
    private List<Integer> getResIds(@StyleableRes int[] styleResIds, TypedArray a) {
        List<Integer> ids = new ArrayList<>();
        for (int resId : styleResIds) {
            ids.add(a.getResourceId(resId, 0));
        }
        return ids;
    }


    public void canFinishActivity() {
        if (context instanceof Activity) {
            setNavigationOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) context).finish();
                }
            });
        }
    }

    public
    @CheckResult
    View getLfetMenu() {
        return mLeftView != null ? mLeftView : null;
    }

    public
    @CheckResult
    View getRighMenut() {
        return mRightView != null ? mRightView : null;
    }

    public
    @CheckResult
    View getCenterMenu() {
        return mCenterView != null ? mCenterView : null;
    }


    /**
     * 得到标题按钮
     */
    public
    @CheckResult
    TextView getTitleView() {
        return (TextView) getSubView("mTitleTextView");
    }

    /**
     * 得到子标题
     */
    public
    @CheckResult
    TextView getSubtitleView() {
        return ((TextView) getSubView("mSubtitleTextView"));
    }

    /**
     * 得到左边的导航按钮
     */
    public
    @CheckResult
    ImageButton getNavButton() {
        return (ImageButton) getSubView("mNavButtonView");
    }

    /**
     * 得到logo的视图
     */
    public
    @CheckResult
    ImageView getLogoView() {
        return ((ImageView) getSubView("mLogoView"));
    }

    /**
     * 得到最右边的可折叠按钮视图
     */
    public
    @CheckResult
    ImageButton getCollapseButton() {
        return (ImageButton) getSubView("mCollapseButtonView");
    }

    private View getSubView(String name) {
        Field field;
        try {
            field = Toolbar.class.getDeclaredField(name);
            field.setAccessible(true);
            View v = (View) field.get(this);
            field.setAccessible(false);
            return v;
        } catch (Exception e) {
        }
        return null;
    }

    public static class TitleConfig {
        // 左边图标
        public int leftViewResId;

        public OnClickListener leftViewListener, centerViewListener;

        // 标题
        public String title;

        // 右边View文字 图标
        public View[] rightViews;

        public View[] centerViews;

        public View[] leftViews;

        public int centerResId;

        public TitleConfig() {
        }

        public TitleConfig(String title) {
            this.title = title;
        }

        public TitleConfig setRightView(View[] views) {
            rightViews = views;
            return this;
        }

        public TitleConfig setLeftOnClickListener(OnClickListener onClickListener) {
            leftViewListener = onClickListener;
            return this;
        }

        public TitleConfig setLeftViews(View[] leftViews) {
            this.leftViews = leftViews;
            return this;
        }

        public TitleConfig setLeftViewResId(int resId) {
            leftViewResId = resId;
            return this;
        }

        public TitleConfig setCenterResId(int centerResId) {
            this.centerResId = centerResId;
            return this;
        }

        public void setCenterViews(View[] centerViews) {
            this.centerViews = centerViews;
        }
    }

    public void setTitleConfig(TitleConfig config) {
        if (config == null) {
            return;
        }
        if (config.leftViewListener == null) {
            if (config.leftViews != null) {
                setLeftMenu(config.leftViews);
            }
        } else {
            View view;
            if (config.leftViewResId > 0) {
                view = setLeftImage(config.leftViewResId);
                view.setOnClickListener(config.leftViewListener);
            } else {
                view = setLeftImage(R.drawable.ic_arrow_back);
                view.setOnClickListener(config.leftViewListener);
            }
        }
        if (!TextUtils.isEmpty(config.title)) {
            setToolbarTitle(config.title);
            if (config.centerViewListener != null) {
                mToolbarTitle.setOnClickListener(config.centerViewListener);
            }
        } else if (config.centerResId > 0) {
            setCenterMenu(context, config.centerResId);
        } else if (config.centerViews != null && config.centerViews.length > 0) {
            setCenterMenu(config.centerViews);
        }
        if (config.rightViews != null) {
            setRightMenu(config.rightViews);
        }

    }

    public void setRightMenu(View[] views) {
        ActionBar.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        for (int i = 0; i < views.length; i++) {
            View view = views[i];
            if (view.getPaddingLeft() == 0 && view.getPaddingBottom() == 0 && view.getPaddingRight() == 0 && view.getPaddingTop() == 0) {
                view.setPadding(CommonUtils.dpTopx(getContext(), 5), CommonUtils.dpTopx(getContext(), 10), CommonUtils.dpTopx(getContext(), 5), CommonUtils.dpTopx(getContext(), 10));
            }
            if (view instanceof TextView) {
                lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            }
            lp.setMargins(0, 0, CommonUtils.dpTopx(getContext(), 10), 0);
            addView(view, lp);
        }
    }

    public void setCenterMenu(View[] views) {
        ActionBar.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        if (views.length > 1) {
            for (int i = 0; i < views.length; i++) {
                if (i != 0) {
                    lp.setMargins(CommonUtils.dpTopx(getContext(), 10), 0, 0, 0);
                } else {
                    lp.setMargins(0, 0, CommonUtils.dpTopx(getContext(), 10), 0);
                }
                addView(views[i], lp);
            }
        } else {
            addView(views[0], lp);
        }
    }

    public void setLeftMenu(View[] views) {
        ActionBar.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.LEFT | Gravity.CENTER_VERTICAL);
        for (int i = 0; i < views.length; i++) {
            View view = views[i];
            if (view.getPaddingLeft() == 0 && view.getPaddingBottom() == 0 && view.getPaddingRight() == 0 && view.getPaddingTop() == 0) {
                view.setPadding(CommonUtils.dpTopx(getContext(), 5), CommonUtils.dpTopx(getContext(), 10), CommonUtils.dpTopx(getContext(), 5), CommonUtils.dpTopx(getContext(), 10));
            }
            if (view instanceof TextView) {
                lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.LEFT | Gravity.CENTER_VERTICAL);
            }
            lp.setMargins(CommonUtils.dpTopx(getContext(), 10), 0, 0, 0);
            addView(view, lp);
        }
    }

    public ImageView setLeftImage(int resID) {
        ActionBar.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.LEFT | Gravity.CENTER_VERTICAL);
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(resID);
        lp.setMargins(CommonUtils.dpTopx(getContext(), 5), 0, 0, 0);
        addView(imageView, lp);
        return imageView;
    }
}