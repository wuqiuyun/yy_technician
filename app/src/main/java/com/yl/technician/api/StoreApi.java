package com.yl.technician.api;


import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.GetStylistResult;
import com.yl.technician.model.vo.result.StylistResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zm on 2018/10/24.
 */
public class StoreApi {

   public interface Api {

       // 获取我的美发师列表
       @POST("/stylist-api/store/storeStylist")
       Observable<GetStylistResult> getMyStylist(@Body RequestBody requestBody);

   }

   private Api mApi;

    public StoreApi() {
        mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 获取我的美发师列表
     * @param nexus 签约0,入驻1
     * @param storeId 门店ID
     */
    public void getMyStylist(int nexus, String storeId, YLRxSubscriberHelper<GetStylistResult> subscriberelper) {
        Map<String, String> params = new HashMap<>();
        params.put("nexus", String.valueOf(nexus));
        params.put("storeId", storeId);

        mApi.getMyStylist(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberelper);
    }

}
