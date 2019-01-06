package com.yl.technician.module.mine.wallet.withdraw.overview.monthbill.income;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.MonthSumBean;

/**
 * Created by jinyan on 2018/12/21.
 */
public interface IIncomeView extends IBaseView{
    void getMonthSumIn(MonthSumBean monthSumInBean);
}
