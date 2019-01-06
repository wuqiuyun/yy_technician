package com.yl.technician.module.home.time;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yl.core.component.log.DLog;
import com.yl.technician.R;
import com.yl.technician.model.vo.bean.TimeBean;

/**
 * Created by zhangzz on 2018/10/31.
 */
public class TimeSelfDefinedAdapter extends BaseQuickAdapter<TimeBean, BaseViewHolder> {
    public TimeSelfDefinedAdapter(int layoutResId) {
        super(layoutResId, null);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, final TimeBean definedBean) {
        if (definedBean != null) {
            viewHolder.setText(R.id.cb_label, getWeekString(definedBean.getWorkday()));
            CheckBox cb_label = viewHolder.getView(R.id.cb_label);
            viewHolder.setText(R.id.tv_dialog_time, definedBean.getStarttime() + "-" + definedBean.getEndtime());
            if (definedBean.getStatus() == 1) {
                cb_label.setChecked(true);
                viewHolder.setTextColor(R.id.tv_dialog_time, mContext.getResources().getColor(R.color.color_FFB848));
            } else {
                cb_label.setChecked(false);
                viewHolder.setTextColor(R.id.tv_dialog_time, mContext.getResources().getColor(R.color.text_color));
            }
            cb_label.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (definedBean.getStatus() == 0) {
                        definedBean.setStatus(1);
                    } else {
                        definedBean.setStatus(0);
                    }
                    notifyDataSetChanged();
                }
            });

        }
    }

    private String getWeekString(int i) {
        switch (i) {
            case 1:
                return mContext.getResources().getString(R.string.monday);
            case 2:
                return mContext.getResources().getString(R.string.tuesday);
            case 3:
                return mContext.getResources().getString(R.string.wednesday);
            case 4:
                return mContext.getResources().getString(R.string.thursday);
            case 5:
                return mContext.getResources().getString(R.string.friday);
            case 6:
                return mContext.getResources().getString(R.string.saturday);
            case 7:
                return mContext.getResources().getString(R.string.sunday);
        }
        return null;
    }
}
