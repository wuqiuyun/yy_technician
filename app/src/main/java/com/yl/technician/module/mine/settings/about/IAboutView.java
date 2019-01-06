package com.yl.technician.module.mine.settings.about;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.AppInfoBean;

/**
 * Created by lvlong on 2018/10/8.
 */
public interface IAboutView extends IBaseView {
    void getAppInfoSuc(AppInfoBean appInfoBean);
}
