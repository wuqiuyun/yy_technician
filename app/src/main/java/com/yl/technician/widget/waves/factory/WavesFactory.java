package com.yl.technician.widget.waves.factory;


import com.yl.technician.widget.waves.product.Waves;

/**
 * Created by zm on 2017/5/15.
 */

public class WavesFactory {

    /**
     * 创建波纹效果的工厂方法
     *
     * @param clz 波纹效果类型
     * @return Waves对象 {@link Waves}
     */
   public static <T extends Waves> T createWaves(Class<T> clz){
        Waves waves = null;
       try {
           waves = (Waves) Class.forName(clz.getName()).newInstance();
       } catch (Exception e) {
           e.printStackTrace();
       }
       return (T)waves;
   }
}
