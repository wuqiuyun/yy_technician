package com.yl.technician.module.home.in.add;


import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseAppCompatActivity;
import com.yl.technician.databinding.ActivityStoresSearchBinding;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.module.mine.store.StoreFragment;
import com.yl.technician.module.mine.stylist.IUpDataFragment;

/**
 * Created by lvlong on 2018/10/27.
 */
public class SearchStoresActivity extends BaseAppCompatActivity {
    private static final String BUNDLE_FRAGMENT = "StoreFragment";
    private Fragment mFragment;
    private IUpDataFragment mIUpDataFragment;
    private ActivityStoresSearchBinding mBinding;

    public void setIUpDataFragment(IUpDataFragment IUpDataFragment) {
        mIUpDataFragment = IUpDataFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_stores_search;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        initFragment();
        mBinding = (ActivityStoresSearchBinding) viewDataBinding;
        mBinding = (ActivityStoresSearchBinding) this.viewDataBinding;
        mBinding.titleView.setLeftClickListener(view -> finish());
        mBinding.btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIUpDataFragment != null)mIUpDataFragment.onUpData(Constants.ACTIVITY_STORE_FILTER_4, mBinding.etSearch.getText().toString().trim());
                }
        });
        mBinding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    if (mIUpDataFragment != null)mIUpDataFragment.onUpData(Constants.ACTIVITY_STORE_FILTER_4, mBinding.etSearch.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });

    }

    private void initFragment() {
        if (savedInstanceState != null) {
            mFragment = getSupportFragmentManager().getFragment(savedInstanceState, BUNDLE_FRAGMENT);
        }
        if (mFragment == null) {
            mFragment = StoreFragment.newInstance(Constants.ACTIVITY_STORE_SEARCH);
            mFragment.setUserVisibleHint(true);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_stylist_search, mFragment)
                .commit();

    }

}
