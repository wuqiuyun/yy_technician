package com.yl.technician.module.mine.settings.security.cashaccount;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.base.fragment.BaseMvpFragment;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.recycleview.GridSpacingItemDecoration;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.FragmentCashaccountBankBinding;
import com.yl.technician.databinding.FragmentCashaccountZfbBinding;
import com.yl.technician.model.vo.bean.AuthApplyBean;
import com.yl.technician.model.vo.bean.CashAliBean;
import com.yl.technician.model.vo.bean.ServerItemBean;
import com.yl.technician.module.home.service.ServiceManageAdapter;
import com.yl.technician.module.mine.settings.security.cashaccount.addbankcard.AddBankCardActivity;
import com.yl.technician.module.mine.settings.security.cashaccount.unbankcard.UnBankCardActivity;
import com.yl.technician.module.mine.settings.security.phone.PhoneVerifyActivity;
import com.yl.technician.util.FormatUtil;

import java.util.ArrayList;

/**
 * 银行卡提现账户
 * Created by lyj on 2018/11/8
 */
@CreatePresenter(CashAccountManagePresenter.class)
public class BankAccountManageFragment extends BaseMvpFragment<CashAccountManageView,CashAccountManagePresenter>
        implements ClickHandler, CashAccountManageView {

    private FragmentCashaccountBankBinding mBinding;
    private int mFrom;
    private CashAccountManageAdapter mAdapter;


    public static Fragment newInstance(int from) {
        BankAccountManageFragment bankAccountManageFragment = new BankAccountManageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("from",from);
        bankAccountManageFragment.setArguments(bundle);
        return bankAccountManageFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cashaccount_bank;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        mFrom = bundle.getInt("from", 0);
        mBinding = (FragmentCashaccountBankBinding) viewDataBinding;
        mBinding.setClick(this);
        // init recycleview
        mBinding.recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(1, 30, false));
        mBinding.recycleView.setNestedScrollingEnabled(false);
//        mAdapter = new CashAccountManageAdapter(getContext());
//        mAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener(){
//            @Override
//            public void onItemClick(View view, int position) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("CashAliBean",mAdapter.getDatas().get(position));
//                UnBankCardActivity.startActivity(getContext(), UnBankCardActivity.class, bundle);
//            }
//        });
//        mBinding.recycleView.setAdapter(mAdapter);

    }

    @Override
    protected void loadData() {

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.ll_bankcard_add ://添加银行卡
            case R.id.bt_bankcard_add ://添加银行卡
                AddBankCardActivity.startActivity(getContext() , AddBankCardActivity.class);
                break;
        }
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onextractaLIAccountSuccess(ArrayList<CashAliBean> cashAliBean) {

    }

    @Override
    public void onextractBankAccountSuccess(ArrayList<CashAliBean> cashAliBean) {
//        if (cashAliBean!=null&&cashAliBean.size()>0){
//            mAdapter.setDatas(cashAliBean,true);
//            mAdapter.notifyDataSetChanged();
//        }else {
//            mAdapter.getDatas().clear();
//            mAdapter.notifyDataSetChanged();
//        }

    }

    @Override
    public void onUnBindSuccess() {

    }

    @Override
    public void onSuccess(ArrayList<ServerItemBean> serverItemBeans) {
    }

    @Override
    public void onFail(int type) {

    }

    @Override
    public void getUserInfoSuccess(AuthApplyBean getApplyStatusBean) {

    }

    @Override
    public void getUserInfoFail() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getMvpPresenter().extractBankAccount("BANK",getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
