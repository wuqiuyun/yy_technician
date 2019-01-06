package com.yl.technician.module.im.groups.groupaddfriends;

import android.app.Activity;
import android.text.TextUtils;

import com.yl.technician.api.ContactsApi;
import com.yl.technician.api.GroupApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.model.vo.bean.GroupAddReqBean;
import com.yl.technician.model.vo.result.UserFriendsResult;

import java.util.List;

/**
 * Created by Lizhuo on 2018/10/13.
 */
public class GroupAddFriendsPresenter extends BasePresenter<GroupAddFriendsView> {
    /**
     * 单个邀请入群
     */
    public void addSingleToGroup(String groupId, String imgroup, String imusername, String userId) {
        if (TextUtils.isEmpty(groupId)) {
            ToastUtils.shortToast("群组id不能为空");
            return;
        }
        if (TextUtils.isEmpty(imgroup)) {
            ToastUtils.shortToast("imgroup不能为空");
            return;
        }
        if (TextUtils.isEmpty(imusername)) {
            ToastUtils.shortToast("imusername不能为空");
            return;
        }
        if (TextUtils.isEmpty(userId)) {
            ToastUtils.shortToast("userId不能为空");
            return;
        }
        new GroupApi().addSingleToGroup(groupId, imgroup, imusername, userId, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().addSingleToGroupSuccess();
            }
        });
    }

    /**
     * 批量邀请群
     */
    public void addBatchToGroup(String groupId, String imgroup, List<GroupAddReqBean> users) {
        if (TextUtils.isEmpty(groupId)) {
            ToastUtils.shortToast("群组id不能为空");
            return;
        }
        if (TextUtils.isEmpty(imgroup)) {
            ToastUtils.shortToast("imgroup不能为空");
            return;
        }
        if (users==null) {
            ToastUtils.shortToast("users不能为空");
            return;
        }
        new GroupApi().addBatchToGroup(groupId, imgroup, users, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().addBatchToGroupSuccess();
            }
        });
    }

    /**
     * 查询用户的所有好友
     *
     * @param userId
     */
    public void findAllContacts(String userId, Activity activity) {
        if (TextUtils.isEmpty(userId)) {
            getMvpView().showToast("userId不能为空");
            return;
        }

        requestFindAllContacts(userId, activity);
    }

    /**
     * 查询用户的所有好友请求
     */
    private void requestFindAllContacts(String param, Activity activity) {
        new ContactsApi().requestFindContacts(param, new YLRxSubscriberHelper<UserFriendsResult>(activity, true) {
            @Override
            public void _onNext(UserFriendsResult result) {
                getMvpView().onFindAllContactsSuccess(result.getData());
            }
        });
    }
}
