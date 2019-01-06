package com.yl.technician.module.login.invitecode;


import android.annotation.SuppressLint;
import android.view.View;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityInviteCodeBinding;
import com.yl.technician.model.local.preferences.CommonSharedPreferences;
import com.yl.technician.module.login.information.PerfectInformationActivity;
import com.yl.technician.module.login.invitecode.two.InviteCode2Activity;
import com.yl.technician.util.FormatUtil;

/**
 * 邀请码（选填）
 */
@CreatePresenter(InviteCodePresenter.class)
public class InviteCodeActivity extends BaseMvpAppCompatActivity<InviteCodeView, InviteCodePresenter> implements ClickHandler, InviteCodeView {

    ActivityInviteCodeBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_invite_code;
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    protected void init() {

        mBinding = (ActivityInviteCodeBinding) viewDataBinding;
        mBinding.setClick(this);

        setTitles();
        // 修改状态栏字体颜色
        StatusBarUtil.setLightMode(this);
        mBinding.tvRegisterReward.setText(
                FormatUtil.Formatstring(CommonSharedPreferences.getInstance().getBasicDataBean().getRegisterReward()));
        mBinding.tvDaibi.setText(FormatUtil.Formatstring(CommonSharedPreferences.getInstance().getBasicDataBean().getInviteReward()));
    }

    private void setTitles() {

        //跳过
        mBinding.titleView.setRightTextClickListener(view -> {

            startActivity(InviteCodeActivity.this , PerfectInformationActivity.class);
            finish();
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_invite_commit:    //提交

                getMvpPresenter().submitInviteCode(mBinding.etInviteCode.getText().toString().trim());
                break;

        }

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void onInviteCodeSuccess() {

        InviteCode2Activity.startActivity(InviteCodeActivity.this, InviteCode2Activity.class);
        finish();
    }
}
