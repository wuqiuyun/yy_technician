package com.yl.technician.module.mine.works;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.core.component.image.ImageLoader;
import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.model.vo.bean.GetOpusBean;
import com.yl.technician.model.vo.bean.WorksBean;

/**
 * Created by zm on 2018/10/10.
 */
public class WorksAdapter extends BaseRecycleViewAdapter<GetOpusBean>{
    private Context context;

    public WorksAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorksViewHolder(LayoutInflater.from(context).inflate(R.layout.item_works, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WorksViewHolder viewHolder = (WorksViewHolder) holder;
        GetOpusBean opus = getDatas().get(position);
        ImageLoader.loadImage(viewHolder.itemView.findViewById(R.id.iv_works), opus.getOpusPath());
    }

    public class WorksViewHolder extends BaseViewHolder {

        public WorksViewHolder(View itemView) {
            super(itemView);
        }
    }
}
