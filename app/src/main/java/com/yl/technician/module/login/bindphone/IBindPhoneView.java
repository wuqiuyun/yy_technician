package com.yl.technician.module.login.bindphone;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.UserBean;

/**
 * Created by lyj on 2018/10/9.
 */
public interface IBindPhoneView extends IBaseView{
    void onSuccess();
    /**
     * 手机号绑定成功
     */
    void onBindPhoneSuccess(UserBean userBean);

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

}
