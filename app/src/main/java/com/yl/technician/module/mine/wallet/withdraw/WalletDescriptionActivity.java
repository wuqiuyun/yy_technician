package com.yl.technician.module.mine.wallet.withdraw;

import com.yl.technician.R;
import com.yl.technician.base.BaseAppCompatActivity;
import com.yl.technician.databinding.ActivityWalletDescriptionBinding;

/**
 *
 * 钱包说明
 *
 * Created by lvlong on 2018/10/13.
 */
public class WalletDescriptionActivity extends BaseAppCompatActivity {

    ActivityWalletDescriptionBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_wallet_description;
    }

    @Override
    protected void init() {

        mBinding = (ActivityWalletDescriptionBinding) viewDataBinding;
        mBinding.titleView.setLeftClickListener(view -> finish());

    }
}
