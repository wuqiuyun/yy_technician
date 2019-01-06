package com.yl.technician.widget.waves.product;


import com.yl.technician.widget.waves.WavesView;

/**
 * Created by zm on 2017/5/15.
 */

public abstract class Waves {
    protected WavesView mWavesView;

    /**
     * 初始化
     * {@link WavesView}
     */
    public abstract Waves init(WavesView wavesView);

    public abstract void start();

    public abstract void stop();
}
