package com.yl.technician.module.mine.wallet.withdraw.overview.monthbill.spending;

import com.yl.technician.api.BillApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.MonthSumResult;

/**
 * Created by wqy on 2018/12/18.
 */

public class SpendingPresenter extends BasePresenter<ISpendingView> {

    /**
     * 支出总计
     * @param month 年月份:2018-12
     */
    public void getMonthSumOut(String month) {
        new BillApi().getMonthSumOut(month, new YLRxSubscriberHelper<MonthSumResult>() {
            @Override
            public void _onNext(MonthSumResult monthSumResult) {
                if (null != monthSumResult.getData()) {
                    getMvpView().getMonthSumOut(monthSumResult.getData());
                }
            }
        });
    }


}
