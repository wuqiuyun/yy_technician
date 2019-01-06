package com.yl.technician.module.mine.stylist;


import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.core.component.image.ImageLoader;
import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.databinding.ItemStylistMineBinding;
import com.yl.technician.model.vo.bean.GetStylistBean;
import com.yl.technician.model.vo.bean.StylistBean;

/**
 * Created by Lizhuo on 2018/10/23.
 */
public class StylistMineAdapter extends BaseRecycleViewAdapter<GetStylistBean> {
    private Context context;

    public StylistMineAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StylistMineAdapter.StylistViewHolder(LayoutInflater.from(context).inflate(R.layout.item_stylist_mine, parent, false));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StylistMineAdapter.StylistViewHolder viewHolder = (StylistMineAdapter.StylistViewHolder) holder;
        GetStylistBean item = getDatas().get(position);
        viewHolder.stylistBinding.ratingBar.setOnTouchListener((v, event) -> true);
        ImageLoader.loadImage(viewHolder.stylistBinding.ivPhoto, item.getStylistCover());
        viewHolder.stylistBinding.tvName.setText(item.getStylistName());
        viewHolder.stylistBinding.tvGrade.setText(item.getGrade() + "分");

        if (!TextUtils.isEmpty(item.getProfessor())) {
            viewHolder.stylistBinding.tvProfessor.setText(item.getProfessor().substring(0, 2));
        }
        viewHolder.stylistBinding.ratingBar.setRating(item.getGrade());
        viewHolder.stylistBinding.tvCount.setText(String.format(context.getString(R.string.stylist_receipt_count), item.getReceiptCount()));

        viewHolder.stylistBinding.tvMaxSalesItem.setText(item.getMaxSalesItem());

    }

    public class StylistViewHolder extends BaseViewHolder {
        private ItemStylistMineBinding stylistBinding;

        public StylistViewHolder(View itemView) {
            super(itemView);
            stylistBinding = DataBindingUtil.bind(itemView);
        }
    }
}
