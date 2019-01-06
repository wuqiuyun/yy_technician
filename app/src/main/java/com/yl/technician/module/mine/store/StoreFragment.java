package com.yl.technician.module.mine.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.base.fragment.BaseMvpFragment;
import com.yl.technician.component.amap.LocationPresenter;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.FragmentStoreBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.StoreBean;
import com.yl.technician.model.vo.requestbody.SortSearchRequesetBody;
import com.yl.technician.module.home.in.add.AddApplyForActivity;
import com.yl.technician.module.home.in.add.SearchStoresActivity;
import com.yl.technician.module.home.store.StoreManagerActivity;
import com.yl.technician.module.mine.collect.CollectActivity;
import com.yl.technician.module.mine.stylist.IUpDataFragment;
import com.yl.technician.util.RefreshLayoutUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 门店
 * <p>
 * Created by zm on 2018/10/10.
 */
@CreatePresenter(StorePresenter.class)
public class StoreFragment extends BaseMvpFragment<IStoreView, StorePresenter>
        implements IStoreView,OnRefreshListener,OnLoadMoreListener, AMapLocationListener,IUpDataFragment {

    FragmentStoreBinding binding;
    private StoreAdapter adapter;
    private ArrayList<StoreBean> data = new ArrayList<>();
    private SmartRefreshLayout refreshLayout;
    
    private LocationPresenter locationPresenter;

    private String mUserId;
    private double lat;
    private double lng;

    private int page = 1;//页数
    private int size = 10;//每页数量

    private int fromActivity;//从哪个页面来的
    private String search;//搜索字段
    private SortSearchRequesetBody mSortSearchRequesetBody;

    private boolean isInviteJoin = false;

    public static Fragment newInstance(int from) {
        StoreFragment storeFragment = new StoreFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("from",from);
        storeFragment.setArguments(bundle);
        return storeFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_store;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        fromActivity = bundle.getInt("from", 0);
        search = bundle.getString("search");

        binding = (FragmentStoreBinding) viewDataBinding;
        // init recycleview
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        refreshLayout = binding.refreshLayout;
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);

        initAdapter();

    }

    private void initAdapter() {
        switch (fromActivity) {
            case Constants.ACTIVITY_STORE_JOIN ://加入门店
                isInviteJoin = true;
                adapter = new StoreAdapter(getContext(),Constants.STORE_LIST_TYPE_0);
                break;
            case Constants.ACTIVITY_STORE_ADD ://筛选门店
                //注册监听
                AddApplyForActivity activity = (AddApplyForActivity) getActivity();
                activity.setIUpDataFragment(this);
                adapter = new StoreAdapter(getContext(),Constants.STORE_LIST_TYPE_1);
                break;
            case Constants.ACTIVITY_STORE_SEARCH ://搜索
                SearchStoresActivity activity2 = (SearchStoresActivity) getActivity();
                activity2.setIUpDataFragment(this);
                adapter = new StoreAdapter(getContext(),Constants.STORE_LIST_TYPE_1);
                break;
            case Constants.ACTIVITY_FOOTPRINT ://足迹
                adapter = new StoreAdapter(getContext(),Constants.STORE_LIST_TYPE_2);
                break;
            case Constants.ACTIVITY_COLLECT ://收藏
                adapter = new StoreAdapter(getContext(),Constants.STORE_LIST_TYPE_2);
                break;
            case 0:
                showToast("来源页获取错误");
                break;
        }
        adapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener(){
            @Override
            public void onItemClick(View view, int position) {
                // TODO 门店详情
                StoreBean store = adapter.getDatas().get(position);
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putString(Constants.STORE_ID, String.valueOf(store.getStoreId()));
                intent.setClass(getContext(),StoreManagerActivity.class);
                intent.putExtras(b);
                startActivityForResult(intent,Constants.RESULT_REFRESH_CODE);
            }
        });
        binding.recycleView.setAdapter(adapter);
        adapter.setDatas(data, true);
    }

    @Override
    protected void loadData() {
        mUserId = AccountManager.getInstance().getUserId();
        locationPresenter = new LocationPresenter(getContext().getApplicationContext());
        locationPresenter.setMapLocationListener(this);
        locationPresenter.startLocation();

        mSortSearchRequesetBody = new SortSearchRequesetBody();
        mSortSearchRequesetBody.setUserId(mUserId);//用户ID
        mSortSearchRequesetBody.setSortType("0");//用户ID

        if (fromActivity!=Constants.ACTIVITY_STORE_SEARCH ) refreshLayout.autoRefresh();


    }
    
    private void getStoreList(double lat, double lng, int page, int size, String mUserId,SortSearchRequesetBody sortSearchRequesetBody) {
        switch (fromActivity) {
            case Constants.ACTIVITY_STORE_JOIN ://加入门店
                getMvpPresenter().getMyStore(String.valueOf(lat), String.valueOf(lng), mUserId);
                break;
            case Constants.ACTIVITY_STORE_ADD ://筛选门店
                //注册监听
                getMvpPresenter().sortSearch(sortSearchRequesetBody);
                break;
            case Constants.ACTIVITY_STORE_SEARCH ://搜索
                getMvpPresenter().search(search,String.valueOf(lng),String.valueOf(lat),mUserId);
                break;
            case Constants.ACTIVITY_FOOTPRINT ://足迹
                getMvpPresenter().getStoreFoot(lat, lng, page, size, mUserId);
                break;
            case Constants.ACTIVITY_COLLECT ://收藏
                getMvpPresenter().getStoreCollection(lat, lng, page, size, mUserId);
                break;
            case 0:
                showToast("来源页获取错误");
                break;
        }

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        page ++;
        getStoreList(lat, lng, page, size, mUserId,mSortSearchRequesetBody);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page = 1;
        getStoreList(lat, lng, page, size, mUserId,mSortSearchRequesetBody);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (fromActivity== Constants.ACTIVITY_STORE_JOIN ){
            getStoreList(lat, lng, page, size, mUserId,mSortSearchRequesetBody);
        }
    }

    @Override
    public void getStoreListSuccess(List<StoreBean> list) {
        RefreshLayoutUtil.finishRefreshLayout(refreshLayout);
        ArrayList<StoreBean> newData = (ArrayList<StoreBean>) list;
        if (page == 1) {
            adapter.setDatas(newData, true);
        } else {
            adapter.addDatas(newData, true);
        }

        switch (fromActivity) {
            case Constants.ACTIVITY_STORE_JOIN ://加入门店
            case Constants.ACTIVITY_STORE_ADD ://筛选门店
            case Constants.ACTIVITY_STORE_SEARCH ://搜索
                refreshLayout.setNoMoreData(true);
                break;
            default:
                if (list.size() < size ) {// 加载的数据不够页面数量 则认为没有下一页
                    refreshLayout.setNoMoreData(true);
                } else {
                    refreshLayout.setNoMoreData(false);
                }
                break;
        }

    }

    @Override
    public void getStoreListFail() {
        RefreshLayoutUtil.finishRefreshLayout(refreshLayout);
        refreshLayout.setNoMoreData(true);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (null != locationPresenter) {
            locationPresenter.stopLocation();
            locationPresenter = null;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        lat = aMapLocation.getLatitude();
        lng = aMapLocation.getLongitude();
        mSortSearchRequesetBody.setLat(String.valueOf(lat));//纬度
        mSortSearchRequesetBody.setLng(String.valueOf(lng));//经度
        if (null != locationPresenter) {
            locationPresenter.stopLocation();
            locationPresenter = null;
        }
    }
    @Override
    public void onUpData(int filterType, Object o) {
        page=1;
        mSortSearchRequesetBody.setSortType("0");
        mSortSearchRequesetBody.setDistrictId(null);
        mSortSearchRequesetBody.setMaxDistance(null);
        mSortSearchRequesetBody.setCategoryIds(null);
        switch (filterType) {
            case Constants.ACTIVITY_STORE_FILTER_1:
                //入驻门店-附近
                Map<String,String> map= (Map<String, String>) o;
                mSortSearchRequesetBody.setProvinceId(map.get("provinceId"));//省ID，默认0
                mSortSearchRequesetBody.setCityId(map.get("cityId"));//城市ID，默认0
                if (map.get("districtId")!=null){
                    mSortSearchRequesetBody.setDistrictId(map.get("districtId").equals("-1")?null:map.get("districtId"));//区ID，默认0 ,
                }
                if (map.get("maxDistance")!=null)mSortSearchRequesetBody.setMaxDistance(map.get("maxDistance").replace(".0",""));//区ID，默认0 ,
                break;
            case Constants.ACTIVITY_STORE_FILTER_2:
                //入驻门店-综合排序
                DLog.d("排序"+o);
                mSortSearchRequesetBody.setSortType(String.valueOf(o));
                break;
            case Constants.ACTIVITY_STORE_FILTER_3:
                //入驻门店-筛选
                mSortSearchRequesetBody.setCategoryIds((ArrayList<String>)o);
                break;
            case Constants.ACTIVITY_STORE_FILTER_4:
                //搜索门店
                search=String.valueOf(o);
                break;
            default:
                showToast("来源页获取错误");
                break;
        }
            refreshLayout.autoRefresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Constants.RESULT_REFRESH_CODE&&resultCode==Constants.RESULT_REFRESH_CODE){
            refreshLayout.autoRefresh();
        }
    }


}
