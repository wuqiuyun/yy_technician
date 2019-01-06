package com.yl.technician.api;

import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.model.vo.result.CoinResult;
import com.yl.technician.model.vo.result.IncomeResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * 美发师收入明细
 * Created by wqy on 2018/12/20.
 */

public class StylistIncomeDetailsApi {
    public interface Api {
        // 收入余额明细
        @GET("/stylist-api/stylistIncomeDetails/getAssetDetailList")
        Observable<IncomeResult> getAssetDetailList(@QueryMap Map<String, String> params);

        //收入积分明细
        @GET("/stylist-api/stylistIncomeDetails/getCoinWalletDetailList")
        Observable<IncomeResult> getCoinWalletDetailList(@QueryMap Map<String, String> params);
    }

    private Api mApi;

    public StylistIncomeDetailsApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 收入余额明细 (根据时间查询)
     *
     * @param date             时间格式：201812
     * @param userId
     * @param pageNo
     * @param pageSize
     * @param subscriberHelper
     */
    public void getAssetDetailList(String date, String userId, int pageNo, int pageSize, YLRxSubscriberHelper<IncomeResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("date", date);
        params.put("userId", userId);
        params.put("pageNo", String.valueOf(pageNo));
        params.put("pageSize", String.valueOf(pageSize));
        mApi.getAssetDetailList(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 收入积分明细 (根据时间查询)
     *
     * @param date             时间格式：201812
     * @param userId
     * @param pageNo
     * @param pageSize
     * @param subscriberHelper
     */
    public void getCoinWalletDetailList(String date, String userId, int pageNo, int pageSize, YLRxSubscriberHelper<IncomeResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("date", date);
        params.put("userId", userId);
        params.put("pageNo", String.valueOf(pageNo));
        params.put("pageSize", String.valueOf(pageSize));
        mApi.getCoinWalletDetailList(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }
}
