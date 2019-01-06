package com.yl.technician.module.home.personpay;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.KeyboardUtil;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityPersonpayBinding;
import com.yl.technician.module.home.personpay.qrcode.OrderCertificateActivity;
import com.yl.technician.module.home.personpay.selectstore.SelectStoreActivity;


/**
 * 当面付
 * Created by wqy on 2018/11/26.
 */
@CreatePresenter(PersonPayPresenter.class)
public class PersonPayActivity extends BaseMvpAppCompatActivity<PersonPayConfirmView, PersonPayPresenter> implements PersonPayConfirmView, ClickHandler {
    private Bundle mBundle;
    public static int FROMACTIVITY = 1001;
    private ActivityPersonpayBinding mActivityPersonpayBinding;
    private String mStoreId,mStoreName,mMoney;

    private String accessToken,appletUrl;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_personpay;
    }

    @Override
    protected void init() {
        initView();
        initData();

    }

    private void initData() {
        mBundle = getIntent().getExtras();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        mActivityPersonpayBinding = (ActivityPersonpayBinding) viewDataBinding;
        mActivityPersonpayBinding.setClick(this);
        mActivityPersonpayBinding.titleView.setLeftClickListener(view -> finish());
        mActivityPersonpayBinding.etPersonpayPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DLog.e("onTextChanged","------------"+s.toString().trim());
                //优惠券必须要达到的金额Integer.parseInt(fullPrice);
                if(s == null || s.length() == 0){
                    return;
                }
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        mActivityPersonpayBinding.etPersonpayPrice.setText(s);
                        mActivityPersonpayBinding.etPersonpayPrice.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    mActivityPersonpayBinding.etPersonpayPrice.setText(s);
                    mActivityPersonpayBinding.etPersonpayPrice.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        mActivityPersonpayBinding.etPersonpayPrice.setText(s.subSequence(0, 1));
                        mActivityPersonpayBinding.etPersonpayPrice.setSelection(1);
                        return;
                    }
                }

                if(s.toString().trim().substring(0,1).equals(".") ||
                        s.toString().trim().substring(s.toString().trim().length()-1,s.toString().trim().length()).equals(".")){
                    return;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_personpay_storename://选择门店
                Intent intent = new Intent();
                intent.setClass(this, SelectStoreActivity.class);
                startActivityForResult(intent, FROMACTIVITY);
                break;
            case R.id.btn_pay_ok://去生成二维码
                // 关闭键盘
                KeyboardUtil.closeSoftKeyboard(this);
                mMoney = mActivityPersonpayBinding.etPersonpayPrice.getText().toString().trim();
                String storeName = mActivityPersonpayBinding.tvStoreName.getText().toString().trim();

                if (storeName.equals("请选择门店")){
                    ToastUtils.shortToast("请选择门店");
                    return;
                }

                if (TextUtils.isEmpty(mStoreId) || mStoreId == null) {
                    ToastUtils.shortToast("请选择门店");
                    return;
                }
                if (TextUtils.isEmpty(mMoney) || mMoney == null) {
                    ToastUtils.shortToast("请输入金额");
                    return;
                }

                double moneyDouble = Double.parseDouble(mMoney);
                if (moneyDouble <= 0) {
                    ToastUtils.shortToast("输入金额不能为0");
                    return;
                }
                Intent intents = new Intent();
                Bundle bundles = new Bundle();
                bundles.putString("storeid", mStoreId);
                bundles.putString("money", mMoney);
                intents.setClass(this, OrderCertificateActivity.class);
                intents.putExtras(bundles);
                startActivity(intents);
                finish();
                break;
        }
    }



    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == FROMACTIVITY) {
            mStoreId = data.getExtras().getString("storeid");
            mStoreName = data.getExtras().getString("storename");
            mActivityPersonpayBinding.tvStoreName.setText(mStoreName);
        }
    }

}
