package com.yl.technician.api;

/*
 *   订单管理
 * Create by lvlong on  2018/10/26
 */


import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.AllOrderDetailResult;
import com.yl.technician.model.vo.result.OrderChartResult;
import com.yl.technician.model.vo.result.getStylistOrderCountResult;
import com.yl.technician.model.vo.result.getStylistSuccessOrdersResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public class StylistOrderApi {

    private interface Api {
        @GET("/stylist-api/stylistOrderCount/getStylistOrderCount")
        Observable<getStylistOrderCountResult> getStylistOrderCount(@QueryMap Map<String, String> params);

        @GET("/stylist-api/stylistOrderCount/getStylistSuccessOrders")
        Observable<getStylistSuccessOrdersResult> getStylistSuccessOrders(@QueryMap Map<String, String> params);

        //美发师订单明细查询
        @GET("/stylist-api/stylistOrder/getListDetail")
        Observable<AllOrderDetailResult> getListDetail(@QueryMap Map<String, String> params);

        //美发师时间分段笔数统计
        @GET("/stylist-api/stylistOrderCount/getStylistTimeSliceOrder")
        Observable<OrderChartResult> getStylistTimeSliceOrder(@QueryMap Map<String, String> params);
    }

    private Api mApi;

    public StylistOrderApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    //所有订单信息
    public void getStylistOrderCount(String type, YLRxSubscriberHelper<getStylistOrderCountResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("stylistId", AccountManager.getInstance().getStylistId());
        mApi.getStylistOrderCount(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    //完成订单信息
    public void getStylistSuccessOrders(String startTime, String endTime, YLRxSubscriberHelper<getStylistSuccessOrdersResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("stylistId", AccountManager.getInstance().getStylistId());
        mApi.getStylistSuccessOrders(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);

    }

    // 美发师订单明细查询
    public void getListDetail(int pageNo, int pageSize, YLRxSubscriberHelper<AllOrderDetailResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("pageNo", String.valueOf(pageNo));
        params.put("pageSize", String.valueOf(pageSize));
        params.put("stylistId", AccountManager.getInstance().getStylistId());
        mApi.getListDetail(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);

    }

    //美发师时间分段笔数统计
    public void getStylistTimeSliceOrder(String type, YLRxSubscriberHelper<OrderChartResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        params.put("stylistId", AccountManager.getInstance().getStylistId());
        mApi.getStylistTimeSliceOrder(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);

    }

}
