package com.yl.technician.widget.waves;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.yl.technician.widget.waves.config.WavesConfig;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 波纹效果
 *
 * Created by zm on 2017/4/18.
 */

public class WavesView extends View{

    private boolean mIsRunning;
    private long mLastCreateTime;
    //如果没有设置mMaxRadius，可mMaxRadius = 最小长度 * mMaxRadiusRate;
    private float mMaxRadius;

    private WavesConfig mConfig;

    private List<Circle> mCircleList = new LinkedList<>();

    public WavesView(Context context) {
        super(context);
    }

    public WavesView(Context context, AttributeSet attrs) {
        super(context,attrs);
        checkConfig();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (!mConfig.mMaxRadiusSet) {
            mMaxRadius = Math.min(w, h) * mConfig.mMaxRadiusRate / 2.0f;
        }
    }

    /**
     * 初始化 MapWavesView
     * @param config
     */
    public void init(WavesConfig config){
        mConfig = config;
        checkConfig();
    }

    /**
     * 检测配置的合法性，内部会根据配置做一些初始化操作
     */
    private void checkConfig() {
        if (mConfig == null){
            mConfig = new WavesConfig.Bulider().create();
        }
        if (mConfig.mMaxRadiusSet){
            mMaxRadius = mConfig.mMaxRadius;
        }
    }

    /**
     * 开始
     */
    public void start(){
        if (!mIsRunning) {
            mIsRunning = true;
            mCreateCircle.run();
            wavesDuration();
        }
    }

    /**
     * 缓慢停止
     */
    public void stop() {
        mIsRunning = false;
    }

    /**
     * 立即停止
     */
    public void stopImmediately() {
        mIsRunning = false;
        mCircleList.clear();
        invalidate();
    }

    /**
     * 波纹持续时间
     */
    private void wavesDuration(){
        postDelayed(new Runnable() {
            @Override
            public void run() {
                stop();
            }
        },mConfig.mStopTime);
    }


    private Runnable mCreateCircle = new Runnable() {
        @Override
        public void run() {
            if (mIsRunning) {
                newCircle();
                postDelayed(mCreateCircle,mConfig.mSpeed);
            }
        }
    };

    protected void onDraw(Canvas canvas){
        Iterator<Circle> iterator = mCircleList.iterator();
        while (iterator.hasNext()){
            Circle circle = iterator.next();
            float radius = circle.getCurrentRadius();
            if (System.currentTimeMillis() - circle.mCreateTime < mConfig.mDuration){
                mConfig.mPaint.setAlpha(circle.getAlpha());
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mConfig.mPaint);
            }else {
                iterator.remove();
            }
        }
        if (mCircleList.size() > 0){
            postInvalidateDelayed(10);
        }
    }


    /**
     * 创建一个新的圆
     */
    public void newCircle() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastCreateTime < mConfig.mSpeed){
            return;
        }
        Circle circle = new Circle();
        mCircleList.add(circle);
        invalidate(); // 请求重新 draw()
        mLastCreateTime = currentTime;
    }

    @Override
    protected void onDetachedFromWindow() {
        stopImmediately();
        super.onDetachedFromWindow();
    }

    private class Circle {
        private long mCreateTime; // 创建圆的当前时间

        Circle() {
            mCreateTime = System.currentTimeMillis();
        }

        int getAlpha() {
            float percent = (getCurrentRadius() - mConfig.mInitialRadius)
                    / (mMaxRadius - mConfig.mInitialRadius);
            return (int)(255 - mConfig.mInterpolator.getInterpolation(percent) * 255);
        }

        float getCurrentRadius() {
            float percent = (System.currentTimeMillis() - mCreateTime) * 1.0f / mConfig.mDuration;
            return mConfig.mInitialRadius + mConfig.mInterpolator.getInterpolation(percent)
                    * (mMaxRadius - mConfig.mInitialRadius);
        }

    }

}
