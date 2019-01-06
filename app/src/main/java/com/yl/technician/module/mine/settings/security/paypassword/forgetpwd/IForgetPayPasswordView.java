package com.yl.technician.module.mine.settings.security.paypassword.forgetpwd;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.UserBean;

/**
 * Created by lyj on 2018/10/9.
 */
public interface IForgetPayPasswordView extends IBaseView{
    void onSuccess();
    /**
     * 短信验证成功
     */
    void onCodePhoneSuccess();

    /**
     * 身份证号码验证成功
     */
    void onInitIdNumberSuccess();

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
