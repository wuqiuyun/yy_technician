package com.yl.technician.module.mine.pplarz.role;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.IncomeRecordBean;

import java.util.ArrayList;

/**
 * Created by zm on 2019/1/4.
 */
public interface IncomeRoleDetailsView extends IBaseView {
    void setData(ArrayList<IncomeRecordBean> datas);
}
