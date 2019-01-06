package com.yl.technician.module.mine.info.updatename;

import com.yl.technician.api.StylistCenterInfoApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.helper.AccountManager;

/**
 * Created by Lizhuo on 2018/10/31.
 */
public class UpdateNamePresenter extends BasePresenter<IUpdateNameView> {

    //修改昵称
    public void updateStylistName(String name) {
        if(null == AccountManager.getInstance().getAccount()){
            getMvpView().showToast("用户为空");
            return;
        }
        String userId = AccountManager.getInstance().getUserId();

        new StylistCenterInfoApi().updateStylistName(userId, name, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().updateStylistNameSuc();
            }
        });
    }
}
