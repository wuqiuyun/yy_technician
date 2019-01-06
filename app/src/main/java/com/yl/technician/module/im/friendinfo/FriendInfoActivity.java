package com.yl.technician.module.im.friendinfo;

import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseAppCompatActivity;
import com.yl.technician.util.FragmentManagerUtil;

/**
 * Created by zhangzz on 2018/9/30
 * 好友资料页面
 */

public class FriendInfoActivity extends BaseAppCompatActivity {
    FragmentManagerUtil fragmentManagerUtil;

    FriendInfoFragment friendInfoFragment;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_friend_info;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        fragmentManagerUtil = new FragmentManagerUtil(this, R.id.layout_frame);
        friendInfoFragment = new FriendInfoFragment();
        friendInfoFragment.setArguments(getIntent().getExtras());

        fragmentManagerUtil.chAddFrag(friendInfoFragment, "", false);
    }

}
