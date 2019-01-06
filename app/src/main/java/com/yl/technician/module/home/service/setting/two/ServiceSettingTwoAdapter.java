package com.yl.technician.module.home.service.setting.two;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yl.core.component.log.DLog;
import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ItemServiceSettingTwoBinding;
import com.yl.technician.model.vo.bean.ServerTypeBean;
import com.yl.technician.model.vo.bean.ServiceSettingTwoBean;
import com.yl.technician.model.vo.requestbody.SaveServerRequestBody;

import java.util.ArrayList;
import java.util.List;


/*
 * Create by lvlong on  2018/10/24
 */

public class ServiceSettingTwoAdapter extends BaseRecycleViewAdapter<ServerTypeBean.OptionsBean> {
    private Context mContext;
    private ArrayList<ServiceSubTypeAdapter> mServiceSubTypeAdapters;
    private ArrayList<SaveServerRequestBody.ServiceOptionsBean> subAdpterData;
    private String optionbutton="添加药水";

    public String getOptionbutton() {
        return optionbutton;
    }
    public ServiceSettingTwoAdapter(Context context) {
        mContext = context;
        mServiceSubTypeAdapters =new ArrayList<>();
    }

    @NonNull
    @Override
    public ServiceSettingTwoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_service_setting_two, parent, false);

        return new ServiceSettingTwoHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ServiceSettingTwoHolder viewHolder = (ServiceSettingTwoHolder) holder;
        viewHolder.mBinding.tvType.setText(mDatas.get(position).getOptiontitle());
        //RecyclerView
        viewHolder.mBinding.rvAddMedicine.setLayoutManager(new LinearLayoutManager(mContext));
        ServiceSubTypeAdapter serviceSubTypeAdapter = new ServiceSubTypeAdapter(mContext, mDatas.get(position).getOptionname());
        mServiceSubTypeAdapters.add(serviceSubTypeAdapter);
        viewHolder.mBinding.rvAddMedicine.setAdapter(serviceSubTypeAdapter);
        viewHolder.mBinding.tvAddMedicine.setText(mDatas.get(position).getOptionbutton());
        optionbutton=mDatas.get(position).getOptionbutton();
        ArrayList<SaveServerRequestBody.ServiceOptionsBean> serviceOptionsBeans = new ArrayList<>();
        if (subAdpterData!=null){
                for (SaveServerRequestBody.ServiceOptionsBean serviceOptionsBean:subAdpterData) {
                    if (mDatas.get(position).getId()==serviceOptionsBean.getOptionId()){
                        for (SaveServerRequestBody.ServiceOptionsBean.ServiceOptionDetails serviceOptionDetails: serviceOptionsBean.getServiceOptionDetails()) {
                            SaveServerRequestBody.ServiceOptionsBean tempBean = new SaveServerRequestBody.ServiceOptionsBean();
                            tempBean.setOptionvalue(serviceOptionDetails.getOptionvalue());
                            tempBean.setPrice(serviceOptionDetails.getPrice());
                            tempBean.setServiceOptionId(serviceOptionDetails.getServiceOptionId());
                            tempBean.setOptionId(serviceOptionsBean.getOptionId());
                            tempBean.setOptiontitle(serviceOptionsBean.getOptiontitle());
                            tempBean.setOptionname(serviceOptionsBean.getOptionname());
                            serviceOptionsBeans.add(tempBean );
                        }
                        break;
                    }
                }
        }
        serviceSubTypeAdapter.setDatas(serviceOptionsBeans,true);
        viewHolder.mBinding.tvAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveServerRequestBody.ServiceOptionsBean serviceOptionsBean = new SaveServerRequestBody.ServiceOptionsBean();
                serviceOptionsBean.setOptionId(mDatas.get(position).getId());
                serviceOptionsBean.setOptiontitle(String.valueOf(mDatas.get(position).getOptiontitle()));
                serviceOptionsBean.setOptionname(String.valueOf(mDatas.get(position).getOptionname()));
                serviceSubTypeAdapter.getDatas().add(serviceOptionsBean );
                serviceSubTypeAdapter.notifyDataSetChanged();
            }
        });



    }

    public void setSubAdpterData(ArrayList<SaveServerRequestBody.ServiceOptionsBean> subAdpterData) {
        this.subAdpterData=subAdpterData;
    }


    public ArrayList<ServiceSubTypeAdapter> getServiceSubTypeAdapters() {
        return mServiceSubTypeAdapters;
    }

    public class ServiceSettingTwoHolder extends BaseViewHolder {
        ItemServiceSettingTwoBinding mBinding;

        public ServiceSettingTwoHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

}
