package com.yl.technician.module.mine.settings.security.phone.password;

import com.yl.technician.api.SettingsApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;

/**
 * Created by lvlong on 2018/10/12.
 */
public class PasswordVerifyPresenter extends BasePresenter<PasswordVerifyView> {

    public void checkPassword(String password){
        new SettingsApi().checkPassword(password, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().onCheckPassword();
            }
        });
    }

}
