package com.yl.technician.module.home.order.details;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.yl.technician.api.OrderApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.GetOrderResult;
import com.yl.technician.util.ActivityUtil;

/**
 * Created by zm on 2018/9/27.
 */
public class OrderDetailsPresenter extends BasePresenter<IOrderDetailsView>{
    private static final int ONECE_TIME = 1000;

    private OrderApi mOrderApi;
    private CountDownTimer mCountDownTimer;

    private OrderApi getOrderApi() {
        return mOrderApi == null ? new OrderApi() : this.mOrderApi;
    }

    /**
     * 开启倒计时
     */
    public void startCountdownTime(long pendingTime) {
        if (mCountDownTimer != null) {
            stopCountdownTime();
        }
        mCountDownTimer = new CountDownTimer(pendingTime, ONECE_TIME) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (getMvpView() != null) {
                    getMvpView().updateCountdownTime(millisUntilFinished / 1000);
                }
            }

            @Override
            public void onFinish() {
                if (getMvpView() != null) {
                    getMvpView().onCountdownFinish();
                }
            }
        };
        mCountDownTimer.start();
    }

    /**
     * 关闭倒计时
     */
    public void stopCountdownTime() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }

    /**
     * 获取订单详情
     * @param orderId
     */
    public void getOrderDetails(Context context, String orderId) {
        getOrderApi().getOrder(orderId, new YLRxSubscriberHelper<GetOrderResult>(context, true) {
            @Override
            public void _onNext(GetOrderResult baseResponse) {
                getMvpView().onGetOrderDetailsSuccess(baseResponse.getData());
            }
        });
    }

    /**
     * 完成订单
     * @param orderId
     */
    public void completeOrder(Context context, String orderId) {
        getOrderApi().completeOrder(orderId, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().onCompleteOrderSuccess();
            }
        });
    }

    /**
     * 发送提醒
     * @param orderId
     * @param status 1 发送加价提醒 2 发送评价邀请
     */
    public void remindReviews(String orderId, String status) {
        getOrderApi().remindReviews(orderId, status, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().showToast("发送提醒成功");
            }
        });
    }

    /**
     * 取消申请加价
     * @param orderId
     */
    public void cancelAddmoneyRequest(Context context, String orderId) {
        getOrderApi().cancelAddmoneyRequest(orderId, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().cancelAddmoneyRequestSuccess();
            }
        });
    }

    /**
     * 同意用户申请取消订单
     * @param orderId
     * @param status 1 同意 2 违约退款
     */
    public void agreeCancelOrder(Context context, String orderId, String status) {
        getOrderApi().agreeCancelOrder(orderId, status, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().agreeCancelOrderSuccess();
            }
        });
    }

    /**
     * 加价
     * @param orderId
     */
    public void addMoneyRequest(Context context, String orderId, String addmoney, String adddesc) {
        getOrderApi().addMoneyRequest(orderId, addmoney, adddesc, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().addMoneyRequestSuccess(addmoney);
            }
        });
    }

    /**
     * 接受订单
     * @param orderId
     */
    public void acceptOrder(Context context, String orderId) {
        getOrderApi().acceptOrder(orderId, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse getOrderResult) {
                getMvpView().acceptOrderSuccess();
            }
        });
    }

    /**
     * 取消订单
     * @param orderId
     */
    public void cancelOrder(Context context, String orderId) {
        getOrderApi().cancelOrder(orderId, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().cancelOrderSuccess();
            }
        });
    }

    /**
     * 开始订单
     * @param orderId
     * @param stylistId
     */
    public void startOrder(Context context, String orderId, String stylistId) {
        if (TextUtils.isEmpty(orderId)) {
            getMvpView().showToast("订单编号不能为空.");
            return;
        }
        getOrderApi().startOrder(orderId, stylistId, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().startOrderSuccess();
            }
        });
    }

    /**
     * 订单退款
     * @param orderId
     */
    public void refuseOrder(Context context, String orderId) {
        getOrderApi().refuseOrder(orderId, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().refuseOrderSuccess();
            }
        });
    }
}
