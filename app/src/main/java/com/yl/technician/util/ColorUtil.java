package com.yl.technician.util;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

import com.yl.technician.YLApplication;

/**
 * Created by zm on 2018/9/27.
 */
public class ColorUtil {

    @ColorInt
    public static final int getColor(@ColorRes int colorId) {
        return ContextCompat.getColor(YLApplication.getContext(), colorId);
    }
}
