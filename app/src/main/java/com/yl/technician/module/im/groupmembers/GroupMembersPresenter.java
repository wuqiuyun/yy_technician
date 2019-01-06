package com.yl.technician.module.im.groupmembers;

import android.app.Activity;
import android.text.TextUtils;

import com.yl.technician.api.GroupApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.findGroupAllUserResult;

/**
 * Created by Lizhuo on 2018/10/18.
 */
public class GroupMembersPresenter extends BasePresenter<GroupMembersView> {
    /**
     * 获取该群中所有用户
     */
    public void findGroupAllUser(String groupId, Activity activity) {
        if (TextUtils.isEmpty(groupId)) {
            getMvpView().showToast("groupId不能为空");
            return;
        }

        new GroupApi().findGroupAllUser(groupId, new YLRxSubscriberHelper<findGroupAllUserResult>(activity, true) {
            @Override
            public void _onNext(findGroupAllUserResult result) {
                getMvpView().findGroupAllUserSuccess(result.getData());
            }
        });
    }

    public void removeSingleFromGroup(String groupId, String imgroup, String imusername, String id, Activity activity) {
        if (TextUtils.isEmpty(groupId)) {
            getMvpView().showToast("groupId不能为空");
            return;
        }
        if (TextUtils.isEmpty(imgroup)) {
            getMvpView().showToast("imgroup不能为空");
            return;
        }
        if (TextUtils.isEmpty(imusername)) {
            getMvpView().showToast("imusername不能为空");
            return;
        }
        if (TextUtils.isEmpty(id)) {
            getMvpView().showToast("id不能为空");
            return;
        }

        new GroupApi().removeSingleFromGroup(groupId, imgroup, imusername, id, new YLRxSubscriberHelper<BaseResponse>(activity, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().deleteMemberSuccess();
            }
        });
    }
}
