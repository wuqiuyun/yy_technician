package com.yl.technician.module.home.service.setting;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.ServerInfoBean;
import com.yl.technician.model.vo.bean.ServerTypeBean;
import com.yl.technician.model.vo.result.SarverInfoResult;
import com.yl.technician.model.vo.result.ServerTypeResult;

import java.util.ArrayList;

/**
 * Created by lvlong on 2018/10/9.
 */
public interface ServiceSettingView extends IBaseView {
    //获取服务信息
    void onSuccess(ServerInfoBean serverInfoBean);
    void saveSuccess();
    void getServerTypeSuccess(ServerTypeBean serverTypeBeans);
    void onUploadFileSuccess(String s);
}
