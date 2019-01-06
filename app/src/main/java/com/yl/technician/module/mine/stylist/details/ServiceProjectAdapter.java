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
import com.yl.technician.databinding.ItemServiceProjectBinding;
import com.yl.technician.model.vo.bean.ServiceProjectBean;

/**
 * 服务项目适配器
 * Created by zm on 2018/10/11.
 */
public class ServiceProjectAdapter extends BaseRecycleViewAdapter<ServiceProjectBean>{
    private Context context;

    public ServiceProjectAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServiceProjectViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_service_project, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    private class ServiceProjectViewHolder extends BaseViewHolder {
        private ItemServiceProjectBinding binding;

        public ServiceProjectViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
