package com.yl.technician.module.home.time;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.TimeBean;
import com.yl.technician.model.vo.bean.TimeManageDayBean;
import com.yl.technician.model.vo.bean.TimeManageMonthBean;
import com.yl.technician.model.vo.result.TimeManageDayResult;
import com.yl.technician.model.vo.result.TimeResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvlong on 2018/10/11.
 */
public interface TimeManageView extends IBaseView {
    TimeResult getStylistTimeSettingSucs(TimeResult result);
    void setStylistServiceSwitchSuc();
    void updateOrSavesSuc(List<TimeBean> timeBeanList);
    void getStylistTimeSettingFailed();
    void getStylistStatusByStylistId(int type);

    void getStylistTimeByStylistIdAndDateSuc(TimeManageDayBean timeManageDayBean);

    void getStylistTimeByStylistIdAndDateFailed();

    void getStylistTimeListByStylistIdAndDatesSuc( ArrayList<TimeManageMonthBean> timeManageMonthBean);

    void updateOrSaveStylistTimeSuc();

}
