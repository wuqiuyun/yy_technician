package com.yl.technician.module.mine.stylist.details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;

/**
 * Created by zm on 2018/10/11.
 */
public class WorksAdapter extends BaseRecycleViewAdapter<String>{
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

    }

    private class WorksViewHolder extends BaseViewHolder {

        public WorksViewHolder(View itemView) {
            super(itemView);
        }
    }
}
