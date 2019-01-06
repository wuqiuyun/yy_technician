package com.yl.technician.module.home.works.upload;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.UploadWorksBean;

import java.util.List;


/*
 * Create by lvlong on  2018/11/2
 */

public interface UploadWorksView extends IBaseView {

    //脸型
    void onGetFeature(List<UploadWorksBean> list);

    //发长
    void onGetHairstyle(List<UploadWorksBean> list);

    //上传作品
    void onSave();

    /**
     * 文件上传成功
     * @param filePaths
     */
    void onUploadFileSuccess(List<String> filePaths);

}
