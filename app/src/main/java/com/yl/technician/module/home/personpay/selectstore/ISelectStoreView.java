package com.yl.technician.module.home.personpay.selectstore;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.StoreBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wqy on 2018/11/6.
 */

public interface ISelectStoreView extends IBaseView{
    void getStoreListSuccess(List<StoreBean> list);

    void getStoreListFail();
}
