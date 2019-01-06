package com.yl.technician.module.im.groups.groupsearch;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.daobean.GroupBean;

import java.util.List;

/**
 * Created by Lizhuo on 2018/10/15.
 */
public interface GroupSearchView extends IBaseView {
    void searchGroupSuccess(List<GroupBean> list);

    void searchGroupFail();
}
