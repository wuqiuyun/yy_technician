package com.yl.technician.module.home.in.store;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;

/**
 * 服务范围适配器
 * <p>
 * Created by zm on 2018/10/13.
 */
public class ServiceScopeAdapter extends BaseRecycleViewAdapter<String>{
    private Context mContext;

    public ServiceScopeAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServiceViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_service_scope, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    public class ServiceViewHolder extends BaseViewHolder {

        public ServiceViewHolder(View itemView) {
            super(itemView);
        }
    }
}
