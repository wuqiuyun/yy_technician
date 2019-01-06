package com.yl.technician.module.home.in.store;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.model.vo.bean.StylistBean;

/**
 *
 * 入驻美发师适配器
 * <p>
 * Created by zm on 2018/10/13.
 */
public class StylistAdapter extends BaseRecycleViewAdapter<StylistBean>{
    private Context mContext;

    public StylistAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StylistViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_store_stylist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    public class StylistViewHolder extends BaseViewHolder {

        public StylistViewHolder(View itemView) {
            super(itemView);
        }
    }
}
