package com.yl.technician.module.home.service.add;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.ServerTypeBean;

import java.util.ArrayList;


/*
 * Create by lvlong on  2018/10/23
 */

public interface AddServiceView extends IBaseView {
    void getAllServerType(ArrayList<ServerTypeBean> serverTypeBeans);

}
