package com.yl.technician.module.mine.footprint;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.YLApplication;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.databinding.ActivityMineFootprintBinding;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.module.mine.store.StoreFragment;
import com.yl.technician.module.mine.stylist.StylistFragment;
import com.yl.technician.module.mine.works.WorksFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的足迹
 * <p>
 * Create by zm on 2018/9/29
 */
@CreatePresenter(FootprintPresenter.class)
public class FootprintActivity extends BaseMvpAppCompatActivity<IFootprintView, FootprintPresenter> implements IFootprintView {

    ActivityMineFootprintBinding binding;

    private String[] labels = new String[] {
            YLApplication.getContext().getString(R.string.label_title_stylist),
            YLApplication.getContext().getString(R.string.label_title_store),
            YLApplication.getContext().getString(R.string.label_title_works)
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_mine_footprint;
    }

    @Override
    protected void init() {

        initView();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        binding = (ActivityMineFootprintBinding) viewDataBinding;
        // 返回
        binding.titleView.setLeftClickListener(v -> {
            finish();
        });
        binding.titleView.setLeftClickListener(v -> finish());
        setTab(binding.tableLayout);
        setFragment();
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
        fragments.add(StylistFragment.newInstance(Constants.ACTIVITY_FOOTPRINT));
        fragments.add(StoreFragment.newInstance(Constants.ACTIVITY_FOOTPRINT));
        fragments.add(WorksFragment.newInstance(Constants.ACTIVITY_FOOTPRINT));
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

    }
}
