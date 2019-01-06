package com.yl.technician.module.mine.wallet.orderdetil;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.OrderDetailBean;
import com.yl.technician.model.vo.bean.RegisterGapBetweenBean;

import java.util.List;

/**
 * Created by jinyan on 2018/12/21.
 */
public interface IAllOrderDetilView extends IBaseView{
    void findOrderDetail(List<OrderDetailBean> list);
    void getRegisterGapBetween(RegisterGapBetweenBean registerGapBetweenBean);
    void findOrderDetailFail();
}
