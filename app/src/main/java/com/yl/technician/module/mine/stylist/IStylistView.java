package com.yl.technician.module.mine.stylist;


import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.GetStylistBean;

import java.util.List;

/**
 * Created by zm on 2018/10/10.
 */
public interface IStylistView extends IBaseView {
    void getStylistSuccess(List<GetStylistBean> stylistBeanList);
    void getStylistFail();
}
