package com.yl.technician.widget.bottomview;

import android.content.Context;

import com.yl.technician.widget.bottomview.base.BaseBottomView;


/**
 * Created by zm on 2018/10/13.
 */
public class BottomViewFactory {

    public enum Type{
        //头像
        Avatar,
        //作品
        Opus,
        /**
         * 地图
         */
        Map,
        /**
         * 支付选择
         * */
        PAY
    }

    public static final BaseBottomView create(Context context, Type type) {
        switch (type) {
            case Avatar:
                return new SelectPhotoView(context);
            case Opus:
                return new DeleteOpusView(context);
            case Map:
                return new MapSelectView(context);
            case PAY:
                return new SelectPayTypeView(context);
            default:
                break;
        }
        return null;
    }
}
