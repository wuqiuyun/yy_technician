package com.yl.technician.api;

import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.requestbody.UpdateAddressRequestBody;
import com.yl.technician.model.vo.result.StylistCenterInfoResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Lizhuo on 2018/10/30.
 * 美发师个人中心设置
 */
public class StylistCenterInfoApi {
    
    public interface Api {
        @GET("/stylist-api/stylistCenterInfo/getStylistCenterInfo")
        Observable<StylistCenterInfoResult> getStylistCenterInfo(@QueryMap Map<String, String> map);

        @POST("/stylist-api/stylistCenterInfo/updateBirthday")
        Observable<BaseResponse> updateBirthday(@Body RequestBody requestBody);

        @POST("/stylist-api/stylistCenterInfo/updateHeadImg")
        Observable<BaseResponse> updateHeadImg(@Body RequestBody requestBody);

        @POST("/stylist-api/stylistCenterInfo/updateHobby")
        Observable<BaseResponse> updateHobby(@Body RequestBody requestBody);

        @POST("/stylist-api/stylistCenterInfo/updateIntroduction")
        Observable<BaseResponse> updateIntroduction(@Body RequestBody requestBody);

        @POST("/stylist-api/stylistCenterInfo/updateStylistName")
        Observable<BaseResponse> updateStylistName(@Body RequestBody requestBody);

        @POST("/stylist-api/stylistCenterInfo/updateLocation")
        Observable<BaseResponse> updateLocation(@Body RequestBody requestBody);

        @POST("/stylist-api/stylistCenterInfo/updatePosition")
        Observable<BaseResponse> updatePosition(@Body RequestBody requestBody);


        @POST("/stylist-api/stylistCenterInfo/updatePortrait")
        Observable<BaseResponse> updatePortrait(@Body RequestBody requestBody);


    }
    
    private Api mApi;

    public StylistCenterInfoApi() {
        mApi = YLRequestManager.getRequest(StylistCenterInfoApi.Api.class);
    }

    /**
     * 展示美发师个人资料
     * id 用户id
     */
    public void getStylistCenterInfo(String userId, YLRxSubscriberHelper<StylistCenterInfoResult> subscriberHelper){
        Map<String, String> params = new HashMap<>();
        params.put("id", userId);

        mApi.getStylistCenterInfo(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 修改生日
     * @param brithday "2018/01/01"
     */
    public void updateBirthday(String userId,String brithday, YLRxSubscriberHelper<BaseResponse> subscriberHelper){
        Map<String, String> params = new HashMap<>();
        params.put("birthday", brithday);
        params.put("userId", userId);

        mApi.updateBirthday(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 修改头像
     * @param headImg 头像地址
     */
    public void updateHeadImg(String userId, String headImg, YLRxSubscriberHelper<BaseResponse> subscriberHelper){
        Map<String, String> params = new HashMap<>();
        params.put("headImg", headImg);
        params.put("userId", userId);

        mApi.updateHeadImg(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 修改爱好
     * @param hobby 爱好
     */
    public void updateHobby(String userId, String hobby, YLRxSubscriberHelper<BaseResponse> subscriberHelper){
        Map<String, String> params = new HashMap<>();
        params.put("hobby", hobby);
        params.put("userId", userId);

        mApi.updateHobby(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 修改个人介绍
     * @param introduction 个人介绍
     */
    public void updateIntroduction(String userId, String introduction, YLRxSubscriberHelper<BaseResponse> subscriberHelper){
        Map<String, String> params = new HashMap<>();
        params.put("introduction", introduction);
        params.put("userId", userId);

        mApi.updateIntroduction(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 修改名字
     * @param name 名字
     */
    public void updateStylistName(String userId, String name, YLRxSubscriberHelper<BaseResponse> subscriberHelper){
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("userId", userId);

        mApi.updateStylistName(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 修改地址
     */
    public void updateLocation(UpdateAddressRequestBody requestBody, YLRxSubscriberHelper<BaseResponse> subscriberHelper){

        mApi.updateLocation(new BaseRequestBody<>(requestBody).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    //修改头衔
    public void updatePosition(String position, YLRxSubscriberHelper<BaseResponse> subscriberHelper){
        Map<String, String> params = new HashMap<>();
        params.put("position", position);
        params.put("userId", AccountManager.getInstance().getUserId());

        mApi.updatePosition(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 修改形象照
     * @param portraitImg  形象照地址
     */
    public void updatePortrait(String userId, String portraitImg , YLRxSubscriberHelper<BaseResponse> subscriberHelper){
        Map<String, String> params = new HashMap<>();
        params.put("portraitImg", portraitImg );
        params.put("userId", userId);

        mApi.updatePortrait(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

}
