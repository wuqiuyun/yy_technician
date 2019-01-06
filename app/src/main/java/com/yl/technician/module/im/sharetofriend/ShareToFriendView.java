package com.yl.technician.module.im.sharetofriend;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.daobean.UserFriendsBean;

import java.util.List;

/**
 * Created by zm on 2018/9/19.
 */
public interface ShareToFriendView extends IBaseView {
    void onFindAllContactsSuccess(List<UserFriendsBean> data);
    void onFindAllContactsFail();
}
