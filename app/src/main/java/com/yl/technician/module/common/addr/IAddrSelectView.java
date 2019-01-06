package com.yl.technician.module.common.addr;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.AddrInfoBean;

import java.util.List;

/**
 * Created by zm on 2018/10/19.
 */
public interface IAddrSelectView extends IBaseView {
    /**
     * 返回模糊搜索检索信息
     * @param addrInfoBeans
     */
    void onSearchedList(List<AddrInfoBean> addrInfoBeans);

    /**
     * 返回周边检索信息
     * @param addrInfoBeans
     */
    void onSelectedList(List<AddrInfoBean> addrInfoBeans);
}
