package com.yl.technician.module.login.information;

import com.yl.technician.base.mvp.IBaseView;

/**
 * Created by lvlong on 2018/9/27.
 */
public interface IPerfectInformationView extends IBaseView {
    /**
     * 完成资料成功回调
     */
    void onDoUserDataSuccess();

    /**
     * 文件上传成功
     * @param filePath
     */
    void onUploadFileSuccess(String filePath);
}
