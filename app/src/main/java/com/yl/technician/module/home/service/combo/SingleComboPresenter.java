package com.yl.technician.module.home.service.combo;

import android.content.Context;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.CategoryApi;
import com.yl.technician.api.StylistServerApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.requestbody.SaveServerRequestBody;
import com.yl.technician.model.vo.result.SarverInfoResult;
import com.yl.technician.model.vo.result.ServerTypeResult;


/*
 * Create by lvlong on  2018/10/24
 */

public class SingleComboPresenter extends BasePresenter<SingleComboView> {
    //获取服务信息
    public void getServiceInfo(String serviceId) {
        new StylistServerApi().getServiceInfo(serviceId, new YLRxSubscriberHelper<SarverInfoResult>() {
            @Override
            public void _onNext(SarverInfoResult result) {
                getMvpView().onSuccess(result.getData());
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }

    //获取服务信息
    public void getSinglePackage(String stylistId) {
        new CategoryApi().getSinglePackage(stylistId, new YLRxSubscriberHelper<ServerTypeResult>() {
            @Override
            public void _onNext(ServerTypeResult result) {
                getMvpView().getServerType(result.getData());
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
    //保存服务信息
    public void save(SaveServerRequestBody saveServerRequestBody, Context context) {


        new StylistServerApi().save(saveServerRequestBody, new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse result) {
                getMvpView().showToast("保存成功");
                getMvpView().saveSuccess();
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
}
