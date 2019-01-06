package com.yl.technician.api;

import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.OpusDetailResult;
import com.yl.technician.model.vo.result.OpusListResult;
import com.yl.technician.model.vo.result.StylistCardResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Lizhuo on 2018/11/5.
 * 美发师名片
 */
public class StylistCardApi {
    public interface Api {
        // 获取美发师名片
        @POST("/stylist-api/stylistCard/card")
        Observable<StylistCardResult> card(@Body RequestBody requestBody);

        // 美发师作品集
        @POST("/stylist-api/stylistCard/opusList")
        Observable<OpusListResult> opusList(@Body RequestBody requestBody);

        // 美发师作品集筛选
        @POST("/stylist-api/stylistCard/opusListScreen")
        Observable<OpusListResult> opusListScreen(@Body RequestBody requestBody);

        // 美发师作品详情
        @POST("/stylist-api/stylistCard/opusDetail")
        Observable<OpusDetailResult> opusDetail(@Body RequestBody requestBody);
        
        // 收藏美发师作品
        @POST("/stylist-api/stylistCard/oupsCollection")
        Observable<BaseResponse> oupsCollection(@Body RequestBody requestBody);

        // 收藏美发师名片
        @POST("/stylist-api/stylistCard/stylistCollection")
        Observable<BaseResponse> stylistCollection(@Body RequestBody requestBody);
        
        //增加转发、查看数
        @POST("/stylist-api/stylistCard/opusCount")
        Observable<BaseResponse> opusCount(@Body RequestBody requestBody);
        
        //删除作品中一张图片
        @GET("/stylist-api/stylistCard/deleteOpusPicture")
        Observable<BaseResponse> deleteOpusPicture(@Query("pictureId") String pictureId);

        //删除整个作品
        @POST("/stylist-api/stylistCard/deleteOpus")
        Observable<BaseResponse> deleteOpus(@Body RequestBody requestBody);

        //修改美发师背景图
        @POST("/stylist-api/stylistCard/saveBackGround")
        Observable<BaseResponse> saveBackGround(@Body RequestBody requestBody);

    }

    private Api mApi;

    public StylistCardApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 获取美发师名片
     * @param stylistId
     * @param userId
     */
    public void getStylistCard(String stylistId, String userId, YLRxSubscriberHelper<StylistCardResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("stylistId", stylistId);
        params.put("userId", userId);

        mApi.card(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 获取美发师作品集
     * @param stylistId
     * @param userId
     */
    public void getOpusList(String stylistId, String userId, YLRxSubscriberHelper<OpusListResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("stylistId", stylistId);
        params.put("userId", userId);

        mApi.opusList(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 筛选美发师作品
     * 
     * @param stylistId
     * @param screenId 对应筛选项的id
     * @param type  筛选类型 1发类  2脸型
     */
    public void getOpusListScreen(String stylistId, String screenId,String type, YLRxSubscriberHelper<OpusListResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("screenId", screenId);
        params.put("stylistId", stylistId);
        params.put("type", type);

        mApi.opusListScreen(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 获取作品详情
     * @param opusId
     */
    public void getOpusDetail(String opusId, String userId, YLRxSubscriberHelper<OpusDetailResult> subscriberHelper){
        Map<String, String> params = new HashMap<>();
        params.put("opusId", opusId);
        params.put("userId", userId);

        mApi.opusDetail(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 作品收藏
     * @param opusId
     * @param userId
     * @param type  0 取消  1 收藏
     */
    public void oupsCollection(String opusId, String userId, String type, YLRxSubscriberHelper<BaseResponse> subscriberHelper){
        Map<String, String> params = new HashMap<>();
        params.put("opusId", opusId);
        params.put("userId", userId);
        params.put("type", type);

        mApi.oupsCollection(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 美发师名片收藏
     * @param stylistId
     * @param userId
     * @param type  0 取消  1 收藏
     */
    public void stylistCollection(String stylistId, String userId, String type, YLRxSubscriberHelper<BaseResponse> subscriberHelper){
        Map<String, String> params = new HashMap<>();
        params.put("stylistId", stylistId);
        params.put("userId", userId);
        params.put("type", type);

        mApi.stylistCollection(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 美发师作品转发、浏览
     * @param opusId
     * @param type  1 转发/分享  2 浏览
     */
    public void opusCount(String opusId , String type, YLRxSubscriberHelper<BaseResponse> subscriberHelper){
        Map<String, String> params = new HashMap<>();
        params.put("opusId", opusId );
        params.put("type", type);

        mApi.opusCount(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 删除某一张作品
     * @param pictureId
     */
    public void deleteOpusPicture(String pictureId, YLRxSubscriberHelper<BaseResponse> subscriberHelper){
        mApi.deleteOpusPicture(pictureId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 删除作品
     * @param opusId
     */
    public void deleteOpus(String opusId, YLRxSubscriberHelper<BaseResponse> subscriberHelper){
        Map<String, String> params = new HashMap<>();
        params.put("opusId", opusId );
        params.put("userId", AccountManager.getInstance().getUserId());

        mApi.deleteOpus(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    //修改美发师背景图
    public void saveBackGround(String backGroundImg , YLRxSubscriberHelper<BaseResponse> subscriberHelper){
        Map<String, String> params = new HashMap<>();
        params.put("stylistId" , AccountManager.getInstance().getStylistId());
        params.put("userId" , AccountManager.getInstance().getUserId());
        params.put("backGroundImg" , backGroundImg);

        mApi.saveBackGround(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);

    }
}
