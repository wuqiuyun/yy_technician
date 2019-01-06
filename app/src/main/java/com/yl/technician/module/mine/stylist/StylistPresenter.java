package com.yl.technician.module.mine.stylist;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.CollectionApi;
import com.yl.technician.api.FootPrintApi;
import com.yl.technician.api.StoreApi;
import com.yl.technician.api.StorestylistApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.GetStylistResult;
import com.yl.technician.model.vo.result.StylistResult;


/**
 * Created by zm on 2018/10/10.
 */
public class StylistPresenter extends BasePresenter<IStylistView> {

    //我的收藏——美发师列表
    public void getStylistCollection(int page, int size, String userId) {
        new CollectionApi().getStylistCollection(page, size, userId, new YLRxSubscriberHelper<GetStylistResult>() {
            @Override
            public void _onNext(GetStylistResult result) {
                getMvpView().getStylistSuccess(result.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if (getMvpView()!=null)getMvpView().getStylistFail();
            }
        });
    }

    //我的足迹——美发师列表
    public void getStylistFoot(int page, int size, String userId) {
        new FootPrintApi().getStylistFoot(page, size, userId, new YLRxSubscriberHelper<GetStylistResult>() {
            @Override
            public void _onNext(GetStylistResult result) {
                if (null != result.getData() ) {
                    getMvpView().getStylistSuccess(result.getData());
                } else {
                    getMvpView().getStylistFail();
                }
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if (getMvpView()!=null)getMvpView().getStylistFail();
            }
        });
    }
    //门店美发师列表
    public void getMyStylist(int nexus, String storeId) {
        new StorestylistApi().storeStylist(nexus, storeId, new YLRxSubscriberHelper<GetStylistResult>()
        {
            @Override
            public void _onNext(GetStylistResult result) {
                getMvpView().getStylistSuccess(result.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if (getMvpView()!=null)getMvpView().getStylistFail();
            }
        });
    }

    //门店搜索美发师列表
    public void storeStylistSearch(String nickname,String storeId,String userId) {
        new StorestylistApi().storeStylistSearch( nickname,storeId,userId, new YLRxSubscriberHelper<GetStylistResult>()
        {
            @Override
            public void _onNext(GetStylistResult result) {
                getMvpView().getStylistSuccess(result.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if (getMvpView()!=null)getMvpView().getStylistFail();
            }
        });
    }
}
