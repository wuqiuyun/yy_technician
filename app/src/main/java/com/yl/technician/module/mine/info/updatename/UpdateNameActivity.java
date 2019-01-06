package com.yl.technician.module.mine.info.updatename;

import android.os.Bundle;
import android.text.TextUtils;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityUpdateNameBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.bean.EventBean;

import org.greenrobot.eventbus.EventBus;

/**
 * 修改昵称
 */
@CreatePresenter(UpdateNamePresenter.class)
public class UpdateNameActivity extends BaseMvpAppCompatActivity<IUpdateNameView, UpdateNamePresenter> implements IUpdateNameView {

    ActivityUpdateNameBinding mBinding;
    private String name;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_update_name;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityUpdateNameBinding) viewDataBinding;
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            name = bundle.getString("name");
        }
        initView();
    }

    private void initView() {
        mBinding.titleView.setLeftClickListener(view -> finish());
        mBinding.titleView.setRightTextClickListener(view -> updateStylistName(mBinding.etName.getText().toString()));
        mBinding.etName.setText(TextUtils.isEmpty(name) ? "" : name);
    }

    private void updateStylistName(String newName) {
        if (TextUtils.isEmpty(newName)) {
            showToast("新昵称不能为空！");
            return;
        }
        
        if (newName.equals(name)) {
            finish();
        }

        getMvpPresenter().updateStylistName(newName);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void updateStylistNameSuc() {
        showToast("修改成功！");
        EventBean.UpdateUserSuc update = new EventBean.UpdateUserSuc();
        update.setName(mBinding.etName.getText().toString());
        EventBus.getDefault().post(update);
        AccountManager.getInstance().setNickname(mBinding.etName.getText().toString());
        finish();
    }
}
