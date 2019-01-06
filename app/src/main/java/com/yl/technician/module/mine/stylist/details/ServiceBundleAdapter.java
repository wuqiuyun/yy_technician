package com.yl.technician.module.mine.stylist.details;

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
import com.yl.technician.model.vo.bean.ServiceBundleBean;

/**
 * 服务项目适配器
 * Created by zm on 2018/10/11.
 */
public class ServiceBundleAdapter extends BaseRecycleViewAdapter<ServiceBundleBean>{
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

    }

    private class ServiceProjectViewHolder extends BaseViewHolder {
        private ItemServiceBundleBinding binding;

        public ServiceProjectViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
