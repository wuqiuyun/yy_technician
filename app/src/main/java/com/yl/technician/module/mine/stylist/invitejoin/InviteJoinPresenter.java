package com.yl.technician.module.mine.stylist.invitejoin;

import android.content.Context;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.JoinStoreApi;
import com.yl.technician.api.StoreApi;
import com.yl.technician.api.StorestylistApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.SendMsgResult;

/**
 * Created by zm on 2018/10/11.
 */
public class InviteJoinPresenter extends BasePresenter<IInviteJoinView> {

    /**
     * 入驻/签约门店
     */
    public void nexusStore(String apply,String storeId,String stylistId,Context context){
        new JoinStoreApi().nexusStore(apply, storeId, stylistId, AccountManager.getInstance().getUserId(), new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().showToast(baseResponse.getMessage());
                getMvpView().onSucceed(apply);
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().showToast(error.getMessage());
                getMvpView().onFail(error.getMessage());
            }
        });
    }

    /**
     * 美发师入驻签约/入驻发送消息接口
     * @param storeId
     * @param stylistId
     * @param context
     */
    public void sendMsg(String storeId, String stylistId, int type, Context context) {
        new StorestylistApi().sendMsg(storeId, stylistId, type, new YLRxSubscriberHelper<SendMsgResult>(context, true) {
            @Override
            public void _onNext(SendMsgResult sendMsgResult) {
                getMvpView().sendMsg(sendMsgResult, type);
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
}
