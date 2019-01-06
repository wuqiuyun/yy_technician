package com.yl.technician.module.mine.pplarz.qrcode;

import com.yl.technician.api.BasicSettingApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;

/**
 * Created by zm on 2018/12/29.
 */
public class InviteQRCodePresenter extends BasePresenter<InviteQRCodeView> {

    public void getWXShareQrCode(String inviteCode) {
        new BasicSettingApi().getWXShareQrCode(inviteCode, new YLRxSubscriberHelper<BaseResponse<String>>() {
            @Override
            public void _onNext(BaseResponse<String> result) {
                getMvpView().setShareQrCodeUrl(result.getData());
            }
        });
    }
}
