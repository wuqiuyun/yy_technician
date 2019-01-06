package com.yl.technician.module.mine.settings.about;

import android.graphics.Paint;
import android.view.View;

import com.yl.core.component.appupdate.config.UpdateConfiguration;
import com.yl.core.component.appupdate.manager.DownloadManager;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.BuildConfig;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityAboutBinding;
import com.yl.technician.model.vo.bean.AppInfoBean;
import com.yl.technician.util.FilePathUtil;

/**
 * Created by lvlong on 2018/10/8.
 *
 * 关于意约
 */
@CreatePresenter(AboutPresenter.class)
public class AboutActivity extends BaseMvpAppCompatActivity<IAboutView, AboutPresenter> implements IAboutView,ClickHandler {

    ActivityAboutBinding mBinding;
    private AppInfoBean appInfoBeans;
    private String newAppVersionName,appUrl,appDescribe ;
    private int newAppVersionCode,isUpdate;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_about;
    }

    @Override
    protected void init() {

        mBinding = (ActivityAboutBinding) viewDataBinding;
        mBinding.setClick(this);

        mBinding.titleView.setLeftClickListener(view -> finish());

        initView();
        getMvpPresenter().getAppInfos();
    }

    private void setVersionCode() {
        //设置立即更新的下划线
        mBinding.tvImmediatelyUpdate.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

    }

    private void initView() {
        StringBuilder currentVer = new StringBuilder();
        currentVer.append("(").append("V").append(BuildConfig.VERSION_NAME).append(")");
        mBinding.tvCurrentVersionCode.setText(currentVer.toString());
        StatusBarUtil.setLightMode(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.tv_immediately_update:

                if (null == appInfoBeans) return;
                if (appInfoBeans.getIsUpdate() == 0) return; // 0，不升级
                // 是否强制升级
                boolean forcedUpgrade = (appInfoBeans.getIsUpdate() == 1 ? false : true);
                UpdateConfiguration configuration = new UpdateConfiguration()
                        //下载完成自动跳动安装页面
                        .setJumpInstallPage(true)
                        //支持断点下载
                        .setBreakpointDownload(true)
                        //设置是否显示通知栏进度
                        .setShowNotification(true)
                        //设置强制更新
                        .setForcedUpgrade(forcedUpgrade);

                DownloadManager manager = DownloadManager.getInstance();
                manager.init(this)
                        .setApkName(appInfoBeans.getAppName() + ".apk")
                        .setApkUrl(appInfoBeans.getAppUrl())
                        .setSmallIcon(R.mipmap.logo)
                        .setShowNewerToast(true)
                        .setConfiguration(configuration)
                        .setDownloadPath(FilePathUtil.getAppUpdatePath())
                        .setApkVersionCode(appInfoBeans.getNewAppVersionCode())
                        .setApkVersionName(appInfoBeans.getNewAppVersionName())
                        .setApkSize(appInfoBeans.getAppSize())
                        .setApkDescription(appInfoBeans.getAppDescribe())
                        .download();

                break;

        }

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void getAppInfoSuc(AppInfoBean appInfoBean) {
        if (appInfoBean!=null){
            appInfoBeans = appInfoBean;
            int newAppVersionCode = Integer.valueOf(appInfoBean.getNewAppVersionName().replace(".", ""));
            isUpdate = appInfoBean.getIsUpdate();
            newAppVersionName = appInfoBean.getNewAppVersionName();
            appUrl = appInfoBean.getAppUrl();
            appDescribe = appInfoBean.getAppDescribe();
            if (newAppVersionCode>BuildConfig.VERSION_CODE){
                mBinding.llHaveNewVersion.setVisibility(View.VISIBLE);
                mBinding.llNotNewVersion.setVisibility(View.GONE);
                mBinding.tvNewVersionCode.setText(appInfoBean.getNewAppVersionName());
            }else {
                mBinding.llNotNewVersion.setVisibility(View.VISIBLE);
                mBinding.llHaveNewVersion.setVisibility(View.GONE);
            }
        }
    }
}