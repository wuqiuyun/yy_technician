package com.yl.technician.module.mine.stylist.details;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.recycleview.GridSpacingItemDecoration;
import com.yl.technician.component.recycleview.SpaceItemDecoration;
import com.yl.technician.databinding.ActivityStylistDetailsBinding;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.ServiceBundleBean;
import com.yl.technician.model.vo.bean.ServiceProjectBean;
import com.yl.technician.model.vo.bean.StoreBean;
import com.yl.technician.module.home.evaluation.EvaluationManagerActivity;
import com.yl.technician.module.mine.works.many.ManyWorksActivity;

import java.util.ArrayList;

/**
 * 美发师详情
 * <p>
 */
@CreatePresenter(StylistDetailsPresenter.class)
public class StylistDetailsActivity extends BaseMvpAppCompatActivity<IStylistDetailsView, StylistDetailsPresenter>
        implements IStylistDetailsView, ClickHandler{

    ActivityStylistDetailsBinding binding;

    private ServiceProjectAdapter projectAdapter;
    private ArrayList<ServiceProjectBean> projectBeans = new ArrayList<>();

    private ServiceBundleAdapter bundleAdapter;
    private ArrayList<ServiceBundleBean> bundleBeans = new ArrayList<>();

    private StoreAdapter storeAdapter;
    private ArrayList<StoreBean> storeBeans = new ArrayList<>();

    private WorksAdapter worksAdapter;
    private ArrayList<String> worksBeans = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_stylist_details;
    }

    @Override
    protected void init() {

        initView();
        loadData();
    }

    private void initView() {
        binding = (ActivityStylistDetailsBinding) viewDataBinding;
        binding.setClick(this);

        // titleview
        binding.ivBack.setOnClickListener(v -> finish());

        // service project
        binding.projectList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.projectList.addItemDecoration(new SpaceItemDecoration(20));
        projectAdapter = new ServiceProjectAdapter(getBaseContext());
        binding.projectList.setHasFixedSize(true);
        binding.projectList.setNestedScrollingEnabled(false);
        binding.projectList.setAdapter(projectAdapter);

        // service bundle
        binding.listService.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.listService.addItemDecoration(new SpaceItemDecoration(30));
        bundleAdapter = new ServiceBundleAdapter(getBaseContext());
        binding.listService.setHasFixedSize(true);
        binding.listService.setNestedScrollingEnabled(false);
        binding.listService.setAdapter(bundleAdapter);

        // store
        binding.storeList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.storeList.addItemDecoration(new SpaceItemDecoration(20));
        binding.storeList.setHasFixedSize(true);
        binding.storeList.setNestedScrollingEnabled(false);
        storeAdapter = new StoreAdapter(getBaseContext());
        binding.storeList.setAdapter(storeAdapter);

        // works
        binding.worksList.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
        binding.worksList.addItemDecoration(new GridSpacingItemDecoration(3, 20, false));
        binding.worksList.setHasFixedSize(true);
        binding.worksList.setNestedScrollingEnabled(false);
        worksAdapter = new WorksAdapter(getBaseContext());
        binding.worksList.setAdapter(worksAdapter);
    }

    private void loadData() {
        for (int i = 0; i < 6; i ++) {
            projectBeans.add(new ServiceProjectBean());
            bundleBeans.add(new ServiceBundleBean());
            storeBeans.add(new StoreBean());
            worksBeans.add("imageUrl");
        }
        projectAdapter.setDatas(projectBeans, true);
        bundleAdapter.setDatas(bundleBeans, true);
        storeAdapter.setDatas(storeBeans, true);
        worksAdapter.setDatas(worksBeans, true);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0,null);
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_look_more: // 查看更多作品
                ManyWorksActivity.startActivity(getBaseContext(), ManyWorksActivity.class);
                break;
            case R.id.tv_look_comment: // 查看更多评论
                Bundle EvaluationBundle = new Bundle();
                EvaluationBundle.putInt(Constants.EVALUATION_TYPE, 2);
                EvaluationManagerActivity.startActivity(getBaseContext(), EvaluationManagerActivity.class ,EvaluationBundle);
                break;
            case R.id.btn_tell_phone: // 电话
                break;
            case R.id.btn_send_msg: // 咨询
                break;
            case R.id.btn_comment_invite: // 解约
                break;
        }
    }
    
}
