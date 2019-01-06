package com.yl.technician.module.mine.settings;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.ReCodeBean;

/**
 * Created by zm on 2018/9/29.
 */
public interface ISettingsView extends IBaseView{
    void changeNoticeSuc();

    void findReCodeSuc(ReCodeBean recode);
}
