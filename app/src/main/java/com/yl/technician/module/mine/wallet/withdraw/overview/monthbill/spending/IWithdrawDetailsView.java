package com.yl.technician.module.mine.wallet.withdraw.overview.monthbill.spending;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.BankSumBean;

import java.util.ArrayList;

/**
 * Created by wqy on 2018/12/21.
 */

public interface IWithdrawDetailsView extends IBaseView {
    //获取各银行提现账单总计
    void getBankSumSuccess(ArrayList<BankSumBean> beans);

    //获取各银行提现账单总计失败
    void getBankSumFail();
}
