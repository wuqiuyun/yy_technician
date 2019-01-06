package com.yl.technician.api;

import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.bean.TimeBean;
import com.yl.technician.model.vo.result.TimeManageDayResult;
import com.yl.technician.model.vo.result.TimeManageMonthResult;
import com.yl.technician.model.vo.result.TimeResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by zm on 2018/10/15.
 */
public class StylistTimeSettingApi {

    public interface Api {
        @GET("/stylist-api/stylistTimeSetting/getStylistTimeSettingByStylistId")
        Observable<TimeResult> getStylistTimeSettingByStylistId(@QueryMap Map<String, String> map);

        //获取开关状态
        @GET("/stylist-api/stylistTimeSetting/getStylistStatusByStylistId")
        Observable<BaseResponse<Integer>> getStylistStatusByStylistId(@Query("stylistId") String stylistId);

        @GET("/stylist-api/stylistTimeSetting/setStylistServiceByStylistId")
        Observable<BaseResponse> setStylistServiceByStylistId(@QueryMap Map<String, String> map);

        @POST("/stylist-api/stylistTimeSetting/updateOrSaves")
        Observable<BaseResponse<List<TimeBean>>> updateOrSaves(@Body RequestBody body);

        //根据时间 及美发师的Id 查询美发师的工作和休息时间
        @GET("/stylist-api/stylistTimeSetting/getStylistTimeByStylistIdAndDate")
        Observable<TimeManageDayResult> getStylistTimeByStylistIdAndDate(@Query("stylistId") String stylistId, @Query("date") String date);
        //根据时间段 及美发师的Id 查询美发师的工作和休息时间
        @GET("/stylist-api/stylistTimeSetting/getStylistTimeListByStylistIdAndDates")
        Observable<TimeManageMonthResult> getStylistTimeListByStylistIdAndDates(@Query("stylistId") String stylistId,@Query("startdate") String startdate,
                                                                       @Query("enddate") String enddate);
        //修改美发师的休息和请假
        @POST("/stylist-api/stylistTimeSetting/updateOrSaveStylistTime")
        Observable<BaseResponse> updateOrSaveStylistTime(@Body RequestBody body);

    }

    private Api mApi;

    public StylistTimeSettingApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 查询美发师的时间设置列表
     *
     * @param subscriberelper
     */
    public void getStylistTimeSettingByStylistId(String stylistId, YLRxSubscriberHelper<TimeResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("stylistId", stylistId);

        mApi.getStylistTimeSettingByStylistId(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    //获取美发师的服务开关设置 status 1 正常 0 关闭
    public void getStylistStatusByStylistId(YLRxSubscriberHelper<BaseResponse<Integer>> subscriberHelper) {
        mApi.getStylistStatusByStylistId(AccountManager.getInstance().getStylistId())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 美发师的服务开关设置 status 1 正常 0 关闭
     *
     * @param subscriberelper
     */
    public void setStylistServiceByStylistId(String stylistId, String status, YLRxSubscriberHelper<BaseResponse> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("stylistId", stylistId);
        params.put("status", status);

        mApi.setStylistServiceByStylistId(params)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 展示美发师个人资料
     * id 用户id
     */
    public void updateOrSaves(List<TimeBean> timeBeans, YLRxSubscriberHelper<BaseResponse<List<TimeBean>>> subscriberHelper) {
        mApi.updateOrSaves(new BaseRequestBody(timeBeans).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 根据时间 及美发师的Id 查询美发师的工作和休息时间
     * @param date 20180112 时间
     * @param subscriberelper
     */
    public void getStylistTimeByStylistIdAndDate(String date,YLRxSubscriberHelper<TimeManageDayResult> subscriberelper) {

        mApi.getStylistTimeByStylistIdAndDate(AccountManager.getInstance().getStylistId(),date)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 根据时间 及美发师的Id 查询美发师的工作和休息时间
     * @param startdate 20180112 时间
     * @param enddate 20180116 时间
     * @param subscriberelper
     */
    public void getStylistTimeListByStylistIdAndDates(String startdate,String enddate,YLRxSubscriberHelper<TimeManageMonthResult> subscriberelper) {
        mApi.getStylistTimeListByStylistIdAndDates(AccountManager.getInstance().getStylistId(),startdate,enddate)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

    /**
     * 根据时间 及美发师的Id 查询美发师的工作和休息时间
     * "createtime": "2018-12-26T02:38:20.373Z",
     * "day": 0,
     * "id": 0,
     * "locktime": "string",
     * "resttime": "string",//只要修改这个字段
     * "stylistId": 0,
     * "updatetime": "2018-12-26T02:38:20.374Z",
     * "worktime": "string"
     */
    public void updateOrSaveStylistTime(String day,String id,
                                        String locktime,String resttime,
                                        String worktime,YLRxSubscriberHelper<BaseResponse> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("stylistId", AccountManager.getInstance().getStylistId());
        params.put("day", day);
        params.put("id", id);
        params.put("locktime", locktime);
        params.put("resttime", resttime);
        params.put("worktime", worktime);

        mApi.updateOrSaveStylistTime(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

}
