package com.yl.technician.widget.waves.config;

import android.graphics.Paint;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 *  波纹参数配置
 *
 * Created by zm on 2017/4/19.
 */

public class WavesConfig {
    /**
     * 画笔
     *  ANTI_ALIAS_FLAG 去锯齿
     */
    public Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 停止时间
     */
    public long mStopTime = 1000;
    /**
     * 初始波纹半径
     */
    public float mInitialRadius = 0.0f;
    /**
     * 最大的波纹半径
     */
    public float mMaxRadius = 0.0f;
    /**
     * 是否设置最大半径
     */
    public boolean mMaxRadiusSet = false;
    /**
     * 一个波纹从创建到消失的时间
     */
    public long mDuration = 2000;
    /**
     * 波纹的创建速度，每500ms创建一个
     */
    public int mSpeed = 500;

    public float mMaxRadiusRate = 0.85f;
    /**
     * 插值器
     */
    public Interpolator mInterpolator = new LinearInterpolator();

    public static class Bulider{
        /**
         * 画笔
         *  ANTI_ALIAS_FLAG 去锯齿
         */
        public Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        /**
         * 波纹持续时间
         */
        public long stopTime = 1000;
        /**
         * 初始波纹半径
         */
        public float initialRadius = 0.0f;
        /**
         * 最大的波纹半径
         */
        public float maxRadius = 0.0f;
        /**
         * 是否设置最大半径
         */
        public boolean maxRadiusSet = false;
        /**
         * 一个波纹从创建到消失的时间
         */
        public long duration = 2000;
        /**
         * 波纹的创建速度，每500ms创建一个
         */
        public int speed = 500;
        /**
         * 如果没有设置mMaxRadius，可mMaxRadius = 最小长度 * mMaxRadiusRate;
         */
        public float maxRadiusRate = 0.85f;
        /**
         * 插值器
         */
        public Interpolator interpolator = new LinearInterpolator();

        /**
         * 设置波纹持续时间
         *
         * @param time
         * @return
         */
        public Bulider setStopTime(long time) {
            this.stopTime = time;
            return this;
        }

        /**
         * 设置初始波纹半径
         *
         * @param initialRadius
         */
        public Bulider setInitialRadius(float initialRadius) {
            this.initialRadius = initialRadius;
            return this;
        }

        /**
         * 设置最大波纹半径
         * @param maxRadius
         * @return
         */
        public Bulider setMaxRadius(float maxRadius) {
            this.maxRadius = maxRadius;
            this.maxRadiusSet = true;
            return this;
        }

        /**
         * 设置波纹持续时间
         * @param duration
         * @return
         */
        public Bulider setDuration(long duration) {
            this.duration = duration;
            return this;
        }

        /**
         * 设置波纹的创建速度
         * @param speed
         * @return
         */
        public Bulider setSpeed(int speed) {
            this.speed = speed;
            return this;
        }

        public Bulider setMaxRadiusRate(float maxRadiusRate) {
            this.maxRadiusRate = maxRadiusRate;
            return this;
        }

        /**
         * 设置插值器
         * @param interpolator
         * @return
         */
        public Bulider setInterpolator(Interpolator interpolator) {
            this.interpolator = interpolator;
            return this;
        }

        /**
         * 设置画笔颜色
         * @param color
         * @return
         */
        public Bulider setColor(int color){
            paint.setColor(color);
            return this;
        }

        /**
         * 设置画笔样式
         * @param style
         * @return
         */
        public Bulider setStyle(Paint.Style style){
            paint.setStyle(style);
            return this;
        }

        /**
         * 设置线条宽度
         * @param stokeWidth
         * @return
         */
        public Bulider setStokeWidth(int stokeWidth){
            paint.setStrokeWidth(stokeWidth);
            return this;
        }

        void applyConfig(WavesConfig config){
            config.mStopTime = stopTime;
            config.mInitialRadius = initialRadius;
            config.mMaxRadius = maxRadius;
            config.mDuration = duration;
            config.mMaxRadiusRate = maxRadiusRate;
            config.mInterpolator = interpolator;
            config.mSpeed = speed;
            config.mMaxRadiusSet = maxRadiusSet;
            config.mPaint = paint;
        }

        public WavesConfig create() {
            WavesConfig config = new WavesConfig();
            applyConfig(config);
            return config;
        }
    }
}
