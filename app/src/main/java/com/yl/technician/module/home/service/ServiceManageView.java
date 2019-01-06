package com.yl.technician.module.home.service;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.ServerItemBean;

import java.util.ArrayList;

/**
 * Created by zm on 2018/10/10.
 */
public interface ServiceManageView extends IBaseView{
    void onSuccess(ArrayList<ServerItemBean> serverItemBeans);
    void operationSuccess();
    void onFail();
}
