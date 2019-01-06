package com.yl.technician.module.im.addfriend;

import android.text.TextUtils;

import com.yl.technician.api.ContactsApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.UserFriendsResult;

/**
 * Created by zhangzz on 2018/9/27
 */
public class AddFriendPresenter extends BasePresenter<AddFriendView> {
    /**
     * 搜索好友
     *
     * @param param
     */
    public void searchUser(String param) {
        if (TextUtils.isEmpty(param)) {
            getMvpView().showToast("搜索内容不能为空");
            return;
        }

        requestAddFriend(param);
    }

    /**
     * 搜索请求
     */
    private void requestAddFriend(String param) {
        new ContactsApi().requestSearchUser(param, new YLRxSubscriberHelper<UserFriendsResult>() {
            @Override
            public void _onNext(UserFriendsResult result) {
                getMvpView().onSearchUserSuccess(result.getData());
            }
        });
    }
}
