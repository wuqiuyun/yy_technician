package com.yl.technician.module.mine.wallet.bill;

import com.yl.technician.api.BillApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.TotalBillResult;

/**
 * Created by wqy on 2018/12/18.
 */

public class BillPresenter extends BasePresenter<IBillView> {

    /**
     * 收入/支出记录
     *
     * @param month
     * @param page
     * @param size
     * @param type
     */
    public void getBill(String month, int page, int size, int type) {
        new BillApi().getBill(month, page, size, type, new YLRxSubscriberHelper<TotalBillResult>() {
            @Override
            public void _onNext(TotalBillResult totalBillResult) {
                if (null != totalBillResult.getData()) {
                    getMvpView().getBillSuccess(totalBillResult.getData());
                } else {
                    getMvpView().getBillFail();
                }
            }
        });
    }
}
