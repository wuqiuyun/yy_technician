package com.yl.technician.module.mine.wallet.incomedetail;

import com.yl.technician.api.StylistIncomeDetailsApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.IncomeResult;

/**
 * Created by wqy on 2018/12/18.
 */

public class IncomeDetailsPresenter extends BasePresenter<IIncomeDetailsView> {

    /**
     * 收入余额明细 (根据时间查询)
     *
     * @param date
     * @param userId
     * @param pageNo
     * @param pageSize
     */
    public void getAssetDetailList(String date, String userId, int pageNo, int pageSize) {
        new StylistIncomeDetailsApi().getAssetDetailList(date, userId, pageNo, pageSize, new YLRxSubscriberHelper<IncomeResult>() {
            @Override
            public void _onNext(IncomeResult result) {
                if (null != result.getData()) {
                    getMvpView().getAssetDetailSuccess(result.getData());
                } else {
                    getMvpView().getAssetDetailFail();
                }
            }
        });
    }

    /**
     * 收入余额明细 (根据时间查询)
     *
     * @param date
     * @param pageNo
     * @param pageSize
     */
    public void getCoinWalletDetailList(String date, int pageNo, int pageSize) {
        new StylistIncomeDetailsApi().getCoinWalletDetailList(date, AccountManager.getInstance().getUserId(), pageNo, pageSize, new YLRxSubscriberHelper<IncomeResult>() {
            @Override
            public void _onNext(IncomeResult result) {
                if (null != result.getData()) {
                    getMvpView().getCoinDetailSuccess(result.getData());
                } else {
                    getMvpView().getCoinDetailFail();
                }

            }
        });
    }

}
