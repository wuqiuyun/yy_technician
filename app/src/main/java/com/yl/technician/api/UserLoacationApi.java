package com.yl.technician.api;


import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.requestbody.SaveLocationBody;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 地区
 * <p>
 * Created by zm on 2018/9/25.
 */
public class UserLoacationApi {

    public interface Api {
        @POST("/stylist-api/userLocation/save")
        Observable<BaseResponse> save(@Body RequestBody requestBody);

        @POST("/stylist-api/userLocation/changesave")
        Observable<BaseResponse> changesave(@Body RequestBody requestBody);
    }

    private Api api;

    public UserLoacationApi() {
        this.api = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 保存用户地址信息
     */
    public void save(SaveLocationBody saveLocationBody, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        api.save(new BaseRequestBody(saveLocationBody).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    public void changesave(String cityId,String cityName, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", AccountManager.getInstance().getUserId());
        params.put("cityId", cityId);
        params.put("cityName", cityName);
        api.changesave(new BaseRequestBody<>(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }
}
