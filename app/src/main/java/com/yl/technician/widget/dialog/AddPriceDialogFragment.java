package com.yl.technician.widget.dialog;

import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.yl.technician.R;
import com.yl.technician.component.toast.ToastUtils;

/**
 * 加价Fragment
 * Created by zm on 2018/11/16.
 */
public class AddPriceDialogFragment extends AbstractCustomDialogFragment{
    private View mView;
    private OnInputListener mOnInputListener;

    public static AddPriceDialogFragment create() {
        AddPriceDialogFragment dialogFragment = new AddPriceDialogFragment();
        return dialogFragment;
    }

    @Override
    public View onCreateDialogView() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_price, null);
        EditText etAmount = mView.findViewById(R.id.et_amount);
        EditText etReason = mView.findViewById(R.id.et_reason);
        // 取消
        mView.findViewById(R.id.negativeButton).setOnClickListener(v -> {
            dismiss();
        });

        // 确定
        mView.findViewById(R.id.positiveButton).setOnClickListener(v -> {
            String amount = etAmount.getText().toString().trim();
            String reason = etReason.getText().toString().trim();
            if (TextUtils.isEmpty(amount)) {
                ToastUtils.shortToast("请输入加价金额.");
                return;
            }
            dismiss();
            if (mOnInputListener != null) {
                mOnInputListener.onInputContent(amount, reason);
            }
        });
        return mView;
    }

    public void show(FragmentActivity activity) {
        show(activity.getSupportFragmentManager(), "addPriceFragment");
    }

    public void setOnInputListener(OnInputListener onInputListener) {
        mOnInputListener = onInputListener;
    }

    public interface OnInputListener {
        void onInputContent(String amount, String reason);
    }
}
