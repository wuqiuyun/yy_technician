package com.yl.technician.api;

import android.text.TextUtils;

import com.yl.technician.base.data.BaseRequestBody;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.component.net.YLRequestManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.rx.RxSchedulers;
import com.yl.technician.model.vo.requestbody.DoUserDataRequestBody;
import com.yl.technician.model.vo.result.LoginResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 用户相关api
 * <p>
 * Created by zm on 2018/9/9.
 */
public class StylistUserApi {

    public interface Api {
        // 登陆
        @POST("/stylist-api/stylistUser/login")
        Observable<LoginResult> login(@Body RequestBody requestBody);

        // 微信登陆
        @GET("/stylist-api/stylistUser/wxlogin")
        Observable<LoginResult> wxlogin(@Query("code") String code,@Query("client") String client);

        // 绑定手机号
        @POST("/stylist-api/stylistUser/loginWXAdd")
        Observable<LoginResult> loginWXAdd(@Body RequestBody requestBody);

        // 登出
        @GET("/stylist-api/stylistUser/logout")
        Observable<BaseResponse> logout();

        // 填写邀请码
        @GET("/stylist-api/stylistUser/inviteFriends")
        Observable<BaseResponse> inviteFriends(@Query("userId") String userId, @Query("invitecode") String invitecode);

        // 更新密码
        @POST("/stylist-api/stylistUser/updatePass")
        Observable<BaseResponse> updatePwd(@Body RequestBody requestBody);

        // 完善资料
        @POST("/stylist-api/stylistUser/doUserData")
        Observable<BaseResponse> doUserData(@Body RequestBody requestBody);
    }

    private Api mApi;

    public StylistUserApi() {
        this.mApi = YLRequestManager.getRequest(Api.class);
    }


    /**
     *完善用户信息
     * @param requestBody
     */
    public void doUserData(DoUserDataRequestBody requestBody, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {

        mApi.doUserData(new BaseRequestBody(requestBody).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 填写邀请码
     * @param userId 用户id
     * @param invitecode 邀请码
     */
    public void inviteFriends(String userId, String invitecode, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
        mApi.inviteFriends(userId, invitecode)
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 微信登录
     * @param code 微信code
     */
    public void wxlogin(String code, YLRxSubscriberHelper<LoginResult> subscriberHelper) {
        mApi.wxlogin(code,"android")
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * @param mobile 手机号码
     * @param phoneCode 验证码
     * @param password 密码
     */
    public void login(String mobile, String phoneCode, String password, YLRxSubscriberHelper<LoginResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("client", "android");
        if (!TextUtils.isEmpty(phoneCode))
            params.put("phoneCode", phoneCode);
        if (!TextUtils.isEmpty(password)) {
            params.put("password", password);
        }
        mApi.login(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     *微信登录确认后绑定手机号
     *
     * @param client 性别 -> 1男 2女 3人妖
     * @param mobile 头像地址
     * @param openId 密码
     * @param phoneCode 密码
     * @param type 密码
     */
    public void loginWXAdd(String client, String mobile, String openId,  String phoneCode, int type,
                           YLRxSubscriberHelper<LoginResult> subscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("client", client);
        params.put("mobile", mobile);
        params.put("openId", openId);
        params.put("phoneCode", phoneCode);
        params.put("type", String.valueOf(type));

        mApi.loginWXAdd(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(subscriberHelper);
    }

    /**
     * 登出
     */
    public void logout(YLRxSubscriberHelper<BaseResponse> rxSubscriberHelper) {
        mApi.logout()
                .compose(RxSchedulers.rxSchedulerHelper())
                .compose(RxSchedulers.handleResult())
                .subscribe(rxSubscriberHelper);
    }

    /**
     * 更新密码
     * @param mobile
     * @param phoneCode
     * @param newPassword
     */
    public void updatePwd(String mobile, String phoneCode, String newPassword, YLRxSubscriberHelper<BaseResponse> subscriberHelper) {
            Map<String, String> params = new HashMap<>();
            params.put("mobile", mobile);
            params.put("phoneCode", phoneCode);
            params.put("newPassword", newPassword);
            mApi.updatePwd(new BaseRequestBody(params).toRequestBody())
                    .compose(RxSchedulers.rxSchedulerHelper())
                    .compose(RxSchedulers.handleResult())
                    .subscribe(subscriberHelper);
    }
}
