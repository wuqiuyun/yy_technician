package com.yl.technician.module.mine.works;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.CollectionApi;
import com.yl.technician.api.FootPrintApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.GetOpusResult;

/**
 * Created by zm on 2018/10/10.
 */
public class WorksPresenter extends BasePresenter<IWorksView> {
    //我的收藏——作品列表
    public void getOpusCollection(int page, int size, String userId) {
        new CollectionApi().getOpusCollection(page, size, userId, new YLRxSubscriberHelper<GetOpusResult>() {
            @Override
            public void _onNext(GetOpusResult result) {
                    getMvpView().getOpusListSuccess(result.getData());
            }

            @Override
            public void _onError(ApiException error) {
                if (getMvpView()!=null)getMvpView().getOpusListFail();
            }
        });


    }

    //我的足迹——作品列表
    public void getOpusFoot(int page, int size, String userId) {
        new FootPrintApi().getOpusFoot(page, size, userId, new YLRxSubscriberHelper<GetOpusResult>() {
            @Override
            public void _onNext(GetOpusResult result) {
                if (null != result.getData() && result.getData().size() > 0) {
                    getMvpView().getOpusListSuccess(result.getData());
                } else {
                    getMvpView().getOpusListFail();
                }
            }

            @Override
            public void _onError(ApiException error) {
                if (getMvpView()!=null)getMvpView().getOpusListFail();
            }
        });
    }

}
