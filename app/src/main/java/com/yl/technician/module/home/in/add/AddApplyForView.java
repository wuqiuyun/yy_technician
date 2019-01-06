package com.yl.technician.module.home.in.add;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.AreaBean;
import com.yl.technician.model.vo.bean.ServerTypeBean;

import java.util.ArrayList;

/**
 * Created by zm on 2018/10/10.
 */
public interface AddApplyForView extends IBaseView{
    void getAllServerType(ArrayList<ServerTypeBean> serverTypeBeans);
    void getArea(ArrayList<AreaBean> areaBeans);
}
