package com.yl.technician.api;

/*我的作品
 * Create by lvlong on  2018/11/3
 */

import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.requestbody.StylistAuthApplyRequestBody;
import com.yl.technician.model.vo.result.StylistOpusResult;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class StylistOpusApi {

    public interface Api {

        // 获取作品列表
        @GET("/stylist-api/stylistOpus/getStylistOpusByStylistId")
        Observable<StylistOpusResult> getStylistOpusByStylistId(@Query("stylistId") String storeId);

        // 提交认证信息
        @POST("/stylist-api/stylistAuthApply/save")
        Observable<BaseResponse> save(@Body RequestBody requestBody);

    }

    private StylistOpusApi.Api mApi;

    public StylistOpusApi() {
        mApi = YLRequestManager.getRequest(StylistOpusApi.Api.class);
    }

    /**
     * 获取作品列表
     * @param subscriberHelper
     */
    public void getStylistOpusByStylistId(YLRxSubscriberHelper<StylistOpusResult> subscriberHelper) {
        mApi.getStylistOpusByStylistId(AccountManager.getInstance().getStylistId())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
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


}
