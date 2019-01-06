package com.yl.technician.module.login.bindphone;

import android.os.CountDownTimer;
import android.text.TextUtils;

import com.yl.technician.api.StylistSmsApi;
import com.yl.technician.api.StylistUserApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.TokenManager;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.bean.UserBean;
import com.yl.technician.model.vo.result.LoginResult;

/**
 * Created by lyj on 2018/10/9.
 */
public class BindPhonePresenter extends BasePresenter<IBindPhoneView>{
    private static final long TOTAL_TIME = 60_000L;
    private static final long ONECE_TIME = 1000L;

    private CountDownTimer mCountDownTimer;

    /**
     * 绑定手机号
     * @param mobile
     */
    public void mobileLogin(String client, String mobile, String openId,  String phoneCode, int type) {
        if (TextUtils.isEmpty(mobile)) {
            getMvpView().showToast("手机号码不能为空");
            return;
        }
        if (TextUtils.isEmpty(phoneCode)) {
            getMvpView().showToast("验证码不能为空");
            return;
        }
        bindPhone(client, mobile, openId, phoneCode, type);
    }



    /**
     * 绑定手机号
     * @param mobile
     * @param phoneCode
     */
    private void bindPhone(String client, String mobile, String openId,  String phoneCode, int type) {
        new StylistUserApi().loginWXAdd(client, mobile, openId, phoneCode, type, new YLRxSubscriberHelper<LoginResult>() {
            @Override
            public void _onNext(LoginResult loginResult) {
                UserBean userBean = loginResult.getData();
                // save userInfo
                AccountManager.getInstance().updateAccount(userBean);
                // save token
                TokenManager.updateToken(userBean.getToken());
                // callback
                getMvpView().onBindPhoneSuccess(userBean);
            }
        });
    }


    /**
     * 获取验证码
     * @param mobile
     */
    public void getPhoneCode(String mobile) {
        if (TextUtils.isEmpty(mobile)){
            getMvpView().showToast("手机号码不能为空");
            return;
        }
        if (mobile.length() != 11) {
            getMvpView().showToast("请输入正确的手机号码");
            return;
        }
        // 限制验证码获取
        startCountdownTime();
        new StylistSmsApi().getPhoneCode(mobile, "2", new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().showToast("验证码获取成功");
            }
        });
    }

    /**
     * 开启倒计时
     */
    private void startCountdownTime() {
        if (null == mCountDownTimer) {
            mCountDownTimer = new CountDownTimer(TOTAL_TIME, ONECE_TIME) {
                @Override
                public void onTick(long millisUntilFinished) {
                    getMvpView().updateCountdownTime(millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    getMvpView().onCountdownFinish();
                }
            };
        }
        mCountDownTimer.start();
    }

    /**
     * 关闭倒计时
     */
    public void stopCountdownTime() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    @Override
    public void onDetachMvpView() {
        super.onDetachMvpView();
        stopCountdownTime();
    }

}
