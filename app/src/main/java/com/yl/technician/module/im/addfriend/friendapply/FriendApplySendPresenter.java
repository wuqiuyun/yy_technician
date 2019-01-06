package com.yl.technician.module.im.addfriend.friendapply;

import android.text.TextUtils;

import com.yl.technician.api.ContactsApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.AddFriendResult;

/**
 * Created by zm on 2018/9/19.
 */
public class FriendApplySendPresenter extends BasePresenter<FriendApplySendView> {

    /**
     * 添加好友
     *
     * @param userId
     * @param friendId
     * @param remarks
     */
    public void addFriend(String userId, String requestNickname, String friendId, String remarks) {
        if (TextUtils.isEmpty(userId)) {
            getMvpView().showToast("userId不能为空");
            return;
        }
        if (TextUtils.isEmpty(friendId)) {
            getMvpView().showToast("friendId不能为空");
            return;
        }
        requestAddFriend(userId, requestNickname, friendId, remarks);
    }

    /**
     * 添加请求
     *
     * @param userId
     * @param friendId
     * @param remarks
     */
    private void requestAddFriend(String userId, String requestNickname, String friendId, String remarks) {
        new ContactsApi().requestAddFriend(userId, requestNickname, friendId, remarks, new YLRxSubscriberHelper<AddFriendResult>() {
            @Override
            public void _onNext(AddFriendResult result) {
//                result.getData();
                getMvpView().onAddFriendSuccess(result);
            }
        });
    }
}
