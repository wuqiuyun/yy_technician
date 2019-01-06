package com.yl.technician.module.mine.settings.feedback;

import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityFeedbackBinding;
import com.yl.technician.model.vo.bean.InitAppFeedbackBean;
import com.yl.technician.util.PhoneUtil;

/**
 * Created by lvlong on 2018/10/8.
 *
 * 体验反馈
 *
 */
@CreatePresenter(FeedbackPresenter.class)
public class FeedbackActivity extends BaseMvpAppCompatActivity<IFeedbackView, FeedbackPresenter> implements ClickHandler , IFeedbackView {

    ActivityFeedbackBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void init() {

        mBinding = (ActivityFeedbackBinding) viewDataBinding;
        mBinding.setClick(this);

        //设置客服电话的下划线
        mBinding.tvServicePhone.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        StatusBarUtil.setLightMode(this);

        mBinding.titleView.setLeftClickListener(view -> finish());
        getMvpPresenter().initAppFeedback(this);

        initInput();
    }
    
    //处理输入限制
    private void initInput(){
        TextWatcher mTextWatcher = new TextWatcher() {
            private CharSequence temp;
            private int editStart ;
            private int editEnd ;
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = mBinding.etFeedback.getSelectionStart();
                editEnd = mBinding.etFeedback.getSelectionEnd();
                mBinding.tvCurrentNum.setText(temp.length() + "");
                mBinding.tvEditHint.setVisibility(temp.length() > 0? View.GONE : View.VISIBLE);
//                if (temp.length() > 10) {
//                    Toast.makeText(TestActivity.this,
//                            "你输入的字数已经超过了限制！", Toast.LENGTH_SHORT)
//                            .show();
//                    s.delete(editStart-1, editEnd);
//                    int tempSelection = editStart;
//                    mEditText.setText(s);
//                    mEditText.setSelection(tempSelection);
//                }
            }
        };
        
        mBinding.etFeedback.addTextChangedListener(mTextWatcher);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tv_service_phone :        //客服电话
                PhoneUtil.toCallPhone(this, mBinding.tvServicePhone.getText().toString());
                break;
            case R.id.tv_submit :               //提交
                getMvpPresenter().submitFeedback(mBinding.etFeedback.getText().toString().trim());
                break;
        }

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void initAppFeedbackSuc(InitAppFeedbackBean bean) {
        mBinding.tvEditHint.setHint(bean.getTip());
        mBinding.tvServicePhone.setText(bean.getTelephone());
    }

    @Override
    public void submitSuc() {
        showToast("反馈提交成功！");
        finish();
    }
}
