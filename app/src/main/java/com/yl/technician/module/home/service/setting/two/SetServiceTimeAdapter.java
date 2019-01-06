package com.yl.technician.module.home.service.setting.two;

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
import com.yl.technician.databinding.ItemServiceTimeBinding;
import com.yl.technician.model.vo.bean.ServiceSettingBean;
import com.yl.technician.util.FormatUtil;


/*
    服务时间适配器
 * Create by lvlong on  2018/10/23
 */

public class SetServiceTimeAdapter extends BaseRecycleViewAdapter<ServiceSettingBean> {

    private Context mContext;

    public SetServiceTimeAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_service_time, parent, false);
        return new SetServiceTimeAdapter.SetServiceTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SetServiceTimeAdapter.SetServiceTimeViewHolder viewHolder = (SetServiceTimeAdapter.SetServiceTimeViewHolder) holder;
        ServiceSettingBean bean = mDatas.get(position);
        viewHolder.mBinding.cbLabel.setText(FormatUtil.Formatstring(bean.getLabel()));
        viewHolder.mBinding.cbLabel.setChecked(bean.isChecked());
        viewHolder.mBinding.cbLabel.setEnabled(bean.isEnabled());
    }

    public class SetServiceTimeViewHolder extends BaseViewHolder{

        private ItemServiceTimeBinding mBinding;

        public SetServiceTimeViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
