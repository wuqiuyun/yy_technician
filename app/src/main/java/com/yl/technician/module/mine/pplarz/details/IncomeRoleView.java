package com.yl.technician.module.mine.pplarz.details;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.result.RecommendUserListResult;

/**
 * Created by zm on 2019/1/4.
 */
public interface IncomeRoleView extends IBaseView {
     void setData(RecommendUserListResult.Data data);
     void onLoadFail();
}
