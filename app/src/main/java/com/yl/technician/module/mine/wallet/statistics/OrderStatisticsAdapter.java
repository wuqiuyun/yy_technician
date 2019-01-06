package com.yl.technician.module.mine.wallet.statistics;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.databinding.ItemOrderManagerBinding;
import com.yl.technician.model.vo.bean.StylistOrderBean;

/**
 * 订单统计
 * Created by wqy on 2018/12/4.
 */

public class OrderStatisticsAdapter extends BaseRecycleViewAdapter<StylistOrderBean.OrderCategoryCountBean> {
    private Context context;

    public OrderStatisticsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderStatisticsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_manager, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderStatisticsViewHolder viewHolder = (OrderStatisticsViewHolder) holder;
        StylistOrderBean.OrderCategoryCountBean bean = getDatas().get(position);
        viewHolder.mBinding.tvServiceName.setText(bean.getName());
        viewHolder.mBinding.tvOrderReservation.setText(String.valueOf(bean.getReceiptNum()));
        viewHolder.mBinding.tvOrderFinish.setText(String.valueOf(bean.getSuccessNum()));
        viewHolder.mBinding.tvIncomeEstimate.setText(String.valueOf(bean.getReceiptMoney()));
        viewHolder.mBinding.tvIncomeTotal.setText(String.valueOf(bean.getSuccessMoney()));
    }

    private class OrderStatisticsViewHolder extends BaseViewHolder {
        ItemOrderManagerBinding mBinding;

        public OrderStatisticsViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
