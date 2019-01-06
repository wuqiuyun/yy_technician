package com.yl.technician.widget.picker;

import android.content.Context;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.Calendar;

/**
 * Created by zm on 2018/11/1.
 */
public abstract class BasePickersView {

    /**
     * 初始化时间选择器
     */
    public abstract TimePickerView init(Context context, OnTimeSelectListener onTimeSelectListener);

    /**
     *
     * @param context
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param onTimeSelectListener
     * @return
     */
    public abstract TimePickerView init(Context context, Calendar startDate, Calendar endDate, OnTimeSelectListener onTimeSelectListener);



    /**
     * 显示
     */
    public abstract void show();
}
