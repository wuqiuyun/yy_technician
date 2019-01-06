package com.yl.technician.api;


import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.GetOrderListResult;
import com.yl.technician.model.vo.result.GetOrderResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by zm on 2018/10/27.
 */
public class OrderApi {

    private interface Api {
        // 门店订单查询
        @GET("/stylist-api/stylistOrder/findOrderPage")
        Observable<GetOrderListResult> getOrderPage(@QueryMap Map<String, Object> params);

        // 订单详情查询
        @GET("/stylist-api/stylistOrder/findOrder")
        Observable<GetOrderResult> getOrder(@Query("orderId") String stylistId);

        // 完成订单
        @GET("/stylist-api/stylistOrder/completeOrder")
        Observable<BaseResponse> completeOrder(@Query("orderId") String orderId);

        // 取消订单
        @GET("/stylist-api/stylistOrder/cancelOrder")
        Observable<BaseResponse> cancelOrder(@Query("orderId") String orderId);

        // 开始订单
        @POST("/stylist-api/stylistOrder/startOrder")
        Observable<BaseResponse> startOrder(@Body RequestBody requestBody);

        // 拒绝接单
        @GET("/stylist-api/stylistOrder/refuseOrder")
        Observable<BaseResponse> refuseOrder(@Query("orderId")  String orderId);

        // 接受订单
        @GET("/stylist-api/stylistOrder/acceptOrder")
        Observable<BaseResponse> acceptOrder(@Query("orderId") String orderId);

        // 美发师申请加价
        @POST("/stylist-api/stylistOrder/addMoneyRequest")
        Observable<BaseResponse> addMoneyRequest(@Body RequestBody requestBody);

        // 取消申请加价
        @GET("/stylist-api/stylistOrder/cancelAddmoneyRequest")
        Observable<BaseResponse> cancelAddmoneyRequest(@Query("orderId") String orderId);

        // 同意用户申请取消订单
        @GET("/stylist-api/stylistOrder/agreeCancelOrder")
        Observable<BaseResponse> agreeCancelOrder(@Query("orderId") String orderId, @Query("status") String status);

        // 发送提醒
        @GET("/stylist-api/stylistOrder/remindReviews")
        Observable<BaseResponse> remindReviews(@Query("orderId") String orderId, @Query("status") String status);


    }

    private Api mApi;

    public OrderApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 发送提醒
     * @param orderId
     * @param status 1 发送加价提醒 2 发送评价邀请
     */
    public void remindReviews(String orderId, String status, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        mApi.remindReviews(orderId, status)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 取消申请加价
     * @param orderId
     */
    public void cancelAddmoneyRequest(String orderId, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        mApi.cancelAddmoneyRequest(orderId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 同意用户申请取消订单
     * @param orderId
     * @param status 1 同意 2 违约退款
     */
    public void agreeCancelOrder(String orderId, String status, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        mApi.agreeCancelOrder(orderId, status)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 加价
     * @param orderId
     */
    public void addMoneyRequest(String orderId, String addmoney, String adddesc, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("addmoney", addmoney);
        params.put("adddesc", adddesc);
        mApi.addMoneyRequest(new BaseRequestBody<>(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }


    /**
     * 接受订单
     * @param orderId
     */
    public void acceptOrder(String orderId, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        mApi.acceptOrder(orderId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 获取订详情
     * @param orderId
     */
    public void getOrder(String orderId, YLRxSubscriberHelper<GetOrderResult> subscriberHelper) {
        mApi.getOrder(orderId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     *
     * @param status
     * @param page
     * @param size
     * @param subscriberHelper
     */
    public void getOrderPage(int status, int page, int size, YLRxSubscriberHelper<GetOrderListResult> subscriberHelper) {
        Map<String, Object> params = new HashMap<>();
        params.put("status", status);
        params.put("pageNo", page);
        params.put("pageSize", size);
        params.put("stylistId", AccountManager.getInstance().getStylistId());
        mApi.getOrderPage(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 完成订单
     * @param orderId
     * @param subscriberHelper
     */
    public void completeOrder(String orderId, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        mApi.completeOrder(orderId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .safeSubscribe(subscriberHelper);
    }

    /**
     * 取消订单
     * @param orderId
     * @param subscriberHelper
     */
    public void cancelOrder(String orderId, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        mApi.cancelOrder(orderId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .safeSubscribe(subscriberHelper);
    }

    /**
     * 开始订单
     * @param orderId
     * @param stylistId
     */
    public void startOrder(String orderId, String stylistId,YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("stylistId", stylistId);
        mApi.startOrder(new BaseRequestBody<>(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .safeSubscribe(subscriberHelper);
    }

    /**
     * 拒绝
     * @param orderId
     * @param subscriberHelper
     */
    public void refuseOrder(String orderId, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        mApi.refuseOrder(orderId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .safeSubscribe(subscriberHelper);
    }
}
