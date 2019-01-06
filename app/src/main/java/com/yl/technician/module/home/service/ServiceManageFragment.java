package com.yl.technician.module.home.service;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.technician.R;
import com.yl.technician.base.fragment.BaseMvpFragment;
import com.yl.technician.component.recycleview.GridSpacingItemDecoration;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.FragmentServiceManageBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.ServerItemBean;
import com.yl.technician.model.vo.bean.StylistBean;
import com.yl.technician.module.home.service.combo.SingleComboActivity;
import com.yl.technician.module.home.service.combo.multiterm.MultitermComboActivity;
import com.yl.technician.module.home.service.setting.ServiceSettingActivity;
import com.yl.technician.module.home.service.setting.two.ServiceSettingTwoActivity;
import com.yl.technician.module.mine.stylist.IUpDataFragment;
import com.yl.technician.util.RefreshLayoutUtil;
import com.yl.technician.widget.dialog.YLDialog;

import java.util.ArrayList;

/**
 * Created by zm on 2018/10/10.
 */
@CreatePresenter(ServiceManagePresenter.class)
public class ServiceManageFragment extends BaseMvpFragment<ServiceManageView, ServiceManagePresenter>
        implements ServiceManageAdapter.IButtonListener
        , ServiceManageView, OnRefreshListener, OnLoadMoreListener {

    private ServiceManageAdapter mAdapter;
    private FragmentServiceManageBinding mBinding;
    private IUpDataFragment mIUpDataFragment;
    private boolean isUpData = true;
    private int mFrom;
    private int page = 1;//页数
    private int size = 10;//每页数量

    public void setIUpDataFragment(IUpDataFragment IUpDataFragment) {
        this.mIUpDataFragment = IUpDataFragment;
    }

    public static Fragment newInstance(int from) {
        ServiceManageFragment serviceManageFragment = new ServiceManageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("from", from);
        serviceManageFragment.setArguments(bundle);
        return serviceManageFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service_manage;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        mFrom = bundle.getInt("from", 0);
        mBinding = (FragmentServiceManageBinding) viewDataBinding;
        initRefreshLoadLayout();
        // init recycleview
        mBinding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(1, 30, false));
        mAdapter = new ServiceManageAdapter(getContext(), mFrom);
        mAdapter.setIButtonListener(this);
        mBinding.recycleView.setAdapter(mAdapter);
    }

    @Override
    protected void loadData() {
        mBinding.refreshLayout.autoRefresh();
    }

    private SmartRefreshLayout refreshLayout;

    protected void initRefreshLoadLayout() {
        refreshLayout = mBinding.refreshLayout;
        if (refreshLayout != null) {
            refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
            refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
            refreshLayout.setOnLoadMoreListener(this);
            refreshLayout.setOnRefreshListener(this);
        }
    }

    private void showDLDialog(String message, int action, ServerItemBean serverItemBean) {
        new YLDialog.Builder(getContext())
                .setType(YLDialog.DIALOG_TYPE_NORMAL)
                .setMessage("服务" + message + "提示")
                .setSubMessage("请确认是否" + message + "服务")
                .setPositiveButton("确定", (dialog, which) -> {
                    switch (action) {
                        case 0:
                            getMvpPresenter().isOnline(serverItemBean.getServiceId(), String.valueOf(0), getContext());
                            break;
                        case 1:
                            getMvpPresenter().delete(serverItemBean.getServiceId(), getContext());
                            break;
                        case 2:
                            getMvpPresenter().isOnline(serverItemBean.getServiceId(), String.valueOf(1), getContext());
                            break;
                    }
                    dialog.dismiss();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }


    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onSuccess(ArrayList<ServerItemBean> serverItemBeans) {

        if (refreshLayout == null || refreshLayout.getState() == RefreshState.Refreshing) { // 刷新
            mAdapter.setDatas(serverItemBeans, true);
        } else if (refreshLayout.getState() == RefreshState.Loading) { // 加载
            mAdapter.addDatas(serverItemBeans, true);
        }
        if (serverItemBeans.size() < size) {// 加载的数据不够页面数量 则认为没有下一页
            refreshLayout.setNoMoreData(true);
        } else {
            refreshLayout.setNoMoreData(false);
        }
        RefreshLayoutUtil.finishRefreshLayout(mBinding.refreshLayout);
    }

    @Override
    public void operationSuccess() {
        if (isUpData) {
            //刷新接单中/未上架Fragment列表
            if (mIUpDataFragment != null) {
                mIUpDataFragment.onUpData(0, null);
            }
        } else {
            refreshLayout.autoRefresh();
        }
    }

    @Override
    public void onFail() {
        RefreshLayoutUtil.finishRefreshLayout(mBinding.refreshLayout);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        page++;
        getMvpPresenter().getServerList(AccountManager.getInstance().getStylistId(), mFrom, page);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page = 1;
        getMvpPresenter().getServerList(AccountManager.getInstance().getStylistId(), mFrom, page);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBinding.refreshLayout.autoRefresh();
    }

    @Override
    public void onLeftListener(View view, int position) {
        if (mFrom == Constants.SERVICE_MANAGE_ORDER_CENTRE) {
            //下架
            isUpData = true;
            showDLDialog("下架", 0, mAdapter.getDatas().get(position));
        } else {
            //删除
            isUpData = false;
            showDLDialog("删除", 1, mAdapter.getDatas().get(position));
        }
    }

    @Override
    public void onRightListener(View view, int position) {
        ServerItemBean serverItemBean = mAdapter.getDatas().get(position);
        if (mFrom == Constants.SERVICE_MANAGE_ORDER_CENTRE) {
            Bundle bundle = new Bundle();
            bundle.putString("typeName", serverItemBean.getServiceName());
            bundle.putString("serviceId", serverItemBean.getServiceId());
            //修改
            switch (serverItemBean.getType()) {
                case "0":
                    //普通服务
                    if (serverItemBean.getIsoption().equals("0")) {//0无服务选项1有
                        ServiceSettingActivity.startActivity(getContext(), ServiceSettingActivity.class, bundle);
                    } else {
                        ServiceSettingTwoActivity.startActivity(getContext(), ServiceSettingTwoActivity.class, bundle);
                    }
                    break;
                case "1":
                    //单项套餐
                    SingleComboActivity.startActivity(getContext(), SingleComboActivity.class, bundle);
                    break;
                case "2":
                    //多项套餐
                    MultitermComboActivity.startActivity(getContext(), MultitermComboActivity.class, bundle);
                    break;
            }
        } else {
            //上架
            isUpData = true;
            showDLDialog("上架", 2, serverItemBean);
        }
    }

    public void upData() {
        //操作成功
        mBinding.refreshLayout.autoRefresh();
    }
}
