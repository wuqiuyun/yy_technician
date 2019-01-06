package com.yl.technician.api;

/*
    获取发长,脸型接口
 * Create by lvlong on  2018/11/5
 */

import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.StylistOpusTypeResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public class StylistOpusTypeApi {

    public interface Api {

        // 获取脸型
        @GET("/stylist-api/opusType/getFeature")
        Observable<StylistOpusTypeResult> getFeature();

        // 获取发长
        @GET("/stylist-api/opusType/getHairstyle")
        Observable<StylistOpusTypeResult> getHairstyle();

        //上传作品
        @POST("/stylist-api/stylistOpus/save")
        Observable<BaseResponse>save(@Body RequestBody body);

    }

    private StylistOpusTypeApi.Api mApi;

    public StylistOpusTypeApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 获取脸型
     */
    public void getFeature(YLRxSubscriberHelper<StylistOpusTypeResult> subscriberHelper) {
        mApi.getFeature()
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 获取发长
     */
    public void getHairstyle(YLRxSubscriberHelper<StylistOpusTypeResult> subscriberHelper) {
        mApi.getHairstyle()
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    //上传作品
    public void save(String describe , int featureId , int hairstyleId, List<String> opusPaths , YLRxSubscriberHelper<BaseResponse> subscriberHelper){

        Map<String, Object> params = new HashMap<>();
        params.put("stylistId" , AccountManager.getInstance().getStylistId());
        params.put("describe" ,describe);
        params.put("featureId" , String.valueOf(featureId));
        params.put("hairstyleId" , String.valueOf(hairstyleId));
        params.put("opusPaths" , opusPaths);

        mApi.save(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);

    }
}
