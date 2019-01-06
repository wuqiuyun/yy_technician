package com.yl.technician.module.home.card;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.databinding.ItemServiceBundleBinding;
import com.yl.technician.model.vo.bean.StylistCardBean;

/**
 * 套餐项目适配器
 * Created by zm on 2018/10/11.
 */
public class ServiceBundleAdapter extends BaseRecycleViewAdapter<StylistCardBean.CardPackage>{
    private Context context;

    public ServiceBundleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServiceProjectViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_service_bundle, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ServiceBundleAdapter.ServiceProjectViewHolder viewHolder = (ServiceBundleAdapter.ServiceProjectViewHolder) holder;
        viewHolder.mBinding.tvBundleName.setText(mDatas.get(position).getName());
        viewHolder.mBinding.tvProjectPrice.setText("￥"+mDatas.get(position).getPrice());
    }

    private class ServiceProjectViewHolder extends BaseViewHolder {
        private ItemServiceBundleBinding mBinding;

        public ServiceProjectViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
