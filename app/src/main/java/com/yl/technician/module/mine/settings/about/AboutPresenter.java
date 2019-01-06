package com.yl.technician.module.mine.settings.about;

import com.yl.technician.api.SettingsApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.GetAppInfoResult;

/**
 * Created by lvlong on 2018/10/8.
 */
public class AboutPresenter extends BasePresenter<IAboutView> {
    /**
     * 获取更新相关信息
     */
    public void getAppInfos() {
        new SettingsApi().getAppInfos(new YLRxSubscriberHelper<GetAppInfoResult>() {
            @Override
            public void _onNext(GetAppInfoResult getAppInfoResult) {
                getMvpView().getAppInfoSuc(getAppInfoResult.getData());
            }
        });
    }
}
