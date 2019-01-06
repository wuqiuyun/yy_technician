package com.yl.technician.module.mine.works.many;

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
import com.yl.technician.databinding.ItemWorksBinding;
import com.yl.technician.model.vo.bean.OpusListBean;

/**
 * Created by zm on 2018/10/11.
 */
public class ManyWorksAdapter extends BaseRecycleViewAdapter<OpusListBean.Opus>{
    private Context context;

    public ManyWorksAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ManyWorksAdapter.WorksViewHolder(LayoutInflater.from(context).inflate(R.layout.item_works, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ManyWorksAdapter.WorksViewHolder viewHolder = (WorksViewHolder) holder;
        ImageLoader.loadImage(viewHolder.mBinding.ivWorks, getDatas().get(position).getStylistOpusCovers());
    }

    public class WorksViewHolder extends BaseViewHolder {

        private ItemWorksBinding mBinding;
        public WorksViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}


