package com.yl.technician.module.mine.works.details;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.OpusDetailBean;
import com.yl.technician.model.vo.bean.ReCodeBean;

/**
 * Created by zm on 2018/10/12.
 */
public interface IWorksDetailsView extends IBaseView{
    void getOpusDetailSuc(OpusDetailBean detail);

    void getOpusDetailFail();

    void collectSuccess();

    void collectFail();
    
    void deleteSingleSuc();
    
    void deleteAllSuc();

    void findReCodeSuc(ReCodeBean reCode);
}
