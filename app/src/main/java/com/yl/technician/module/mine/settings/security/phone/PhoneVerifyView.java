package com.yl.technician.module.mine.settings.security.phone;

import com.yl.technician.base.mvp.IBaseView;

/**
 * Created by lvlong on 2018/10/12.
 */
public interface PhoneVerifyView extends IBaseView {
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
     * 校验手机成功
     */
    void onSuccess();
    /**
     * 更换手机号码成功
     */
    void onUpdataPhoneSuccess();
}
