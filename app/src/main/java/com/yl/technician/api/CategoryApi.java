package com.yl.technician.api;

import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.CategoryByIdResult;
import com.yl.technician.model.vo.result.ServerTypeResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by zm on 2018/10/15.
 */
public class CategoryApi {

    public interface Api {
        //获取平台类目
        @POST("/stylist-api/category/getAll")
        Observable<ServerTypeResult> getAll();
        //获取服务信息接口
        @POST("/stylist-api/category/getByCondition")
        Observable<ServerTypeResult> getByCondition(@Body RequestBody requestBody);
        //根据服务类型获取类目
        @POST("/stylist-api/category/getCategoryById")
        Observable<CategoryByIdResult> getCategoryById(@Body RequestBody requestBody);
        //根据美发师ID获取单项套餐类
        @POST("/stylist-api/category/getSinglePackage")
        Observable<ServerTypeResult> getSinglePackage(@Body RequestBody requestBody);

    }

    private Api mApi;

    public CategoryApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 获取平台类目
     */
    public void getAll( YLRxSubscriberHelper<ServerTypeResult> subscriberelper) {
        mApi.getAll()
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }
    /**
     * 套餐查询类型，0全部,1套餐一可选,2套餐二可选
     * @param packageType 服务ID
     */
    public void getByCondition(String packageType, YLRxSubscriberHelper<ServerTypeResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("packageType", packageType);
        mApi.getByCondition(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }
    /**
     * 根据服务类型获取类目
     * @param categoryId 类型ID
     */
    public void getCategoryById(String categoryId, YLRxSubscriberHelper<CategoryByIdResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("categoryId", categoryId);
        mApi.getCategoryById(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }
    /**
     * 根据美发师ID获取单项套餐类
     */
    public void getSinglePackage(String stylistId, YLRxSubscriberHelper<ServerTypeResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("stylistId", stylistId);
        mApi.getSinglePackage(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }



}
