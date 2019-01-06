package com.yl.technician.widget.filter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yl.core.component.log.DLog;
import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.databinding.ItemFilterNearbyBinding;
import com.yl.technician.model.vo.bean.AreaBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 *  @描述：    附近的适配器
 */

public class FilterNearbyAdapter extends BaseRecycleViewAdapter {
    private Context mContext;
    private SynthesisAdapter mSynthesisAdapter;
    private List<AreaBean> areaList;
    private ArrayList<String> areaNames;
    private HashMap<String, String> hashMap = new HashMap<>();
    private int getIdType;


    public FilterNearbyAdapter(Context context,int getIdType) {
        mContext = context;
        this.getIdType = getIdType;
    }

    @NonNull
    @Override
    public FilterNearbyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_filter_nearby, parent, false);
        return new FilterNearbyAdapter.FilterNearbyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FilterNearbyViewHolder filterNearbyViewHolder= (FilterNearbyViewHolder) holder;
        filterNearbyViewHolder.mBinding.rvParentMenu.setLayoutManager(new LinearLayoutManager(mContext));

        if (mSynthesisAdapter==null){
            areaNames = new ArrayList<>();
            for (AreaBean areaBean:areaList) {
                areaNames.add(areaBean.getName());
            }
            mSynthesisAdapter = new SynthesisAdapter(mContext,1);

            mSynthesisAdapter.setDatas(areaNames,true);

            mSynthesisAdapter.setItemListener(new RecycleViewItemListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (mINearbySelectListener!=null){
                        if (position==0||position==1){
                            hashMap.put("districtId","-1");
                        }else {
                            if (getIdType==0){
                                hashMap.put("districtId",String.valueOf(areaList.get(position).getAreaId()));
                            }else {
                                hashMap.put("districtId",String.valueOf(areaList.get(position).getId()));
                            }
                        }

                        mINearbySelectListener.callBack(hashMap);
                    }
                }
                @Override
                public void OnItemLongClickListener(View view, int position) {
                }
            });
        }
        filterNearbyViewHolder.mBinding.rvParentMenu.setAdapter(mSynthesisAdapter);
    }
    public void setAreaList(List<AreaBean> areaList) {
        this.areaList=areaList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class FilterNearbyViewHolder extends BaseViewHolder {
        private ItemFilterNearbyBinding mBinding;

        public FilterNearbyViewHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
            mBinding.rvSubtMenu.setLayoutManager(new LinearLayoutManager(mContext));
           ArrayList<String> distances = new ArrayList<>();
                distances.add("0.5km");
                distances.add("1km");
                distances.add("2km");
                distances.add("3km");
                distances.add("4km");
                distances.add("5km");
            SynthesisAdapter synthesisAdapter2 = new SynthesisAdapter(mContext,0);
            synthesisAdapter2.setItemListener(new RecycleViewItemListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (mINearbySelectListener!=null){
                        synthesisAdapter2.setTempPosition(position);
                        HashMap<String, String> hashMap = new HashMap<>();
                        float f = Float.valueOf(distances.get(position).replace("km", ""));
                        hashMap.put("distance",String.valueOf(f*1000));
                        mINearbySelectListener.callBack(hashMap);
                    }
                }

                @Override
                public void OnItemLongClickListener(View view, int position) {

                }
            });
            mBinding.rvSubtMenu.setAdapter(synthesisAdapter2);
            synthesisAdapter2.setDatas(distances,true);
        }
    }
    private INearbySelectListener mINearbySelectListener;

    public interface INearbySelectListener {
        void callBack(Map<String, String> screenings);
//        void areanCallBack( Map<String, String> screenings );
//        void distancesCallBack( Map<String, String> screenings );
    }

    public void setINearbySelectListener(INearbySelectListener INearbySelectListener) {
        mINearbySelectListener = INearbySelectListener;
    }
}
