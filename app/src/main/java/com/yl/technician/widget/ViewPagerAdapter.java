package com.yl.technician.widget;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zm on 2018/11/28.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private List<View> viewList = null;
    private int viewSize = 0;
    private boolean isRecycle = false;

    public ViewPagerAdapter(List<View> views, boolean isRecycle) {
        super();
        this.viewList = views;
        this.viewSize = views.size();
        this.isRecycle = isRecycle;
    }

    @Override
    public int getCount() {
        return isRecycle ? Integer.MAX_VALUE : viewSize;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (isRecycle) {
            position = position % viewSize;
        }
        if (viewList.get(position).getParent() != null) {
            ((ViewPager) viewList.get(position).getParent()).removeView(viewList.get(position));
        }
        container.addView(viewList.get(position), 0);
        return viewList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }
}
