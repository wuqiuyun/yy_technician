package com.yl.technician.module.mine.wallet.address;

import android.view.View;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityAddressBinding;
import com.yl.technician.model.local.preferences.CommonSharedPreferences;
import com.yl.technician.module.mine.wallet.WalletActivity;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.widget.dialog.YLDialog;

/**
 * 外部钱包地址
 * <p>
 * Create by zm on 2018/10/8
 */
@CreatePresenter(AddressPresenter.class)
public class AddressActivity extends BaseMvpAppCompatActivity<IAddressView, AddressPresenter>
        implements IAddressView, ClickHandler{

    private ActivityAddressBinding mAddressBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_address;
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        mAddressBinding = (ActivityAddressBinding) viewDataBinding;
        mAddressBinding.setClick(this);
        // title
        mAddressBinding.titleView.setLeftClickListener(v -> {
            finish();
        });
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit: // 提交
                //getMvpPresenter().bindWalletURL(AccountManager.getInstance().getUserId(),mAddressBinding.etAddress.getText().toString().trim(),this);
                ToastUtils.shortToast("暂未开放,敬请期待!");
                break;
        }
    }
    private void showDLDialog() {
        new YLDialog.Builder(this)
                .setType(YLDialog.DIALOG_TYPE_NORMAL)
                .setMessage("绑定成功")
                .setSubMessage("现在可以上链"+ FormatUtil.Formatstring(CommonSharedPreferences.getInstance().getBasicDataBean().getSysCoinDefault())+"奖励了 ")
                .setPositiveButton("确定", (dialog, which) ->{
                    startActivity(AddressActivity.this, WalletActivity.class);
                    dialog.dismiss();
                    finish();
                })
                .create()
                .show();
    }

    @Override
    public void onSuccess() {
        showDLDialog();
    }
}
