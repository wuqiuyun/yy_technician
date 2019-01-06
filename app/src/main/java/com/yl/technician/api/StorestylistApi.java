package com.yl.technician.api;

import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.CheckMsgResult;
import com.yl.technician.model.vo.result.GetStylistResult;
import com.yl.technician.model.vo.result.SendMsgResult;
import com.yl.technician.model.vo.result.StoreStylistNumberResult;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by zm on 2018/10/24.
 */
public class StorestylistApi {

   public interface Api {
       // 门店美发师数量接口
       @POST("/stylist-api/storestylist/getStoreStylistNumber")
       Observable<StoreStylistNumberResult> getStoreStylistNumber(@Body RequestBody requestBody);
       // 门店美发师接口
       @POST("/stylist-api/storestylist/storeStylist")
       Observable<GetStylistResult> storeStylist(@Body RequestBody requestBody);
       // 门店美发师搜索接口
       @POST("/stylist-api/storestylist/storeStylistSearch")
       Observable<GetStylistResult> storeStylistSearch(@Body RequestBody requestBody);

       @POST("/stylist-api/storestylist/nexus")
       Observable<BaseResponse> nexus(@Body RequestBody requestBody);//门店端签约或者入驻美发师接口

       @POST("/stylist-api/storestylist/refuse")
       Observable<BaseResponse> refuse(@Body RequestBody requestBody);//门店端拒绝 签约或者入驻美发师接口

       @POST("/stylist-api/storestylist/sendMsg")
       Observable<SendMsgResult> sendMsg(@Body RequestBody requestBody);//美发师入驻签约/入驻发送消息接口

       @POST("/store-api/store/checkMsg")
       Observable<CheckMsgResult> checkMsg(@Body RequestBody requestBody);

   }
   private Api api;

    public StorestylistApi() {
        api = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 门店美发师数量接口
     * @param subscriberHelper
     */
    public void getStoreStylistNumber(String storeId,YLRxSubscriberHelper<StoreStylistNumberResult> subscriberHelper) {
        HashMap<String, String> params = new HashMap<>();
        params.put("storeId",storeId);
        params.put("userId",AccountManager.getInstance().getUserId());
        api.getStoreStylistNumber(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 美发师列表
     * @param subscriberHelper
     */
    public void storeStylist(int nexus,String storeId,YLRxSubscriberHelper<GetStylistResult> subscriberHelper) {
        HashMap<String, String> params = new HashMap<>();
        params.put("nexus",String.valueOf(nexus));
        params.put("storeId",storeId);
        params.put("userId",AccountManager.getInstance().getUserId());
        api.storeStylist(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 搜索美发师
     * @param subscriberHelper
     */
    public void storeStylistSearch(String nickname,String storeId,String userId,YLRxSubscriberHelper<GetStylistResult> subscriberHelper) {
        HashMap<String, String> params = new HashMap<>();
        params.put("storeId",storeId);
        params.put("nickname",nickname);
        params.put("userId",userId);
        api.storeStylistSearch(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 门店端签约或者入驻美发师接口
     * nexus": 0,
     * "storeId": 0, 门店ID
     * "stylistId": 0  美发师ID
     */
    public void nexus(String nexus, String storeId, String stylistId, String msgId, YLRxSubscriberHelper<BaseResponse> rxSubscriberHelper) {
        HashMap<String, String> params = new HashMap<>();
        params.put("nexus", nexus);
        params.put("storeId", storeId);
        params.put("stylistId", stylistId);
        params.put("userId", AccountManager.getInstance().getUserId());
        params.put("msgId", msgId);

        api.nexus(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(rxSubscriberHelper);
    }

    /**
     * 门店端拒绝  签约或者入驻美发师接口
     * nexus": 0,
     * "storeId": 0, 门店用户ID
     * "stylistId": 0 美发师用户ID
     *     签约类型0入驻，1签约
     */
    public void refuse(String nexus, String storeId, String stylistId, String msgId, YLRxSubscriberHelper<BaseResponse> rxSubscriberHelper) {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", nexus);//0入驻，1签约
        params.put("storeUserId", storeId);
        params.put("stylistUserId", stylistId);
        params.put("msgId", msgId);

        api.refuse(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(rxSubscriberHelper);
    }

    /**
     * 消息处理状态
     * @param msgId 入驻申请消息id
     * @param rxSubscriberHelper
     */
    public void checkMsg(String msgId, YLRxSubscriberHelper<CheckMsgResult> rxSubscriberHelper) {
        HashMap<String, String> params = new HashMap<>();
        params.put("msgId", msgId);
        api.checkMsg(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(rxSubscriberHelper);
    }

    /**
     *
     * @param storeId
     * @param stylistId
     * @param nexus 签约类型0入驻平台，1签约门店
     * @param rxSubscriberHelper
     */
    public void sendMsg(String storeId, String stylistId, int nexus, YLRxSubscriberHelper<SendMsgResult> rxSubscriberHelper) {
        HashMap<String, String> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("stylistId", stylistId);
        if (nexus == 113){ //113平台美发师加入申请 114门店美发师加入申请
            params.put("nexus", "0");
        } else {
            params.put("nexus", "1");
        }
        api.sendMsg(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(rxSubscriberHelper);
    }
}
