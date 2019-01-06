package com.yl.technician.module.home.evaluation;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.component.recycleview.GridSpacingItemDecoration;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ItemEvaluationManagerBinding;
import com.yl.technician.model.vo.bean.SylistCommentListBean;
import com.yl.technician.widget.PhotoView.PhotoViewActivity;

import java.util.ArrayList;

/**
 * 评价管理适配器
 * Created by lvlong on 2018/10/11.
 */
public class EvaluationManagerAdapter extends BaseRecycleViewAdapter<SylistCommentListBean> {

    private Context mContext;
    private int mType;

    public EvaluationManagerAdapter(Context context, int type) {
        mContext = context;
        mType = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EvaluationManagerAdapter.EvaluationManagerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_evaluation_manager, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EvaluationManagerAdapter.EvaluationManagerViewHolder viewHolder = (EvaluationManagerAdapter.EvaluationManagerViewHolder) holder;
        SylistCommentListBean bean = mDatas.get(position);
        viewHolder.mBinding.materialRatingBar.setRating(bean.getLevel());
        viewHolder.mBinding.materialRatingBar.setEnabled(false);
        String times = bean.getCreatetime();
        viewHolder.mBinding.tvTime.setText(times.substring(5, times.length()));
        viewHolder.mBinding.tvName.setText(bean.getNickname());
        if (null != bean.getComment() && !TextUtils.isEmpty(bean.getComment().trim())) {
            viewHolder.mBinding.tvUserReply.setText(bean.getComment());
        } else {
            viewHolder.mBinding.tvUserReply.setText("这个家伙很懒，什么也没有留下~");
        }
        viewHolder.mBinding.tvHairdressingType.setText("[" + bean.getServiceName() + "]");

        if (mType != 3) {
            String tvReply = mDatas.get(position).getReply();
            if (tvReply != null && !TextUtils.isEmpty(tvReply)) {
                viewHolder.mBinding.etReply.setText(tvReply);
                viewHolder.mBinding.etReply.setEnabled(false);
                viewHolder.mBinding.tvSend.setVisibility(View.GONE);

            } else {
                viewHolder.mBinding.tvSend.setVisibility(View.VISIBLE);
                viewHolder.mBinding.etReply.setText("");
                viewHolder.mBinding.etReply.setEnabled(true);
            }
            viewHolder.mBinding.tvSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String etStr = viewHolder.mBinding.etReply.getText().toString().trim();
                    if (TextUtils.isEmpty(etStr)) {
                        ToastUtils.shortToast("请输入评论!");
                    } else {
                        onSendViewClick.sendMessage(etStr, position);
                    }
                }
            });
        } else {
            viewHolder.mBinding.llReply.setVisibility(View.GONE);
            viewHolder.mBinding.etReply.setEnabled(false);
        }

        //设置头像
        ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                .setAsBitmap(true)
                .setCropType(ImageLoaderConfig.CENTER_INSIDE)
                .setPlaceHolderResId(R.drawable.icon_head_pic_def)
                .setErrorResId(R.drawable.icon_head_pic_def)
                .setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE)
                .setPrioriy(ImageLoaderConfig.LoadPriority.NORMAL)
                .build();
        ImageLoader.loadImage(viewHolder.mBinding.civHeadPhoto, bean.getHeadImg(), config, null);

        //评论recycleview
        RecyclerView replyRecycle = viewHolder.mBinding.rvReply;

        //图片recycleview
        RecyclerView photoRecycle = viewHolder.mBinding.photoRecycle;
        PhotoAdapter photoAdapter = new PhotoAdapter(mContext);
        photoRecycle.setLayoutManager(new GridLayoutManager(mContext, 3));
        if (photoRecycle.getItemDecorationCount() <= 0) {
            photoRecycle.addItemDecoration(new GridSpacingItemDecoration(3, 15, false));
        }
        ArrayList<String> imgs = new ArrayList<>();
        imgs.addAll(bean.getImgPaths());
        photoRecycle.setAdapter(photoAdapter);
        photoAdapter.setDatas(imgs, true);
        photoAdapter.setItemListener(new SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(PhotoViewActivity.IMAGE_URL, photoAdapter.getDatas());
                bundle.putInt(PhotoViewActivity.SHOW_POSITION, position);
                PhotoViewActivity.startActivity(mContext, PhotoViewActivity.class, bundle);
            }
        });
    }

    public class EvaluationManagerViewHolder extends BaseViewHolder {
        private ItemEvaluationManagerBinding mBinding;

        public EvaluationManagerViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }


    private OnsendViewClick onSendViewClick;

    public void setOnSendViewClick(OnsendViewClick onSendViewClick) {
        this.onSendViewClick = onSendViewClick;
    }

    interface OnsendViewClick {

        void sendMessage(String mssage, int position);
    }


}
