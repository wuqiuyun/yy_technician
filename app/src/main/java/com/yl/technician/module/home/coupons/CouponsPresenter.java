package com.yl.technician.module.home.coupons;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.yl.technician.api.StylistCouponApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.CouponResult;


/*
 * Create by lvlong on  2018/10/29
 */

public class CouponsPresenter extends BasePresenter<CouponsView> {
    public void getAllCoupons(String stylistId, Activity activity) {
        if (TextUtils.isEmpty(stylistId)) {
            getMvpView().showToast("stylistId不能为空");
            return;
        }

        new StylistCouponApi().getAll(stylistId, new YLRxSubscriberHelper<CouponResult>(activity, true) {
            @Override
            public void _onNext(CouponResult result) {
                getMvpView().getAllSuccess(result);
            }
        });
    }




    public void requestEditPutaway(String stylistCouponId, String status, Activity activity) {
        if (TextUtils.isEmpty(stylistCouponId)) {
            getMvpView().showToast("stylistCouponId不能为空");
            return;
        }

        if (TextUtils.isEmpty(status)) {
            getMvpView().showToast("status不能为空");
            return;
        }

        new StylistCouponApi().editPutaway(stylistCouponId, status, new YLRxSubscriberHelper<BaseResponse>(activity, true) {
            @Override
            public void _onNext(BaseResponse result) {
                getMvpView().editPutawaySuccess();
            }
        });
    }

    /**
     * 删除优惠券
     * @param stylistCouponId
     */
    public void deleteCoupon(Context context, String stylistCouponId, int position) {
        new StylistCouponApi().delete(stylistCouponId, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().deleteCouponSuccess(position);
            }
        });
    }
}
