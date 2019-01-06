package com.yl.technician.module.mine.wallet.withdraw.overview.monthbill.spending;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.databinding.ItemSpendingBinding;
import com.yl.technician.model.vo.bean.MonthSumBean;

public class SpendingAdapter extends BaseRecycleViewAdapter<MonthSumBean.ClassifyOutBean> {
    private Context context;

    public SpendingAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SpendingHolder(LayoutInflater.from(context).inflate(R.layout.item_spending, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SpendingHolder spendingHolder =(SpendingHolder) holder;
        MonthSumBean.ClassifyOutBean classifyOutBean = mDatas.get(position);
        spendingHolder.mBinding.tvLable.setText(String.format(classifyOutBean.getName()));
        spendingHolder.mBinding.tvOutMoney.setText(String.format(String.valueOf(classifyOutBean.getOutMoney())));
    }

    private class SpendingHolder extends BaseViewHolder {
        private ItemSpendingBinding mBinding;
        public SpendingHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }


}
