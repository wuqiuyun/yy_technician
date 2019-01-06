package com.yl.technician.api;

import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.model.vo.result.CouponResult;
import com.yl.technician.module.home.coupons.CouponBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by zhangzz on 2018/11/5.
 */
public class StylistCouponApi {

    public interface Api {
        @POST("/stylist-api/stylistCoupon/getAll")
        Observable<CouponResult> getAll(@Body RequestBody requestBody);

        @POST("/stylist-api/stylistCoupon/editPutaway")
        Observable<BaseResponse> editPutaway(@Body RequestBody requestBody);

        @POST("/stylist-api/stylistCoupon/add")
        Observable<BaseResponse> add(@Body RequestBody couponBean);

        @POST("/stylist-api/stylistCoupon/delete")
        Observable<BaseResponse> delete(@Body RequestBody requestBody);
    }

    private Api mApi;

    public StylistCouponApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 查询所有优惠券
     *
     * @param subscriberelper
     */
    public void getAll(String stylistId, YLRxSubscriberHelper<CouponResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("stylistId", stylistId);

        mApi.getAll(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * POST /stylistCoupon/editPutaway 优惠券上下架
     * 传优惠券ID和上下架状态（0下架，1上架）
     *
     * @param subscriberelper
     */
    public void editPutaway(String stylistCouponId, String status, YLRxSubscriberHelper<BaseResponse> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("stylistCouponId", stylistCouponId);
        params.put("status", status);

        mApi.editPutaway(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 添加优惠券
     *
     * @param subscriberelper
     */
    public void add(CouponBean couponBean, YLRxSubscriberHelper<BaseResponse> subscriberelper) {
        mApi.add(new BaseRequestBody(couponBean).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 删除优惠券
     * @param stylistCouponId
     * @param subscriberelper
     */
    public void delete(String stylistCouponId, YLRxSubscriberHelper<BaseResponse> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("stylistCouponId", stylistCouponId);
        mApi.delete(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }
}
