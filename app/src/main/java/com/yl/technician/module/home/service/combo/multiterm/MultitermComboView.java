package com.yl.technician.module.home.service.combo.multiterm;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.ServerInfoBean;
import com.yl.technician.model.vo.bean.ServerTypeBean;

import java.util.ArrayList;


/*
 * Create by lvlong on  2018/10/24
 */

public interface MultitermComboView extends IBaseView {
    //获取服务信息
    void onSuccess(ServerInfoBean serverInfoBean);
    //获取套餐二可选类型
    void getServerType(ArrayList<ServerTypeBean> serverTypeBeans);
    //保存套餐
    void saveSuccess();
}
