package com.ahuo.myapp2.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ahuo.myapp2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description :
 * author : LiuHuiJie
 * created on : 2017-8-4
 */
public class ItemChooseView extends RelativeLayout{

    @BindView(R.id.tv_content)
    TextView mTvContent;

    public ItemChooseView(Context context) {
        this(context, null, 0);
    }

    public ItemChooseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemChooseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemChooseView);
        int n = ta.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.ItemChooseView_hint:
                    mTvContent.setHint(ta.getString(attr));
                    break;
                default:
                    break;
            }
        }
        ta.recycle();
    }

    public void initView(Context context){
        View view = View.inflate(context, R.layout.item_choose_layout, null);
        addView(view);
        ButterKnife.bind(this, view);
    }

    public void setContent(String content){
        if (TextUtils.isEmpty(content)){
            mTvContent.setText("");
            return;
        }
        mTvContent.setText(content);
    }



}
