package com.yl.technician.module.mine.wallet;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.databinding.ItemWalletProjectBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.bean.CoinInfoAccountBean;

/**
 * Created by lyj on 2018/10/29.
 */
public class WalletCoinAdapter extends BaseRecycleViewAdapter<CoinInfoAccountBean>{
    private Context context;

    public WalletCoinAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WalletViewHolder(LayoutInflater.from(context).inflate(R.layout.item_wallet_project, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WalletCoinAdapter.WalletViewHolder viewHolder = (WalletCoinAdapter.WalletViewHolder) holder;
        viewHolder.mBinding.tvDate.setText(mDatas.get(position).getCreatetime());
        //如果toUserId等于你上传的userId，那就是收入
        if (AccountManager.getInstance().getUserId().equals(String.valueOf(mDatas.get(position).getToUserId()))){//1增加
            viewHolder.mBinding.tvProjectType.setText("收入项目");
            viewHolder.mBinding.tvPrice.setText("+"+mDatas.get(position).getAmount());
            viewHolder.mBinding.tvPrice.setTextColor(context.getResources().getColor(R.color.color_red));
        }else {//减少
            viewHolder.mBinding.tvProjectType.setText("支出项目");
            viewHolder.mBinding.tvPrice.setText("-"+mDatas.get(position).getAmount());
            viewHolder.mBinding.tvPrice.setTextColor(context.getResources().getColor(R.color.color_FFA200));
        }

        String formUserId = mDatas.get(position).getFromUserId()+"";
        if (formUserId.equals("0")){
            viewHolder.mBinding.tvHint.setText("注册赠送");
        }else {
            viewHolder.mBinding.tvHint.setText("");
        }

    }

    private class WalletViewHolder extends BaseViewHolder {
        private ItemWalletProjectBinding mBinding;
        public WalletViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
