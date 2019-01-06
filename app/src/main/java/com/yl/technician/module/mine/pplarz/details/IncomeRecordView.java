package com.yl.technician.module.mine.pplarz.details;


import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.IncomeRecordBean;

import java.util.ArrayList;

/**
 * Created by zm on 2019/1/4.
 */
public interface IncomeRecordView extends IBaseView {
    void setData(ArrayList<IncomeRecordBean> datas);
}
