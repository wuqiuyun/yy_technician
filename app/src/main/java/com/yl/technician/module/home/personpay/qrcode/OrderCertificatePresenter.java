package com.yl.technician.module.home.personpay.qrcode;

import android.content.Context;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.JoinStoreApi;
import com.yl.technician.api.PersonPayApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.StoreListResult;

/**
 * Created by lyj 2018/11/28.
 */

public class OrderCertificatePresenter extends BasePresenter<OrderCertificateView>{

    //获取微信二维码
    public void getQrCode(String storeId, String stylistId, String tradePayAmount, Context context) {
        new PersonPayApi().getQrCode( storeId, stylistId, tradePayAmount,new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse result) {
                getMvpView().getQCodeSuccess(result.getData());
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if (getMvpView()!=null)getMvpView().getStoreListFail();
            }
        });
    }
}
