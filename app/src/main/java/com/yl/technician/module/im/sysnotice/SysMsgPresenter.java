package com.yl.technician.module.im.sysnotice;

import android.text.TextUtils;

import com.yl.technician.api.ContactsApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.SysMsgResult;

/**
 * Created by zm on 2018/9/19.
 */
public class SysMsgPresenter extends BasePresenter<SysMsgView> {
    /**
     * 查询互动消息列表
     *
     * @param userId
     */
//    public void findAddFriend(String userId) {
//        if (TextUtils.isEmpty(userId)) {
//            getMvpView().showToast("userId不能为空");
//            return;
//        }
//
//        requestFindAddFriend(userId);
//    }
//
    /**
     * 查询互动消息列表
     */
//    private void requestFindAddFriend(String userId) {
//        new ContactsApi().requestFindAddFriend(userId, new YLRxSubscriberHelper<SysMsgResult>() {
//            @Override
//            public void _onNext(SysMsgResult result) {
//                getMvpView().onFindAddFriendSuccess(result.getData());
//            }
//        });
//    }

    public void receiveAddFriend(String id, String status) {
        if (TextUtils.isEmpty(id)) {
            getMvpView().showToast("id不能为空");
            return;
        }


        if (TextUtils.isEmpty(status)) {
            getMvpView().showToast("status不能为空");
            return;
        }

        requestReceiveAddFriend(id, status);
    }

    /**
     * 接收好友添加申请
     */
    private void requestReceiveAddFriend(String id, String status) {
        new ContactsApi().requestReceiveAddFriend(id, status, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse response) {
                getMvpView().onReceiveAddFriendSuccess();
            }
        });
    }

    /**
     * 接收添加群组申请POST /group/receiveAddGroup
     */
    public void requestReceiveAddGroup(String id, String status) {
        if (TextUtils.isEmpty(id)) {
            getMvpView().showToast("id不能为空");
            return;
        }

        if (TextUtils.isEmpty(status)) {
            getMvpView().showToast("status不能为空");
            return;
        }

        new ContactsApi().requestReceiveAddGroup(id, status, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse response) {
                getMvpView().onReceiveAddGroupSuccess();
            }
        });
    }
}
