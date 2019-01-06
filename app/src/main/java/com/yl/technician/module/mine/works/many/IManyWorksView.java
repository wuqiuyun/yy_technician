package com.yl.technician.module.mine.works.many;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.OpusListBean;

/**
 * Created by zm on 2018/10/13.
 */
public interface IManyWorksView extends IBaseView {
    void getOpusListSuc(OpusListBean opusList);
    
    void getOpusListFail();
    
    void getOpusListScreenSuc(OpusListBean opusList);
    
    void getOpusListScreenFail();
}
