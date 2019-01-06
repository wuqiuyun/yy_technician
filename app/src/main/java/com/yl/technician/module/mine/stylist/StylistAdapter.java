package com.yl.technician.module.mine.stylist;


import android.annotation.SuppressLint;
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
import com.yl.technician.databinding.ItemStylistBinding;
import com.yl.technician.model.vo.bean.GetStylistBean;
import com.yl.technician.util.FormatUtil;

/**
 * Created by zm on 2018/10/10.
 */
public class StylistAdapter extends BaseRecycleViewAdapter<GetStylistBean> {
    private Context context;

    public StylistAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StylistViewHolder(LayoutInflater.from(context).inflate(R.layout.item_stylist, parent, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StylistViewHolder viewHolder = (StylistViewHolder) holder;
        GetStylistBean stylistBean = mDatas.get(position);
        ImageLoader.loadImage(viewHolder.stylistBinding.ivPhoto, stylistBean.getHeadPortrait());
        viewHolder.stylistBinding.ratingBar.setRating(stylistBean.getStar());
        viewHolder.stylistBinding.ratingBar.setOnTouchListener((v, event) -> true);
        viewHolder.stylistBinding.tvName.setText(FormatUtil.Formatstring(stylistBean.getNickname()));
        viewHolder.stylistBinding.tvTotalPerformance.setText(FormatUtil.Formatstring(stylistBean.getWaitVerification()+"单"));
        viewHolder.stylistBinding.tvAmount.setText(FormatUtil.Formatstring("￥"+ stylistBean.getTotalPerformance()));
    }

    public class StylistViewHolder extends BaseViewHolder {
        private ItemStylistBinding stylistBinding;

        public StylistViewHolder(View itemView) {
            super(itemView);
            stylistBinding = DataBindingUtil.bind(itemView);
        }
    }
}
