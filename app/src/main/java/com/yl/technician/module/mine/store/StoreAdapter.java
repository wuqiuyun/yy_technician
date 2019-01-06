package com.yl.technician.module.mine.store;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.databinding.ItemStoreBinding;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.StoreBean;
import com.yl.technician.util.FormatKmUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 2018/10/10.
 */
public class StoreAdapter extends BaseRecycleViewAdapter<StoreBean> {
    private Context context;
    private int type;

    public StoreAdapter(Context context,int type) {
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoreViewHolder(LayoutInflater.from(context).inflate(R.layout.item_store, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        StoreViewHolder holder = (StoreViewHolder) viewHolder;
        StoreBean item = getDatas().get(position);
        ArrayList<String> list = item.getServes();
        String serves = getServes(list);
        ImageLoader.loadImage(holder.storeBinding.ivStore, item.getStoreCover());
        holder.storeBinding.tvName.setText(item.getStoreName());
        holder.storeBinding.tvLocationDistance.setText(FormatKmUtil.FormatKmStr(item.getDistance()));
        holder.storeBinding.tvAddress.setText(item.getLocation());
        holder.storeBinding.tvServiceType.setText(serves);
        holder.storeBinding.tvOrderNum.setText(item.getServers());
        holder.storeBinding.tvGrade.setText(String.valueOf(item.getGrade()));
        holder.storeBinding.ratingBar.setRating((float) item.getGrade());

        holder.storeBinding.ratingBar.setOnTouchListener((v, event) -> true);

        switch (type){
            case Constants.STORE_LIST_TYPE_0:
                holder.storeBinding.trServiceType.setVisibility(View.GONE);
                holder.storeBinding.trOrderNum.setVisibility(View.VISIBLE);
                holder.storeBinding.llGrade.setVisibility(View.GONE);
            break;
            case Constants.STORE_LIST_TYPE_1:
                holder.storeBinding.trServiceType.setVisibility(View.GONE);
                holder.storeBinding.trOrderNum.setVisibility(View.GONE);
                holder.storeBinding.llGrade.setVisibility(View.VISIBLE);
            break;
            case Constants.STORE_LIST_TYPE_2:
                holder.storeBinding.trServiceType.setVisibility(View.VISIBLE);
                holder.storeBinding.trOrderNum.setVisibility(View.GONE);
                holder.storeBinding.llGrade.setVisibility(View.GONE);
            break;
        }
    }

    public class StoreViewHolder extends BaseViewHolder {
        private ItemStoreBinding storeBinding;
        
        public StoreViewHolder(View itemView) {
            super(itemView);
            storeBinding = DataBindingUtil.bind(itemView);
        }
    }
    
    private String getServes(List<String> list) {
        StringBuffer sb = new StringBuffer();
        if (null == list || list.size() == 0) {
            return "暂无";
        } else {
            for (String serve: list ) {
                sb.append(serve).append("、");
            }
            return sb.toString().substring(0,sb.length()-1);
        }
    }
}
