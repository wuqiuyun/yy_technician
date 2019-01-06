package com.yl.technician.module.home.service.setting;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.databinding.ItemServiceSettingBinding;
import com.yl.technician.model.vo.bean.ServiceSettingBean;
import com.yl.technician.util.FormatUtil;


/**
 * Created by lvlong on 2018/10/12.
 */
public class ServiceSettingAdapter extends BaseRecycleViewAdapter<ServiceSettingBean> {

    private Context mContext;

    public ServiceSettingAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_service_setting, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ServiceViewHolder viewHolder = (ServiceViewHolder) holder;
        ServiceSettingBean bean = mDatas.get(position);
        viewHolder.mBinding.cbLabel.setText(FormatUtil.Formatstring(bean.getLabel()));
        viewHolder.mBinding.cbLabel.setChecked(bean.isChecked());
    }

    public class ServiceViewHolder extends BaseViewHolder {
        private ItemServiceSettingBinding mBinding;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

}
