package com.yl.technician.module.mine.pplarz.details;

import android.content.Context;

import com.yl.technician.api.RecomUserApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.IncomeRecordsResult;

/**
 * Created by zm on 2019/1/4.
 */
public class IncomeRecordPresenter extends BasePresenter<IncomeRecordView> {
    private RecomUserApi userApi;

    private RecomUserApi getRecomUserApi() {
        return userApi == null ? new RecomUserApi() : userApi;
    }

    /**
     * 推荐收益列表
     * @param context
     * @param roleType
     * @param page
     */
    public void recommendIncomeList(Context context, int roleType, int page) {
        new RecomUserApi().recommendIncomeList(roleType, page, new YLRxSubscriberHelper<IncomeRecordsResult>(context, true) {
            @Override
            public void _onNext(IncomeRecordsResult baseResponse) {
                getMvpView().setData(baseResponse.getData());
            }
        });
    }

    /**
     * 推荐收益列表按月筛选
     * @param month
     * @param roleType
     * @param page
     */
    public void recommendIncomeByMonth(String month, int roleType, int page) {
        new RecomUserApi().recommendIncomeByMonth(month, roleType, page, new YLRxSubscriberHelper<IncomeRecordsResult>() {
            @Override
            public void _onNext(IncomeRecordsResult baseResponse) {
                getMvpView().setData(baseResponse.getData());
            }
        });
    }
}
