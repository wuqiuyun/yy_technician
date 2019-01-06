package com.yl.technician.module.im.redpacket.redrecords;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.result.RedRecordResult;

/**
 * Created by zhangzz on 2018/11/6.
 */
public interface RedRecordsView extends IBaseView {
    void requestSuccess(RedRecordResult redRecordResult, boolean isRefresh);
    void requestFail();
}
