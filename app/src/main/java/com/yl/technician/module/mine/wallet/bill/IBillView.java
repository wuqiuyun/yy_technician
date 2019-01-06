package com.yl.technician.module.mine.wallet.bill;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.TotalBillBean;

import java.util.ArrayList;

/**
 * Created by wqy on 2018/12/18.
 */

public interface IBillView extends IBaseView {
    void getBillSuccess(ArrayList<TotalBillBean> list);

    void getBillFail();
}
