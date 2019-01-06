package com.yl.technician.module.home.orders.manager;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.OrderManagerBean;

import java.util.ArrayList;

/**
 * Created by zm on 2018/12/17.
 */
public interface IOrderManagerView extends IBaseView {
    void setDatas(ArrayList<OrderManagerBean> datas);
}
