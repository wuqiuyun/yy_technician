package com.yl.technician.api;


import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.requestbody.StylistAuthApplyRequestBody;
import com.yl.technician.model.vo.result.AuthApplyResult;
import com.yl.technician.model.vo.result.GetStylistAuthApplyResult;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 店铺认证申请api
 * <p>
 * Created by zm on 2018/10/22.
 */
public class StylistAuthApplyApi {

    public interface Api {
        // 提交认证信息
        @POST("/stylist-api/stylistAuthApply/save")
        Observable<BaseResponse> save(@Body RequestBody requestBody);

        // 修改认证信息
        @POST("/stylist-api/stylistAuthApply/updateOrSave")
        Observable<BaseResponse> updateOrSave(@Body RequestBody requestBody);

        // 获取认证信息
        @GET("/stylist-api/stylistAuthApply/getStylistAuthStatusByStylistId")
        Observable<GetStylistAuthApplyResult> getStoreAuthApplyByStoreId(@Query("stylistId") String storeId);

        // 获取美发师认证信息
        @GET("/stylist-api/stylistAuthApply/getStylistAuth")
        Observable<AuthApplyResult> getStylistAuth(@Query("stylistId") String stylistId);


    }

    private Api mApi;

    public StylistAuthApplyApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 提交认证信息
     * @param requestBody 认证信息
     * @param subscriberHelper
     */
    public void save(StylistAuthApplyRequestBody requestBody, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        mApi.save(new BaseRequestBody(requestBody).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 获取认证信息
     * @param subscriberHelper
     */
    public void getStylistAuthApplyByStylistId(YLRxSubscriberHelper<GetStylistAuthApplyResult> subscriberHelper) {
        mApi.getStoreAuthApplyByStoreId(AccountManager.getInstance().getStylistId())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 获取美发师认证信息
     * @param subscriberHelper
     */
    public void getStylistAuth(YLRxSubscriberHelper<AuthApplyResult> subscriberHelper) {
        mApi.getStylistAuth(AccountManager.getInstance().getStylistId())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

}
