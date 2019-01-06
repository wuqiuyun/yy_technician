package com.yl.technician.module.mine.wallet.recharge.pay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.pay.PayHelper;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityPayBinding;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.util.ColorUtil;
import com.yl.technician.widget.dialog.YLDialog;

/**
 * 支付
 * <p>
 * Create by zm on 2018/10/9.
 */
@CreatePresenter(PayPresenter.class)
public class PayActivity extends BaseMvpAppCompatActivity<IPayView, PayPresenter>
        implements IPayView, ClickHandler {
    private static final int PAY_WECHAT = 0;
    private static final int ALIPAY = 1;

    ActivityPayBinding payBinding;

    private int payType = PAY_WECHAT; // 0微信支付 1支付宝

    private double mMoney = 0.00;//充值金额
    private String goWebUrl;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pay;
    }

    @Override
    protected void init() {

        initView();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        payBinding = (ActivityPayBinding) viewDataBinding;
        payBinding.setClick(this);
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            mMoney = bundle.getDouble(Constants.RECHARGE_MONEY);
            payBinding.tvRechargeBalance.setText("￥"+mMoney);
        }

        // back
        payBinding.titleView.setLeftClickListener(v -> {
            finish();
        });
        // 默认选择微信支付
        paySelect(PAY_WECHAT);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(this, ColorUtil.getColor(R.color.white));
    }

    private void paySelect(int payType) {
        this.payType = payType;
        payBinding.wechatpay.setRightIcon(ContextCompat.getDrawable(this,
                payType == PAY_WECHAT ? R.drawable.icon_selected : R.drawable.icon_normal));
        payBinding.alipay.setRightIcon(ContextCompat.getDrawable(this,
                payType == ALIPAY ? R.drawable.icon_selected : R.drawable.icon_normal));
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit: // 提交
                if (payType==0){//微信支付
                    getMvpPresenter().wxRechargeCash("1",mMoney);
                }else {
//                    getMvpPresenter().aLiRechargeCash(mMoney);
                    getMvpPresenter().aLiRechargeCashPost("http://w1.zhexi.tech:60825/app/alipay/prepay");
                }
                break;
            case R.id.wechatpay: // 微信支付
                paySelect(PAY_WECHAT);
                break;
            case R.id.alipay: // 支付宝
                paySelect(ALIPAY);
                break;
        }
    }

    @Override
    public void onWxRechargeCashSuccess() {
        PayHelper.wxpay(this,"11111");
    }

    @Override
    public void onALiRechargeCashSuccess(String json) {
        Log.e("onALiRechargeCas","--------"+json);
        goWebUrl = json;
        if (!TextUtils.isEmpty(goWebUrl)){
            showGoPayDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ToastUtils.shortToast("支付宝充值成功");
           finish();
        }else {
            ToastUtils.shortToast("支付已取消");
        }
    }

    private void showGoPayDialog() {
        new YLDialog.Builder(this)
                .setType(YLDialog.DIALOG_TYPE_NORMAL)
                .setMessage("即将前往浏览器支付")
                .setPositiveButton("确定", (dialog, which) -> {
                    Uri uri = Uri.parse(goWebUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    dialog.dismiss();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }

}
