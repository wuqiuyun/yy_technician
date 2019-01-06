package com.yl.technician.module.mine.wallet.cochain;

import android.content.Context;
import android.text.TextUtils;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.WalletInfoApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.local.preferences.CommonSharedPreferences;
import com.yl.technician.util.FormatUtil;

/**
 * Created by zm on 2018/10/9.
 */
public class CochainPresenter extends BasePresenter<ICochainView>{
    //上链
    public void cochain(String coinId, String num, Context context) {
        if (TextUtils.isEmpty(num)){
            getMvpView().showToast("请输入"+ FormatUtil.Formatstring(CommonSharedPreferences.getInstance().getBasicDataBean().getSysCoinDefault())+"数量");
        }
        new WalletInfoApi().cochain(coinId, num, new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse result) {
                getMvpView().onSuccess();
            }

            @Override
            public void _onError(ApiException error) {
            }
        });
    }
}
