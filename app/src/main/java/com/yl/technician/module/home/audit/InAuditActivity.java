package com.yl.technician.module.home.audit;

import android.view.View;

import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.databinding.ActivityInAuditBinding;
import com.yl.technician.module.home.service.add.AddServiceActivity;
import com.yl.technician.module.home.time.TimeManageActivity;


/*
    服务信息
 * Create by lvlong on  2018/10/25
 */

public class InAuditActivity extends BaseAppCompatActivity implements ClickHandler{

    ActivityInAuditBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_in_audit;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityInAuditBinding) viewDataBinding;
        mBinding.setClick(this);

        mBinding.titleView.setLeftClickListener(v -> finish());

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_service_manage:   //服务管理
                startActivity(this , AddServiceActivity.class);
                break;

            case R.id.btn_time_manage:      //时间管理
                startActivity(this , TimeManageActivity.class);
                break;

        }

    }
}
