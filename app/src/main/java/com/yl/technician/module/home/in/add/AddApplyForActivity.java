package com.yl.technician.module.home.in.add;

import android.os.Bundle;
import android.view.View;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.databinding.ActivityAddApplyForBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.AreaBean;
import com.yl.technician.model.vo.bean.ServerTypeBean;
import com.yl.technician.module.mine.store.StoreFragment;
import com.yl.technician.module.mine.stylist.IUpDataFragment;
import com.yl.technician.widget.filter.FilterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 加入申请
 * <p>
 * Created by zm on 2018/10/10.
 */
@CreatePresenter(AddApplyForPresenter.class)
public class AddApplyForActivity extends BaseMvpAppCompatActivity<AddApplyForView, AddApplyForPresenter>
        implements AddApplyForView {
    private static final String BUNDLE_FRAGMENT = "StoreFragment";

    private StoreFragment fragment;
    private ActivityAddApplyForBinding binding;
    private HashMap<String, String> areaMap = new HashMap<>();
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, BUNDLE_FRAGMENT, fragment);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_apply_for;
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        binding = (ActivityAddApplyForBinding) viewDataBinding;
        binding.titleView.setLeftClickListener(v -> finish());
        //搜索
        binding.titleView.setRightImgClickListener(view -> {
            startActivity(AddApplyForActivity.this,SearchStoresActivity.class);
        });
        setFilterViewCallBack();
        binding.vBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.viewFilter.showView(binding.viewFilter.getTempPosition());
            }
        });
        initFragment();
        getMvpPresenter().getAll();
        getMvpPresenter().getAreaByUserId(AccountManager.getInstance().getUserId());

    }

    private void setFilterViewCallBack() {
        binding.viewFilter.setIFilterDataCallBack(new FilterView.IFilterDataCallBack() {
            @Override
            public void onFilterNearbyCallBack(Map<String, String> screenings) {
                if (mIUpDataFragment!=null){
                    areaMap.put("districtId",screenings.get("districtId"));
                    areaMap.put("maxDistance",screenings.get("distance"));
                    mIUpDataFragment.onUpData(Constants.ACTIVITY_STORE_FILTER_1,areaMap);
                }
                //附近数据回调
            }
            @Override
            public void onSynthesisCallBack(String sortType) {
                //综合排序数据回调
                int mSortType = 0;
                switch (sortType){
                    case "综合排序":
                        mSortType=0;
                        break;
                    case "距离最近":
                        mSortType=1;
                        break;
                    case "月接单最多":
                        mSortType=2;
                        break;
                    case "评论量最多":
                        mSortType=3;
                        break;
                }
                if (mIUpDataFragment!=null){
                    mIUpDataFragment.onUpData(Constants.ACTIVITY_STORE_FILTER_2,mSortType);
                }
            }

            @Override
            public void onFilterCallBack(ArrayList<String> screenings) {
                //综合筛选数据回调
                if (mIUpDataFragment!=null){
                    mIUpDataFragment.onUpData(Constants.ACTIVITY_STORE_FILTER_3,screenings);
                }
            }

            @Override
            public void setDimBackground(boolean b) {
                //背景变暗
                if (b){
                    binding.vBg.setVisibility(View.VISIBLE);
                }else {
                    binding.vBg.setVisibility(View.GONE);
                }

            }
        });
    }

    private void initFragment() {
        if (savedInstanceState != null) {
            fragment = (StoreFragment) getSupportFragmentManager().getFragment(savedInstanceState, BUNDLE_FRAGMENT);
        }
        if (fragment == null) {
            fragment = (StoreFragment) StoreFragment.newInstance(Constants.ACTIVITY_STORE_ADD);
            fragment.setUserVisibleHint(true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_content, fragment)
                .commit();
        }
    }



    @Override
    public void showToast(String message) {
    }
    private IUpDataFragment mIUpDataFragment;

    public void setIUpDataFragment(IUpDataFragment IUpDataFragment) {
        mIUpDataFragment = IUpDataFragment;
    }

    @Override
    public void getAllServerType(ArrayList<ServerTypeBean> serverTypeBeans) {
        binding.viewFilter.setFilterData(serverTypeBeans);
    }

    @Override
    public void getArea(ArrayList<AreaBean> areaBeans) {

        if (areaBeans!=null&&areaBeans.size()>0){
            areaBeans.get(0).setName("全部区域");
            areaMap.put("cityId",String.valueOf(areaBeans.get(0).getId()));
            areaMap.put("provinceId",String.valueOf(areaBeans.get(0).getParentId()));
        }
        areaBeans.add(0,new AreaBean("附近"));
        areaMap.put("districtId","0");
        if (mIUpDataFragment!=null){
            //筛选接口省市id必传
            mIUpDataFragment.onUpData(Constants.ACTIVITY_STORE_FILTER_1,areaMap);
        }
        binding.viewFilter.setNearbyArea(areaBeans);
    }


}
