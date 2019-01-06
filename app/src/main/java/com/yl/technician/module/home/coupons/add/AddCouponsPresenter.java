package com.yl.technician.module.home.coupons.add;

import android.app.Activity;

import com.yl.technician.api.StylistCouponApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.module.home.coupons.CouponBean;


/*
 * Create by lvlong on  2018/10/29
 */

public class AddCouponsPresenter extends BasePresenter<AddCouponsView> {
    public void requestAddCoupon(CouponBean couponBean, Activity activity) {
        if (couponBean == null) {
            getMvpView().showToast("添加不能为空");
            return;
        }

        new StylistCouponApi().add(couponBean, new YLRxSubscriberHelper<BaseResponse>(activity, true) {
            @Override
            public void _onNext(BaseResponse result) {
                getMvpView().showToast("发布成功.");
                getMvpView().addSuccess();
            }
        });
    }
}
