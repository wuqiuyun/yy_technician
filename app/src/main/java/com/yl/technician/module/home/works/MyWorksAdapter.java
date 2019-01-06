package com.yl.technician.module.home.works;

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
import com.yl.technician.databinding.ItemMyWorksBinding;
import com.yl.technician.model.vo.bean.MyWorksBean;
import com.yl.technician.util.FormatUtil;


/*
 * Create by lvlong on  2018/11/2
 */

public class MyWorksAdapter extends BaseRecycleViewAdapter<MyWorksBean> {

    private Context mContext;

    public MyWorksAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_works, parent, false);
        return new MyWorksAdapter.MyWorkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyWorkViewHolder viewHolder = (MyWorkViewHolder) holder;
        MyWorksBean bean = mDatas.get(position);

        if (!TextUtils.isEmpty(bean.getOpusPaths()[0])) {
            ImageLoader.loadImage(viewHolder.mBinding.ivPhoto, bean.getOpusPaths()[0]);
        }

        viewHolder.mBinding.tvTitle.setText(bean.getDescribe());
        viewHolder.mBinding.tvCollectionNum.setText(FormatUtil.Formatstring(String.valueOf(bean.getCollection())));
        viewHolder.mBinding.tvForwardingNum.setText(FormatUtil.Formatstring(String.valueOf(bean.getReposted())));
        viewHolder.mBinding.tvLookNum.setText(FormatUtil.Formatstring(String.valueOf(bean.getPageview())));

    }

    public class MyWorkViewHolder extends BaseViewHolder {

        private ItemMyWorksBinding mBinding;

        public MyWorkViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
