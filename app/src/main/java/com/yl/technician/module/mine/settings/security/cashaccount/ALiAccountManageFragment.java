package com.yl.technician.module.mine.settings.security.cashaccount;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.technician.R;
import com.yl.technician.base.fragment.BaseMvpFragment;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.FragmentCashaccountZfbBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.local.preferences.SharePreferencesUtils;
import com.yl.technician.model.vo.bean.AuthApplyBean;
import com.yl.technician.model.vo.bean.CashAliBean;
import com.yl.technician.model.vo.bean.ServerItemBean;
import com.yl.technician.module.mine.settings.security.phone.PhoneVerifyActivity;
import com.yl.technician.util.FormatUtil;

import java.util.ArrayList;

/**
 * 支付宝提现账户
 * Created by lyj on 2018/11/8
 */
@CreatePresenter(CashAccountManagePresenter.class)
public class ALiAccountManageFragment extends BaseMvpFragment<CashAccountManageView,CashAccountManagePresenter>
        implements ClickHandler, CashAccountManageView {

    private FragmentCashaccountZfbBinding mBinding;
    private int mFrom;
    private int bindId;
    private boolean isBind = true;//true 绑定 false解绑

    public static Fragment newInstance(int from) {
        ALiAccountManageFragment cashAccountManageFragment = new ALiAccountManageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("from",from);
        cashAccountManageFragment.setArguments(bundle);
        return cashAccountManageFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cashaccount_zfb;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        mFrom = bundle.getInt("from", 0);
        mBinding = (FragmentCashaccountZfbBinding) viewDataBinding;
        mBinding.setClick(this);
        String realName = SharePreferencesUtils.getSharePreferencesUtils().getUserRealName();
        if (realName!=null&&!TextUtils.isEmpty(realName)){
            mBinding.etZfbName.setText(realName);
            mBinding.etZfbName.setEnabled(false);
        }
    }

    @Override
    protected void loadData() {

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.bt_zfb_push ://提交/解除绑定
                if (isBind){
                    getMvpPresenter().bindAccount(mBinding.etZfbId.getText().toString().trim(),
                            "ALI",
                            null,
                            mBinding.etZfbName.getText().toString().trim(),
                            "ALI",
                            AccountManager.getInstance().getStylistId()
                            ,getActivity());
                }else {
                    getMvpPresenter().unbindAccount(String.valueOf(bindId),getActivity());
                }
                break;
        }
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onextractaLIAccountSuccess(ArrayList<CashAliBean> cashAliBean) {
        if (cashAliBean!=null&&cashAliBean.size()>0){
            isBind = false;
            String rename = cashAliBean.get(0).getRealName();
            String accountNo = FormatUtil.Formatstring(cashAliBean.get(0).getAccountno());
            mBinding.etZfbName.setText(FormatUtil.Formatstring(rename));
            mBinding.etZfbName.setEnabled(false);
            bindId = cashAliBean.get(0).getBindId();
            mBinding.etZfbId.setEnabled(false);
            mBinding.btZfbPush.setText("解除绑定");
            mBinding.etZfbId.setText(accountNo);

        }else {
            isBind = true;
            mBinding.etZfbId.setEnabled(true);
            mBinding.btZfbPush.setText("提交");
            mBinding.etZfbId.setText("");
            String realName = SharePreferencesUtils.getSharePreferencesUtils().getUserRealName();
            if (realName!=null&&!TextUtils.isEmpty(realName)){
                mBinding.etZfbName.setText(realName);
                mBinding.etZfbName.setEnabled(false);
            }else {
                mBinding.etZfbName.setEnabled(true);
                mBinding.etZfbName.setText("");
            }
        }
    }

    @Override
    public void onextractBankAccountSuccess(ArrayList<CashAliBean> cashAliBean) {

    }

    @Override
    public void onUnBindSuccess() {
        getMvpPresenter().extractAccount("ALI",getActivity());
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
        getMvpPresenter().extractAccount("ALI",getActivity());
    }

}
