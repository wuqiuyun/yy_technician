package com.yl.technician.module.mine.settings.security.cashaccount.unbankcard;

import android.content.Context;
import android.text.TextUtils;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.SettingsApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.bean.BankCardBean;
import com.yl.technician.model.vo.result.BankCardResult;

import java.util.ArrayList;

/**
 * Created by lyj on 2018/11/8.
 */
public class UnBankCardPresenter extends BasePresenter<UnBankCardView>{
    //解除绑定账户
    public void unbindAccount(String bindId, Context context) {
        new SettingsApi().unBind( bindId, new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse result) {
                getMvpView().onUnBankSuccess();
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }

        });
    }
}
