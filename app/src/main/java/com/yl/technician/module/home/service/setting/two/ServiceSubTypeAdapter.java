package com.yl.technician.module.home.service.setting.two;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.core.component.log.DLog;
import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.databinding.ItemServerTypeSubitemBinding;
import com.yl.technician.databinding.ItemServiceSettingTwoBinding;
import com.yl.technician.model.vo.bean.ServerTypeBean;
import com.yl.technician.model.vo.requestbody.SaveServerRequestBody;


/*
 * Create by lvlong on  2018/10/24
 */

public class ServiceSubTypeAdapter extends BaseRecycleViewAdapter<SaveServerRequestBody.ServiceOptionsBean> {
    private Context mContext;
    private String name;

    public ServiceSubTypeAdapter(Context context,String name) {
        mContext = context;
        this.name = name;
    }

    @NonNull
    @Override
    public ServiceSubTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_server_type_subitem, parent, false);
        return new ServiceSubTypeAdapter.ServiceSubTypeHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ServiceSubTypeHolder viewHolder = (ServiceSubTypeHolder) holder;
        viewHolder.mBinding.tvName.setText(name+":");
        viewHolder.setIsRecyclable(false);
        viewHolder.mBinding.etMedicineName.setText(mDatas.get(position).getOptionvalue());
        viewHolder.mBinding.etPrice.setText(mDatas.get(position).getPrice());
        viewHolder.mBinding.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatas.remove(position);
                notifyDataSetChanged();
            }
        });
        viewHolder.mBinding.etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                mDatas.get(position).setPrice(editable.toString());
            }
        });
        viewHolder.mBinding.etMedicineName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                mDatas.get(position).setOptionvalue(editable.toString());
            }
        });
    }
    
    public class ServiceSubTypeHolder extends BaseViewHolder {
        ItemServerTypeSubitemBinding mBinding;

        public ServiceSubTypeHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
