package com.yl.technician.module.im.groups.groupaddfriends;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.daobean.UserFriendsBean;

import java.util.List;

/**
 * Created by Lizhuo on 2018/10/13.
 */
public interface GroupAddFriendsView extends IBaseView {
    void addSingleToGroupSuccess();

    void addBatchToGroupSuccess();

    void onFindAllContactsSuccess(List<UserFriendsBean> data);
}
