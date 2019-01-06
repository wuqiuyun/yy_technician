package com.yl.technician.module.mine.wallet.bill.details;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.BillDetailsBean;

/**
 * Created by wqy on 2018/12/18.
 */

public interface IBillDetailsView extends IBaseView {
    void getDetailSuccess(BillDetailsBean billDetailsBean);

    void getDetailFail();
}
