package com.yl.technician.module.home.service.combo.multiterm;

import android.content.Context;
import android.text.TextUtils;

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

public class MultitermComboPresenter extends BasePresenter<MultitermComboView> {
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
    public void getServerType(String packageType) {
        new CategoryApi().getByCondition(packageType, new YLRxSubscriberHelper<ServerTypeResult>() {
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
        if (TextUtils.isEmpty(saveServerRequestBody.getServicename())) {
            getMvpView().showToast("请输入套餐名称");
            return;
        }
        if (TextUtils.isEmpty(saveServerRequestBody.getCostprice())) {
            getMvpView().showToast("请输入套餐原价");
            return;
        }
        if (TextUtils.isEmpty(saveServerRequestBody.getPrice())) {
            getMvpView().showToast("请输入套餐价格");
            return;
        }
        if (Float.valueOf(saveServerRequestBody.getPrice())<=0) {
            getMvpView().showToast("套餐价格不可为0");
            return;
        }
        if (Float.valueOf(saveServerRequestBody.getCostprice())<=0) {
            getMvpView().showToast("套餐原价不可为0");
            return;
        }


        new StylistServerApi().save(saveServerRequestBody, new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse result) {
                getMvpView().saveSuccess();
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
}
