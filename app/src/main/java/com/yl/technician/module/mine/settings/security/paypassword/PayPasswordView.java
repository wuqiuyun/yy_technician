package com.yl.technician.module.mine.settings.security.paypassword;

import com.yl.technician.base.mvp.IBaseView;

/**
 * Created by lyj on 2018/11/9.
 */
public interface PayPasswordView extends IBaseView {
    void upDatePasswordSuccess();
    void upDatePasswordFail(String s);
    void checkPasswordSuccess();
}
