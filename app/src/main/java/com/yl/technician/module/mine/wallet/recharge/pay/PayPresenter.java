package com.yl.technician.module.mine.wallet.recharge.pay;

import com.yl.technician.api.WalletInfoApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;


/**
 * Created by zm on 2018/10/9.
 */
public class PayPresenter extends BasePresenter<IPayView> {

    /**
     * 微信充值
     *
     * code (string, optional):
     * money (integer, optional): 充值金额
     * */
    public void wxRechargeCash(String code,double money) {
        new WalletInfoApi().wxRechargeCash(code, money, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                // callback
                getMvpView().onWxRechargeCashSuccess();
            }
        });
    }

    /**
     * 支付宝充值
     *
     * money (integer, optional): 充值金额
     * */
    public void aLiRechargeCash(double money) {
        new WalletInfoApi().aLiRechargeCash( money, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                // callback
                getMvpView().onALiRechargeCashSuccess(baseResponse.getData()+"");
            }
        });
    }

    /**
     * 支付宝充值
     *
     * money (integer, optional): 充值金额
     * */
    public void aLiRechargeCashPost(String url) {
        new WalletInfoApi().aLiRechargeCashPost(url, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                // callback
                getMvpView().onALiRechargeCashSuccess(baseResponse.getData()+"");
            }
        });
    }

}