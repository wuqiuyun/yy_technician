package com.yl.technician.module.mine.pplarz.role;

import android.content.Context;

import com.yl.technician.api.RecomUserApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.IncomeRecordsResult;

/**
 * Created by zm on 2019/1/4.
 */
public class IncomeRoleDetailsPresenter extends BasePresenter<IncomeRoleDetailsView> {

    public void recommendUserIncome(Context context, String inviteUserId, int roleType, int page) {
        new RecomUserApi().recommendUserIncome(inviteUserId, roleType, page, new YLRxSubscriberHelper<IncomeRecordsResult>(context, true) {
            @Override
            public void _onNext(IncomeRecordsResult baseResponse) {
                getMvpView().setData(baseResponse.getData());
            }
        });
    }
}
