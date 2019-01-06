package com.yl.technician.module.mine.wallet.recharge.pay;

import com.yl.technician.base.mvp.IBaseView;

/**
 * Created by zm on 2018/10/9.
 */
public interface IPayView extends IBaseView {

    //微信充值成功
    void onWxRechargeCashSuccess();

    //支付宝充值成功
    void onALiRechargeCashSuccess(String json);
}
