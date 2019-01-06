package com.yl.technician.module.mine.settings.feedback;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.InitAppFeedbackBean;

/**
 * Created by lvlong on 2018/10/8.
 */
public interface IFeedbackView extends IBaseView {
    void initAppFeedbackSuc(InitAppFeedbackBean bean);

    void submitSuc();
}
