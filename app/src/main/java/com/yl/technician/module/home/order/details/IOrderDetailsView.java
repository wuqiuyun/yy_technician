package com.yl.technician.module.home.order.details;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.OrderDetailsBean;

/**
 * Created by zm on 2018/9/27.
 */
public interface IOrderDetailsView extends IBaseView{
    /**
     * 订单详情
     */
    void onGetOrderDetailsSuccess(OrderDetailsBean data);

    /**
     * 订单完成回调
     */
    void onCompleteOrderSuccess();

    void cancelAddmoneyRequestSuccess();

    void agreeCancelOrderSuccess();

    void addMoneyRequestSuccess(String addMoney);

    void acceptOrderSuccess();

    void cancelOrderSuccess();

    void startOrderSuccess();

    void refuseOrderSuccess();

    /**
     * 更新倒计时
     *
     * @param time
     */
    void updateCountdownTime(long time);

    /**
     * 倒计时结束
     */
    void onCountdownFinish();
}
