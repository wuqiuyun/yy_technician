package com.yl.technician.module.im.groupmembers;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.daobean.GroupUserBean;

import java.util.List;

/**
 * Created by Lizhuo on 2018/10/18.
 */
public interface GroupMembersView extends IBaseView {
    void findGroupAllUserSuccess(List<GroupUserBean> list);

    void deleteMemberSuccess();
}
