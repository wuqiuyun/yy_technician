package com.yl.technician.module.home.join;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.StorestylistApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.StoreStylistNumberResult;

/**
 * Created by zm on 2018/10/10.
 */
public class StoreStylistPresenter extends BasePresenter<StoreStylistView>{
    //门店美发师数量接口
    public void getStoreStylistNumber(String storeId) {
        new StorestylistApi().getStoreStylistNumber(storeId,new YLRxSubscriberHelper<StoreStylistNumberResult>() {
            @Override
            public void _onNext(StoreStylistNumberResult storeStylistNumberResult) {
                getMvpView().getStoreStylistNumber(storeStylistNumberResult.getData());
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }

}
