package com.yl.technician.module.home;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.BannerBean;
import com.yl.technician.model.vo.bean.StoreOrderStatisticalBean;
import com.yl.technician.model.vo.bean.StylistAuthApplyBean;

import java.util.ArrayList;

/**
 * Created by zm on 2018/9/19.
 */
public interface IHomeView extends IBaseView {
    /**
     * 获取认证信息成功回调
     */
    void onGetStoreAuthApplyInfoSuccess(StylistAuthApplyBean bean);

    /**
     * @param bean
     */
    void onGetStoreOrderStatisticalSuccess(StoreOrderStatisticalBean bean);

    /**
     * banner
     * @param beans
     */
    void getBannerSuccess(ArrayList<BannerBean> beans);

}
