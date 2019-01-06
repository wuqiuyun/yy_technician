package com.yl.technician.api;

import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.AreaResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 当面付二维码
 * Created by lyj on 2018/10/28.
 */
public class PersonPayApi {

    public interface Api {

        @GET("/stylist-api/stylistUser/getAccessToken")
        Observable<AreaResult> getAccessToken(@Body RequestBody requestBody);

        @POST("/stylist-api/stylistUser/getQrCode")
        Observable<BaseResponse> getQrCode(@Body RequestBody requestBody);
    }

    private Api api;

    public PersonPayApi() {
        this.api = YLRequestManager.getRequest(Api.class);
    }

    /**
     * storeId (integer, optional),
     * stylistId (integer, optional),
     * tradePayAmount (number, optional),
     * userId (integer, optional)
     */
    public void getAccessToken(String storeId,String stylistId,String tradePayAmount,YLRxSubscriberHelper<AreaResult> rxSubscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserId());
        params.put("stylistId", stylistId);
        params.put("tradePayAmount", tradePayAmount);
        params.put("storeId", storeId);

        api.getAccessToken(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(rxSubscriberHelper);
    }

    /**
     * 获取微信支付二维码
     * storeId (integer, optional),
     * stylistId (integer, optional),
     * tradePayAmount (number, optional)
     *
     */
    public void getQrCode( String storeId,String stylistId,String tradePayAmount,YLRxSubscriberHelper<BaseResponse> rxSubscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("stylistId", stylistId);
        params.put("tradePayAmount", tradePayAmount);

        api.getQrCode(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(rxSubscriberHelper);
    }
}
