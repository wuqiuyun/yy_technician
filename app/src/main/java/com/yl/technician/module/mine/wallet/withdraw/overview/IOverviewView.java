package com.yl.technician.module.mine.wallet.withdraw.overview;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.BankSumBean;
import com.yl.technician.model.vo.bean.MonthSumBean;

import java.util.ArrayList;

/**
 * Created by jinyan on 2018/12/21.
 */
public interface IOverviewView extends IBaseView{
    //账单总计
    void getMonthSum(MonthSumBean monthSumBean);
    //显示最近提现两个账户总计
    void getNewesTwoSuccess(ArrayList<BankSumBean> beans);
}
