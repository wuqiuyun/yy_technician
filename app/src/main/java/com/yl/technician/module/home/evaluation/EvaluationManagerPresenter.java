package com.yl.technician.module.home.evaluation;

import android.content.Context;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.StylistCommentApi;
import com.yl.technician.api.StylistOrderCountApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.bean.StylistManageScopeBean;
import com.yl.technician.model.vo.bean.SylistCommentListBean;
import com.yl.technician.model.vo.result.GetEvaluateResult;
import com.yl.technician.model.vo.result.StoreScopeResult;
import com.yl.technician.model.vo.result.StylistCommentListResult;
import com.yl.technician.model.vo.result.StylistManageScopeResult;

import java.util.ArrayList;

/**
 * Created by lvlong on 2018/10/11.
 */
public class EvaluationManagerPresenter extends BasePresenter<EvaluationManagerView> {
    /**
     *  回复客户评论
     *
     * context (string, optional): 内容
     * storeId (integer, optional): 门店id（非本人门店必传） ,
     * userId (integer, optional): 用户id
     *
     */
    public void replyStoreComment(String msg, String commentId, Context context) {
        new StylistCommentApi().replyStoreComment(msg,commentId,new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                // callback
                getMvpView().replyStoreCommentSucceed();
            }
        });
    }


    /**
     * 美发师顾客评价列表
     *
     * page 页数
     * id (integer, optional): 用户id
     * size (integer, optional): 每一页的大小
     *
     * */
    public void getStylistCommentList(String stylistId,int page,int size, Context context) {
        new StylistCommentApi().getStylistCommentList(stylistId,page, size,new YLRxSubscriberHelper<StylistCommentListResult>(context,true) {
            @Override
            public void _onNext(StylistCommentListResult baseResponse) {
                ArrayList<SylistCommentListBean> storeCommentListBean = baseResponse.getData();
                // callback
                getMvpView().getStyistCommentListSucceed(storeCommentListBean);
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().getStylistCommentListFail();
            }
        });
    }

    /**
     * 美发师顾客评价
     *
     * storeId (integer, optional): 门店ID，非本人门店必传 ,
     * userId (integer, optional): 用户id
     * */
    public void getEvaluate(String stylistId,Context context) {

        new StylistOrderCountApi().getEvaluate(stylistId ,new YLRxSubscriberHelper<GetEvaluateResult>(context,true) {
            @Override
            public void _onNext(GetEvaluateResult getEvaluateResult) {
                getMvpView().getEvaluateSucceed(getEvaluateResult.getData());
            }
        });
    }

    //门店评分
    public void getStoreScore(String userId , String storeId,Context context){
        new StylistOrderCountApi().getStoreScore(userId, storeId, new YLRxSubscriberHelper<StoreScopeResult>(context,true) {
            @Override
            public void _onNext(StoreScopeResult storeScopeResult) {
                getMvpView().onGetStoreScore(storeScopeResult.getData());
            }
        });
    }

    //门店评论列表
    public void getStoreCommentList(String storeId,int page,int size,Context context){
        new StylistOrderCountApi().getStoreCommentList(storeId, page, size, new YLRxSubscriberHelper<StylistCommentListResult>(context,true) {
            @Override
            public void _onNext(StylistCommentListResult stylistCommentListResult) {
                ArrayList<SylistCommentListBean> storeCommentListBean = stylistCommentListResult.getData();
                getMvpView().getStyistCommentListSucceed(storeCommentListBean);
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().getStylistCommentListFail();
            }
        });
    }
}
