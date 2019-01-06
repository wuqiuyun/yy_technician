package com.yl.technician.module.im.chat;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.GroupApi;
import com.yl.technician.api.RedBagApi;
import com.yl.technician.api.SettingsApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.RedBagSendResult;
import com.yl.technician.model.vo.result.findGroupAllUserResult;

/**
 * Created by zm on 2018/9/19.
 */
public class ChatPresenter extends BasePresenter<ChatView> {

    public void findRedBagReq(String id, Activity activity) {
        if (TextUtils.isEmpty(id)) {
            getMvpView().showToast("红包Id不能为空");
            return;
        }

        new RedBagApi().findRedBag(id, new YLRxSubscriberHelper<RedBagSendResult>(activity, true) {
            @Override
            public void _onNext(RedBagSendResult result) {
                getMvpView().findRedBagSuccess(result);
            }
        });
    }

    public void receiveRedBagReq(String id, Activity activity) {
        if (TextUtils.isEmpty(id)) {
            getMvpView().showToast("红包Id不能为空");
            return;
        }

        new RedBagApi().receiveRedBag(id, new YLRxSubscriberHelper<RedBagSendResult>(activity, true) {
            @Override
            public void _onNext(RedBagSendResult result) {
                getMvpView().receiveRedBagSuc(result);
            }
        });
    }

    public void findTransferReq(String id, Activity activity) {
        if (TextUtils.isEmpty(id)) {
            getMvpView().showToast("红包Id不能为空");
            return;
        }

        new RedBagApi().findTransfer(id, new YLRxSubscriberHelper<RedBagSendResult>(activity, true) {
            @Override
            public void _onNext(RedBagSendResult result) {
                getMvpView().findTransferSuccess(result);
            }
        });
    }

    public void receiveTransferReq(String id, Activity activity) {
        if (TextUtils.isEmpty(id)) {
            getMvpView().showToast("红包Id不能为空");
            return;
        }

        new RedBagApi().receiveTransfer(id, new YLRxSubscriberHelper<RedBagSendResult>(activity, true) {
            @Override
            public void _onNext(RedBagSendResult result) {
                getMvpView().receiveTransferSuc(result);
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
