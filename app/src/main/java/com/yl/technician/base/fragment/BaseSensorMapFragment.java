package com.yl.technician.base.fragment;


import com.amap.api.maps.AMap;
import com.yl.core.component.map.uitl.SensorEventHelper;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.base.mvp.IBaseView;

/**
 * 基于地图fragment增加罗盘方向
 * Created by zm on 2018/9/14.
 */

public abstract class BaseSensorMapFragment<V extends IBaseView, P extends BasePresenter<V>> extends BaseMapFragment<V,P> {

    protected SensorEventHelper mSensorHelper;

    /**
     * 传感器选择角度
     */
    protected float mAngle = 0;

    @Override
    protected void initMap(AMap amap) {
        super.initMap(amap);
        mSensorHelper = new SensorEventHelper(getActivity());
        mSensorHelper.setOnSensorAngleListener(new SensorEventHelper.SensorAngleListener() {
            @Override
            public void onAngleChanged(float angle) {
                mAngle = angle;
                if (pointerMarker != null) {
                    float bearing = aMap != null && aMap.getCameraPosition() != null
                            ? aMap.getCameraPosition().bearing
                            : 0;
                    pointerMarker.setRotateAngle(-mAngle + bearing);
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSensorHelper != null) {
            mSensorHelper.unRegisterSensorListener();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSensorHelper != null) {
            mSensorHelper.unRegisterSensorListener();
            mSensorHelper = null;
        }
    }
}
