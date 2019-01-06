package com.yl.technician.module.login.agreement;

import android.view.View;

import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.databinding.ActivitySignedAgreementBinding;

/**
 * Created by lvlong on 2018/9/25.
 *
 * 签署协议页
 */
public class SignedAgreementActivity extends BaseMvpAppCompatActivity<SignedAgreementView,SignedAgreementPresenter> implements SignedAgreementView ,ClickHandler {

    private ActivitySignedAgreementBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_signed_agreement;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivitySignedAgreementBinding) viewDataBinding;
        mBinding.setClick(this);

        setTitles();
    }

    private void setTitles() {

        mBinding.titleView.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_agree:        //同意协议
                finish();
                break;
        }
    }

    @Override
    public void showToast(String message) {

    }

}
