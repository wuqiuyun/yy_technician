package com.yl.technician.api;

import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.model.vo.result.GetOpusResult;
import com.yl.technician.model.vo.result.GetStylistResult;
import com.yl.technician.model.vo.result.StoreListResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Lizhuo on 2018/11/3.
 */
public class CollectionApi {

    public interface Api{
        //我的收藏 作品
        @POST("/stylist-api/collection/getOpus")
        Observable<GetOpusResult> getOpusCollection(@Body RequestBody requestBody);
        //我的收藏 门店
        @POST("/stylist-api/collection/getStore")
        Observable<StoreListResult> getStoreCollection(@Body RequestBody requestBody);
        //我的收藏 美发师
        @POST("/stylist-api/collection/getStylist")
        Observable<GetStylistResult> getStylistCollection(@Body RequestBody requestBody);
    }

    private Api mApi;

    public CollectionApi()
    {
        this.mApi = YLRequestManager.getRequest(CollectionApi.Api.class);
    }

    /**
     * 收藏——作品列表
     * @param page 页码
     * @param size 每页数量
     * @param userId 用户id
     */
    public void getOpusCollection(int page, int size, String userId, YLRxSubscriberHelper<GetOpusResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));
        params.put("userId", userId);

        mApi.getOpusCollection(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 收藏——门店列表
     * @param lat 经纬度
     * @param lng
     * @param page 页码
     * @param size 每页数
     * @param userId 用户id
     */
    public void getStoreCollection(double lat, double lng, int page, int size, String userId, YLRxSubscriberHelper<StoreListResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("lat", String.valueOf(lat));
        params.put("lng", String.valueOf(lng));
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));
        params.put("userId", userId);

        mApi.getStoreCollection(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 收藏——美发师列表
     * @param page 页码
     * @param size 每页数量
     * @param userId 用户id
     */
    public void getStylistCollection(int page, int size, String userId, YLRxSubscriberHelper<GetStylistResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));
        params.put("userId", userId);

        mApi.getStylistCollection(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

}
