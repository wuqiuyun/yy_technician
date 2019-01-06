package com.yl.technician.module.mine.settings.security.cashaccount;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.AuthApplyBean;
import com.yl.technician.model.vo.bean.CashAliBean;
import com.yl.technician.model.vo.bean.ServerItemBean;

import java.util.ArrayList;

/**
 * Created by lyj on 2018/11/8.
 */
public interface CashAccountManageView extends IBaseView{
    void onextractaLIAccountSuccess(ArrayList<CashAliBean> cashAliBean);

    void onextractBankAccountSuccess(ArrayList<CashAliBean> cashAliBean);

    void onUnBindSuccess();

    void onSuccess(ArrayList<ServerItemBean> serverItemBeans);
    void onFail(int type);

    void getUserInfoSuccess(AuthApplyBean getApplyStatusBean);

    void getUserInfoFail();

}
