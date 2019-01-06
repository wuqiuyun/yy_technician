package com.yl.technician.module.im.friends;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.daobean.UserFriendsBean;

import java.util.List;

/**
 * Created by zm on 2018/9/19.
 */
public interface FriendsView extends IBaseView {
    void onFindAllContactsSuccess(List<UserFriendsBean> data);
    void onFindAllContactsFail();
}
