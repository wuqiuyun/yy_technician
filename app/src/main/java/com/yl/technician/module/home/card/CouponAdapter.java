package com.yl.technician.module.home.card;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.technician.R;
import com.yl.technician.databinding.ItemStylistCouponType1Binding;
import com.yl.technician.databinding.ItemStylistCouponType1NoBinding;
import com.yl.technician.databinding.ItemStylistCouponType2Binding;
import com.yl.technician.databinding.ItemStylistCouponType2NoBinding;

import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.model.vo.bean.StylistCardBean;

import java.text.DecimalFormat;

/**
 * Created by Lizhuo on 2018/11/5.
 */
public class CouponAdapter extends BaseRecycleViewAdapter<StylistCardBean.CardCoupon> {
    private Context mContext;
    private final int TYPE_1 = 1;//满减券
    private final int TYPE_1_NO = 2;//满减券（不可抢）
    private final int TYPE_2 = 3;//折扣券
    private final int TYPE_2_NO = 4;//折扣券（不可抢）

    public CouponAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_1:
                viewHolder = new CardCouponViewHolder1(LayoutInflater.from(mContext).inflate(R.layout.item_stylist_coupon_type_1, parent, false));
                break;
            case TYPE_1_NO:
                viewHolder = new CardCouponViewHolder2(LayoutInflater.from(mContext).inflate(R.layout.item_stylist_coupon_type_1_no, parent, false));
                break;
            case TYPE_2:
                viewHolder = new CardCouponViewHolder3(LayoutInflater.from(mContext).inflate(R.layout.item_stylist_coupon_type_2, parent, false));
                break;
            case TYPE_2_NO:
                viewHolder = new CardCouponViewHolder4(LayoutInflater.from(mContext).inflate(R.layout.item_stylist_coupon_type_2_no, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        StylistCardBean.CardCoupon data = getDatas().get(position);
        DecimalFormat df = new DecimalFormat("0.0");
        String percent = df.format(data.getUsePercent() * 100);
        switch (holder.getItemViewType()) {
            case TYPE_1://待抢满减券
                CardCouponViewHolder1 viewHolder1 = (CardCouponViewHolder1) holder;
                viewHolder1.mBinding.tvAmount.setText(data.getDeduction()+"");
                viewHolder1.mBinding.tvLimited.setText(String.format(mContext.getString(R.string.full_reduction_hint), data.getAmount()+""));
                viewHolder1.mBinding.tvDeduction.setText(String.format(mContext.getString(R.string.item_deduction), percent +"%"));
                viewHolder1.mBinding.pbDeduction.setProgress((int) (data.getUsePercent()*100));
                break;
            case TYPE_1_NO://已抢光满减券
                CardCouponViewHolder2 viewHolder2 = (CardCouponViewHolder2) holder;
                viewHolder2.mBinding.tvAmount.setText(data.getDeduction()+"");
                viewHolder2.mBinding.tvLimited.setText(String.format(mContext.getString(R.string.full_reduction_hint), data.getAmount()+""));
                viewHolder2.mBinding.pbDeduction.setProgress(100);
                break;
            case TYPE_2://待抢折扣券
                CardCouponViewHolder3 viewHolder3 = (CardCouponViewHolder3) holder;
                viewHolder3.mBinding.tvAmount.setText(data.getDeduction()+"");
                viewHolder3.mBinding.tvDeduction.setText(String.format(mContext.getString(R.string.item_deduction), percent+"%"));
                viewHolder3.mBinding.pbDeduction.setProgress((int) (data.getUsePercent()*100));
                break;
            case TYPE_2_NO://已抢光折扣券
                CardCouponViewHolder4 viewHolder4 = (CardCouponViewHolder4) holder;
                viewHolder4.mBinding.tvAmount.setText(data.getDeduction()+"");
                viewHolder4.mBinding.pbDeduction.setProgress(100);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        StylistCardBean.CardCoupon data = getDatas().get(position);
        if (data.getType() == 1) {
            if (data.getUsePercent() < 1) {
                return TYPE_1;
            } else {
                return TYPE_1_NO;
            }
        } else if (data.getType() == 2) {
            if (data.getUsePercent() < 1) {
                return TYPE_2;
            } else {
                return TYPE_2_NO;
            }
        } else {
            return TYPE_2_NO;
        }
    }

    public class CardCouponViewHolder1 extends BaseViewHolder {

        private ItemStylistCouponType1Binding mBinding;

        public CardCouponViewHolder1(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    public class CardCouponViewHolder2 extends BaseViewHolder {

        private ItemStylistCouponType1NoBinding mBinding;

        public CardCouponViewHolder2(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    public class CardCouponViewHolder3 extends BaseViewHolder {

        private ItemStylistCouponType2Binding mBinding;

        public CardCouponViewHolder3(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    public class CardCouponViewHolder4 extends BaseViewHolder {

        private ItemStylistCouponType2NoBinding mBinding;

        public CardCouponViewHolder4(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
