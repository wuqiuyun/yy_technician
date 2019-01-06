package com.yl.technician.module.im.groups.groupsedit;

import com.yl.technician.base.mvp.IBaseView;

/**
 * Created by Lizhuo on 2018/10/12.
 */
public interface GroupsEditView extends IBaseView {
    void updateSuccess();

    void updateFail();

    void deleteSuccess();

    void deleteFail();
}
