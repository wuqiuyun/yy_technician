package com.yl.technician.module.mine.settings.security.cashaccount.unbankcard;

import android.os.Bundle;
import android.view.View;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityUnbindbankcardBinding;
import com.yl.technician.model.vo.bean.CashAliBean;
import com.yl.technician.module.mine.settings.ISettingsView;
import com.yl.technician.util.BankImgUtil;

/**
 * 解除绑定银行卡
 * <p>
 * Create by lyj on 2018/11/8
 */
@CreatePresenter(UnBankCardPresenter.class)
public class UnBankCardActivity extends BaseMvpAppCompatActivity<UnBankCardView, UnBankCardPresenter>
        implements ClickHandler,UnBankCardView {

    ActivityUnbindbankcardBinding mBinding;
    private String bindId = "";
    private CashAliBean cashBankList = new CashAliBean();
    private String imgNa;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_unbindbankcard;
    }

    @Override
    protected void init() {

        initView();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityUnbindbankcardBinding) viewDataBinding;
        mBinding.titleView.setLeftClickListener(v -> finish());
        mBinding.setClick(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            cashBankList = (CashAliBean) bundle.getSerializable("CashAliBean");
            mBinding.tvBankName.setText(cashBankList.getTypeName());
            mBinding.tvBankCardid.setText(cashBankList.getAccountno());
            bindId = String.valueOf(cashBankList.getBindId());
            imgNa = cashBankList.getShortName();
        }
        mBinding.ivBankimg.setImageResource(BankImgUtil.getBankImgTwo(imgNa));
        mBinding.rlBankcard.setBackgroundResource(BankImgUtil.getBankImgBag(imgNa));
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_unbankcard_push ://解除绑定
                getMvpPresenter().unbindAccount(bindId,this);
                break;
        }
    }


    @Override
    public void onUnBankSuccess() {
        finish();
    }
    
}
