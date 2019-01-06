package com.yl.technician.module.main;

import com.yl.technician.api.SettingsApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.GetAppInfoResult;

/**
 * Created by zm on 2018/9/10.
 */
public class MainPresenter extends BasePresenter<IMainView> {
    /**
     * 获取应用更新信息
     */
    public void getAppInfos() {
        new SettingsApi().getAppInfos(new YLRxSubscriberHelper<GetAppInfoResult>() {
            @Override
            public void _onNext(GetAppInfoResult getAppInfoResult) {
                getMvpView().onUpdateAppInfo(getAppInfoResult.getData());
            }
        });
    }
}
