package com.yl.technician.module.home.service.add;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.component.recycleview.GridSpacingItemDecoration;
import com.yl.technician.databinding.ActivityAddServiceBinding;
import com.yl.technician.model.vo.bean.ServerTypeBean;
import com.yl.technician.model.vo.bean.ServiceSettingBean;
import com.yl.technician.module.home.service.combo.SingleComboActivity;
import com.yl.technician.module.home.service.combo.multiterm.MultitermComboActivity;
import com.yl.technician.module.home.service.setting.ServiceSettingActivity;
import com.yl.technician.module.home.service.setting.two.ServiceSettingTwoActivity;

import java.util.ArrayList;


/*
    添加服务
 * Create by lvlong on  2018/10/23
 */
@CreatePresenter(AddServicePresenter.class)
public class AddServiceActivity extends BaseMvpAppCompatActivity<AddServiceView, AddServicePresenter> implements AddServiceView {

    private ActivityAddServiceBinding mBinding;
    private RecyclerView mRcvIndividualServices;
    private RecyclerView mRcvPackageService;
    private AddServiceAdapter mIndividualAdapter;
    private AddServiceAdapter mPackageAdapter;

    private ArrayList<ServiceSettingBean> mIndividualData = new ArrayList<>();
    private ArrayList<ServiceSettingBean> mPackageData = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_service;
    }

    @Override
    protected void init() {

        mBinding = (ActivityAddServiceBinding) viewDataBinding;

        initView();
        loadData();
    }

    private void loadData() {
        getMvpPresenter().getAll();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        mBinding.titleView.setLeftClickListener(v -> finish());

        //单项服务
        mRcvIndividualServices = mBinding.rcvIndividualServices;
        //套餐服务
        mRcvPackageService = mBinding.rcvPackageService;

        //设置单项服务的view和数据
        setIndividualRcv();

        //设置套餐服务的view和数据
        setPackageRcv();
        setPackageData();

    }

    private void setIndividualRcv() {
        Bundle bundle = new Bundle();

        mIndividualAdapter = new AddServiceAdapter(this);
        mIndividualAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                initAdapterData(mPackageData);
                initAdapterData(mIndividualData);
                mIndividualData.get(position).setChecked(true);
                bundle.putString("typeName", mIndividualData.get(position).getLabel());
                bundle.putString("categoryId", String.valueOf(mIndividualData.get(position).getId()));

                mIndividualAdapter.notifyDataSetChanged();
                mPackageAdapter.notifyDataSetChanged();

                if (mIndividualData.get(position).getLabel() .equals("洗剪吹") || mIndividualData.get(position).getLabel() .equals("洗吹")){
                    startActivity(AddServiceActivity.this , ServiceSettingActivity.class,bundle);
                }else{
                    startActivity(AddServiceActivity.this , ServiceSettingTwoActivity.class,bundle);
                }

            }
        });
        mRcvIndividualServices.setLayoutManager(new GridLayoutManager(this, 3));
        mRcvIndividualServices.setAdapter(mIndividualAdapter);
        mRcvIndividualServices.addItemDecoration(new GridSpacingItemDecoration(3, 50, false));

    }



    private void setIndividualData(ArrayList<ServerTypeBean> serverTypeBeans) {
        for (ServerTypeBean serverTypeBean:serverTypeBeans) {
            mIndividualData.add(new ServiceSettingBean(serverTypeBean.getName(), false,serverTypeBean.getId()));
        }
        mIndividualAdapter.setDatas(mIndividualData, true);
    }

    private ArrayList<ServiceSettingBean> initAdapterData(ArrayList<ServiceSettingBean> list) {
        for (ServiceSettingBean serviceSettingBean:list) {
            serviceSettingBean.setChecked(false);
        }
        return list;
    }


    private void setPackageRcv() {

        mPackageAdapter = new AddServiceAdapter(this);
        mPackageAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                initAdapterData(mPackageData);
                initAdapterData(mIndividualData);
                mPackageData.get(position).setChecked(true);

                mIndividualAdapter.notifyDataSetChanged();
                mPackageAdapter.notifyDataSetChanged();

                if (mPackageData.get(position).getLabel().equals("单项套餐") ){
                    startActivity(AddServiceActivity.this , SingleComboActivity.class);
                }else if (mPackageData.get(position).getLabel().equals("多项套餐") ){
                    startActivity(AddServiceActivity.this , MultitermComboActivity.class);
                }

            }
        });
        mRcvPackageService.setLayoutManager(new GridLayoutManager(this, 3));
        mRcvPackageService.setAdapter(mPackageAdapter);
        mRcvPackageService.addItemDecoration(new GridSpacingItemDecoration(3, 50, false));

    }


    private void setPackageData() {
        mPackageData.add(new ServiceSettingBean("单项套餐", false));
        mPackageData.add(new ServiceSettingBean("多项套餐", false));
        mPackageAdapter.setDatas(mPackageData, true);

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void getAllServerType(ArrayList<ServerTypeBean> serverTypeBeans) {
        setIndividualData(serverTypeBeans);
    }
}
