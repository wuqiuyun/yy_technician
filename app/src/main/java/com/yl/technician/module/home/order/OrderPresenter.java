package com.yl.technician.module.home.order;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.OrderApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.GetOrderListResult;

/**
 * Created by zm on 2018/9/20.
 */
public class OrderPresenter extends BasePresenter<IOrderView>{

    /**
     * 获取订单列表
     * @param status
     * @param page
     * @param size
     */
    public void getOrderList(int status, int page, int size) {
        new OrderApi().getOrderPage(status, page, size, new YLRxSubscriberHelper<GetOrderListResult>() {
            @Override
            public void _onNext(GetOrderListResult baseResponse) {
                getMvpView().onGetOrderListSuccess(baseResponse.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().onGetOrderListFail();
            }
        });
    }
}
