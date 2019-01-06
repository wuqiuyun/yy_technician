package com.yl.technician.module.im.groups.creategroup;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.daobean.GroupBean;

import java.util.List;

/**
 * Created by Lizhuo on 2018/10/16.
 */
public interface CreateGroupView extends IBaseView {
    //创建群成功
    void onSuccess(GroupBean groupbean);

    /**
     * 文件上传成功
     *
     * @param filePaths
     */
    void onUploadFileSuccess(List<String> filePaths);
}
