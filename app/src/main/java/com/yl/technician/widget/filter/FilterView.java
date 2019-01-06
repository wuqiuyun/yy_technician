package com.yl.technician.widget.filter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ItemFilterBinding;
import com.yl.technician.model.vo.bean.AreaBean;
import com.yl.technician.model.vo.bean.ServerTypeBean;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 筛选
 * <p>
 * Created by zm on 2018/10/12.
 */
public class FilterView extends LinearLayout implements ClickHandler {

    private IFilterDataCallBack mIFilterDataCallBack;
    private ItemFilterBinding mFilterBinding;
    private Context mContext;
    private ArrayList<TextView> mTextViews;
    private int tempPosition=-1;
    private boolean isShow=true;
    public static final int NEARBY = 0;
    public static final int SORT = 1;
    public static final int FILTER = 2;
    private SynthesisAdapter mSynthesisAdapter;
    private FilterAdapter mFilterAdapter;
    private FilterNearbyAdapter mFilterNearbyAdapter;
    private CustomLinearLayoutManager mCustomLinearLayoutManager;
    private List<AreaBean> mNearbyArea;

    public void setIFilterDataCallBack(IFilterDataCallBack IFilterDataCallBack) {
        mIFilterDataCallBack = IFilterDataCallBack;
    }

    public FilterView(Context context) {
        this(context, null);
    }

    public FilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFilterBinding = DataBindingUtil.inflate(inflater, R.layout.item_filter, this, true);
        mFilterBinding.setClick(this);
        mTextViews = new ArrayList<>();
        mTextViews.add(mFilterBinding.tvNearby);
        mTextViews.add(mFilterBinding.tvSort);
        mTextViews.add(mFilterBinding.tvFilter);
        mCustomLinearLayoutManager = new CustomLinearLayoutManager(mContext);
//                禁止滑动
        mCustomLinearLayoutManager.setScrollEnabled(false);
        mFilterBinding.rvFilterCondition.setLayoutManager(mCustomLinearLayoutManager);
//        mFilterBinding.rvFilterCondition.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        //综合排序
        mSynthesisAdapter = new SynthesisAdapter(mContext,0);
        final ArrayList<String> tempList = new ArrayList<>();
            tempList.add("综合排序");
            tempList.add("距离最近");
            tempList.add("月接单最多");
            tempList.add("评论量最多");
        mSynthesisAdapter.setDatas(tempList,true);
        mSynthesisAdapter.setItemListener(new BaseRecycleViewAdapter.RecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                showView(SORT);
                mSynthesisAdapter.setTempPosition(position);
                if (mIFilterDataCallBack!=null){
                    mIFilterDataCallBack.onSynthesisCallBack(mSynthesisAdapter.getDatas().get(position));
                }
                mFilterBinding.tvSort.setText(tempList.get(position).toString());
            }

            @Override
            public void OnItemLongClickListener(View view, int position) {

            }
        });

    }

    public void setFilterData(ArrayList<ServerTypeBean> serverTypeBeans) {
        //筛选........
        if (mFilterAdapter==null)mFilterAdapter = new FilterAdapter(mContext);
        mFilterAdapter.setIOkButtonListener(new FilterAdapter.IOkButtonListener() {
            @Override
            public void onOkButtonClick(ArrayList<String> categoryIds) {
                showView(FILTER);
                if (mIFilterDataCallBack!=null){
                    mIFilterDataCallBack.onFilterCallBack(categoryIds);
                }
            }

        });
        mFilterAdapter.setServerTypeBeans(serverTypeBeans);
    }


    private void setDrawableRight(TextView textView, @NotNull Drawable drawableRight) {
        drawableRight.setBounds(0, 0,drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawableRight, null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_nearby: // 附近
                if (mFilterNearbyAdapter!=null){
                    mFilterBinding.rvFilterCondition.setAdapter(mFilterNearbyAdapter);
                    showView(NEARBY);
                }else {
                    ToastUtils.shortToast("区域加载异常");
                }
                break;
            case R.id.tv_sort: // 综合排序
                mFilterBinding.rvFilterCondition.setAdapter(mSynthesisAdapter);
                showView(SORT);
                break;
            case R.id.tv_filter: // 筛选
                if (mFilterAdapter!=null){
                    mFilterBinding.rvFilterCondition.setAdapter(mFilterAdapter);
                    showView(FILTER);
                }else {
                    ToastUtils.shortToast("筛选类目加载异常");
                }

                break;
        }
    }

    public int getTempPosition() {
        return tempPosition;
    }

    public void showView(int p) {
        for (int i = 0; i < mTextViews.size(); i++) {
            if (p==i){
                if (tempPosition==p){
                    isShow=!isShow;
                }else {
                    isShow=true;
                }
                if (isShow){
//                show
                    if (mIFilterDataCallBack!=null){
                        mIFilterDataCallBack.setDimBackground(true);
                    }
                    mFilterBinding.rvFilterCondition.setVisibility(VISIBLE);
                    setDrawableRight(mTextViews.get(i), ContextCompat.getDrawable(mContext, R.drawable.icon_up));
                }else {
//                hide
                    mIFilterDataCallBack.setDimBackground(false);
                    mFilterBinding.rvFilterCondition.setVisibility(GONE);
                    setDrawableRight(mTextViews.get(i), ContextCompat.getDrawable(mContext, R.drawable.icon_down));
                }
            }else {
                setDrawableRight(mTextViews.get(i), ContextCompat.getDrawable(mContext, R.drawable.icon_down));
            }
        }
        tempPosition=p;
    }

    public void setNearbyArea(List<AreaBean> nearbyArea) {
        //附近
        mFilterNearbyAdapter = new FilterNearbyAdapter(mContext,1);
        mFilterNearbyAdapter.setINearbySelectListener(new FilterNearbyAdapter.INearbySelectListener() {
            @Override
            public void callBack(Map<String, String> screenings) {
                showView(NEARBY);
                if (mIFilterDataCallBack!=null){
                    mIFilterDataCallBack.onFilterNearbyCallBack(screenings);

                }
            }
        });

        mFilterNearbyAdapter.setAreaList(nearbyArea);


    }

    public class CustomLinearLayoutManager extends LinearLayoutManager {
        private boolean isScrollEnabled = true;

        public CustomLinearLayoutManager(Context context) {
            super(context);
        }

        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }

        @Override
        public boolean canScrollVertically() {
            //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
            return isScrollEnabled && super.canScrollVertically();
        }
    }


    public interface IFilterDataCallBack {
        void onFilterNearbyCallBack(Map<String, String> screenings);
        void onSynthesisCallBack(String sortType);
        void onFilterCallBack(ArrayList<String> categoryIds);
        void setDimBackground(boolean b);
    }

}
