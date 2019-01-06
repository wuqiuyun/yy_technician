package com.yl.technician.api;

import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.model.vo.requestbody.SaveServerRequestBody;
import com.yl.technician.model.vo.result.SarverInfoResult;
import com.yl.technician.model.vo.result.SarverItemResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by zm on 2018/10/15.
 */
public class StylistServerApi {

    public interface Api {
        //美发师服务删除
        @POST("/stylist-api/stylistServer/delete")
        Observable<BaseResponse> delete(@Body RequestBody requestBody);
        //获取服务信息接口
        @POST("/stylist-api/stylistServer/getServiceInfo")
        Observable<SarverInfoResult> getServiceInfo(@Body RequestBody requestBody);
        //美发师服务上下架
        @POST("/stylist-api/stylistServer/isOnline")
        Observable<BaseResponse> isOnline(@Body RequestBody requestBody);
        //美发师服务列表
        @POST("/stylist-api/stylistServer/list")
        Observable<SarverItemResult> getServerList(@Body RequestBody requestBody);
        //美发师服务保存接口
        @POST("/stylist-api/stylistServer/save")
        Observable<BaseResponse> save(@Body RequestBody requestBody);

    }

    private Api mApi;

    public StylistServerApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 删除服务
     * @param serviceId 服务ID
     */
    public void delete(String serviceId, YLRxSubscriberHelper<BaseResponse> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("serviceId", serviceId);
        mApi.delete(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }
    /**
     * 获取服务信息
     * @param serviceId 服务ID
     */
    public void getServiceInfo(String serviceId, YLRxSubscriberHelper<SarverInfoResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("serviceId", serviceId);
        mApi.getServiceInfo(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }
    /**
     * 获取服务信息
     * @param serviceId 服务ID
     * @param online 状态下架0上架1
     */
    public void isOnline(String serviceId,String online, YLRxSubscriberHelper<BaseResponse> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("serviceId", serviceId);
        params.put("online", online);
        mApi.isOnline(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }
    /**
     * 美发师服务列表按条件查询
     * @param stylistId  美发师ID
     * @param online 状态下架0上架1
     */
    public void getServerList(String stylistId ,int online,int page, YLRxSubscriberHelper<SarverItemResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("stylistId", stylistId);
        params.put("online", String.valueOf(online));
        params.put("page", String.valueOf(page));
        mApi.getServerList(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }
    /**
     * 保存
     */
    public void save(SaveServerRequestBody saveServerRequestBody, YLRxSubscriberHelper<BaseResponse> subscriberelper) {
        mApi.save(new BaseRequestBody(saveServerRequestBody).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }
}
