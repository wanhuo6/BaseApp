package com.ahuo.myapp2.ui.widget.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahuo.myapp2.R;
import com.ahuo.myapp2.entity.other.NormalPickerEntity;
import com.ahuo.myapp2.ui.widget.CustomerWheelView;
import com.ahuo.tool.util.MyOnClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 2016-12-13.
 *
 * @author LiuHuiJie
 */
public class NormalPickerDialog extends DialogFragment {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.number_picker)
    CustomerWheelView mNumberPicker;
    @BindView(R.id.tv_confirm)
    TextView mTvConfirm;

    private NormalPickerEntity mNormalPickerEntity;
    private static NormalPickerDialog mNormalPickerDialog;
    private ConfirmLister mConfirmLister;

    private static final String INTENT_ENTITY = "intent_entity";

    public static NormalPickerDialog createDialog(NormalPickerEntity entity) {
        mNormalPickerDialog = new NormalPickerDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(INTENT_ENTITY, entity);
        mNormalPickerDialog.setArguments(bundle);
        mNormalPickerDialog.setCancelable(false);
        return mNormalPickerDialog;
    }

    public void showDialog(FragmentManager manager, String tag) {

        show(manager, tag);

    }

    public NormalPickerDialog setOnConfirmListener(ConfirmLister listener) {
        this.mConfirmLister = listener;
        return mNormalPickerDialog;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNormalPickerEntity = getArguments().getParcelable(INTENT_ENTITY);
        if (mNormalPickerEntity == null) {
            dismiss();
            return;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.NormalDialogStyle);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_normal_picker, null);
        ButterKnife.bind(this, view);
        initData();
        builder.setView(view);
        return builder.create();
    }

    private void initData() {
        if (!TextUtils.isEmpty(mNormalPickerEntity.mTitle)) {
            mTvTitle.setText(mNormalPickerEntity.mTitle);
        }
        mTvConfirm.setOnClickListener(mKKClickListener);
        mIvClose.setOnClickListener(mKKClickListener);
        initWheelPicker(mNumberPicker, mNormalPickerEntity.mChooseList, mNormalPickerEntity.mConfirmItem);

    }

    private MyOnClickListener mKKClickListener = new MyOnClickListener() {
        @Override
        protected void onMyClick(View v) {
            switch (v.getId()) {
                case R.id.tv_confirm:
                    mNormalPickerEntity.mConfirmItem = mNormalPickerEntity.mSelectItem;
                    if (mConfirmLister != null) {
                        mConfirmLister.OnConfirmClick();
                    }
                    dismiss();
                    break;
                case R.id.iv_close:
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    private void initWheelPicker(CustomerWheelView numberPicker, List<String> data_list, String default_item) {
        numberPicker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        if (TextUtils.isEmpty(mNormalPickerEntity.mSelectItem)) {
            mNormalPickerEntity.mSelectItem = data_list.get(0);
        }

        int selected_index = 0;
        if (data_list != null && data_list.size() > 0) {
            for (int i = 0, n = data_list.size(); i < n; i++) {
                if (data_list.get(i).equals(default_item)) {
                    selected_index = i;
                    break;
                }
            }
        }
        numberPicker.setItems(data_list);
        numberPicker.setOnWheelViewListener(new CustomerWheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                mNormalPickerEntity.mSelectItem = item;
            }
        });
        numberPicker.setOffset(1);
        numberPicker.setSeletion(selected_index);
    }


    public interface ConfirmLister {

        void OnConfirmClick();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNormalPickerDialog = null;
        mConfirmLister = null;
    }
}
