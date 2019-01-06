package com.yl.technician.module.mine.pplarz.banding;

import android.text.TextUtils;

import com.yl.technician.api.RecomUserApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;

/**
 * Created by Lizhuo on 2018/10/29.
 */
public class BandingRefereesPresenter extends BasePresenter<IBandingRefereesView> {
    public void bindInviteCode(String code) {
        if (TextUtils.isEmpty(code)) {
            getMvpView().showToast("推荐码不可为空");
            return;
        }
        
        new RecomUserApi().bindInviteCode(code, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().bindingSuc();
            }
        });
    }
}
