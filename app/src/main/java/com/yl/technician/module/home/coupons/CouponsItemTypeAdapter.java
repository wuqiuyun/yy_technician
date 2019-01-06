package com.yl.technician.module.home.coupons;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yl.technician.R;
import com.yl.technician.helper.AccountManager;

import java.util.List;

/**
 * Created by zhangzz on 2018/11/2
 */
public class CouponsItemTypeAdapter extends BaseMultiItemQuickAdapter<CouponBean, BaseViewHolder> {

    public CouponsItemTypeAdapter(List data) {
        super(data);
        addItemType(CouponBean.FULLSUB, R.layout.adapter_fullsub_layout);
        addItemType(CouponBean.DISCOUNT, R.layout.adapter_discount_layout);
    }

    @Override
    protected void convert(BaseViewHolder holder, CouponBean item) {
        switch (holder.getItemViewType()) {
            case CouponBean.FULLSUB:
                holder.setText(R.id.tv_reduce_limit,   "￥"+item.getDeduction());
                String amount = String.format(mContext.getResources().getString(R.string.full_reduction_hint), item.getAmount() + "");
                holder.setText(R.id.tv_meet_limit, amount);
                holder.setText(R.id.tv_valid_time, item.getValidend() + "");
                holder.setText(R.id.tv_received_time, item.getReceiveend() + "");
                holder.addOnClickListener(R.id.tv_full_reduction_down);
                holder.addOnClickListener(R.id.btn_delete);
                if (item.getStatus() == 1) {
                    holder.setText(R.id.tv_full_reduction_down, R.string.down_frame);
                } else if (item.getStatus() == 0) {
                    holder.setText(R.id.tv_full_reduction_down, R.string.up_frame);
                }
                break;
            case CouponBean.DISCOUNT:
                holder.setText(R.id.tv_discount, item.getDeduction() + "");
                holder.setText(R.id.tv_favourable_time, item.getUsestart() + "-" + item.getUseend());
                holder.setText(R.id.tv_valid_time, item.getValidend() + "");
                holder.setText(R.id.tv_receive_time, item.getReceiveend() + "");
                holder.setText(R.id.tv_name , "仅限"+AccountManager.getInstance().getNickname()+"可用");
                holder.addOnClickListener(R.id.tv_discount_down);
                holder.addOnClickListener(R.id.btn_delete);
                if (item.getStatus() == 1) {
                    holder.setText(R.id.tv_discount_down,R.string.down_frame);
                } else if (item.getStatus() == 0) {
                    holder.setText(R.id.tv_discount_down, R.string.up_frame);
                }
                break;
        }
    }
}