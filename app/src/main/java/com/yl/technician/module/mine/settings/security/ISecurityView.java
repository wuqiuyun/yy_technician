package com.yl.technician.module.mine.settings.security;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.SecurityInfoBean;

/**
 * Created by lvlong on 2018/10/8.
 */
public interface ISecurityView extends IBaseView {
    //获取账户安全信息成功
    void onAccountInfoSuccess(SecurityInfoBean bean);

    //获取账户支付密码信息成功
    void oninitPayWordInfoSuccess(String json);

    //获取账户支付密码信息失败
    void oninitPayWordInfoFail();

    //绑定微信号成功
    void onBindWxSuccess();
}
