package com.yl.technician.widget.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;
import com.yl.technician.R;
import com.yl.technician.util.ColorUtil;

/**
 * 月日历布局
 * Created by lyj on 2018/12/25.
 */

public class CalendarMonthView extends MonthView {

    private int mRadius;

    public CalendarMonthView(Context context) {
        super(context);

        //兼容硬件加速无效的代码
        setLayerType(View.LAYER_TYPE_SOFTWARE, mSelectedPaint);
        //4.0以上硬件加速会导致无效
//        mSelectedPaint.setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.SOLID));

        setLayerType(View.LAYER_TYPE_SOFTWARE, mSchemePaint);
//        mSchemePaint.setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.SOLID));
    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 11 * 4;
    }

    /**
     * 如果需要点击Scheme没有效果，则return true
     *
     * @param canvas    canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param y         日历Card y起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return false 则不绘制onDrawScheme，因为这里背景色是互斥的
     */
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
//        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
        return true;
    }

    /**
     * 绘制标记的事件日子
     *
     * @param canvas   canvas
     * @param calendar 日历calendar
     * @param x        日历Card x起点坐标
     * @param y        日历Card y起点坐标
     */
    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
    }

    /**
     * 绘制文本
     * //月份 农历展示
     * @param canvas     canvas
     * @param calendar   日历calendar
     * @param x          日历Card x起点坐标
     * @param y          日历Card y起点坐标
     * @param hasScheme  是否是标记的日期
     * @param isSelected 是否选中
     */
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;
        int top = y - mItemHeight / 8;

        if (isSelected) {//手动选中
            if (hasScheme){
                if (calendar.getScheme().equals("约满")){//灰色底色已经文字颜色
                    mSchemeTextPaint.setColor(ColorUtil.getColor(R.color.color_999999));
                    mSelectedLunarTextPaint.setColor(ColorUtil.getColor(R.color.color_999999));
                }else {
                    mSchemeTextPaint.setColor(ColorUtil.getColor(R.color.color_white));
                    mSelectedLunarTextPaint.setColor(ColorUtil.getColor(R.color.color_white));
                }
                canvas.drawText(String.valueOf(calendar.getDay()),
                        cx,
                        mTextBaseLine + top,
                        mSchemeTextPaint);
                canvas.drawText(calendar.getScheme(), cx, mTextBaseLine + y + mItemHeight / 10, mSelectedLunarTextPaint);
            }else {
                canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                        calendar.isCurrentDay() ? mCurDayTextPaint :
                                calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
            }
        } else if (hasScheme) {//设置选中
            //当天/当月字体颜色判断
            //            canvas.drawText(String.valueOf(calendar.getDay()),
            //                    cx,
            //                    mTextBaseLine + top,
            //                    calendar.isCurrentDay() ? mCurDayTextPaint :
            //                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);
            if (calendar.getScheme().equals("约满")){//灰色底色已经文字颜色
                mSchemeTextPaint.setColor(ColorUtil.getColor(R.color.color_999999));
                mSelectedLunarTextPaint.setColor(ColorUtil.getColor(R.color.color_999999));
            }else {
                mSchemeTextPaint.setColor(ColorUtil.getColor(R.color.color_white));
                mSelectedLunarTextPaint.setColor(ColorUtil.getColor(R.color.color_white));
            }
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    mTextBaseLine + top,
                    mSchemeTextPaint);
            canvas.drawText(calendar.getScheme(), cx, mTextBaseLine + y + mItemHeight / 10, mSelectedLunarTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }


    }
}
