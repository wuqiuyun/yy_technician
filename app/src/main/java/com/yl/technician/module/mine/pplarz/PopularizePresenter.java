package com.yl.technician.module.mine.pplarz;

import android.content.Context;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.RecomUserApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.FindInviteResult;
import com.yl.technician.model.vo.result.FindReCodeResult;
import com.yl.technician.model.vo.result.RecommendResult;

/**
 * Created by zm on 2018/12/28.
 */
public class PopularizePresenter extends BasePresenter<IPopularizeView> {

    public void recommend(Context context) {
        new RecomUserApi().recommend(new YLRxSubscriberHelper<RecommendResult>(context, true) {
            @Override
            public void _onNext(RecommendResult baseResponse) {
                getMvpView().setIncomeData(baseResponse.getData());
            }
        });
    }

    /**
     * 获取自己的推荐人(上级)
     */
    public void findInvite(){
        new RecomUserApi().findInvite(new YLRxSubscriberHelper<FindInviteResult>() {
            @Override
            public void _onNext(FindInviteResult findInviteResult) {
                getMvpView().findInviteSuc(findInviteResult.getData());
            }
        });
    }

    /**
     * 获取我的推荐码
     */
    public void findReCode(){
        new RecomUserApi().findReCode(new YLRxSubscriberHelper<FindReCodeResult>() {
            @Override
            public void _onNext(FindReCodeResult findReCodeResult) {
                if (null != findReCodeResult.getData()) getMvpView().findReCodeSuc(findReCodeResult.getData());
                else getMvpView().showToast("获取我的推荐码失败");
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
}
