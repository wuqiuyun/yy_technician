package com.yl.technician.module.home.orders.manager;

import com.yl.technician.api.StylistManagerApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.GetStylistManagerResult;

/**
 * Created by zm on 2018/12/17.
 */
public class OrderManagerPresenter extends BasePresenter<IOrderManagerView> {

    /**
     * 获取订单数据
     * @param date
     */
    public void getOrderDatas(String date) {
        new StylistManagerApi().timemanage(date, new YLRxSubscriberHelper<GetStylistManagerResult>() {
            @Override
            public void _onNext(GetStylistManagerResult getStylistManagerResult) {
                getMvpView().setDatas(getStylistManagerResult.getData());
            }
        });
    }
}
