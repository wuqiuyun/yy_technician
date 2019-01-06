package com.yl.technician.module.im.friendinfo;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.daobean.UserFriendsBean;

/**
 * Created by zm on 2018/9/19.
 */
public interface FriendInfoView extends IBaseView {
    void onDeleteFriendSingleSuccess();

    void onAddToBlackListSuccess();

    void onRemoveFromBlackListSuccess();

    void onGetFriendsSuccess(UserFriendsBean friendInfoBean);

}
