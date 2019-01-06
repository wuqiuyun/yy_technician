package com.yl.technician.module.mine.settings;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.RecomUserApi;
import com.yl.technician.api.SettingsApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.FindReCodeResult;

/**
 * Created by zm on 2018/9/29.
 */
public class ISettingsPresenter extends BasePresenter<ISettingsView> {
    public void changeNotice(int shutdown){
        new SettingsApi().changeNotice(shutdown, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().changeNoticeSuc();
            }
        });
    }

    /**
     * 获取我的推荐码
     */
    public void findReCode(){
        new RecomUserApi().findReCode(new YLRxSubscriberHelper<FindReCodeResult>() {
            @Override
            public void _onNext(FindReCodeResult findReCodeResult) {
                if (null != findReCodeResult.getData()) getMvpView().findReCodeSuc(findReCodeResult.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
}
