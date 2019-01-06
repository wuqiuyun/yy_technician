package com.yl.technician.module.mine.wallet.statistics;

import com.yl.technician.api.StylistOrderApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.OrderChartResult;
import com.yl.technician.model.vo.result.getStylistOrderCountResult;

/**
 * Created by lvlong on 2018/10/13.
 */
public class OrderStatisticsPresenter extends BasePresenter<IOrderStatisticsView> {

    //获取所有的订单
    public void getStylistOrderCount(String type) {
        new StylistOrderApi().getStylistOrderCount(type, new YLRxSubscriberHelper<getStylistOrderCountResult>() {
            @Override
            public void _onNext(getStylistOrderCountResult getStylistOrderCountResult) {
                getMvpView().onGetStylistOrderCount(getStylistOrderCountResult.getData());
            }
        });
    }

    //获取订单统计
    public void getStylistTimeSliceOrder(String type) {
        new StylistOrderApi().getStylistTimeSliceOrder(type, new YLRxSubscriberHelper<OrderChartResult>() {
            @Override
            public void _onNext(OrderChartResult orderChartResult) {
                getMvpView().getStylistTimeSliceOrder(orderChartResult.getData());
            }
        });
    }
}
