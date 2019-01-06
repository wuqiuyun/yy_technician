package com.yl.technician.module.home.order;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.util.DateUtil;
import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.databinding.ItemOrderBinding;
import com.yl.technician.model.constant.OrderStatus;
import com.yl.technician.model.vo.bean.OrderBean;
import com.yl.technician.util.ColorUtil;
import com.yl.technician.util.FormatUtil;

import org.greenrobot.greendao.annotation.NotNull;

import static com.yl.technician.model.constant.OrderStatus.ORDER_COMPLETE;
import static com.yl.technician.model.constant.OrderStatus.ORDER_CONFIRM;
import static com.yl.technician.model.constant.OrderStatus.ORDER_REFUND;
import static com.yl.technician.model.constant.OrderStatus.ORDER_SERVICE;

/**
 * Created by zm on 2018/9/20.
 */
public class OrderAdapter extends BaseRecycleViewAdapter<OrderBean>{
    private Context context;
    @OrderStatus.OrderType
    private int orderType; // 订单类型

    public OrderAdapter(Context context, @OrderStatus.OrderType int orderType) {
        this.context = context;
        this.orderType = orderType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemOrderBinding orderBinding = ((OrderViewHolder) holder).mOrderBinding;

        OrderBean orderBean = mDatas.get(position);
        // 头像
        ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                .setErrorResId(R.drawable.icon_head_pic_def)
                .setPlaceHolderResId(R.drawable.icon_head_pic_def)
                .setAsBitmap(true)
                .setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE)
                .setPrioriy(ImageLoaderConfig.LoadPriority.HIGH)
                .build();
        ImageLoader.loadImage(orderBinding.ivPhoto, orderBean.getUserPath(), config, null);
        // 订单编号
        orderBinding.orderId.setContentText(orderBean.getOrderno());
        // 用户昵称
        orderBinding.tvName.setText(FormatUtil.Formatstring(orderBean.getUserNickname()));
        // 服务名店
        orderBinding.orderStore.setContentText(orderBean.getStoreName());
        // 服务项目
        orderBinding.orderProject.setContentText(orderBean.getOrdername());
        // 优惠券
        switch (orderBean.getCoupontype()) {
            case 0:
                orderBinding.orderCoupon.setVisibility(View.GONE);
                break;
            case 1:
                orderBinding.orderCoupon.setVisibility(View.VISIBLE);
                orderBinding.orderCoupon.setContentText("满减券");
                break;
            case 2:
                orderBinding.orderCoupon.setVisibility(View.VISIBLE);
                orderBinding.orderCoupon.setContentText("折扣券");
                break;
            case 3:
                orderBinding.orderCoupon.setVisibility(View.VISIBLE);
                orderBinding.orderCoupon.setContentText("抵扣券");
                break;
        }
        // 性别xw
        setDrawableRight(orderBinding.tvName,
                ContextCompat.getDrawable(context,
                        orderBean.getUserGender() == 1 ? R.drawable.icon_boy : R.drawable.icon_girl));
        // 服务时间
        orderBinding.orderDate.setSubContentText(orderBean.getDatename());

