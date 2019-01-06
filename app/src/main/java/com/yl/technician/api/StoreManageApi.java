package com.yl.technician.api;


import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.StoreManageNexusStyScroolResult;
import com.yl.technician.model.vo.result.StoreManageScopeInfoResult;
import com.yl.technician.model.vo.result.StoreManageScopeResult;
import com.yl.technician.model.vo.result.StylistResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 我的门店管理api
 * <p>
 * Created by lyj on 2018/10/22.
 */

public class StoreManageApi {
    public interface Api {
        /**
         * 我的管理门店入驻/签约理发师（名称、图片）
         *
         * nexus (string, optional): 入驻/签约，0入驻，1签约 ,
         * storeId (integer, optional): 门店id（非本人门店必传） ,
         * userId (integer, optional): 用户id
         * */
        @POST("/stylist-api/storeManage/getNexusStyScrool")
        Observable<StoreManageNexusStyScroolResult> getNexusStyScrool(@Body RequestBody requestBody);

        /**
         * 门店位置信息
         *
         * lat (number, optional): 用户定位纬度 ,
         * lng (number, optional): 用户定位经度 ,
         * storeId (integer, optional): 门店id ,
         * userId (integer, optional): 用户id
         * */
        @POST("/stylist-api/storeManage/getStoreInfo")
        Observable<StoreManageScopeInfoResult> getStoreInfo(@Body RequestBody requestBody);

        /**
         * 门店顾客评价
         *
         * storeId (integer, optional): 门店ID，非本人门店必传 ,
         * userId (integer, optional): 用户id
         * */
        @GET("/stylist-api/storeManage/getStoreScore")
        Observable<StoreManageScopeResult> getStoreScore(@Query("userId") String userId, @Query("storeId") String storeId);

        /**
         * 门店服务范围
         *
         * storeId (integer, optional): 门店ID，非本人门店必传 ,
         * userId (integer, optional): 用户id
         * */
        @GET("/stylist-api/storeManage/getStoreServerScope")
        Observable<StoreManageScopeResult> getStoreServerScope(@Query("userId") String userId, @Query("storeId") String storeId);

        /**
         * 收藏或取消
         */
        @POST("/stylist-api/storeManage/updateCollectionType")
        Observable<BaseResponse> updateCollectionType(@Body RequestBody requestBody);

    }



    private Api mApi;

    public StoreManageApi() {
        this.mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     *  我的管理门店入驻/签约理发师（名称、图片）
     *
     * nexus (string, optional): 入驻/签约，0入驻，1签约 ,
     * storeId (integer, optional): 门店id（非本人门店必传） ,
     * userId (integer, optional): 用户id
     *
     */
    public void getNexusStyScrool(int nexus, String storeId,
                                  YLRxSubscriberHelper<StoreManageNexusStyScroolResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserId());
        params.put("storeId", storeId);
        params.put("nexus", String.valueOf(nexus));

        mApi.getNexusStyScrool(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 门店位置信息
     *
     * lat (number, optional): 用户定位纬度 ,
     * lng (number, optional): 用户定位经度 ,
     * storeId (integer, optional): 门店id ,
     * userId (integer, optional): 用户id
     * */
    public void getStoreInfo(String lat, String lng,String storeId,
                             YLRxSubscriberHelper<StoreManageScopeInfoResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("storeId", storeId);
        params.put("userId", AccountManager.getInstance().getUserId());

        mApi.getStoreInfo(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 门店顾客评价
     *
     * storeId (integer, optional): 门店ID，非本人门店必传 ,
     * userId (integer, optional): 用户id
     * */
    public void getStoreScore(String storeId,YLRxSubscriberHelper<StoreManageScopeResult> subscriberHelper) {

        mApi.getStoreScore(AccountManager.getInstance().getUserId(),storeId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 门店服务范围
     *
     * storeId (integer, optional): 门店ID，非本人门店必传 ,
     * userId (integer, optional): 用户id
     * */
    public void getStoreServerScope(String storeId,YLRxSubscriberHelper<StoreManageScopeResult> subscriberHelper) {

        mApi.getStoreServerScope(AccountManager.getInstance().getUserId(),storeId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 门店收藏或取消
     * 
     * @param isCollection
     * @param storeId
     * @param userId
     */
    public void updateCollectionType(String isCollection, String storeId, String userId, 
                                     YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("isCollection", isCollection);
        params.put("storeId", storeId);
        params.put("userId", userId);

        mApi.updateCollectionType(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }
}
