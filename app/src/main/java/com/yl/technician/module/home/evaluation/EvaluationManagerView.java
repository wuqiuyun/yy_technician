package com.yl.technician.module.home.evaluation;


import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.EvaluateBean;
import com.yl.technician.model.vo.bean.StoreScopeBean;
import com.yl.technician.model.vo.bean.StylistManageScopeBean;
import com.yl.technician.model.vo.bean.SylistCommentListBean;

import java.util.ArrayList;

/**
 * Created by lvlong on 2018/10/11.
 */

public interface EvaluationManagerView extends IBaseView {
    /**
     * 获取美发师总评分成功
     */
    void getEvaluateSucceed(EvaluateBean evaluateBean);

    /**
     * 获取美发师顾客评价列表成功
     */
    void getStyistCommentListSucceed(ArrayList<SylistCommentListBean> storeManageScopeBean);

    /**
     * 获取美发师顾客评价列表失败
     */
    void getStylistCommentListFail();

    /**
     * 回复顾客评价成功
     */
    void replyStoreCommentSucceed();

    //获取门店评分成功
    void onGetStoreScore(StoreScopeBean bean);


}