        switch (orderType) {
            case ORDER_CONFIRM:
                // 服务时间
                orderBinding.orderDate.setTitleText(context.getString(R.string.order_date));
                orderBinding.orderDate.setContentText(DateUtil.date2Str(orderBean.getOrdertime(), DateUtil.FORMAT_YMDHM));
                // 金额
                orderBinding.orderProject.setRightText(String.format(context.getString(R.string.RMB),
                        orderBean.getPackagetype() != 0 ? orderBean.getOrderamount() : orderBean.getPayamount()));
                orderBinding.tvOrderType.setTextColor(ColorUtil.getColor(R.color.color_28C8B5));
                break;
            case ORDER_SERVICE:
                // 服务时间
                orderBinding.orderDate.setTitleText(context.getString(R.string.order_date));
                orderBinding.orderDate.setContentText(DateUtil.date2Str(orderBean.getOrdertime(), DateUtil.FORMAT_YMDHM));
                // 金额
                orderBinding.orderProject.setRightText(String.format(context.getString(R.string.RMB),
                        orderBean.getPackagetype() != 0 ? orderBean.getOrderamount() : orderBean.getPayamount()));
                // 订单状态
                orderBinding.tvOrderType.setText("");
                break;
            case ORDER_COMPLETE:
                // 服务时间
                orderBinding.orderDate.setTitleText(context.getString(R.string.order_date_stop));
                orderBinding.orderDate.setContentText(DateUtil.date2Str(orderBean.getEndtime(), DateUtil.FORMAT_YMDHM));
                // 金额
                orderBinding.orderProject.setRightText(String.format(context.getString(R.string.RMB),
                        orderBean.getPackagetype() == 1 ? orderBean.getOrderamount() : orderBean.getPayamount()));
                // 订单状态
                orderBinding.tvOrderType.setText("");
                break;
            case ORDER_REFUND:
                // 服务时间
                orderBinding.orderDate.setTitleText(context.getString(R.string.order_date_cancel));
                orderBinding.orderDate.setContentText(DateUtil.date2Str(orderBean.getCanceltime(), DateUtil.FORMAT_YMDHM));
                // 金额
                orderBinding.orderProject.setRightText(String.format(context.getString(R.string.RMB),
                        orderBean.getPackagetype() == 1 ? orderBean.getOrderamount() : orderBean.getPayamount()));
                // 查看详情
                orderBinding.llOrderDetails.setVisibility(View.GONE);
                // 订单状态
                orderBinding.tvOrderType.setTextColor(ColorUtil.getColor(R.color.color_CCCCCC));
                break;
        }

        // 订单状态
       switch (orderBean.getStatus()) {
            // ORDER_CONFIRM
           case 3: // 付款完成
               orderBinding.btn1.setVisibility(View.VISIBLE);
               orderBinding.btn1.setText("查看详情");
               orderBinding.tvOrderType.setText("待接单");
               break;
           case 8: // 美发师申请加价
               orderBinding.btn1.setVisibility(View.VISIBLE);
               orderBinding.btn1.setText("查看详情");
               orderBinding.tvOrderType.setText("加价待用户通过");
               break;
           case 14: // 用户取消订单申请中
               orderBinding.btn1.setVisibility(View.VISIBLE);
               orderBinding.btn1.setText("查看详情");
               orderBinding.tvOrderType.setText("取消订单待通过");
               break;
            // ORDER_SERVICE
           case 4: // 待服务
               orderBinding.llOrderDetails.setVisibility(View.GONE);
               break;
           case 6: // 用户修改预约时间
               orderBinding.llOrderDetails.setVisibility(View.GONE);
               break;
           case 7: // 服务中
           case 9: // 同意加价
           case 10: // 拒绝加价
               orderBinding.llOrderDetails.setVisibility(View.VISIBLE);
               orderBinding.btn1.setVisibility(View.VISIBLE);
               orderBinding.btn1.setText("完成服务");
               break;
            // ORDER_COMPLETE
           case 11: // 已完成
               orderBinding.llOrderDetails.setVisibility(View.GONE);
               break;
           case 18: // 已评价
               orderBinding.btn0.setVisibility(View.VISIBLE);
               orderBinding.btn0.setText("查看评价");
               break;
            // ORDER_REFUND
           case 5: // 美发师拒绝接单
               orderBinding.tvOrderType.setText("您已拒绝");
               break;
           case 12: // 美发师取消订单完成
               orderBinding.tvOrderType.setText("您已取消");
               break;
           case 13: // 用户取消订单完成
               orderBinding.tvOrderType.setText("用户已取消");
               break;
           case 15: // 用户申请取消完成
               orderBinding.tvOrderType.setText("用户已取消");
               break;
       }
    }

    private void setDrawableRight(TextView textView, @NotNull Drawable drawableRight) {
        drawableRight.setBounds(0, 0,drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawableRight, null);
    }

    public class OrderViewHolder extends BaseViewHolder {
        private ItemOrderBinding mOrderBinding;

        public OrderViewHolder(View itemView) {
            super(itemView);
            mOrderBinding = DataBindingUtil.bind(itemView);
        }
    }

}
