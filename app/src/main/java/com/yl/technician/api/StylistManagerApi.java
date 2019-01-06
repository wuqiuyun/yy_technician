package com.yl.technician.api;

import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.bean.ShowtimeBean;
import com.yl.technician.model.vo.result.GetStylistManagerResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by zm on 2018/12/17.
 */
public class StylistManagerApi {

    private interface Api {
        @POST("/stylist-api/stylistManage/timemanage")
        Observable<GetStylistManagerResult> timemanage(@Body RequestBody requestBody);

        @GET("/stylist-api/stylistManage/getShowTime")
        Observable<BaseResponse<ShowtimeBean>> getShowTime();
    }

    private Api api;

    public StylistManagerApi() {
        this.api = YLRequestManager.getRequest(Api.class);
    }

    public void getShowTime(YLRxSubscriberHelper<BaseResponse<ShowtimeBean>> subscriberHelper) {
        api.getShowTime()
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    public void timemanage(String datetime, YLRxSubscriberHelper<GetStylistManagerResult> subscriberHelper) {
        Map<String, String > parmas = new HashMap<>();
        parmas.put("datetime", datetime);
        parmas.put("stylistId", AccountManager.getInstance().getStylistId());

        api.timemanage(new BaseRequestBody<>(parmas).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }
}
