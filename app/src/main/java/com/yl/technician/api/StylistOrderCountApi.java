package com.yl.technician.api;

import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.model.vo.result.GetEvaluateResult;
import com.yl.technician.model.vo.result.GetOrderCommentResult;
import com.yl.technician.model.vo.result.StoreScopeResult;
import com.yl.technician.model.vo.result.StylistCommentListResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zm on 2018/11/6.
 */
public class StylistOrderCountApi {

    private interface Api {
        @GET("/stylist-api/stylistOrderCount/getEvaluate")
        Observable<GetEvaluateResult> getEvaluate(@Query("stylistId") String stylistId);

        // 查询评论
        @GET("/stylist-api/stylistOrderCount/getOrderComment")
        Observable<GetOrderCommentResult> getOrderComment(@Query("orderId") String orderId);

        // 获取门店评分
        @GET("/stylist-api/storeManage/getStoreScore")
        Observable<StoreScopeResult> getStoreScore(@Query("userId") String userId , @Query("storeId") String storeId );

        // 查看门店评论
        @GET("/stylist-api/storeComment/getStoreCommentList")
        Observable<StylistCommentListResult> getStoreCommentList(@Query("storeId") String storeId , @Query("page") int page , @Query("size") int size );

    }

    private Api mApi;

    public StylistOrderCountApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    public void getEvaluate(String stylistId,YLRxSubscriberHelper<GetEvaluateResult> subscriberHelper) {
        mApi.getEvaluate(stylistId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 查询评价
     * @param orderId
     * @param subscriberHelper
     */
    public void getOrderComment(String orderId, YLRxSubscriberHelper<GetOrderCommentResult> subscriberHelper) {
        mApi.getOrderComment(orderId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    //获取门店评分
    public void getStoreScore(String userId , String storeId , YLRxSubscriberHelper<StoreScopeResult> subscriberHelper){
        mApi.getStoreScore(userId,storeId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    //查看门店评论
    public void getStoreCommentList(String storeId , int page , int size , YLRxSubscriberHelper<StylistCommentListResult> subscriberHelper){
        mApi.getStoreCommentList(storeId , page , size)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }
}
