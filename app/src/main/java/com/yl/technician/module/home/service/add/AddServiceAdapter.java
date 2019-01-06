package com.yl.technician.module.home.service.add;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.databinding.ItemAddServiceBinding;
import com.yl.technician.model.vo.bean.ServiceSettingBean;
import com.yl.technician.util.FormatUtil;


/*
    添加服务适配器
 * Create by lvlong on  2018/10/23
 */

public class AddServiceAdapter extends BaseRecycleViewAdapter<ServiceSettingBean> {

    private Context mContext;

    public AddServiceAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_add_service, parent, false);
        return new AddServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AddServiceViewHolder viewHolder = (AddServiceViewHolder) holder;
        ServiceSettingBean bean = mDatas.get(position);
        viewHolder.mBinding.cbLabel.setText(FormatUtil.Formatstring(bean.getLabel()));
        viewHolder.mBinding.cbLabel.setChecked(bean.isChecked());
    }

    public class AddServiceViewHolder extends BaseViewHolder{

        private ItemAddServiceBinding mBinding;

        public AddServiceViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
