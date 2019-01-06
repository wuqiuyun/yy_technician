package com.yl.technician.module.im.friendinfo;

import android.text.TextUtils;

import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseAppCompatActivity;
import com.yl.technician.databinding.ActivityFriendIntroduceBinding;

/**
 * Created by zhangzz on 2018/9/30
 * 好友资料页面
 */

public class FriendIntroduceActivity extends BaseAppCompatActivity {
    private String intro;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_friend_introduce;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        ActivityFriendIntroduceBinding mBinding = (ActivityFriendIntroduceBinding) viewDataBinding;

        intro = getIntent().getStringExtra("introduce");

        if (!TextUtils.isEmpty(intro)){
            mBinding.fiIntroduceTv.setText(intro);
        } else {
            mBinding.fiIntroduceTv.setText("暂无个人介绍");
        }
    }

}
