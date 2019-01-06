package com.yl.technician.module.home.personpay.selectstore;

import com.yl.technician.api.JoinStoreApi;
import com.yl.technician.api.StylistServerApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.bean.StoreBean;
import com.yl.technician.model.vo.result.StoreListResult;
import com.yl.core.component.net.exception.ApiException;

import java.util.ArrayList;

/**
 * Created by wqy on 2018/11/6.
 */

public class SelectStorePresenter extends BasePresenter<ISelectStoreView>{

    //我签约/入驻的门店
    public void getMyStore(String lat,String lng,String userId) {
        new JoinStoreApi().myStore( lat, lng, userId,new YLRxSubscriberHelper<StoreListResult>() {
            @Override
            public void _onNext(StoreListResult result) {
                getMvpView().getStoreListSuccess(result.getData());
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if (getMvpView()!=null)getMvpView().getStoreListFail();
            }
        });
    }
}
