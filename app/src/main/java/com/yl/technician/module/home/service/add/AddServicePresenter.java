package com.yl.technician.module.home.service.add;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.CategoryApi;
import com.yl.technician.api.StylistServerApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.SarverInfoResult;
import com.yl.technician.model.vo.result.ServerTypeResult;


/*
 * Create by lvlong on  2018/10/23
 */

public class AddServicePresenter extends BasePresenter<AddServiceView> {
    //获取服务信息
    public void getAll() {
        new CategoryApi().getAll( new YLRxSubscriberHelper<ServerTypeResult>() {
            @Override
            public void _onNext(ServerTypeResult result) {
                getMvpView().getAllServerType(result.getData());
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
}
