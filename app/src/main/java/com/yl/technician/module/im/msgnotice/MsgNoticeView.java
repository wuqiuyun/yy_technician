package com.yl.technician.module.im.msgnotice;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.BulletinBean;

import java.util.List;

/**
 * Created by zm on 2018/9/19.
 */
public interface MsgNoticeView extends IBaseView {
    void getMsgListSuc(List<BulletinBean> list);

    void getMsgListFail();
}

