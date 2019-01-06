package com.yl.technician.module.mine.wallet.withdraw.account;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.AuthApplyBean;
import com.yl.technician.model.vo.bean.CashAliBean;
import com.yl.technician.model.vo.result.AuthApplyResult;

import java.util.ArrayList;

/**
 * Created by lyj on 2018/10/30.
 */
public interface IAccountWithdrawView extends IBaseView {

    void onextractBankAccountSuccess(ArrayList<CashAliBean> cashAliBean);

    void getUserInfoSuccess(AuthApplyBean getApplyStatusBean);

    void getUserInfoFail();

}
