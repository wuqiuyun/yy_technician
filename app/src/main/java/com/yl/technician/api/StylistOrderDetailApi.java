package com.yl.technician.api;

/*
 *   美发师订单明细
 * Create by lvlong on  2018/10/26
 */


import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.AllOrderDetailResult;
import com.yl.technician.model.vo.result.OrderChartResult;
import com.yl.technician.model.vo.result.OrderDetailResult;
import com.yl.technician.model.vo.result.RegisterGapBetweenResult;
import com.yl.technician.model.vo.result.getStylistOrderCountResult;
import com.yl.technician.model.vo.result.getStylistSuccessOrdersResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public class StylistOrderDetailApi {

    private interface Api {

        @GET("/stylist-api/stylistOrderDetail/findOrderDetail")
        Observable<OrderDetailResult> findOrderDetail(@QueryMap Map<String, String> params);

        @GET("/stylist-api/stylistOrderDetail/getRegisterGapBetween")
        Observable<RegisterGapBetweenResult> getRegisterGapBetween(@QueryMap Map<String, String> params);

    }

    private Api mApi;

    public StylistOrderDetailApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    //订单明细
    public void findOrderDetail(int type,int pageNo,int pageSize, YLRxSubscriberHelper<OrderDetailResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserId());
        params.put("type", String.valueOf(type));
        params.put("pageNo", String.valueOf(pageNo));
        params.put("pageSize", String.valueOf(pageSize));
        mApi.findOrderDetail(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }
    //注册奖励差距
    public void getRegisterGapBetween(YLRxSubscriberHelper<RegisterGapBetweenResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserId());
        mApi.getRegisterGapBetween(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

}
