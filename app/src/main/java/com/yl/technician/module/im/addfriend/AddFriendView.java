package com.yl.technician.module.im.addfriend;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.daobean.UserFriendsBean;

import java.util.List;

/**
 * Created by zhangzz on 2018/10/16.
 */
public interface AddFriendView extends IBaseView {
    void onSearchUserSuccess(List<UserFriendsBean> data);
}
