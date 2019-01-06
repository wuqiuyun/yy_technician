package com.yl.technician.api;

import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.model.vo.requestbody.SortSearchRequesetBody;
import com.yl.technician.model.vo.result.NexuStatusResult;
import com.yl.technician.model.vo.result.ServerTypeResult;
import com.yl.technician.model.vo.result.StoreListResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zm on 2018/10/15.
 */
public class JoinStoreApi {

    public interface Api {
        //查询当前门店，技师申请状态
        @GET("/stylist-api/joinStore/getNexuStatus")
        Observable<NexuStatusResult> getNexuStatus(@Query("userId")String userid, @Query("storeId")String storeId);
        //我入驻/签约的门店
        @POST("/stylist-api/joinStore/myStore")
        Observable<StoreListResult> myStore(@Body RequestBody requestBody);
        //入驻/签约门店
        @POST("/stylist-api/joinStore/nexusStore")
        Observable<BaseResponse> nexusStore(@Body RequestBody requestBody);
        //解约
        @GET("/stylist-api/joinStore/relieve")
        Observable<BaseResponse> relieve(@Query("stylistId")String stylistId,@Query("storeId")String storeId);
        //搜索门店
        @GET("/stylist-api/joinStore/search")
        Observable<StoreListResult> search(@Query("search")String search,@Query("lng")String lng,@Query("lat")String lat,@Query("userId")String userId);
        //排序查询
        @POST("/stylist-api/joinStore/sortSearch")
        Observable<StoreListResult> sortSearch(@Body RequestBody requestBody);


    }

    private Api mApi;

    public JoinStoreApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 查询当前门店，技师申请状态
     */
    public void getNexuStatus(String userId, String storeId, YLRxSubscriberHelper<NexuStatusResult> subscriberelper) {
        mApi.getNexuStatus(userId,storeId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }
    /**
     * 我入驻/签约的门店
     */
    public void myStore(String lat,String lng,String userId, YLRxSubscriberHelper<StoreListResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("userId", userId);
        mApi.myStore(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }
    /**
     * 入驻/签约门店
     * @param apply 0入驻，1签约
     */
    public void nexusStore(String apply,String storeId,String stylistId,String userId, YLRxSubscriberHelper<BaseResponse> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("apply", apply);
        params.put("storeId", storeId);
        params.put("stylistId", stylistId);
        params.put("userId", userId);
        mApi.nexusStore(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }
    /**
     * 解约
     */
    public void relieve(String stylistId ,String storeId, YLRxSubscriberHelper<BaseResponse> subscriberelper) {
        mApi.relieve(stylistId,storeId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }
    /**
     * 搜索门店
     */
    public void search(String search,String lng,String lat,String userId, YLRxSubscriberHelper<StoreListResult> subscriberelper) {

        mApi.search(search,lng,lat,userId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }
    /**
     * 排序查询
     */
    public void sortSearch(SortSearchRequesetBody sortSearchRequesetBody, YLRxSubscriberHelper<StoreListResult> subscriberelper) {
        mApi.sortSearch(new BaseRequestBody(sortSearchRequesetBody).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }
}
