package com.yl.technician.module.mine.works;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.GetOpusBean;

import java.util.List;

/**
 * Created by zm on 2018/10/10.
 */
public interface IWorksView extends IBaseView {
    void getOpusListSuccess(List<GetOpusBean> list);

    void getOpusListFail();
}
