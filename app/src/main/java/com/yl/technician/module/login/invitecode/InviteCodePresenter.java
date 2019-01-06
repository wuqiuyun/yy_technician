package com.yl.technician.module.login.invitecode;

import android.text.TextUtils;

import com.yl.technician.api.StylistUserApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.helper.AccountManager;

/**
 * Created by lvlong on 2018/9/27.
 */
public class InviteCodePresenter extends BasePresenter<InviteCodeView> {


    /**
     * 提交邀请码
     * @param inviteCode 邀请码
     */
    public void submitInviteCode(String inviteCode) {
        if (TextUtils.isEmpty(inviteCode)) {
            getMvpView().showToast("邀请码不能为空");
            return;
        }

        new StylistUserApi().inviteFriends(AccountManager.getInstance().getAccount().getId(),
                inviteCode, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {

                getMvpView().onInviteCodeSuccess();
            }
        });
    }
}
