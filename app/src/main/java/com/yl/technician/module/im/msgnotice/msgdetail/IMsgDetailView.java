package com.yl.technician.module.im.msgnotice.msgdetail;


import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.BulletinDetailBean;

/**
 * Created by Lizhuo on 2018/11/20.
 */
public interface IMsgDetailView extends IBaseView {
    void getDetailSuc(BulletinDetailBean detail);
    
    void getDetailFail();
}
