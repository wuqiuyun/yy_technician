package com.yl.technician.module.home.in.store;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.recycleview.SpaceItemHorizontalDecoration;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityStoreBinding;
import com.yl.technician.model.vo.bean.StylistBean;
import com.yl.technician.module.home.evaluation.EvaluationManagerActivity;
import com.yl.technician.module.home.join.StoreStylistActivity;

import java.util.ArrayList;

/**
 * 门店
 * <p>
 * Create by zm on 2018/10/12
 */
@CreatePresenter(StorePresenter.class)
public class StoreActivity extends BaseMvpAppCompatActivity<IStoreView, StorePresenter>
        implements IStoreView, ClickHandler{

    ActivityStoreBinding mBinding;

    private StylistAdapter mStylistAdapter;
    private ArrayList<StylistBean> mStylistBeans = new ArrayList<>();

    private ServiceScopeAdapter mServiceAdapter;
    private ArrayList<String> mScopeList = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_store;
    }

    @Override
    protected void init() {

        initView();
        loadData();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityStoreBinding) viewDataBinding;
        mBinding.setClick(this);
        // titleview
        mBinding.titleView.setLeftClickListener(v -> finish());
        mBinding.titleView.setRightImgClickListener(v -> {
            // 分享
        });
        mBinding.titleView.setSubRightImgClickListener(v -> {
            // 收藏
        });
        // stylist list
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 横向滚动
        mBinding.stylistList.setLayoutManager(linearLayoutManager);
        mBinding.stylistList.addItemDecoration(new SpaceItemHorizontalDecoration(20));
        mStylistAdapter = new StylistAdapter(getBaseContext());
        mBinding.stylistList.setAdapter(mStylistAdapter);
        // service scope list
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getBaseContext());
        LayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 横向滚动
        mBinding.serviceScopeList.setLayoutManager(LayoutManager);
        mBinding.serviceScopeList.addItemDecoration(new SpaceItemHorizontalDecoration(30));
        mServiceAdapter = new ServiceScopeAdapter(getBaseContext());
        mBinding.serviceScopeList.setAdapter(mServiceAdapter);
    }

    private void loadData() {
        for (int i = 0; i < 10; i ++) {
            mStylistBeans.add(new StylistBean());
            mScopeList.add("");
        }
        mStylistAdapter.setDatas(mStylistBeans, true);
        mServiceAdapter.setDatas(mScopeList, true);
    }

    @Override
    public void showToast(String message) {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_look_more: // 查看更多
                StoreStylistActivity.startActivity(getBaseContext(), StoreStylistActivity.class);
                break;

            case R.id.tv_look_comment: // 查看全部评价
                EvaluationManagerActivity.startActivity(getBaseContext(), EvaluationManagerActivity.class);
                break;

            case R.id.btn_tell_phone: //电话
                ToastUtils.shortToast("打电话");
                break;

            case R.id.btn_send_msg:     //发消息
                ToastUtils.shortToast("发消息");
                break;

            case R.id.btn_comment_invite:   //解约
                ToastUtils.shortToast("解约");
                break;
        }
    }
}
