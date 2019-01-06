package com.yl.technician.module.im.sysnotice;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.SysMsgBean;

import java.util.List;

/**
 * Created by zm on 2018/9/19.
 */
public interface SysMsgView extends IBaseView {
//    void onFindAddFriendSuccess(List<SysMsgBean> data);

    void onReceiveAddFriendSuccess();

    void onReceiveAddGroupSuccess();
}
