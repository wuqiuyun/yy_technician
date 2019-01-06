package com.yl.technician.module.im.addfriend;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.greendao.DaoCallBackInterface;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityAddFriendBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.daobean.UserFriendsBean;
import com.yl.technician.module.im.addfriend.friendapply.FriendApplySendActivity;
import com.yl.technician.module.im.daoutil.UserFriendDaoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzz on 2018/9/27
 * 添加好友页面
 */

@CreatePresenter(AddFriendPresenter.class)
public class AddFriendActivity extends BaseMvpAppCompatActivity<AddFriendView, AddFriendPresenter> implements AddFriendView {
    private ActivityAddFriendBinding mBinding;

    private UserFriendDaoUtils userFriendDaoUtils;

    private BaseQuickAdapter addFriendAdapter;
    private List<UserFriendsBean> frindLists = new ArrayList<>();

    private String addId;//需要添加的好友的id

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_friend;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityAddFriendBinding) viewDataBinding;

        if (null == userFriendDaoUtils) userFriendDaoUtils = new UserFriendDaoUtils(this);
        userFriendDaoUtils.setOnQuerySingleBackInterface(new DaoCallBackInterface.OnQuerySingleBackInterface() {
            @Override
            public void onQuerySingleBackInterface(Object entry, String id) {
                //查询结果不为空，说明已经是好友了 不允许再发送添加请求
                if (null != entry) {
                    showToast("已经是好友了！");
                }
                //查询结果为空，允许跳转并发送请求
                else {
                    Intent intent = new Intent(AddFriendActivity.this, FriendApplySendActivity.class);
                    intent.putExtra(Constants.EXTRA_SERACH_USERID, addId);//好友userId
                    startActivity(intent);
                }
            }
        });

        mBinding.rvAddFriend.setHasFixedSize(true);
        mBinding.rvAddFriend.setNestedScrollingEnabled(false);
        mBinding.rvAddFriend.setLayoutManager(new LinearLayoutManager(this));

        addFriendAdapter = new AddFriendAdapter(R.layout.adapter_addfriend);
        addFriendAdapter.openLoadAnimation();
        mBinding.rvAddFriend.setAdapter(addFriendAdapter);

        addFriendAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.af_tv_addfriend) {
                String userId = AccountManager.getInstance().getUserId();
                if (!TextUtils.isEmpty(userId)) {
                    if (userId.equals(frindLists.get(position).getId() + "")) {
                        showToast("不能添加自己为好友");
                    } else {
                        if (TextUtils.isEmpty(frindLists.get(position).getImusername())) {
                            showToast("查询好友数据有误");
                            DLog.e("ImUsername为空");
                        } else {
                            addId = frindLists.get(position).getId() + "";
                            userFriendDaoUtils.queryWhereUser(frindLists.get(position).getImusername());
                        }
                    }
                } else {
                    showToast("登录异常，userId不存在");
                }
            }
        });

        mBinding.tvSearch.setOnClickListener(v -> {
            getMvpPresenter().searchUser(mBinding.imsEdit.getText().toString());
        });

        mBinding.ivLeft.setOnClickListener(v -> {
            finish();
        });

        mBinding.imsEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getMvpPresenter().searchUser(mBinding.imsEdit.getText().toString());
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onSearchUserSuccess(List<UserFriendsBean> data) {
        if (data != null && data.size() > 0) {
            frindLists = data;
            addFriendAdapter.setNewData(data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != userFriendDaoUtils){
            userFriendDaoUtils.closeConnection();
        }
    }
}
