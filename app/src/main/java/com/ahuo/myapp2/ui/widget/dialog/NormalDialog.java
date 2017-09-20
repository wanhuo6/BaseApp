package com.ahuo.myapp2.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ahuo.myapp2.R;
import com.ahuo.myapp2.ui.widget.listener.NormalDialogListener;
import com.ahuo.tool.util.MyOnClickListener;
import com.ahuo.tool.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * description : 标准对话框
 * author : LiuHuiJie
 * created on : 2017-8-7
 */
public class NormalDialog extends Dialog {


    private static Builder mBuilder;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.et_input_message)
    EditText mEtInputMessage;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.iv_divider_ver)
    View mIvDividerVer;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;

    private Unbinder mUnBinder = null;

    public static Builder with(Context context) {
        if (mBuilder != null) {
            mBuilder.clear();
            mBuilder = null;
        }
        if (mBuilder == null) {
            mBuilder = new Builder(context);
        }
        return mBuilder;
    }

    private NormalDialog(DialogParam param) {
        super(param.context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View contentView = LayoutInflater.from(param.context).inflate(R.layout.dialog_normal, null);
        mUnBinder = ButterKnife.bind(this, contentView);
        setContentView(contentView);
        initDialogParam(param);
    }

    private void initDialogParam(DialogParam param) {
        if (!TextUtils.isEmpty(param.title)) {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(param.title);
        }

        if (param.contentType == DialogParam.TYPE_EDITTEXT) {
            mEtInputMessage.setVisibility(View.VISIBLE);
        } else if (param.contentType == DialogParam.TYPE_TEXT) {
            mTvContent.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(param.content)) {
            mTvContent.setText(param.content);
        }
        setListener(param);
    }

    private void setListener(final DialogParam param) {
        mBtnConfirm.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onMyClick(View v) {
                if (param.onclickListener == null) {
                    dismiss();
                } else {
                    if (param.contentType == DialogParam.TYPE_EDITTEXT) {
                        String result = mEtInputMessage.getText().toString().trim();
                        if (!TextUtils.isEmpty(result)) {
                            param.onclickListener.onRightClickEditText(result);
                            dismiss();
                        } else {
                            ToastUtil.showToast("输入内容为空！");
                        }
                    } else {
                        param.onclickListener.onRightClick();
                        dismiss();
                    }
                }
            }
        });
        mBtnCancel.setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onMyClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mBuilder != null) {
            mBuilder.clear();
            mBuilder = null;
        }
        if (mUnBinder != null) {
            mUnBinder.unbind();
            mUnBinder = null;
        }
    }

    public static class DialogParam {
        public static final int TYPE_TEXT = 0;
        public static final int TYPE_EDITTEXT = 1;
        private Context context;
        private String title;
        private String content;
        private int contentType;
        private String confirm;
        private String cancel;
        private NormalDialogListener onclickListener;
    }

    public static class Builder {
        private DialogParam mDialogParam;

        public Builder(Context context) {
            mDialogParam = new DialogParam();
            mDialogParam.context = context;
        }

        public Builder setTitle(String title) {
            mDialogParam.title = title;
            return this;
        }

        public Builder setConfirm(String confirm) {
            mDialogParam.confirm = confirm;
            return this;
        }

        public Builder setCancel(String cancel) {
            mDialogParam.cancel = cancel;
            return this;
        }

        public Builder setContent(String content) {
            mDialogParam.content = content;
            return this;
        }

        public Builder setContentType(int type) {
            mDialogParam.contentType = type;
            return this;
        }

        public Builder setOnclickListener(NormalDialogListener onclickListener) {
            mDialogParam.onclickListener = onclickListener;
            return this;
        }

        public void clear() {
            mDialogParam = null;
        }

        public NormalDialog create() {
            return new NormalDialog(mDialogParam);
        }

    }

}
