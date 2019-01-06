package com.yl.technician.module.mine.stylist.details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.model.vo.bean.StoreBean;

/**
 * Created by zm on 2018/10/11.
 */
public class StoreAdapter extends BaseRecycleViewAdapter<StoreBean>{
    private Context context;

    public StoreAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoreViewHolder(LayoutInflater.from(context).inflate(R.layout.item_store_join, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    private class StoreViewHolder extends BaseViewHolder {

        public StoreViewHolder(View itemView) {
            super(itemView);
        }
    }
}
