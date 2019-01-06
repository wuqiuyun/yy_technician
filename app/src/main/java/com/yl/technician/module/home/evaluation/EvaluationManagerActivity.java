package com.yl.technician.module.home.evaluation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.KeyboardUtil;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.YLApplication;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityEvaluationManagerBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.EvaluateBean;
import com.yl.technician.model.vo.bean.StoreScopeBean;
import com.yl.technician.model.vo.bean.StylistManageScopeBean;
import com.yl.technician.model.vo.bean.SylistCommentListBean;
import com.yl.technician.util.RefreshLayoutUtil;

import java.util.ArrayList;

/**
 * 评价管理
 * <p>
 * Created by lvlong on 2018/10/11.
 */
@CreatePresenter(EvaluationManagerPresenter.class)
public class EvaluationManagerActivity extends BaseMvpAppCompatActivity<EvaluationManagerView, EvaluationManagerPresenter>
        implements EvaluationManagerView, ClickHandler, OnLoadMoreListener, OnRefreshListener {

    ActivityEvaluationManagerBinding mBinding;
    private EvaluationManagerAdapter adapter;
    private ArrayList<SylistCommentListBean> stylistCommentListBeanList = new ArrayList<>();

    protected int pageIndx = 1; //第几页
    protected int pageSize = 10; // 每页数量
    private SmartRefreshLayout refreshLayout;

    private int type; //1&2查看美发师评论,3查看门店评论

    private String stylistId;
    private String storeId;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_evaluation_manager;
    }

    @Override
    protected void init() {

        mBinding = (ActivityEvaluationManagerBinding) viewDataBinding;
        mBinding.setClick(this);

        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            type = bundle.getInt(Constants.EVALUATION_TYPE);

            if (type == 1 || type == 2) {
                stylistId = bundle.getString(Constants.STYLIST_ID);
            }

            if (type == 3) {
                storeId = bundle.getString(Constants.STORE_ID);
            }
        } else {
            finish();
        }

        initView();
        loadData();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        mBinding.materialRatingBar.setEnabled(false);
        mBinding.titleView.setLeftClickListener(view -> finish());

        //设置适配器
        RecyclerView recyclerView = mBinding.recycleView;
        adapter = new EvaluationManagerAdapter(this , type);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        if (type != 3) {

            adapter.setOnSendViewClick((mssage, position) -> {
                if (adapter.getDatas().size()>0){
                    getMvpPresenter().replyStoreComment(mssage, adapter.getDatas().get(position).getId() + "",this);
                    KeyboardUtil.closeSoftKeyboard(EvaluationManagerActivity.this);
                }
            });

        }

        initRefreshLoadLayout();

    }

    private void loadData() {
        refreshLayout.autoRefresh();
    }

    private void initDate() {
        if (type != 3) {

            getMvpPresenter().getEvaluate(AccountManager.getInstance().getStylistId(),this);//获取评分
            getMvpPresenter().getStylistCommentList(stylistId, pageIndx, pageSize,this);

        } else {
            getMvpPresenter().getStoreScore(AccountManager.getInstance().getUserId(), storeId,this);
            getMvpPresenter().getStoreCommentList(storeId, pageIndx, pageSize,this);
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    protected void initRefreshLoadLayout() {
         refreshLayout = mBinding.refreshLayout;
        if (refreshLayout != null) {
            refreshLayout.setRefreshHeader(new ClassicsHeader(this));
            refreshLayout.setRefreshFooter(new ClassicsFooter(this));
            refreshLayout.setOnLoadMoreListener(this);
            refreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        pageIndx++;
        if (type != 3) {

            getMvpPresenter().getStylistCommentList(stylistId, pageIndx, pageSize,this);
        } else {
            getMvpPresenter().getStoreCommentList(storeId, pageIndx, pageSize,this);
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        pageIndx = 1;
        if (type != 3) {
            getMvpPresenter().getEvaluate(AccountManager.getInstance().getStylistId(),this);//获取评分
            getMvpPresenter().getStylistCommentList(stylistId, pageIndx, pageSize,this);

        } else {
            getMvpPresenter().getStoreScore(AccountManager.getInstance().getUserId(), storeId,this);
            getMvpPresenter().getStoreCommentList(storeId, pageIndx, pageSize,this);
        }
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void getEvaluateSucceed(EvaluateBean evaluateBean) {
        if (evaluateBean != null) {
            mBinding.titleView.setSubTitleText("(" + evaluateBean.getTimes() + ")");
            mBinding.materialRatingBar.setRating((float) evaluateBean.getComprehensive());
            mBinding.tvProfessionSkill.setText(String.format(getString(R.string.number), evaluateBean.getSkill()));
            mBinding.tvServiceAttitude.setText(String.format(getString(R.string.number), evaluateBean.getServer()));

            int pareEnvirtAvg = evaluateBean.getSkillavg();    //技能平均分比较
            if (pareEnvirtAvg == 1) {
                mBinding.tvRelativeRatio1.setText("高于平均水平");
            } else if (pareEnvirtAvg == 0) {
                mBinding.tvRelativeRatio1.setText("等于平均水平");
            } else if (pareEnvirtAvg == -1) {
                mBinding.tvRelativeRatio1.setText("低于平均水平");
            } else if (pareEnvirtAvg == 10) {
                mBinding.tvRelativeRatio1.setText("等于平均水平");
            }

            int pareServerAvg = evaluateBean.getServeravg();    //服务平均分比较
            if (pareServerAvg == 1) {
                mBinding.tvRelativeRatio2.setText("高于平均水平");
            } else if (pareServerAvg == 0) {
                mBinding.tvRelativeRatio2.setText("等于平均水平");
            } else if (pareServerAvg == -1) {
                mBinding.tvRelativeRatio2.setText("低于平均水平");
            } else if (pareServerAvg == 10) {
                mBinding.tvRelativeRatio2.setText("等于平均水平");
            }
        }

    }

    @Override
    public void getStyistCommentListSucceed(ArrayList<SylistCommentListBean> storeCommentListBean) {
        RefreshLayoutUtil.finishRefreshLayout(refreshLayout);

        if(pageIndx==1){//刷新
            adapter.setDatas(storeCommentListBean, true);
        }else {
            adapter.addDatas(storeCommentListBean, true);
        }
        if (storeCommentListBean.size() < pageSize) {// 加载的数据不够页面数量 则认为没有下一页
            refreshLayout.setNoMoreData(true);
        } else {
            refreshLayout.setNoMoreData(false);
        }
    }

    @Override
    public void getStylistCommentListFail() {
        RefreshLayoutUtil.finishRefreshLayout(refreshLayout);
    }

    @Override
    public void replyStoreCommentSucceed() {
        ToastUtils.shortToast("回复成功");
        pageIndx = 1;
        if (type != 3) {

            getMvpPresenter().getStylistCommentList(stylistId, pageIndx, pageSize,this);
        } else {
            getMvpPresenter().getStoreCommentList(storeId, pageIndx, pageSize,this);
        }
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onGetStoreScore(StoreScopeBean bean) {
        if (bean != null) {
            mBinding.titleView.setSubTitleText("(" + bean.getScoretimes() + ")");
            mBinding.materialRatingBar.setRating((float) bean.getScore());

            mBinding.tvText.setText("环境评分");
            mBinding.tvProfessionSkill.setText(String.format(getString(R.string.number), bean.getEnvironmentScore()));
            mBinding.tvServiceAttitude.setText(String.format(getString(R.string.number), bean.getServerScore()));

            int pareEnvirtAvg = bean.getPareEnvirtAvg();    //环境平均分比较
            if (pareEnvirtAvg == 1) {
                mBinding.tvRelativeRatio1.setText("高于平均水平");
            } else if (pareEnvirtAvg == 0) {
                mBinding.tvRelativeRatio1.setText("等于平均水平");
            } else if (pareEnvirtAvg == -1) {
                mBinding.tvRelativeRatio1.setText("低于平均水平");
            } else if (pareEnvirtAvg == 10) {
                mBinding.tvRelativeRatio1.setText("等于平均水平");
            }

            int pareServerAvg = bean.getPareServerAvg();    //服务平均分比较
            if (pareServerAvg == 1) {
                mBinding.tvRelativeRatio2.setText("高于平均水平");
            } else if (pareServerAvg == 0) {
                mBinding.tvRelativeRatio2.setText("等于平均水平");
            } else if (pareServerAvg == -1) {
                mBinding.tvRelativeRatio2.setText("低于平均水平");
            } else if (pareServerAvg == 10) {
                mBinding.tvRelativeRatio2.setText("等于平均水平");
            }
        }
    }


}
