package com.yl.technician.module.mine.stylist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.base.fragment.BaseMvpFragment;
import com.yl.technician.component.recycleview.GridSpacingItemDecoration;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.FragmentStylistBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.GetStylistBean;
import com.yl.technician.module.home.card.MyCardActivity;
import com.yl.technician.module.home.join.SearchStylistActivity;
import com.yl.technician.util.RefreshLayoutUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 2018/10/10.
 */
@CreatePresenter(StylistPresenter.class)
public class StylistFragment extends BaseMvpFragment<IStylistView,StylistPresenter>
        implements IStylistView,OnRefreshListener,OnLoadMoreListener,IUpDataFragment {
    private String mUserId;
    private int page = 1;//页数
    private int size = 10;//每页数量
    private StylistMineAdapter mineAdapter;
    private StylistAdapter  adapter;
    private SmartRefreshLayout refreshLayout;
    
    private int fromActivity;//从哪个页面来的
    private FragmentStylistBinding mStylistBinding;
    private String stylistId = "";//美发师ID
    private String searchContent;
    private String mStoreId;
    private InputMethodManager mImm;
    private Intent mStartIntent;
    private Bundle mStartBundle;

    public static Fragment newInstance(int from) {
        StylistFragment stylistFragment = new StylistFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("from",from);
        stylistFragment.setArguments(bundle);
        return stylistFragment;
    }
    public static Fragment newInstance(int from,String storeId) {
        StylistFragment stylistFragment = new StylistFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("from",from);
        bundle.putString(Constants.STORE_ID,storeId);
        stylistFragment.setArguments(bundle);
        return stylistFragment;
    }
    protected void initRefreshLoadLayout() {
        refreshLayout = mStylistBinding.refreshLayout;
        if (refreshLayout != null) {
            refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
            refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
            refreshLayout.setOnLoadMoreListener(this);
            refreshLayout.setOnRefreshListener(this);
        }
    }

    private void initStylistMineAdapter() {
        mineAdapter = new StylistMineAdapter(getContext());
        mineAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener(){
            @Override
            public void onItemClick(View view, int position) {
                //把美发师ID传入下个页面
                stylistId = mineAdapter.getDatas().get(position).getStylistId()+"";
                mStartBundle.putString(Constants.STYLIST_ID,stylistId);
                mStartIntent.setClass(getContext(),MyCardActivity.class);
                mStartIntent.putExtras(mStartBundle);
                startActivityForResult(mStartIntent,Constants.RESULT_REFRESH_CODE);
            }
        });
        mStylistBinding.recycleView.setAdapter(mineAdapter);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_stylist;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        fromActivity = bundle.getInt("from", 0);
        mStoreId = bundle.getString(Constants.STORE_ID);
        mStylistBinding = (FragmentStylistBinding) viewDataBinding;
        // init recycleview
        mStylistBinding.recycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mStylistBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        initRefreshLoadLayout();
    }

    @Override
    protected void loadData() {
        mUserId = AccountManager.getInstance().getUserId();
        mStartIntent = new Intent();
        mStartBundle = new Bundle();
        switch (fromActivity){
            case Constants.ACTIVITY_STORE_SEARCH:
                SearchStylistActivity activity = (SearchStylistActivity) getActivity();
                activity.setIUpDataFragment(this);
                mImm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                initStylistAdapter();
                break;
            case Constants.ACTIVITY_COLLECT:
                initStylistMineAdapter();
                refreshLayout.autoRefresh();
                break;
            default:
                initStylistAdapter();
                refreshLayout.autoRefresh();
                break;
        }
    }

    private void initStylistAdapter() {
        adapter = new StylistAdapter(getContext());
        adapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener(){
            @Override
            public void onItemClick(View view, int position) {
                //把美发师ID传入下个页面
                stylistId = adapter.getDatas().get(position).getStylistId()+"";
                mStartBundle.putString(Constants.STYLIST_ID,stylistId);
                mStartIntent.setClass(getContext(),MyCardActivity.class);
                mStartIntent.putExtras(mStartBundle);
                startActivityForResult(mStartIntent,Constants.RESULT_REFRESH_CODE);
            }
        });
        mStylistBinding.recycleView.setAdapter(adapter);
    }

    @Override
    public void showToast(String message)
    {
        ToastUtils.shortToast(message);
    }


    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page=1;
        switch (fromActivity) {
            //平台美发师/自有美发师
            case Constants.ACTIVITY_JOIN_STYLIST_1 :
            case Constants.ACTIVITY_JOIN_STYLIST_2 :
                getMvpPresenter().getMyStylist(fromActivity-1,mStoreId);
                break;
            case Constants.ACTIVITY_COLLECT :
                getMvpPresenter().getStylistCollection(page, size, mUserId);
                break;
            case Constants.ACTIVITY_FOOTPRINT :
                getMvpPresenter().getStylistFoot(page, size, mUserId);
                break;
            case Constants.ACTIVITY_STORE_SEARCH :
                getMvpPresenter().storeStylistSearch(searchContent,mStoreId,mUserId);
                break;
            default:
                showToast("来源页获取错误");
                break;
        }
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        page++;
        switch (fromActivity) {
            //我的美发师
            case Constants.ACTIVITY_JOIN_STYLIST_1 :
            case Constants.ACTIVITY_JOIN_STYLIST_2 :
                getMvpPresenter().getMyStylist(fromActivity-1,mUserId);
                break;
            case Constants.ACTIVITY_COLLECT :
                getMvpPresenter().getStylistCollection(page, size, mUserId);
                break;
            case Constants.ACTIVITY_FOOTPRINT :
                getMvpPresenter().getStylistFoot(page, size, mUserId);
                break;
            default:
                showToast("来源页获取错误");
                break;
        }
    }

    @Override
    public void getStylistSuccess(List<GetStylistBean> list) {
        RefreshLayoutUtil.finishRefreshLayout(mStylistBinding.refreshLayout);
        ArrayList<GetStylistBean> newData = (ArrayList<GetStylistBean>) list;

        switch (fromActivity){
            case Constants.ACTIVITY_COLLECT:
                if (refreshLayout == null || refreshLayout.getState()== RefreshState.Refreshing) { // 刷新
                    mineAdapter.setDatas(newData, true);
                }else if (refreshLayout.getState() == RefreshState.Loading){ // 加载
                    mineAdapter.addDatas( newData, true);
                }
                break;
            default:
                if (refreshLayout == null || refreshLayout.getState()== RefreshState.Refreshing) { // 刷新
                    adapter.setDatas(newData, true);
                }else if (refreshLayout.getState() == RefreshState.Loading){ // 加载
                    adapter.addDatas( newData, true);
                }
                break;
        }


        if (list.size() < size||fromActivity==Constants.ACTIVITY_STORE_SEARCH ) {// 加载的数据不够页面数量 则认为没有下一页
            refreshLayout.setNoMoreData(true);
        } else {
            refreshLayout.setNoMoreData(false);
        }

        //搜索隐藏键盘
        if(fromActivity==Constants.ACTIVITY_STORE_SEARCH&&newData.size()!=0){
            mImm.hideSoftInputFromWindow(mStylistBinding.refreshLayout.getWindowToken(), 0);
        }
    }

    @Override
    public void getStylistFail() {
        RefreshLayoutUtil.finishRefreshLayout(mStylistBinding.refreshLayout);
        refreshLayout.setNoMoreData(true);
    }

    @Override
    public void onUpData(int filterType, Object o) {
        if (filterType==Constants.ACTIVITY_FILTER_STYLIST_4){
            //邀请美发师-搜索
             searchContent =(String)o;
        }
        refreshLayout.autoRefresh();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Constants.RESULT_REFRESH_CODE&&resultCode==Constants.RESULT_REFRESH_CODE){
            refreshLayout.autoRefresh();
        }
    }
}
