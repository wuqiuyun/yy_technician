package com.yl.technician.api;

import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.StylistCommentListResult;
import com.yl.technician.model.vo.result.StylistManageScopeResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 评价管理api
 * <p>
 * Created by lyj on 2018/10/23.
 */

public class StylistCommentApi {
    public interface Api {

        /**
         * 评价评分
         *
         * storeId (integer, optional): 门店id（非本人门店必传） ,
         * userId (integer, optional): 用户id
         * */
        @GET("/stylist-api/stylistComment/getStylistComment")
        Observable<StylistManageScopeResult> getStylistComment(@Query("userId") String userId, @Query("stylistId") String storeId);

        /**
         * 门店顾客评价
         *
         * storeId (integer, optional): 门店ID，非本人门店必传 ,
         * userId (integer, optional): 用户id
         * */
        @GET("/stylist-api/stylistManage/getStylistScore")
        Observable<StylistManageScopeResult> getStoreScore(@Query("userId") String userId, @Query("stylistId") String storeId);

        /**
         * 回复客户评论
         *
         * context  (number, optional): 内容 ,
         * storeId (integer, optional): 门店id ,
         * userId (integer, optional): 用户id
         * */
        @POST("/stylist-api/stylistComment/replyStylistComment")
        Observable<BaseResponse> replyStoreComment(@Body RequestBody requestBody);

        /**
         * 门店顾客评价列表
         *
         * page 页数
         * id (integer, optional): 用户id
         * size (integer, optional): 每一页的大小
         *
         * */
        @GET("/stylist-api/stylistComment/getStylistCommentList")
        Observable<StylistCommentListResult> getStylistCommentList(@Query("stylistId") String stylistId, @Query("page") String page, @Query("size") String size);

    }



    private Api mApi;

    public StylistCommentApi() {
        this.mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     *  回复客户评论
     *
     * context (string, optional): 内容
     * storeId (integer, optional): 门店id（非本人门店必传） ,
     * userId (integer, optional): 用户id
     *
     */
    public void replyStoreComment(String context, String stylistReviewsId,
                                  YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("stylistReviewsId", stylistReviewsId);
        params.put("context", context);

        mApi.replyStoreComment(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 评价
     *
     * storeId (integer, optional): 门店id（非本人门店必传） ,
     * userId (integer, optional): 用户id
     *
     * */
    public void getStoreScore(String stylistId,
                             YLRxSubscriberHelper<StylistManageScopeResult> subscriberHelper) {

        mApi.getStoreScore(AccountManager.getInstance().getUserId(),stylistId)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 门店顾客评价列表
     *
     * page 页数
     * id (integer, optional): 用户id
     * size (integer, optional): 每一页的大小
     *
     * */
    public void getStylistCommentList(String stylistId,int page,int size,YLRxSubscriberHelper<StylistCommentListResult> subscriberHelper) {

        mApi.getStylistCommentList(stylistId,String.valueOf(page),String.valueOf(size))
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }
}
