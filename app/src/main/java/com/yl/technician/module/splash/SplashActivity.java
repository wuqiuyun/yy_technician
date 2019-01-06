package com.yl.technician.module.splash;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.R;
import com.yl.technician.api.BasicSettingApi;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.local.preferences.CommonSharedPreferences;
import com.yl.technician.model.local.preferences.SystemConfigSharedPreferences;
import com.yl.technician.model.vo.result.BsicDataResult;
import com.yl.technician.module.common.GuideActivity;
import com.yl.technician.module.login.LoginActivity;
import com.yl.technician.module.main.MainActivity;
import com.yl.technician.util.ActivityUtil;

/**
 * 闪屏页
 * <p>
 * Created by zm on 2018/9/11
 */
@CreatePresenter(SplashPresenter.class)
public class SplashActivity extends BaseMvpAppCompatActivity<ISplashView, SplashPresenter> implements ISplashView {

    private String orderId;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        // 获取基础信息
        getBasicData();
    }

    protected void jumpMain() {
        new Handler().postDelayed(() -> {
            if (ActivityUtil.isActivityFinished(this)) {
                return;
            }
            new RxPermissions(this)
                    .request(Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                    .subscribe(granted -> {
                        if (granted) {
                            autoLogin();
                        } else {
                            finish();
                            ToastUtils.shortToast(getString(R.string.permissions_phone_state));
                        }
                    });
        }, 2000);
    }

    @Override
    protected void setStatusBar() {
//       StatusBarUtil.setTranslucent(this, 0);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    /**
     * 检测是否已登录
     */
    private void autoLogin() {
        if (SystemConfigSharedPreferences.getInstance().isShowGuide()) {
            GuideActivity.startActivity(SplashActivity.this, GuideActivity.class);
            finish();
            return;
        }
        if (!AccountManager.getInstance().isLogin()) {
            LoginActivity.startActivity(this, LoginActivity.class);
            finish();
            return;
        }

        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        //如果启动app的Intent中带有额外的参数，表明app是从点击通知栏的动作中启动的
        //将参数取出，传递到MainActivity中
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderId = bundle.getString(Constants.NOTIFICATION_DATA);
            if (orderId!=null&&!TextUtils.isEmpty(orderId)){
                intent.putExtra(Constants.NOTIFICATION_DATA, getIntent().getExtras().getString(Constants.NOTIFICATION_DATA) );
            }
        }
        startActivity(intent);
        finish();
    }

    /**
     * 获取基础信息
     */
    private void getBasicData() {
        new BasicSettingApi().getSysData(new YLRxSubscriberHelper<BsicDataResult>() {
            @Override
            public void _onNext(BsicDataResult bsicDataResult) {
                CommonSharedPreferences.getInstance().saveBasicData(bsicDataResult.getData());
                if (CommonSharedPreferences.getInstance().getBasicDataBean() != null) {
                    jumpMain();
                }
            }

            @Override
            public void _onError(ApiException error) {
                if (CommonSharedPreferences.getInstance().getBasicDataBean() != null) {
                    jumpMain();
                }
            }
        });
    }
}
