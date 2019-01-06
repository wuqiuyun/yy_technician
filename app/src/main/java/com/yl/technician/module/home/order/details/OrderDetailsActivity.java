package com.yl.technician.module.home.order.details;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.DateUtil;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityOrderDetailsBinding;
import com.yl.technician.model.vo.bean.EventBean;
import com.yl.technician.model.vo.bean.OrderDetailsBean;
import com.yl.technician.model.vo.bean.OrderRefundBean;
import com.yl.technician.module.home.order.comment.CommentActivity;
import com.yl.technician.module.home.qrcode.ScanCodeActivity;
import com.yl.technician.module.im.chat.ChatActivity;
import com.yl.technician.util.BigDecimalUtils;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.util.PhoneUtil;
import com.yl.technician.util.easyutils.EasyUtil;
import com.yl.technician.widget.dialog.YLDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.annotations.NonNull;

/**
 * 订单详情
 * <p>
 * Create by zm on 2018/10/27
 */
@CreatePresenter(OrderDetailsPresenter.class)
public class OrderDetailsActivity extends BaseMvpAppCompatActivity<IOrderDetailsView, OrderDetailsPresenter>
        implements IOrderDetailsView, ClickHandler {
    private static final String EXTRAS_ORDER_ID = "order_id";

    private ActivityOrderDetailsBinding detailsBinding;
    private String orderId;

    private OrderDetailsBean data;

    /**
     * start orderId
     *
     * @param context
     * @param orderId 订单id
     */
    public static void startActivity(Context context, @NonNull String orderId) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_ORDER_ID, orderId);
        startActivity(context, OrderDetailsActivity.class, bundle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadData();
    }

    @Override
    protected void init() {
        EventBus.getDefault().register(this);

        hasExtras();
        initView();
        loadData();
    }

    private void hasExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderId = bundle.getString(EXTRAS_ORDER_ID);
        }
        // orderId不能为空
        if (TextUtils.isEmpty(orderId)) {
            finish();
            return;
        }
    }

    private void initView () {
        StatusBarUtil.setLightMode(this);
        detailsBinding = (ActivityOrderDetailsBinding) viewDataBinding;
        detailsBinding.setClick(this);
        // 返回
        detailsBinding.titleView.setLeftClickListener(v -> {
            finish();
        });
    }

    private void loadData() {
        getMvpPresenter().getOrderDetails(this, orderId);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getMvpPresenter().stopCountdownTime();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onGetOrderDetailsSuccess(OrderDetailsBean data) {
        this.data = data;
        // 倒计时
        if (data.getPendingTime() <= 0) {
            detailsBinding.tvOrderHint.setVisibility(View.GONE);
            getMvpPresenter().stopCountdownTime();
        }else {
            getMvpPresenter().startCountdownTime(data.getPendingTime() * 1000);
        }
        // 头像
        ImageLoaderConfig config = new ImageLoaderConfig.Builder().
                setCropType(ImageLoaderConfig.CENTER_INSIDE).
                setAsBitmap(true).
                setCropCircle(true).
                setPlaceHolderResId(R.drawable.icon_head_pic_def).
                setErrorResId(R.drawable.icon_head_pic_def).
                setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE).
                setPrioriy(ImageLoaderConfig.LoadPriority.HIGH).build();
        ImageLoader.loadImage(detailsBinding.ivPhoto, data.getUserPath(), config, null);
        // 标签
        detailsBinding.tvLabel.setText(FormatUtil.Formatstring(data.getUserLabel()));
        // 姓名
        detailsBinding.tvName.setText(FormatUtil.Formatstring(data.getUserNickname()));
        // 性别
        setDrawableRight(detailsBinding.tvName, ContextCompat.getDrawable(this,
                data.getUserGender() == 1 ? R.drawable.icon_boy : R.drawable.icon_girl));
        // 服务项目
        detailsBinding.orderProject.setContentText(data.getOrdername());
        // 服务门店
        detailsBinding.orderStore.setContentText(data.getStoreName());
        // 预约时间
        detailsBinding.orderDateRese.setContentText(DateUtil.date2Str(data.getOrdertime(), DateUtil.FORMAT_YMDHM));
        detailsBinding.orderDateRese.setSubContentText(data.getDatename());
        // 开始时间
        if (data.getStarttime() > 0L) {
            detailsBinding.orderDateStart.setVisibility(View.VISIBLE);
            detailsBinding.orderDateStart.setContentText(DateUtil.date2Str(data.getStarttime(), DateUtil.FORMAT_YMDHM));
        }
        // 完成时间
        if (data.getEndtime() > 0L) {
            detailsBinding.orderDateStop.setVisibility(View.VISIBLE);
            detailsBinding.orderDateStop.setContentText(DateUtil.date2Str(data.getEndtime(), DateUtil.FORMAT_YMDHM));
        }else {
            detailsBinding.orderDateStop.setVisibility(View.GONE);
        }
        // 取消时间
        if (data.getCanceltime() > 0L) {
            detailsBinding.orderDateStart.setTitleText(getString(R.string.order_date_cancel));
            detailsBinding.orderDateStart.setVisibility(View.VISIBLE);
            detailsBinding.orderDateStart.setContentText(DateUtil.date2Str(data.getCanceltime(), DateUtil.FORMAT_YMDHM));
        }
        // 退款时间
        if (data.getRefundtime() > 0L) {
            detailsBinding.orderDateStop.setTitleText(getString(R.string.order_date_refund));
            detailsBinding.orderDateStop.setVisibility(View.VISIBLE);
            detailsBinding.orderDateStop.setContentText(DateUtil.date2Str(data.getRefundtime(), DateUtil.FORMAT_YMDHM));
        }
        // 原预约时间
        if (data.getOldordertime() > 0L ) {
            detailsBinding.orderDatePromise.setVisibility(View.VISIBLE);
            detailsBinding.orderDatePromise.setContentText(DateUtil.date2Str(data.getOldordertime(), DateUtil.FORMAT_YMDHM));
        }
        // 订单编号
        detailsBinding.orderId.setContentText(data.getOrderno());
        // 订单金额
        detailsBinding.orderAmount.setContentText(String.format(getString(R.string.RMB), String.valueOf(data.getOrderamount())));
        // 支付金额
        detailsBinding.payAmount.setContentText(String.format(getString(R.string.RMB), String.valueOf(data.getPayamount())));
        // 订单备注
        detailsBinding.orderRemarks.setContentText(data.getRemark());
        // 预计收入
        if (data.getStatus() == 3 ||data.getStatus() == 4 || data.getStatus() == 6) {//待确认 待核销
            detailsBinding.clearAmount.setContentText(String.format(getString(R.string.RMB), TextUtils.isEmpty(data.getClearamount())?"0.00":data.getClearamount()));
        }else {
            detailsBinding.clearAmount.setVisibility(View.GONE);
        }
        // 优惠金额
        if (data.getServicemodel() == 2) { // 套餐
            detailsBinding.couponAmount.setTitleText("支付方式：");
            detailsBinding.couponAmount.setContentText("套餐券");
            detailsBinding.payAmount.setVisibility(View.GONE);
        }else { // 正常订单
            detailsBinding.couponAmount.setTitleText("优惠金额：");
            detailsBinding.couponAmount.setContentText(String.format(getString(R.string.RMB),"0"));
            switch (data.getCoupontype()) {
                case 1:
                    detailsBinding.couponAmount.setContentText(String.format(getString(R.string.RMB), String.valueOf(data.getCouponamount())));
                    detailsBinding.couponAmount.setSubContentText("(满减券)");
                    break;
                case 2:
                    detailsBinding.couponAmount.setContentText(String.format(getString(R.string.RMB),
                            String.valueOf(BigDecimalUtils.formatRoundUp(data.getOrderamount() * (1 - data.getCouponamount() / 10), BigDecimalUtils.MONEY_POINT))));
                    detailsBinding.couponAmount.setSubContentText("(折扣券)");
                    break;
                case 3:
                    detailsBinding.couponAmount.setContentText(String.format(getString(R.string.RMB), String.valueOf(data.getCouponamount())));
                    detailsBinding.couponAmount.setSubContentText("(抵扣券)");
                    break;
            }
        }
        // 退款
        OrderRefundBean orderRefundBean = data.getOrderRefund();
        if (orderRefundBean != null) {
            detailsBinding.refundAmount.setVisibility(View.VISIBLE);
            detailsBinding.refundAmount.setContentText(String.format(getString(R.string.RMB), String.valueOf(orderRefundBean.getRefundamount())));
            detailsBinding.orderHandlingFee.setVisibility(View.VISIBLE);
            detailsBinding.orderHandlingFee.setContentText(String.format(getString(R.string.RMB), String.valueOf(orderRefundBean.getHandlingfee())));
            detailsBinding.orderHandlingFee.setSubContentText("（项目价格*5%）");
        }

        // 订单状态
        switch (data.getStatus()) {
            // ORDER_CONFIRM
            case 3: // 付款完成
                // 项目价格
                detailsBinding.btn0.setVisibility(View.VISIBLE);
                detailsBinding.btn0.setText("拒绝接单");
                detailsBinding.btn0.setOnClickListener(v -> {
                    new YLDialog.Builder(this)
                            .setTitle("拒绝接单提示")
                            .setMessage("确定拒绝该订单？")
                            .setPositiveButton("确定", (dialog, which) -> {
                                dialog.dismiss();
                                getMvpPresenter().refuseOrder(this, orderId);
                            }).setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                            .create().show();
                });
                detailsBinding.btn1.setVisibility(View.VISIBLE);
                detailsBinding.btn1.setText("同意");
                detailsBinding.btn1.setOnClickListener(v -> {
                    new YLDialog.Builder(this)
                            .setTitle("同意接单提示")
                            .setMessage("确定同意服务该订单？")
                            .setPositiveButton("确定", (dialog, which) -> {
                                dialog.dismiss();
                                getMvpPresenter().acceptOrder(this, orderId);
                            }).setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                            .create().show();
                });
                break;
            case 14: // 用户取消订单申请中
                detailsBinding.btn0.setVisibility(View.VISIBLE);
                detailsBinding.btn0.setText("违约退款");
                detailsBinding.btn0.setOnClickListener(v -> {
                    new YLDialog.Builder(this)
                            .setTitle("违约退款提示")
                            .setMessage("确定采取违约退款方式？")
                            .setPositiveButton("确定", (dialog, which) -> {
                                dialog.dismiss();
                                getMvpPresenter().agreeCancelOrder(this, orderId, "2");
                            }).setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                            .create().show();
                });
                detailsBinding.btn1.setVisibility(View.VISIBLE);
                detailsBinding.btn1.setText("同意");
                detailsBinding.btn1.setOnClickListener(v -> {
                    new YLDialog.Builder(this)
                            .setTitle("退款提示")
                            .setMessage("确定同意退款？")
                            .setPositiveButton("确定", (dialog, which) -> {
                                dialog.dismiss();
                                getMvpPresenter().agreeCancelOrder(this, orderId, "1");
                            }).setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                            .create().show();
                });
                break;
            // ORDER_SERVICE
            case 4: // 待服务
            case 6: // 用户修改预约时间
                detailsBinding.btn0.setVisibility(View.VISIBLE);
                detailsBinding.btn0.setText("取消订单");
                detailsBinding.btn0.setOnClickListener(v -> {
                    StringBuilder cancelHint = new StringBuilder();
                    if (data.getOrdertime() - System.currentTimeMillis() >= 8*60*60*1000) {
                        cancelHint.append("确定取消订单？");
                    }else {
                        cancelHint.append("当前时间已超过订单无责任取消有效期，强制取消预约将扣除订单价格的5%手续费返还给用户等价的平台通用抵扣券，是否确认取消该订单？");
                    }
                    new YLDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage(cancelHint.toString())
                            .setPositiveButton("确定", (dialog, which) -> {
                                dialog.dismiss();
                                getMvpPresenter().cancelOrder(this, orderId);
                            }).setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                            .create().show();
                });
                detailsBinding.btn1.setVisibility(View.VISIBLE);
                detailsBinding.btn1.setText("完成服务");
                detailsBinding.btn1.setOnClickListener(v -> {
                    new RxPermissions(this)
                            .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                            .subscribe(granted -> {
                                if (granted) {
                                    // 扫码
                                    ScanCodeActivity.startActivity(this, ScanCodeActivity.class);
                                } else {
                                    ToastUtils.shortToast(getString(R.string.permissions_camera_state));
                                }
                            });
                });
                break;
            // ORDER_COMPLETE
            case 11: // 已完成
                detailsBinding.btn1.setVisibility(View.GONE);
                detailsBinding.btn0.setVisibility(View.GONE);
                break;
            case 18: // 已评价
                detailsBinding.btn1.setVisibility(View.VISIBLE);
                detailsBinding.btn0.setVisibility(View.GONE);
                detailsBinding.btn1.setText("查看评价");
                detailsBinding.btn1.setOnClickListener(v -> {
                    CommentActivity.startActivity(OrderDetailsActivity.this, orderId);
                });
                break;
            // ORDER_REFUND
            case 5:
            case 12: // 美发师取消订单完成
            case 13: // 用户取消订单完成
            case 15: // 用户申请取消完成
                detailsBinding.llBottom.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 二维码
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBean.ScanBean event) {
        getMvpPresenter().startOrder(this, event.getContent(), String.valueOf(data.getStylistId()));
    }

    /**
     * 订单新消息通知
     * @param message
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBean.NewMessage message) {
       loadData();
    }

    @Override
    public void onCompleteOrderSuccess() {
        loadData();
    }

    @Override
    public void cancelAddmoneyRequestSuccess() {
        loadData();
    }

    @Override
    public void agreeCancelOrderSuccess() {
        loadData();
    }

    @Override
    public void addMoneyRequestSuccess(String addMoney) {
        EasyUtil.getEmManager().sendAddMoneyMsg(this,
                data.getUserImusername(),
                String.valueOf(data.getUserId()),
                data.getUserNickname(),
                data.getUserPath(),
                data.getOrdername(),
                Double.valueOf(addMoney) ,orderId);
        loadData();
    }

    @Override
    public void acceptOrderSuccess() {
        loadData();
    }

    @Override
    public void cancelOrderSuccess() {
        loadData();
    }

    @Override
    public void startOrderSuccess() {
        loadData();
    }

    @Override
    public void refuseOrderSuccess() {
        loadData();
    }

    @Override
    public void updateCountdownTime(long time) {
        if (detailsBinding.tvOrderHint.getVisibility() != View.VISIBLE) {
            detailsBinding.tvOrderHint.setVisibility(View.VISIBLE);
            detailsBinding.tvOrderHint.bringToFront();
        }
        detailsBinding.tvOrderHint.setText("您需在 " + pendingTimeStr(time) + " 内处理该订单，超时将默认拒绝...");
    }

    @Override
    public void onCountdownFinish() {
        detailsBinding.tvOrderHint.setVisibility(View.GONE);
    }

    private void setDrawableRight(TextView textView, Drawable drawableRight) {
        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawableRight, null);
    }

    /**
     * 时间换算
     */
    private String pendingTimeStr(long pendingTime) {
        if (pendingTime <= 0) {
            return "0小时0分";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(pendingTime / 3600);
        sb.append("小时");
        sb.append(pendingTime / 60 - (pendingTime / 3600 * 60));
        sb.append("分");
        sb.append(pendingTime % 60);
        sb.append("秒");
        return sb.toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tell_phone: // 电话
                new RxPermissions(this)
                        .request(Manifest.permission.CALL_PHONE)
                        .subscribe(grant -> {
                            if (grant) {
                                PhoneUtil.toCallPhone(this, data.getUserMobile());
                            } else {
                                showToast("您拒绝了拨打电话权限.");
                            }
                        });
                break;
            case R.id.btn_send_msg: // 消息
                ChatActivity.startFromZIxunActivity(OrderDetailsActivity.this,
                        data.getUserImusername(),
                        data.getUserId()+"",
                        data.getUserNickname(),
                        data.getUserPath());
                break;
        }
    }
}
