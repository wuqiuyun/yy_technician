package com.yl.technician.api;

import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.BannerResult;
import com.yl.technician.model.vo.result.GetStylistOrderStatisticalResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zm on 2018/10/24.
 */
public class StylistApi {

   public interface Api {
       // 获取订单统计
       @GET("/stylist-api/stylist/getStylistOrderStatistical")
       Observable<GetStylistOrderStatisticalResult> getStylistOrderStatistical(@Query("stylistId") String storeId);
       // banner
       @GET("stylist-api/stylistBanner/getBanner")
       Observable<BannerResult> getBanner();
   }

   private Api mApi;

    public StylistApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 获取订单统计
     * @param subscriberHelper
     */
    public void getStylistOrderStatistical(YLRxSubscriberHelper<GetStylistOrderStatisticalResult> subscriberHelper) {
        mApi.getStylistOrderStatistical(AccountManager.getInstance().getStylistId())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     *  banner
     */
    public void getBanner( YLRxSubscriberHelper<BannerResult> subscriberHelper) {
        mApi.getBanner()
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .safeSubscribe(subscriberHelper);
    }
}
