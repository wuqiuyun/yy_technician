package com.yl.technician.module.home.order;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.OrderBean;

import java.util.ArrayList;

/**
 * Created by zm on 2018/9/20.
 */
public interface IOrderView extends IBaseView{
    /**
     * 获取订单列表成功回调
     */
    void onGetOrderListSuccess(ArrayList<OrderBean> orderBeans);

    /**
     * 获取订单列表异常
     */
    void onGetOrderListFail();
}
