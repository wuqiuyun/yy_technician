package com.yl.technician.module.mine.works.details;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.core.component.image.ImageLoader;
import com.yl.technician.R;
import com.yl.technician.databinding.ItemWorksListBinding;
import com.yl.technician.model.vo.bean.OpusDetailBean;
import com.yl.technician.model.vo.bean.WorksBean;

import java.util.ArrayList;

/**
 * Created by zm on 2018/10/12.
 */
public class WorksPageAdapter extends PagerAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<OpusDetailBean.pictrue> mWorksBeans;

    public WorksPageAdapter(Context context, ArrayList<OpusDetailBean.pictrue> worksBeans) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mWorksBeans = worksBeans;
    }


    @Override
    public int getCount() {
        return mWorksBeans.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.item_works_list, container, false);
        ItemWorksListBinding binding = DataBindingUtil.bind(view);
        ImageLoader.loadImage(binding.ivWorks, mWorksBeans.get(position).getPath());
        binding.ivWorks.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ((WorksDetailsActivity)mContext).showBottomView(position);
                return true;
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(Object object) {
        // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    
    
}
