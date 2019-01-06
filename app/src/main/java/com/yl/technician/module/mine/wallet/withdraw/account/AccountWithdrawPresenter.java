package com.yl.technician.module.mine.wallet.withdraw.account;

import android.content.Context;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.SettingsApi;
import com.yl.technician.api.StylistAuthApplyApi;
import com.yl.technician.api.WalletInfoApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.bean.AuthApplyBean;
import com.yl.technician.model.vo.bean.CashAliBean;
import com.yl.technician.model.vo.result.AuthApplyResult;
import com.yl.technician.model.vo.result.CashAliResult;

import java.util.ArrayList;


/**
 * Created by lyj on 2018/10/30.
 */
public class AccountWithdrawPresenter extends BasePresenter<IAccountWithdrawView> {

    //当前支付宝绑定账户
    public void extractAccount(String bindType, Context context) {
        new SettingsApi().extractAccount(bindType, new YLRxSubscriberHelper<CashAliResult>(context,true) {
            @Override
            public void _onNext(CashAliResult result) {
                ArrayList<CashAliBean> cashAliBeans = result.getData();
                getMvpView().onextractBankAccountSuccess(cashAliBeans);
            }

        });
    }

    //当前支付宝绑定账户
    public void getStylistAuth( Context context) {
        new StylistAuthApplyApi().getStylistAuth(new YLRxSubscriberHelper<AuthApplyResult>(context,true) {
            @Override
            public void _onNext(AuthApplyResult result) {
                AuthApplyBean authApplyBean = result.getData();
                getMvpView().getUserInfoSuccess(authApplyBean);
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().getUserInfoFail();
            }
        });
    }

}
