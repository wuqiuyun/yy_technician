package com.yl.technician.module.home.in.add;

import com.yl.core.component.log.DLog;
import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.AreaApi;
import com.yl.technician.api.CategoryApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.bean.AreaBean;
import com.yl.technician.model.vo.result.AreaResult;
import com.yl.technician.model.vo.result.ServerTypeResult;

/**
 * Created by zm on 2018/10/10.
 */
public class AddApplyForPresenter extends BasePresenter<AddApplyForView>{
    //获取所有类目
    public void getAll() {
        new CategoryApi().getAll(new YLRxSubscriberHelper<ServerTypeResult>() {
            @Override
            public void _onNext(ServerTypeResult result) {
                getMvpView().getAllServerType(result.getData());
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
    //获取所有地区
    public void getArea() {
        new AreaApi().getArea(new YLRxSubscriberHelper<AreaResult>() {
            @Override
            public void _onNext(AreaResult result) {
                getMvpView().getArea(result.getData());
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
    //通过用户ID获取用户区域
    public void getAreaByUserId( String userId){
        new AreaApi().getAreaByUserId(userId, new YLRxSubscriberHelper<AreaResult>() {
            @Override
            public void _onNext(AreaResult result) {
                if (null != result.getData() && result.getData().size() > 0) {
                    getMvpView().getArea(result.getData());
                } else {
                    getMvpView().showToast("定位区域失败!");
                }
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if(getMvpView()!=null)getMvpView().showToast("定位区域失败!");
            }
        });
    }
}
