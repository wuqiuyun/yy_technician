package com.yl.technician.module.mine.wallet.statistics;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.OrderChartBean;
import com.yl.technician.model.vo.bean.StylistOrderBean;
import com.yl.technician.model.vo.bean.StylistSuccessOrdersBean;

import java.util.List;

/**
 * Created by lvlong on 2018/10/13.
 */
public interface IOrderStatisticsView extends IBaseView {

    //所有订单
    void onGetStylistOrderCount(StylistOrderBean bean);
    //订单统计
    void getStylistTimeSliceOrder(List<List<OrderChartBean>> orderChartBeans);
}
