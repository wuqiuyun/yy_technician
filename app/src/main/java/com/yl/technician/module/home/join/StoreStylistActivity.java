package com.yl.technician.module.home.join;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityStoreStylistBinding;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.StoreStylistNumberBena;
import com.yl.technician.module.mine.stylist.StylistFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 门店美发师
 * <p>
 * Create by zm on 2018/10/10
 */
@CreatePresenter(StoreStylistPresenter.class)
public class StoreStylistActivity extends BaseMvpAppCompatActivity<StoreStylistView, StoreStylistPresenter> implements StoreStylistView {

    ActivityStoreStylistBinding binding;

    private String[] labels = new String[2];
    private String mStoreId;
    private Intent mStartIntent;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_store_stylist;
    }

    @Override
    protected void init() {

        initView();

        initData();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        binding = (ActivityStoreStylistBinding) viewDataBinding;
        binding.titleView.setLeftClickListener(v -> finish());
        binding.titleView.setRightImgClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.STORE_ID,mStoreId);
                startActivity(StoreStylistActivity.this,SearchStylistActivity.class,bundle);
            }
        });

    }


    private void initData() {
        if (getIntent().getExtras()!=null)mStoreId = getIntent().getExtras().getString(Constants.STORE_ID);
        getMvpPresenter().getStoreStylistNumber(mStoreId);
        mStartIntent = new Intent();
    }

    private void setTab(TabLayout tableLayout) {
        for (String label : labels) {
            TabLayout.Tab newTab = tableLayout.newTab();
            View tabView = getLayoutInflater().inflate(R.layout.tab_layout_home, null);
            newTab.setCustomView(tabView);

            TextView tvLabel = tabView.findViewById(R.id.tv_label);
            tvLabel.setText(label);
            tableLayout.addTab(newTab);
        }
    }

    private void setFragment() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(StylistFragment.newInstance(Constants.ACTIVITY_JOIN_STYLIST_1,mStoreId));
        fragments.add(StylistFragment.newInstance(Constants.ACTIVITY_JOIN_STYLIST_2,mStoreId));
        binding.viewPage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }
        });
        binding.viewPage.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tableLayout));
        binding.tableLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(binding.viewPage) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPage.setCurrentItem(tab.getPosition(), false);
            }
        });
        // 是否允许viewPage滑动切换
        binding.viewPage.setScanScroll(true);
        // viewPage预加载1个页面
        binding.viewPage.setOffscreenPageLimit(1);
        // 加载第几个页面
        binding.viewPage.setCurrentItem(0, false);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }


    @SuppressLint("StringFormatMatches")
    @Override
    public void getStoreStylistNumber(StoreStylistNumberBena storeStylistNumberBena) {
        labels[0] = String.format(getString(R.string.stylist_join_num) , storeStylistNumberBena.getEnter());
        labels[1] = String.format(getString(R.string.stylist_signing_num) , storeStylistNumberBena.getSign());
        setTab(binding.tableLayout);
        setFragment();
    }
}
