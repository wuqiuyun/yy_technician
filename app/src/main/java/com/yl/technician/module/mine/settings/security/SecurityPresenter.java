package com.yl.technician.module.mine.settings.security;

import android.content.Context;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.SettingsApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.SecurityInfoResult;

/**
 * Created by lvlong on 2018/10/8.
 */
public class SecurityPresenter extends BasePresenter<ISecurityView> {
    //获取账户安全详情
    public void getAccountInfoAccount() {
        new SettingsApi().getAccountSafeInfo(new YLRxSubscriberHelper<SecurityInfoResult>() {
            @Override
            public void _onNext(SecurityInfoResult CoinInfoResult) {
                getMvpView().onAccountInfoSuccess(CoinInfoResult.getData());
            }
        });
    }

    //微信绑定
    public void bindWXAccount(String code, Context context) {
        new SettingsApi().bindWXAccount(code, new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse CoinInfoResult) {
                getMvpView().onBindWxSuccess();
            }
        });
    }

    //支付密码状态
    public void initPayWord(Context context) {
        new SettingsApi().initPayWord(new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse CoinInfoResult) {
                getMvpView().oninitPayWordInfoSuccess(CoinInfoResult.getData()+"");
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }

}
