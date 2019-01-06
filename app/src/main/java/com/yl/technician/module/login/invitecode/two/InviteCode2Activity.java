package com.yl.technician.module.login.invitecode.two;

import android.view.View;

import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.databinding.ActivityInviteCode2Binding;
import com.yl.technician.model.local.preferences.CommonSharedPreferences;
import com.yl.technician.module.login.information.PerfectInformationActivity;
import com.yl.technician.util.FormatUtil;

public class InviteCode2Activity extends BaseAppCompatActivity implements ClickHandler{

    ActivityInviteCode2Binding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_invite_code2;
    }

    @Override
    protected void init() {

        mBinding = (ActivityInviteCode2Binding) viewDataBinding;
        mBinding.setClick(this);
        // 修改状态栏字体颜色
        StatusBarUtil.setLightMode(this);
        mBinding.tvInviteReward.setText(
                FormatUtil.Formatstring(CommonSharedPreferences.getInstance().getBasicDataBean().getInviteReward()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_invite_ok:    //确定
                startActivity(this , PerfectInformationActivity.class);
                finish();
                break;

        }
    }
}
