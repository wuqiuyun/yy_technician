package com.yl.technician.module.home.card;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.core.component.image.ImageLoader;
import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.databinding.ItemServiceProjectBinding;
import com.yl.technician.model.vo.bean.StylistCardBean;

/**
 * 服务项目适配器
 * Created by zm on 2018/10/11.
 */
public class ServiceProjectAdapter extends BaseRecycleViewAdapter<StylistCardBean.CardServerItem>{
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
        ServiceProjectAdapter.ServiceProjectViewHolder viewHolder = (ServiceProjectAdapter.ServiceProjectViewHolder) holder;
        viewHolder.binding.tvProjectName.setText(mDatas.get(position).getName());
        viewHolder.binding.tvProjectPrice.setText("￥"+mDatas.get(position).getPrice());
        ImageLoader.loadImage(viewHolder.binding.ivIcon,mDatas.get(position).getPicture());
    }

    private class ServiceProjectViewHolder extends BaseViewHolder {
        private ItemServiceProjectBinding binding;

        public ServiceProjectViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
