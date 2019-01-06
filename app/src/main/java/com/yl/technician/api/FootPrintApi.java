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
public class FootPrintApi {
    public interface Api{
        //我的足迹 作品
        @POST("/stylist-api/foot/getOpus")
        Observable<GetOpusResult> getOpusFoot(@Body RequestBody requestBody);
        //我的足迹 门店
        @POST("/stylist-api/foot/getStore")
        Observable<StoreListResult> getStoreFoot(@Body RequestBody requestBody);
        //我的足迹 美发师
        @POST("/stylist-api/foot/getStylist")
        Observable<GetStylistResult> getStylistFoot(@Body RequestBody requestBody);
    }

    private Api mApi;

    public FootPrintApi()
    {
        this.mApi = YLRequestManager.getRequest(FootPrintApi.Api.class);
    }

    /**
     * 足迹——作品列表
     * @param page 页码
     * @param size 每页数量
     * @param userId 用户id
     */
    public void getOpusFoot(int page, int size, String userId, YLRxSubscriberHelper<GetOpusResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));
        params.put("userId", userId);

        mApi.getOpusFoot(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 足迹——门店列表
     * @param lat 经纬度
     * @param lng
     * @param page 页码
     * @param size 每页数
     * @param userId 用户id
     */
    public void getStoreFoot(double lat, double lng, int page, int size, String userId, YLRxSubscriberHelper<StoreListResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("lat", String.valueOf(lat));
        params.put("lng", String.valueOf(lng));
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));
        params.put("userId", userId);

        mApi.getStoreFoot(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 足迹——美发师列表
     * @param page 页码
     * @param size 每页数量
     * @param userId 用户id
     */
    public void getStylistFoot(int page, int size, String userId, YLRxSubscriberHelper<GetStylistResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));
        params.put("userId", userId);

        mApi.getStylistFoot(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

}
