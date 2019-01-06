package com.yl.technician.module.mine.settings.security.cashaccount;

import android.content.Context;
import android.text.TextUtils;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.SettingsApi;
import com.yl.technician.api.StylistAuthApplyApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.bean.AuthApplyBean;
import com.yl.technician.model.vo.bean.CashAliBean;
import com.yl.technician.model.vo.result.AuthApplyResult;
import com.yl.technician.model.vo.result.CashAliResult;

import java.util.ArrayList;

/**
 * Created by lyj on 2018/11/8.
 */
public class CashAccountManagePresenter extends BasePresenter<CashAccountManageView>{


    //当前账户认证状态
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

    //当前支付宝绑定账户
    public void extractAccount(String bindType,Context context) {
        new SettingsApi().extractAccount(bindType, new YLRxSubscriberHelper<CashAliResult>(context,true) {
            @Override
            public void _onNext(CashAliResult result) {
                ArrayList<CashAliBean> cashAliBeans = result.getData();
                getMvpView().onextractaLIAccountSuccess(cashAliBeans);
            }

        });
    }

    //当前银行卡绑定账户
    public void extractBankAccount(String bindType,Context context) {
        new SettingsApi().extractAccount(bindType, new YLRxSubscriberHelper<CashAliResult>(context,true) {
            @Override
            public void _onNext(CashAliResult result) {
                ArrayList<CashAliBean> cashAliBeans = result.getData();
                getMvpView().onextractBankAccountSuccess(cashAliBeans);
            }

        });
    }

    //绑定账户
    public void bindAccount(String accountNo,String bindType,String branch,
                            String realName,String shortName, String stylistId,Context context) {
        if (TextUtils.isEmpty(realName)) {
            getMvpView().showToast("请输入真实姓名");
            return;
        }
        if (bindType.equals("ALI")){
            if (TextUtils.isEmpty(accountNo)) {
                getMvpView().showToast("请输入支付宝账户号");
                return;
            }
        }else {
            if (TextUtils.isEmpty(shortName)) {
                getMvpView().showToast("请选择发卡银行");
                return;
            }
            if (TextUtils.isEmpty(branch)) {
                getMvpView().showToast("请输入发卡支行");
                return;
            }
            if (TextUtils.isEmpty(accountNo)) {
                getMvpView().showToast("请输入银行卡号");
                return;
            }
        }

        new SettingsApi().bindAccount( accountNo, bindType, branch,
                realName, shortName,  stylistId, new YLRxSubscriberHelper<BaseResponse>(context,true) {
                    @Override
                    public void _onNext(BaseResponse result) {
                        getMvpView().onUnBindSuccess();
                    }

                    @Override
                    public void _onError(ApiException error) {
                        super._onError(error);
                    }
                });
    }

    //解除绑定账户
    public void unbindAccount(String bindId,Context context) {
        new SettingsApi().unBind( bindId, new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse result) {
                getMvpView().onUnBindSuccess();
            }

            @Override
            public void onError(Throwable throwable) {
                super.onError(throwable);
            }

        });
    }

}
