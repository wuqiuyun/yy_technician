package com.yl.technician.module.mine.wallet.bill.card;

import com.yl.technician.api.TakeApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.BankDetailsResult;
import com.yl.technician.model.vo.result.BankSumResult;

/**
 * Created by wqy on 2018/12/18.
 */

public class CardDetailsPresenter extends BasePresenter<ICardDetailsView> {

    /**
     * 当前银行提现记录
     *
     * @param month
     * @param shortName
     * @param page
     * @param size
     */
    public void showBanks(String month, String shortName, String accountno, int page, int size) {
        new TakeApi().showBanks(month, shortName, accountno, page, size, new YLRxSubscriberHelper<BankDetailsResult>() {
            @Override
            public void _onNext(BankDetailsResult bankDetailsResult) {
                if (null != bankDetailsResult.getData()) {
                    getMvpView().showBanksSuccess(bankDetailsResult.getData());
                } else {
                    getMvpView().showBanksFail();
                }
            }
        });
    }

    /**
     * 当前银行提现记录和
     *
     * @param month
     * @param shortName
     * @param page
     * @param size
     */
    public void showBanksSum(String month, String shortName, String accountno, int page, int size) {
        new TakeApi().showBanksSum(month, shortName, accountno, page, size, new YLRxSubscriberHelper<BankDetailsResult>() {
            @Override
            public void _onNext(BankDetailsResult bankDetailsResult) {
                if (null != bankDetailsResult.getData()) {
                    getMvpView().showBanksSumSuccess(bankDetailsResult.getData());
                } else {
                    getMvpView().showBanksSumFail();
                }
            }
        });
    }
}
