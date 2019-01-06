package com.yl.technician.widget.waves.product;

import android.graphics.Color;
import android.graphics.Paint;

import com.yl.technician.widget.waves.WavesView;
import com.yl.technician.widget.waves.config.WavesConfig;

/**
 * 填充式波纹效果
 *
 * Created by zm on 2017/5/15.
 */

public class WavesFillView extends Waves {
    /**
     * 波纹的创建速度
     */
    private static final int MAVES_SPEED = 1500;
    /**
     * 一个波纹从创建到消失的时间
     */
    private static final int MAVES_DURATION = 1000;

    @Override
    public Waves init(WavesView wavesView) {
        mWavesView = wavesView;
        WavesConfig config = new WavesConfig.Bulider()
                .setStyle(Paint.Style.FILL)
                .setColor(Color.parseColor("#f79859"))
                .setSpeed(MAVES_SPEED)
                .setDuration(MAVES_DURATION)
                .create();
        mWavesView.init(config);
        return this;
    }

    @Override
    public void start() {
        if (mWavesView != null){
            mWavesView.start();
        }
    }

    @Override
    public void stop() {
        if (mWavesView != null) {
            mWavesView.stopImmediately();
        }
    }
}
