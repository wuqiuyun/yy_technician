package com.yl.technician.module.home.order.comment;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.OrderCommentBean;

/**
 * Created by zm on 2018/11/15.
 */
public interface ICommentView extends IBaseView{
    void setOrderCommentData(OrderCommentBean commentBean);
    void replyStoreCommentSuccess();
}
