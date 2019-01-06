package com.yl.technician.module.login.forgetpwd;

import com.yl.technician.base.mvp.IBaseView;

/**
 * Created by lvlong on 2018/9/27.
 */
public interface IForgetPasswordView extends IBaseView {
    /**
     * 更新倒计时
     *
     * @param time
     */
    void updateCountdownTime(long time);

    /**
     * 倒计时结束
     */
    void onCountdownFinish();

    /**
     * 修改密码成功
     */
    void onUpdatePwdSuccess();
}
