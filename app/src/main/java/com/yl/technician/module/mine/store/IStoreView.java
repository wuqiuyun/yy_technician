package com.yl.technician.module.mine.store;


import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.StoreBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 2018/10/10.
 */
public interface IStoreView extends IBaseView {
    void getStoreListSuccess(List<StoreBean> list);
    
    void getStoreListFail();

}
