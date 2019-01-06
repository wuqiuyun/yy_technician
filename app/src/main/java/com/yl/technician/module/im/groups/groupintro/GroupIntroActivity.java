package com.yl.technician.module.im.groups.groupintro;


import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.databinding.ActivityGroupIntroBinding;
import com.yl.technician.model.vo.bean.EventBean;

import org.greenrobot.eventbus.EventBus;

/**
 * 群介绍编辑
 * Created by lizhuo on 2018/10/12.
 */
public class GroupIntroActivity extends BaseMvpAppCompatActivity<GroupIntroView, GroupIntroPresenter> implements GroupIntroView {
    private ActivityGroupIntroBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_group_intro;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityGroupIntroBinding) viewDataBinding;
        mBinding.titleView.setLeftClickListener(view -> finish());
        mBinding.titleView.setRightTextClickListener(view -> {
            String intro = mBinding.etIntro.getEditableText().toString();
            EventBus.getDefault().post(new EventBean.GroupIntroduce(intro));
            finish();
        });
    }

    @Override
    public void showToast(String message) {

    }

}
