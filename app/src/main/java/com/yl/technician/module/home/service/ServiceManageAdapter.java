package com.yl.technician.module.home.service;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ItemServiceManageBinding;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.ServerItemBean;
import com.yl.technician.util.FormatUtil;

/**
 * Created by zm on 2018/10/10.
 */
public class ServiceManageAdapter extends BaseRecycleViewAdapter<ServerItemBean>{
    private Context mContext;
    public IButtonListener mIButtonListener;
    private int type;

    public ServiceManageAdapter(Context context,int type) {
        this.mContext = context;
        this.type = type;
    }

    public void setIButtonListener(IButtonListener iButtonListener){
        this.mIButtonListener = iButtonListener;
    }

    @NonNull
    @Override
    public ServiceManageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServiceManageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_service_manage, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ServiceManageViewHolder viewHolder = (ServiceManageViewHolder) holder;
        ServerItemBean serverItemBean = mDatas.get(position);

        if (type== Constants.SERVICE_MANAGE_NOT_SHELVES){
            viewHolder.mBinding.tvUpdate.setText(mContext.getString(R.string.up_frame));
            viewHolder.mBinding.tvDownFrame.setText(mContext.getString(R.string.delete));
        }
        //服务类型
        viewHolder.mBinding.tvType.setText(FormatUtil.Formatstring(serverItemBean.getServiceName()));
        //价钱
        if (serverItemBean.getPriceType()!=null&&serverItemBean.getPriceType().equals("2")){
            viewHolder.mBinding.tvPrice.setText(FormatUtil.Formatstring(serverItemBean.getFromPrice()+"~"+serverItemBean.getToPrice()));
        }else {
            viewHolder.mBinding.tvPrice.setText(FormatUtil.Formatstring(serverItemBean.getPrice()));
        }
        //服务时长
        viewHolder.mBinding.tvOrderTime.setText(FormatUtil.Formatstring(serverItemBean.getBegintime()+"~"+serverItemBean.getEndtime()));
        //预约期限
        viewHolder.mBinding.tvBookingDeadline.setText(FormatUtil.Formatstring(serverItemBean.getDeadline())+"天");
        //服务门店
        viewHolder.mBinding.tvServiceOutlets.setText(serverItemBean.getStoreLimit()==0?"不限":"限制");
        //销量
        viewHolder.mBinding.tvSoldNum.setText(FormatUtil.Formatstring(serverItemBean.getSell()));
        //服务时长
        viewHolder.mBinding.tvOrderTime.setText(String.format(mContext.getString(R.string.Order_time2) , FormatUtil.Formatstring(serverItemBean.getDuration())));

        viewHolder.mBinding.tvDownFrame.setOnClickListener(v -> {
            if (mIButtonListener != null){
                mIButtonListener.onLeftListener(v,position);
            }
        });
        viewHolder.mBinding.tvUpdate.setOnClickListener(v -> {
            if (mIButtonListener != null){
                mIButtonListener.onRightListener(v , position);
            }
        });


    }

    public class ServiceManageViewHolder extends BaseViewHolder {
        private ItemServiceManageBinding mBinding;

        public ServiceManageViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }

    public interface IButtonListener{
        void onLeftListener(View view, int position);
        void onRightListener(View view, int position);

    }

}
