package com.yl.technician.module.mine.settings.security.phone.update;

import android.view.View;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityUpdatePhoneBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.module.mine.settings.security.phone.PhoneVerifyPresenter;
import com.yl.technician.module.mine.settings.security.phone.PhoneVerifyView;
import com.yl.technician.util.FormatUtil;

/**
 * 更换手机号
 * Created by lvlong on 2018/10/12.
 */
@CreatePresenter(PhoneVerifyPresenter.class)
public class UpdatePhoneActivity extends BaseMvpAppCompatActivity<PhoneVerifyView, PhoneVerifyPresenter> implements ClickHandler , PhoneVerifyView {

    ActivityUpdatePhoneBinding mBinding;
    private String mMobile;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_update_phone;
    }

    @Override
    protected void init() {

        mBinding = (ActivityUpdatePhoneBinding) viewDataBinding;
        mBinding.setClick(this);

        initView();

    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        mBinding.titleView.setLeftClickListener(view -> finish());

        mBinding.tvCurrentPhone.setText(FormatUtil.Formatstring(AccountManager.getInstance().getMobile()));

        StatusBarUtil.setLightMode(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.tv_get_code :     //获取验证码

                getMvpPresenter().getPhoneCode(mBinding.etPhone.getText().toString());

                break;

            case R.id.btn_complete :    //完成
                String mobile = FormatUtil.Formatstring(AccountManager.getInstance().getMobile());
                mMobile = mBinding.etPhone.getText().toString();
                String code = mBinding.etCode.getText().toString();

                getMvpPresenter().updataPhone(mobile, mMobile,code);

                break;

        }

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void updateCountdownTime(long time) {
        mBinding.tvGetCode.setEnabled(false); // 禁止再次点击
        mBinding.tvGetCode.setText(String.format(getString(R.string.login_get_code_time), time + ""));
    }

    @Override
    public void onCountdownFinish() {

        mBinding.tvGetCode.setText(getString(R.string.login_get_again));
        mBinding.tvGetCode.setEnabled(true);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onUpdataPhoneSuccess() {

        showToast("手机号更改成功");
        AccountManager.getInstance().insertingLogout();

    }
}
