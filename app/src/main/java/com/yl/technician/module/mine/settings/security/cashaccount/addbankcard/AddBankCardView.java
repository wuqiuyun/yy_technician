package com.yl.technician.module.mine.settings.security.cashaccount.addbankcard;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.BankCardBean;
import com.yl.technician.model.vo.bean.CashAliBean;
import com.yl.technician.model.vo.bean.ServerItemBean;

import java.util.ArrayList;

/**
 * Created by lyj on 2018/11/8.
 */
public interface AddBankCardView extends IBaseView{
    void onBankSuccess(ArrayList<BankCardBean> bankCardBeans);

    void onBindSuccess();

    void onFail(int type);
}
