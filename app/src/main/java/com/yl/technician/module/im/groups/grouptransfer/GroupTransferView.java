package com.yl.technician.module.im.groups.grouptransfer;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.daobean.GroupUserBean;

import java.util.List;

/**
 * Created by Lizhuo on 2018/10/15.
 */
public interface GroupTransferView extends IBaseView {
    void findGroupAllUserSuccess(List<GroupUserBean> list);

    void transferSuccess();

    void transferFail();
}
