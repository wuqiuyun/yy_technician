package com.yl.technician.module.mine.wallet.orderdetil;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.StylistOrderDetailApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.OrderDetailResult;
import com.yl.technician.model.vo.result.RegisterGapBetweenResult;

/**
 * Created by wqy on 2018/12/18.
 */

public class AllOrderDetilPresenter extends BasePresenter<IAllOrderDetilView> {
    //订单明细
    public void findOrderDetail(int type,int pageNo,int pageSize) {
        new StylistOrderDetailApi().findOrderDetail(type,pageNo,pageSize,new YLRxSubscriberHelper<OrderDetailResult>() {
            @Override
            public void _onNext(OrderDetailResult orderDetailResult) {
                getMvpView().findOrderDetail(orderDetailResult.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().findOrderDetailFail();
            }
        });
    }

    //注册奖励差距
    public void getRegisterGapBetween() {
        new StylistOrderDetailApi().getRegisterGapBetween(new YLRxSubscriberHelper<RegisterGapBetweenResult>() {
            @Override
            public void _onNext(RegisterGapBetweenResult registerGapBetweenResult) {
                getMvpView().getRegisterGapBetween(registerGapBetweenResult.getData());
            }
        });
    }
}
