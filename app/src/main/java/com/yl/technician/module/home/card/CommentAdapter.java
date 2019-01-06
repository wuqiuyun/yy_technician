package com.yl.technician.module.home.card;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.databinding.ItemCommentBinding;
import com.yl.technician.model.vo.bean.StylistCardBean;

/**
 * Created by Lizhuo on 2018/11/5.
 * 评价适配
 */
public class CommentAdapter extends BaseRecycleViewAdapter<StylistCardBean.CardGrade> {
    private Context context;

    public CommentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommentAdapter.CommentViewHolder viewHolder = (CommentAdapter.CommentViewHolder) holder;
        StylistCardBean.CardGrade data = mDatas.get(position);
        viewHolder.binding.tvGradeType.setText(data.getGradeType());
        viewHolder.binding.tvGradePoint.setText(data.getPoint()+"");
        viewHolder.binding.tvGradeDescribe.setText(data.getGradeDescrip());
    }

    private class CommentViewHolder extends BaseRecycleViewAdapter.BaseViewHolder {
        private ItemCommentBinding binding;

        public CommentViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
