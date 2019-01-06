package com.yl.technician.module.mine.wallet.address;

import android.content.Context;
import android.text.TextUtils;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.WalletInfoApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;

/**
 * Created by zm on 2018/10/8.
 */
public class AddressPresenter extends BasePresenter<IAddressView>{
    //绑定外部钱包地址
    public void bindWalletURL(String userId, String url, Context context) {
        if (TextUtils.isEmpty(url)){
            getMvpView().showToast("请输入钱包地址");
            return;
        }
        new WalletInfoApi().bindWalletURL(userId, url, new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse result) {
                getMvpView().onSuccess();
            }

            @Override
            public void _onError(ApiException error) {
                getMvpView().showToast(error.message);
            }
        });
    }
}
