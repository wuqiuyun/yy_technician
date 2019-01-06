package com.yl.technician.module.home.works.upload;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yl.core.component.image.ImageLoader;
import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.databinding.ItemImageAddBinding;
import com.yl.technician.util.BitmapUtils;

/**
 * Created by zm on 2018/10/23.
 */
public class AddImageAdapter extends BaseRecycleViewAdapter<String> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private OnDeleteItemListener mOnDeleteItemListener;

    public AddImageAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setOnDeleteItemListener(OnDeleteItemListener onDeleteItemListener) {
        mOnDeleteItemListener = onDeleteItemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddImageViewHolder(mLayoutInflater.inflate(R.layout.item_image_add, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AddImageViewHolder viewHolder = (AddImageViewHolder) holder;
        ImageView ivPhoto = viewHolder.mBinding.ivPhoto;

        String imagePath = mDatas.get(position);
        if (imagePath.startsWith("http")) {
            ImageLoader.loadImage(ivPhoto, imagePath);
        } else {
            ivPhoto.post(() -> {
                ivPhoto.setImageBitmap(BitmapUtils.decodeSampledBitmapFromFile(imagePath,
                        ivPhoto.getWidth(), ivPhoto.getHeight()));
            });
        }
        if (mOnDeleteItemListener!=null){
            viewHolder.mBinding.ivDelete.setOnClickListener(v -> {
                    mOnDeleteItemListener.onDeleteItem(v, position);
            });
            viewHolder.itemView.setOnClickListener(view -> {
                mOnDeleteItemListener.onItemClick(view, position);

            });
        }


    }

    private class AddImageViewHolder extends BaseViewHolder {
        private ItemImageAddBinding mBinding;

        public AddImageViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    public static interface OnDeleteItemListener extends RecycleViewItemListener{
        void onDeleteItem(View view, int position);
    }
}
