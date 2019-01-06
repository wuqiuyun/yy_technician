package com.yl.technician.module.home.service;

import android.content.Context;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.StylistServerApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.SarverItemResult;

/**
 * Created by zm on 2018/10/10.
 */
public class ServiceManagePresenter extends BasePresenter<ServiceManageView>{
    //服务列表
    public void getServerList(String stylistId ,int online,int page) {
        new StylistServerApi().getServerList(stylistId, online, page, new YLRxSubscriberHelper<SarverItemResult>() {
            @Override
            public void _onNext(SarverItemResult result) {
                    getMvpView().onSuccess(result.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().onFail();
            }
        });
    }
    //服务上下架
    public void isOnline(String serviceId, String online, Context context) {
        new StylistServerApi().isOnline(serviceId,online, new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse result) {
                getMvpView().showToast(online.equals("0")?"下架成功":"上架成功");
                getMvpView().operationSuccess();
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().onFail();
            }
        });
    }
    //删除
    public void delete(String serviceId , Context context) {
        new StylistServerApi().delete(serviceId, new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse result) {
                    getMvpView().showToast("删除成功!");
                    getMvpView().operationSuccess();

            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().onFail();
            }
        });
    }

}
