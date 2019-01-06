package com.yl.technician.module.im.transfer;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.CashInfoBean;
import com.yl.technician.model.vo.result.RedBagSendResult;

/**
 * Created by zhangzz on 2018/11/6.
 */
public interface TransferAccountsView extends IBaseView {
    void requestSuccess(RedBagSendResult redBagSendResult);
    void checkPasswordSuccess();
    //获取钱包余额
    void onGetCashInfoSuccess(CashInfoBean bean);
}
