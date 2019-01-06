package com.yl.technician.module.mine.wallet.withdraw.overview;

import com.yl.technician.api.BillApi;
import com.yl.technician.api.TakeApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.BankSumResult;
import com.yl.technician.model.vo.result.MonthSumResult;

/**
 * Created by wqy on 2018/12/18.
 */

public class OverviewPresenter extends BasePresenter<IOverviewView> {

    /**
     * 账单总计
     */
    public void getMonthSum(String month) {
        new BillApi().getMonthSum(month, new YLRxSubscriberHelper<MonthSumResult>() {
            @Override
            public void _onNext(MonthSumResult monthSumResult) {
                if (null != monthSumResult.getData()) {
                    getMvpView().getMonthSum(monthSumResult.getData());
                }
            }
        });
    }

    /**
     * 显示最近提现两个账户总计
     *
     * @param month
     */
    public void getNewesTwo(String month) {
        new TakeApi().getNewesTwo(month, new YLRxSubscriberHelper<BankSumResult>() {
            @Override
            public void _onNext(BankSumResult bankSumResult) {
                    getMvpView().getNewesTwoSuccess(bankSumResult.getData());
            }
        });
    }


}
