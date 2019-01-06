package com.yl.technician.module.im.transfer.transrecords;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.result.RedBagSendResult;

/**
 * Created by zhangzz on 2018/11/6.
 */
public interface TransferRecordsView extends IBaseView {
    void requestSuccess(RedBagSendResult redBagSendResult);
}
