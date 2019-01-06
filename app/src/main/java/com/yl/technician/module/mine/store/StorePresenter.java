package com.yl.technician.module.mine.store;


import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.CollectionApi;
import com.yl.technician.api.FootPrintApi;
import com.yl.technician.api.JoinStoreApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.requestbody.SortSearchRequesetBody;
import com.yl.technician.model.vo.result.StoreListResult;

/**
 * Created by zm on 2018/10/10.
 */
public class StorePresenter extends BasePresenter<IStoreView> {
    //我的收藏——门店列表
    public void getStoreCollection(double lat, double lng, int page, int size, String userId) {
        new CollectionApi().getStoreCollection(lat, lng, page, size, userId, new YLRxSubscriberHelper<StoreListResult>() {
            @Override
            public void _onNext(StoreListResult result) {
                getMvpView().getStoreListSuccess(result.getData());
            }

            @Override
            public void _onError(ApiException error) {
                if (getMvpView()!=null)getMvpView().getStoreListFail();
            }
        });
    }

    //我的足迹——门店列表
    public void getStoreFoot(double lat, double lng, int page, int size, String userId) {
        new FootPrintApi().getStoreFoot(lat, lng, page, size, userId, new YLRxSubscriberHelper<StoreListResult>() {
            @Override
            public void _onNext(StoreListResult result) {
                if (null != result.getData() ) {
                    getMvpView().getStoreListSuccess(result.getData());
                } else {
                    getMvpView().getStoreListFail();
                }
            }

            @Override
            public void _onError(ApiException error) {
                if (getMvpView()!=null)getMvpView().getStoreListFail();
            }
        });
    }

    //我签约/入驻的门店
    public void getMyStore(String lat,String lng,String userId) {
        new JoinStoreApi().myStore( lat, lng, userId,new YLRxSubscriberHelper<StoreListResult>() {
            @Override
            public void _onNext(StoreListResult result) {
                getMvpView().getStoreListSuccess(result.getData());
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if (getMvpView()!=null)getMvpView().getStoreListFail();
            }
        });
    }
    //我签约/入驻的门店
    public void sortSearch(SortSearchRequesetBody sortSearchRequesetBody) {
        new JoinStoreApi().sortSearch(sortSearchRequesetBody,new YLRxSubscriberHelper<StoreListResult>() {
            @Override
            public void _onNext(StoreListResult result) {
                getMvpView().getStoreListSuccess(result.getData());
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if (getMvpView()!=null)getMvpView().getStoreListFail();
            }
        });
    }
    //搜索
    public void search(String search,String lng,String lat,String userId) {
        new JoinStoreApi().search(search,lng,lat,userId,new YLRxSubscriberHelper<StoreListResult>() {
            @Override
            public void _onNext(StoreListResult result) {
                getMvpView().getStoreListSuccess(result.getData());
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if (getMvpView()!=null)getMvpView().getStoreListFail();
            }
        });
    }
}
