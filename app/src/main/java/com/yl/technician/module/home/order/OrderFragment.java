package com.yl.technician.module.home.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.base.fragment.BasePageMvpFragment;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.FragmentOrderBinding;
import com.yl.technician.model.constant.OrderStatus;
import com.yl.technician.model.vo.bean.EventBean;
import com.yl.technician.model.vo.bean.OrderBean;
import com.yl.technician.module.home.order.details.OrderDetailsActivity;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.util.RefreshLayoutUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Objects;

import static com.yl.technician.model.constant.Constants.EXTRA_ORDER_TYPE;

/**
 * 我的订单
 * <p>
 * Created by zm on 2018/9/20.
 */
@CreatePresenter(OrderPresenter.class)
public class OrderFragment extends BasePageMvpFragment<IOrderView, OrderPresenter, OrderBean> implements IOrderView{
    @OrderStatus.OrderType
    private int orderType;
    private OrderAdapter orderAdapter;

    FragmentOrderBinding orderBinding;
    /**
     *
     * @param orderType 订单类型
     * @return
     */
    public static Fragment newInstance(@OrderStatus.OrderType int orderType) {
        Fragment orderFragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_ORDER_TYPE, orderType);
        orderFragment.setArguments(bundle);
        return orderFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        pageIndx = 1;
        getMvpPresenter().getOrderList(orderType+1, pageIndx, pageSize);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initView() {
        hasExtras();
        // 初始化加载刷新控件
        initRefreshLoadLayout();
        initRecycleView();
    }

    private void hasExtras() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            orderType = bundle.getInt(EXTRA_ORDER_TYPE);
        }
    }

    private void initRecycleView() {
        orderBinding = (FragmentOrderBinding) viewDataBinding;
        orderBinding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        // 添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.shape_divider_line)));
        orderBinding.recycleView.addItemDecoration(divider);
        orderAdapter = new OrderAdapter(getContext(), orderType);
        orderAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener(){
            @Override
            public void onItemClick(View view, int position) {
                OrderDetailsActivity.startActivity(getContext(), orderAdapter.getDatas().get(position).getId());
            }
        });
        orderBinding.recycleView.setAdapter(orderAdapter);
    }

    @Override
    protected void loadData() {
//        getMvpPresenter().getOrderList(orderType+1, pageIndx, pageSize);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        pageIndx ++;
        getMvpPresenter().getOrderList(orderType+1, pageIndx, pageSize);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        pageIndx = 1;
        getMvpPresenter().getOrderList(orderType+1, pageIndx, pageSize);
    }

    /**
     * 订单新消息通知
     * @param message
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBean.NewMessage message) {
        getMvpPresenter().getOrderList(orderType+1, 1,
                orderAdapter.getDatas().size() == 0 ? pageSize : orderAdapter.getDatas().size());
    }

    @Override
    public void onGetOrderListSuccess(ArrayList<OrderBean> orderBeans) {
        RefreshLayoutUtil.finishRefreshLayout(orderBinding.refreshLayout);
        if (pageIndx == 1) {
            orderAdapter.setDatas(orderBeans, true);
        }else {
            orderAdapter.addDatas(orderBeans, true);
        }
        // 是否有更多的数据
        orderBinding.refreshLayout.setNoMoreData(orderBeans.size() < pageSize);

        if (orderBeans.size() == 0 && pageIndx == 1){
            orderBinding.ivNoDate.setVisibility(View.VISIBLE);
        }else {
            orderBinding.ivNoDate.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGetOrderListFail() {
        RefreshLayoutUtil.finishRefreshLayout(orderBinding.refreshLayout);
    }
}
