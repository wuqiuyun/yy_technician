package com.yl.technician.module.home.personpay.selectstore;

import android.content.Intent;
import android.os.Bundle;
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
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.component.amap.LocationPresenter;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.recycleview.SpaceItemHorizontalDecoration;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityStoreSelectionBinding;
import com.yl.technician.databinding.FragmentStoreBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.StoreBean;
import com.yl.technician.model.vo.bean.UserBean;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.technician.module.home.personpay.PersonPayActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择门店
 * Created by wqy on 2018/11/6.
 */
@CreatePresenter(SelectStorePresenter.class)
public class SelectStoreActivity extends BaseMvpAppCompatActivity<ISelectStoreView, SelectStorePresenter> implements ISelectStoreView,
        AMapLocationListener,ClickHandler {
    private StoreSelectionAdapter storeSelectionAdapter;
    private ArrayList<StoreBean> storeBeans = new ArrayList<>();
    private Bundle mBundle;
    private UserBean userBean;
    private String mStoreId,mStoreName;
    private double lat;
    private double lng;
    private LocationPresenter locationPresenter;
    private String mUserId;
    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_store_selection;
    }

    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initData() {
        mBundle = getIntent().getExtras();
        if (mBundle != null) {
        }
        mUserId = AccountManager.getInstance().getUserId();
        locationPresenter = new LocationPresenter(this.getApplicationContext());
        locationPresenter.setMapLocationListener(this);
        locationPresenter.startLocation();
    }

    private void initView() {

        ActivityStoreSelectionBinding storeSelectionBinding = (ActivityStoreSelectionBinding) viewDataBinding;
        storeSelectionBinding.setClick(this);
        storeSelectionBinding.titleView.setLeftClickListener(view -> finish());
        storeSelectionBinding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        storeSelectionBinding.recycleView.addItemDecoration(new SpaceItemHorizontalDecoration(10));

        storeSelectionAdapter = new StoreSelectionAdapter(this);
        storeSelectionBinding.recycleView.setAdapter(storeSelectionAdapter);

        storeSelectionAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < storeSelectionAdapter.getDatas().size(); i++) {
                    StoreBean storeBean = storeSelectionAdapter.getDatas().get(i);
                    if (i == position) {
                        storeBean.setStatus(true);
                        mStoreId = String.valueOf(storeBean.getStoreId());
                        mStoreName = storeBean.getStoreName();
                    } else {
                        storeBean.setStatus(false);
                    }
                }
                storeSelectionAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                if (mStoreId == null) {
                    showToast("请选择门店");
                    return;
                }
                    //提交订单页返回
                    Intent intent = new Intent();
                    intent.putExtra("storeid", mStoreId);
                    intent.putExtra("storename",mStoreName);
                    setResult(PersonPayActivity.FROMACTIVITY, intent);
                    finish();
                break;
        }
    }

    @Override
    public void getStoreListSuccess(List<StoreBean> list) {
        ArrayList<StoreBean> newData = (ArrayList<StoreBean>) list;
        storeSelectionAdapter.setDatas(newData, true);
    }

    @Override
    public void getStoreListFail() {
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        lat = aMapLocation.getLatitude();
        lng = aMapLocation.getLongitude();
        getMvpPresenter().getMyStore(String.valueOf(lat), String.valueOf(lng), mUserId);
        if (null != locationPresenter) {
            locationPresenter.stopLocation();
            locationPresenter = null;
        }
    }

}
