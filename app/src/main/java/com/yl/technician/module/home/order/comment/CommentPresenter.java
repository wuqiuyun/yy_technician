package com.yl.technician.module.home.order.comment;

import android.text.TextUtils;

import com.yl.technician.api.StylistCommentApi;
import com.yl.technician.api.StylistOrderCountApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.GetOrderCommentResult;
import com.yl.technician.model.vo.result.StylistManageScopeResult;

/**
 * Created by zm on 2018/11/15.
 */
public class CommentPresenter extends BasePresenter<ICommentView> {

    /**
     * 查询订单评价
     * @param orderId
     */
    public void getOrderComment(String orderId) {
        new StylistOrderCountApi().getOrderComment(orderId, new YLRxSubscriberHelper<GetOrderCommentResult>() {
            @Override
            public void _onNext(GetOrderCommentResult getOrderCommentResult) {
                getMvpView().setOrderCommentData(getOrderCommentResult.getData());
            }
        });
    }

    /**
     * 回复评论
     * @param content
     * @param stylistReviewsId
     */
    public void replyStylistComment(String content, String stylistReviewsId) {
        if (TextUtils.isEmpty(stylistReviewsId)) return;
        if (TextUtils.isEmpty(content)) {
            getMvpView().showToast("请输入回复内容.");
            return;
        }
        new StylistCommentApi().replyStoreComment(content, stylistReviewsId, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse stylistManageScopeResult) {
                getMvpView().replyStoreCommentSuccess();
            }
        });
    }
}
