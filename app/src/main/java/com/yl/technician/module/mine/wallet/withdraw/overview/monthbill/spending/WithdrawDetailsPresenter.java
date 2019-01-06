package com.yl.technician.module.mine.wallet.withdraw.overview.monthbill.spending;

import com.yl.technician.api.TakeApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.BankSumResult;

/**
 * Created by wqy on 2018/12/21.
 */

public class WithdrawDetailsPresenter extends BasePresenter<IWithdrawDetailsView> {
    /**
     * 各银行提现账单总计
     *
     * @param month
     */
    public void getBankSum(String month) {
        new TakeApi().getBankSum(month, new YLRxSubscriberHelper<BankSumResult>() {
            @Override
            public void _onNext(BankSumResult bankSumResult) {
                if (null != bankSumResult.getData()) {
                    getMvpView().getBankSumSuccess(bankSumResult.getData());
                } else {
                    getMvpView().getBankSumFail();
                }
            }
        });
    }
}
