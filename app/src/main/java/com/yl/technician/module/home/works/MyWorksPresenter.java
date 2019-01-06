package com.yl.technician.module.home.works;

import com.yl.technician.api.StylistOpusApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.StylistOpusResult;


/*
 * Create by lvlong on  2018/11/2
 */

public class MyWorksPresenter extends BasePresenter<MyWorksView> {

    //获取作品列表
    public void getStylistOpusByStylistId(){
        new StylistOpusApi().getStylistOpusByStylistId(new YLRxSubscriberHelper<StylistOpusResult>() {
            @Override
            public void _onNext(StylistOpusResult result) {
                getMvpView().onGetStylistOpusByStylistId(result.getData());
            }
        });
    }
}
