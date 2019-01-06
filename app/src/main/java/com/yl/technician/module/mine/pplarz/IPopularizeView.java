package com.yl.technician.module.mine.pplarz;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.FindInviteBean;
import com.yl.technician.model.vo.bean.ReCodeBean;
import com.yl.technician.model.vo.result.RecommendResult;

/**
 * Created by zm on 2018/12/28.
 */
public interface IPopularizeView extends IBaseView {
    void setIncomeData(RecommendResult.Data data);
    void findInviteSuc(FindInviteBean findInvite);
    void findReCodeSuc(ReCodeBean reCode);
}
