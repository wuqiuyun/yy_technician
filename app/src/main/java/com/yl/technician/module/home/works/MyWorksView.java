package com.yl.technician.module.home.works;

/*
 * Create by lvlong on  2018/11/2
 */

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.MyWorksBean;

import java.util.List;

public interface MyWorksView extends IBaseView {

    //所有订单
    void onGetStylistOpusByStylistId(List<MyWorksBean> list);
}
