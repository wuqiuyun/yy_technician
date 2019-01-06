package com.yl.technician.module.mine.wallet.bill.card;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.BankDetailsBean;
import com.yl.technician.model.vo.bean.BankSumBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wqy on 2018/12/18.
 */

public interface ICardDetailsView extends IBaseView {
    void showBanksSuccess(BankDetailsBean bean);

    void showBanksFail();

    void showBanksSumSuccess(BankDetailsBean bean);

    void showBanksSumFail();
}
