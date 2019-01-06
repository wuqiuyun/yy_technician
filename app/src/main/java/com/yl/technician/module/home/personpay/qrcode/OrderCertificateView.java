package com.yl.technician.module.home.personpay.qrcode;

import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.StoreBean;

import java.util.List;

/**
 * Created by wqy on 2018/11/6.
 */

public interface OrderCertificateView extends IBaseView{
    void getQCodeSuccess(Object object);

    void getStoreListFail();
}
