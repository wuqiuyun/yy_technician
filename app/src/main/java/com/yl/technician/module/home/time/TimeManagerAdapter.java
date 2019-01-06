package com.yl.technician.module.home.time;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.model.vo.bean.TimeManageBean;
import com.yl.technician.util.ColorUtil;

/**
 * Created by lyj on 2018/12/20.
 */
public class TimeManagerAdapter extends BaseRecycleViewAdapter<TimeManageBean> {

    private Context mContext;

    public TimeManagerAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public TimeManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_service_setting_three, parent, false);
        return new TimeManagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TimeManagerViewHolder viewHolder = (TimeManagerViewHolder) holder;
        TimeManageBean timeBean = mDatas.get(position);
        viewHolder.cb_time.setText(timeBean.getTime());

        if (timeBean.isLock()) {
            viewHolder.cb_time.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_btn_selected2));
            viewHolder.cb_time.setTextColor(ColorUtil.getColor(R.color.color_999999));
        }else {
            viewHolder.cb_time.setBackground(ContextCompat.getDrawable(mContext, R.drawable.text_bg_selector4));
            viewHolder.cb_time.setTextColor(ColorUtil.getColor(timeBean.isChecked() ? R.color.white : R.color.color_343434));
        }
        viewHolder.cb_time.setEnabled(timeBean.isEnable());
        viewHolder.cb_time.setChecked(timeBean.isChecked());
    }

    public class TimeManagerViewHolder extends BaseViewHolder {
        private CheckBox cb_time;

        public TimeManagerViewHolder(View itemView) {
            super(itemView);
            cb_time = itemView.findViewById(R.id.cb_label);
        }
    }


    public String getSelectTime() {
        for (TimeManageBean timeBean : mDatas) {
            if (timeBean.isChecked()) {
                return timeBean.getTime();
            }
        }
        return "";
    }

}