package com.yl.technician.module.mine.settings.security.cashaccount;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseAppCompatActivity;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.recycleview.GridSpacingItemDecoration;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityCashaccountManageBinding;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.local.preferences.SharePreferencesUtils;
import com.yl.technician.model.vo.bean.AuthApplyBean;
import com.yl.technician.model.vo.bean.CashAliBean;
import com.yl.technician.model.vo.bean.ServerItemBean;
import com.yl.technician.module.home.coupons.CouponBean;
import com.yl.technician.module.home.coupons.CouponsActivity;
import com.yl.technician.module.mine.settings.security.cashaccount.addbankcard.AddBankCardActivity;
import com.yl.technician.module.mine.settings.security.cashaccount.unbankcard.UnBankCardActivity;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.widget.dialog.YLDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 提现账户管理
 * <p>
 * Create by lyj on 2018/11/8
 */
@CreatePresenter(CashAccountManagePresenter.class)
public class CashAccountManageActivity extends BaseMvpAppCompatActivity<CashAccountManageView,CashAccountManagePresenter>
        implements ClickHandler, CashAccountManageView {

    ActivityCashaccountManageBinding mBinding;
    private BaseQuickAdapter mAdapter;
    private List<CashAliBean> cashAliBeanList = new ArrayList<>();
    private String bindId = "";
    private int choosePosition;
    private boolean isWithdraw = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_cashaccount_manage;
    }

    @Override
    protected void init() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            isWithdraw = bundle.getBoolean("isWithdraw");
        }

        initView();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityCashaccountManageBinding) viewDataBinding;
        mBinding.recycleView.setVisibility(View.GONE);
        mBinding.ivNoDate.setVisibility(View.VISIBLE);
        mBinding.titleView.setLeftClickListener(v -> finish());
        mBinding.titleView.setRightImgClickListener(view -> {
            //添加银行卡等
            getMvpPresenter().getStylistAuth(this);
        });
        // init recycleview
        mBinding.recycleView.setLayoutManager(new LinearLayoutManager(this));
//        mBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(1, 30, false));
        mBinding.recycleView.setNestedScrollingEnabled(false);
        mAdapter = new CashAccountManageAdapter(cashAliBeanList);
//        mAdapter.setOnItemClick();
        mBinding.recycleView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.btn_delete) {//删除
                choosePosition = position;
                bindId = cashAliBeanList.get(position).getBindId()+"";
                showDLDialog();
            }else if (view.getId() == R.id.rl_bankcard){
                if (isWithdraw){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectAccount", cashAliBeanList.get(position));
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(Constants.BANK_BACK_CODE, intent);
                    finish();
                }
            }
        });

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
        cashAliBeanList = cashAliBean;
        if (cashAliBeanList!=null&&cashAliBeanList.size()>0){
            mBinding.recycleView.setVisibility(View.VISIBLE);
            mBinding.ivNoDate.setVisibility(View.GONE);
            mAdapter.setNewData(cashAliBeanList);
        }else {
            mBinding.recycleView.setVisibility(View.GONE);
            mBinding.ivNoDate.setVisibility(View.VISIBLE);
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUnBindSuccess() {
        ToastUtils.shortToast("解绑成功");
        mAdapter.remove(choosePosition);
        mAdapter.notifyItemRemoved(choosePosition);
    }

    @Override
    public void onSuccess(ArrayList<ServerItemBean> serverItemBeans) {
    }

    @Override
    public void onFail(int type) {

    }

    @Override
    public void getUserInfoSuccess(AuthApplyBean getApplyStatusBean) {
        if (getApplyStatusBean!=null){
            String realName = getApplyStatusBean.getRealName();
            if (realName!=null&&!TextUtils.isEmpty(realName)){
                SharePreferencesUtils.getSharePreferencesUtils().setRealName(realName);
            }
            AddBankCardActivity.startActivity(this , AddBankCardActivity.class);
        }else {
            ToastUtils.shortToast("实名认证未通过,无法添加");
        }

    }

    @Override
    public void getUserInfoFail() {
        ToastUtils.shortToast("获取实名认证状态失败");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMvpPresenter().extractBankAccount("BANK",this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * 解绑弹窗
     * */
    private void showDLDialog() {
        new YLDialog.Builder(this)
                .setType(YLDialog.DIALOG_TYPE_NORMAL)
                .setMessage("是否确定解绑?")
                .setPositiveButton("确定", (dialog, which) -> {
                    getMvpPresenter().unbindAccount(bindId,this);
                    dialog.dismiss();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }

}
