package com.yl.technician.module.home.service;

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
import com.yl.technician.databinding.ActivityServiceManageBinding;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.ServerItemBean;
import com.yl.technician.module.home.service.add.AddServiceActivity;
import com.yl.technician.module.mine.stylist.IUpDataFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务管理
 * <p>
 * Create by zm on 2018/10/10
 */
@CreatePresenter(ServiceManagePresenter.class)
public class ServiceManageActivity extends BaseMvpAppCompatActivity<ServiceManageView, ServiceManagePresenter> implements IUpDataFragment ,ServiceManageView{

    ActivityServiceManageBinding mBinding;

    private String[] labels = new String[] {
            YLApplication.getContext().getString(R.string.order_centre),
            YLApplication.getContext().getString(R.string.not_shelves)
    };
    private List<ServiceManageFragment> mFragments;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_service_manage;
    }

    @Override
    protected void init() {

        initView();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityServiceManageBinding) viewDataBinding;
        mBinding.titleView.setLeftClickListener(v -> finish());
        mBinding.titleView.setRightImgClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ServiceManageActivity.this , AddServiceActivity.class);
            }
        });

        setTab(mBinding.tableLayout);
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
        mFragments = new ArrayList<>();
        ServiceManageFragment serviceManageFragment = (ServiceManageFragment) ServiceManageFragment.newInstance(Constants.SERVICE_MANAGE_ORDER_CENTRE);
        ServiceManageFragment serviceManageFragment2 = (ServiceManageFragment) ServiceManageFragment.newInstance(Constants.SERVICE_MANAGE_NOT_SHELVES);
        serviceManageFragment.setIUpDataFragment(this);
        serviceManageFragment2.setIUpDataFragment(this);
        mFragments.add(serviceManageFragment);
        mFragments.add(serviceManageFragment2);
        mBinding.viewPage.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                super.destroyItem(container, position, object);
            }
        });
        mBinding.viewPage.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mBinding.tableLayout));
        mBinding.tableLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mBinding.viewPage) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBinding.viewPage.setCurrentItem(tab.getPosition(), false);
            }
        });
        // 是否允许viewPage滑动切换
        mBinding.viewPage.setScanScroll(true);
        // viewPage预加载1个页面
        mBinding.viewPage.setOffscreenPageLimit(1);
        // 加载第几个页面
        mBinding.viewPage.setCurrentItem(0, false);
    }


    @Override
    public void onUpData(int filterType, Object o) {
        for (ServiceManageFragment serviceManageFragment:mFragments) {
            serviceManageFragment.upData();
        }
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void onSuccess(ArrayList<ServerItemBean> serverItemBeans) {

    }

    @Override
    public void operationSuccess() {

    }

    @Override
    public void onFail() {

    }
}
