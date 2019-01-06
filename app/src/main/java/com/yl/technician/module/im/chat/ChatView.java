package com.yl.technician.module.im.chat;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.daobean.GroupUserBean;
import com.yl.technician.model.vo.result.RedBagSendResult;

import java.util.List;

/**
 * Created by zm on 2018/9/19.
 */
public interface ChatView extends IBaseView {
    void findRedBagSuccess(RedBagSendResult redBagSendResult);
    void receiveRedBagSuc(RedBagSendResult redBagSendResult);
    void findTransferSuccess(RedBagSendResult redBagSendResult);
    void receiveTransferSuc(RedBagSendResult redBagSendResult);
    //获取账户支付密码信息成功
    void oninitPayWordInfoSuccess(String json);
}
