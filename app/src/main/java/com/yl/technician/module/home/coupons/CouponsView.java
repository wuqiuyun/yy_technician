package com.yl.technician.module.home.coupons;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.result.CouponResult;


/*
 * Create by zhangzz on  2018/11/5
 */

public interface CouponsView extends IBaseView {


    void editPutawaySuccess();

    void getAllSuccess(CouponResult couponResult);

    void deleteCouponSuccess(int position);
}
