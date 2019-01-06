package com.yl.technician.module.home.join;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.AreaBean;
import com.yl.technician.model.vo.bean.ServerTypeBean;
import com.yl.technician.model.vo.bean.StoreStylistNumberBena;

import java.util.ArrayList;

/**
 * Created by zm on 2018/10/10.
 */
public interface StoreStylistView extends IBaseView{
    void getStoreStylistNumber(StoreStylistNumberBena storeStylistNumberBena);
}
