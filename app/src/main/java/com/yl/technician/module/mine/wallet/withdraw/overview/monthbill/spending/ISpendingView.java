package com.yl.technician.module.mine.wallet.withdraw.overview.monthbill.spending;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.MonthSumBean;

/**
 * Created by jinyan on 2018/12/21.
 */
public interface ISpendingView extends IBaseView{
    void getMonthSumOut(MonthSumBean monthSumBean);
}
