package com.yl.technician.module.im.friendcheck;

import android.text.TextUtils;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivitySetRemakeBinding;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.EventBean;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhangzz on 2018/9/27
 * 设置备注页面
 */
@CreatePresenter(SetRemakePresenter.class)
public class SetRemakeActivity extends BaseMvpAppCompatActivity<SetRemakeView, SetRemakePresenter> implements SetRemakeView {
    private ActivitySetRemakeBinding mBinding;
    private String friendRelationId = "";//好友关系id

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_set_remake;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivitySetRemakeBinding) viewDataBinding;
        friendRelationId = getIntent().getStringExtra(Constants.EXTRA_FRIEND_RELATION_ID);

        mBinding.titleView.setLeftClickListener(v -> {
            finish();
        });

        initEvent();
    }

    private void initEvent() {
        mBinding.titleView.setRightTextClickListener(v -> {
            String toStr = null;
            if (TextUtils.isEmpty(mBinding.afEdit.getText().toString())) {
                toStr = getString(R.string.ims_remake);
                ToastUtils.shortToast(toStr);
                return;
            }

            getMvpPresenter().updateNickName(friendRelationId, mBinding.afEdit.getText().toString());
        });
    }

    @Override
    public void onUpdateNickNameSuccess() {
        showToast(getString(R.string.ims_remake_sucs));
        EventBus.getDefault().post(new EventBean.FriendInfoUpdate(0));
        EventBus.getDefault().post(new EventBean.FriendChangeEventBean(Constants.EVENT_FRIEND_CHANGE));
        finish();
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }
}
