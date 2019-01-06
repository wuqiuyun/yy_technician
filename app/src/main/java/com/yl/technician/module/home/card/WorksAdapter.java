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
import com.yl.technician.databinding.ItemStylistWorksBinding;
import com.yl.technician.model.vo.bean.StylistCardBean;

/**
 * Created by zm on 2018/10/11.
 */
public class WorksAdapter extends BaseRecycleViewAdapter<StylistCardBean.CardOpus>{
    private Context context;

    public WorksAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorksViewHolder(LayoutInflater.from(context).inflate(R.layout.item_stylist_works, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WorksAdapter.WorksViewHolder viewHolder = (WorksAdapter.WorksViewHolder) holder;
        ImageLoader.loadImage(viewHolder.binding.ivWorks,mDatas.get(position).getStylistOpusCovers());
    }

    private class WorksViewHolder extends BaseViewHolder {
        private ItemStylistWorksBinding binding;

        public WorksViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
