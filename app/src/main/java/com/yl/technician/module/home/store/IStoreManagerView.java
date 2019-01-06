package com.yl.technician.module.home.store;


import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.NexuStatusBean;
import com.yl.technician.model.vo.bean.ReCodeBean;
import com.yl.technician.model.vo.bean.StoreManageNexusStyScroolBean;
import com.yl.technician.model.vo.bean.StoreManageScopeBean;
import com.yl.technician.model.vo.bean.StoreManageScopeInfoBean;
import com.yl.technician.model.vo.result.CheckMsgResult;
import com.yl.technician.model.vo.result.NexuStatusResult;

import java.util.ArrayList;

/**
 * Created by zm on 2018/10/12.
 */
public interface IStoreManagerView extends IBaseView {
    /**
     * 获取门店顾客评价成功
     */
    void getStoreScoreSucceed(StoreManageScopeBean storeManageScopeBean);

    /**
     * 获取门店服务范围成功
     */
    void getStoreServerScoreSucceed(StoreManageScopeBean storeManageScopeBean);

    /**
     * 获取入驻/签约理发师成功
     */
    void getNexusStyScroolSucceed(ArrayList<StoreManageNexusStyScroolBean> storeManageNexusStyScroolBean);

    /**
     * 获取门店位置信息成功
     */
    void getStoreInfoSucceed(StoreManageScopeInfoBean storeManageScopeInfoBean);

    /**
     * 修改门店收藏状态成功
     */
    void updateCollectionTypeSuc();

    /**
     * 查询当前门店，技师申请状态
     */
    void  getNexuStatus(NexuStatusBean nexuStatusBean);

    /**
     * 签约/解约成功
     */
    void  onSucceed();

    /**
     * 签约或者入驻成功
     */
    void nexusSuccess(boolean isAgree);

    void findReCodeSuc(ReCodeBean recode);

    void checkMsg(CheckMsgResult checkMsgResult);
}
