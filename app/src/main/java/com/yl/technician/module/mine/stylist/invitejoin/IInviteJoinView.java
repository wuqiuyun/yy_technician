package com.yl.technician.module.mine.stylist.invitejoin;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.result.SendMsgResult;

/**
 * Created by zm on 2018/10/11.
 */
public interface IInviteJoinView extends IBaseView {

    /**
     * 签约/解约成功
     *
     * @param apply
     */
    void onSucceed(String apply);

    void onFail(String msg);

    void sendMsg(SendMsgResult sendMsgResult, int type);
}
