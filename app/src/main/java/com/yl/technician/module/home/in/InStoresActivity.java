package com.yl.technician.module.home.in;

import android.support.v4.app.Fragment;
import android.view.View;

import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.databinding.ActivityInStoresBinding;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.StoreBean;
import com.yl.technician.module.home.in.add.AddApplyForActivity;
import com.yl.technician.module.mine.store.StoreFragment;

import java.util.ArrayList;


/*
     入驻门店
 * Create by lvlong on  2018/10/28
 */

public class InStoresActivity extends BaseMvpAppCompatActivity<InStoresView , InStoresPresenter> implements InStoresView {
    ActivityInStoresBinding mBinding;
    private String BUNDLE_FRAGMENT="StoreFragment";
    private Fragment mFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_in_stores;
    }

    @Override
    protected void init() {
        mBinding = (ActivityInStoresBinding) viewDataBinding;
        initView();
        loadData();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        mBinding.titleView.setLeftClickListener(v -> finish());
        mBinding.titleView.setRightTextClickListener(v -> startActivity(InStoresActivity.this , AddApplyForActivity.class));
        initFragment();
    }

    private void initFragment() {
        if (savedInstanceState != null) {
            mFragment = getSupportFragmentManager().getFragment(savedInstanceState, BUNDLE_FRAGMENT);
        }
        if (mFragment == null) {
            mFragment = StoreFragment.newInstance(Constants.ACTIVITY_STORE_JOIN);
            mFragment.setUserVisibleHint(true);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_add, mFragment)
                .commit();

    }

    private void loadData() {

    }

    @Override
    public void showToast(String message) {

    }

}
