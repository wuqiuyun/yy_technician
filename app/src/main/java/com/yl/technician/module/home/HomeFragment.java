package com.yl.technician.module.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.technician.R;
import com.yl.technician.YLApplication;
import com.yl.technician.base.fragment.BaseMvpFragment;
import com.yl.technician.component.amap.LocationPresenter;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.FragmentHomeBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.constant.OrderStatus;
import com.yl.technician.model.local.preferences.CommonSharedPreferences;
import com.yl.technician.model.vo.bean.BannerBean;
import com.yl.technician.model.vo.bean.EventBean;
import com.yl.technician.model.vo.bean.StoreOrderStatisticalBean;
import com.yl.technician.model.vo.bean.StylistAuthApplyBean;
import com.yl.technician.model.vo.requestbody.SaveLocationBody;
import com.yl.technician.module.certification.CertificationActivity;
import com.yl.technician.module.common.WebActivity;
import com.yl.technician.module.home.audit.InAuditActivity;
import com.yl.technician.module.home.card.MyCardActivity;
import com.yl.technician.module.home.coupons.CouponsActivity;
import com.yl.technician.module.home.evaluation.EvaluationManagerActivity;
import com.yl.technician.module.home.in.InStoresActivity;
import com.yl.technician.module.home.in.add.AddApplyForActivity;
import com.yl.technician.module.home.order.OrderCenterActivity;
import com.yl.technician.module.home.orders.manager.OrderManagerActivity;
import com.yl.technician.module.home.personpay.PersonPayActivity;
import com.yl.technician.module.home.qrcode.ScanCodeActivity;
import com.yl.technician.module.home.service.ServiceManageActivity;
import com.yl.technician.module.home.time.TimeManageActivity;
import com.yl.technician.module.home.works.MyWorksActivity;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.widget.banner.BannerConfig;
import com.yl.technician.widget.banner.listener.OnBannerListener;
import com.yl.technician.widget.banner.loader.ImageLoaders;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * <p>
 * Created by zm on 2018/9/19.
 */
@CreatePresenter(HomePresenter.class)
public class HomeFragment extends BaseMvpFragment<IHomeView, HomePresenter>
        implements IHomeView, ClickHandler, AMapLocationListener, OnBannerListener {
    private static final int PLAY_TIME = 5000;
    FragmentHomeBinding mBinding;

    private LocationPresenter locationPresenter;
    private SaveLocationBody.Builder mSaveLocationBody;

    private List<HotCity> hotCities;
    private ImageLoaderConfig config;
    private ArrayList<BannerBean> data = new ArrayList<>();

    public static Fragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 认证信息
        getMvpPresenter().getStylistAuthApplyInfo();
        // 订单统计
        getMvpPresenter().getStylistOrderStatistical();
        // banner
        getMvpPresenter().getBanner();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mBinding = (FragmentHomeBinding) viewDataBinding;
        mBinding.setClick(this);
    }

    /**
     * 收到新的推送 刷新显示
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBean.NewOrderMessage event) {
        if (event != null) {
            // 订单统计
            getMvpPresenter().getStylistOrderStatistical();
        }
    }

    @SuppressLint("CheckResult")
    @Override
    protected void loadData() {
        mSaveLocationBody = new SaveLocationBody.Builder();
        startLocation();
    }

    @SuppressLint("CheckResult")
    private void startLocation() {
        if (locationPresenter != null) {
            locationPresenter.startLocation();
        }else {
            locationPresenter = new LocationPresenter(YLApplication.getContext());
            locationPresenter.setMapLocationListener(this);
        }
        new RxPermissions(this)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        locationPresenter.startLocation();
                    } else {
                        ToastUtils.shortToast(getString(R.string.permissions_location));
                    }
                });
    }

    private void selectCity() {
        if (hotCities == null) {
            hotCities = new ArrayList<>();
            hotCities.add(new HotCity("北京", "110000"));
            hotCities.add(new HotCity("上海", "310000"));
            hotCities.add(new HotCity("广州", "440100"));
            hotCities.add(new HotCity("深圳", "440300"));
            hotCities.add(new HotCity("杭州", "330100"));
        }
        CityPicker.from(getActivity())
                .enableAnimation(true)
                .setAnimationStyle(R.style.DefaultCityPickerTheme)
                .setLocatedCity(null)
                .setHotCities(hotCities)
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        mBinding.tvLocation.setText(data.getName());
                        getMvpPresenter().changesave(data.getCode(), data.getName());
                    }

                    @Override
                    public void onLocate() {
                        startLocation();
                    }

                    @Override
                    public void onCancel() {

                    }
                }).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_perfect_stores: //审核中,完善门店
                InAuditActivity.startActivity(getContext(), InAuditActivity.class);
                break;
            case R.id.tv_again_apply: //审核失败,重新申请
                CertificationActivity.startActivity(getContext(), CertificationActivity.class);
                break;
            case R.id.tv_ok: //审核通过,确认
                CommonSharedPreferences.getInstance().setCeritSuccessUiShow();
                AddApplyForActivity.startActivity(getContext(), AddApplyForActivity.class);
                mBinding.llAuditSucceed.setVisibility(View.GONE);
                break;
            //---------------------------------------------------------------------------------------
            case R.id.btn_order_confirm: // 待确认
                OrderCenterActivity.startActivity(getContext(), OrderStatus.ORDER_CONFIRM);
                break;
            case R.id.btn_order_service: // 待核销
                OrderCenterActivity.startActivity(getContext(), OrderStatus.ORDER_SERVICE);
                break;
            case R.id.btn_order_refund: // 已退款
                OrderCenterActivity.startActivity(getContext(), OrderStatus.ORDER_REFUND);
                break;
            case R.id.btn_order_complete: // 已完成
                OrderCenterActivity.startActivity(getContext(), OrderStatus.ORDER_COMPLETE);
                break;
            //---------------------------------------------------------------------------------------
            case R.id.btn_service_manage: // 服务管理
                ServiceManageActivity.startActivity(getContext(), ServiceManageActivity.class);
                break;
            case R.id.btn_enter_stores: // 入驻门店
                InStoresActivity.startActivity(getContext(), InStoresActivity.class);
                break;
            case R.id.btn_time_manager: // 时间管理
                TimeManageActivity.startActivity(getContext(), TimeManageActivity.class);
                break;
            case R.id.btn_marketing_activities: // 营销活动
                CouponsActivity.startActivity(getContext(), CouponsActivity.class);
                break;
            case R.id.btn_business_card: //我的名片
                MyCardActivity.startActivity(getContext(), MyCardActivity.class);
                break;
            case R.id.btn_cancel_after_verification: //扫码核销/当面付
                ScanCodeActivity.startActivity(getContext(), PersonPayActivity.class);
                break;
            case R.id.btn_comment_manager: // 评价管理
                String stylistId = AccountManager.getInstance().getStylistId();
                Bundle EvaluationBundle = new Bundle();
                EvaluationBundle.putInt(Constants.EVALUATION_TYPE, 1);
                EvaluationBundle.putString(Constants.STYLIST_ID, stylistId);
                EvaluationManagerActivity.startActivity(getContext(), EvaluationManagerActivity.class, EvaluationBundle);
                break;
            case R.id.btn_order_statistics:
                // 预约管理
                OrderManagerActivity.startActivity(getContext(), OrderManagerActivity.class);
                break;
            case R.id.btn_my_works: //我的作品
                MyWorksActivity.startActivity(getContext(), MyWorksActivity.class);
                break;
            case R.id.tv_location:
                startLocation();
                selectCity();
                break;
        }
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void onGetStoreAuthApplyInfoSuccess(StylistAuthApplyBean bean) {
        switch (bean.getStatus()) {
            case 0: // 待审核
                mBinding.llTheAudit.setVisibility(View.VISIBLE);
                mBinding.llAuditSucceed.setVisibility(View.GONE);
                mBinding.llAuditFailure.setVisibility(View.GONE);
                break;

            case 1: // 通过
                mBinding.llTheAudit.setVisibility(View.GONE);
                mBinding.llAuditFailure.setVisibility(View.GONE);
                if (CommonSharedPreferences.getInstance().isCeritSuccessUiShow()) {
                    mBinding.llAuditSucceed.setVisibility(View.GONE);
                } else {
                    mBinding.llAuditSucceed.setVisibility(View.VISIBLE);
                }
                break;

            case -1: // 驳回
                mBinding.llAuditFailure.setVisibility(View.VISIBLE);
                mBinding.llTheAudit.setVisibility(View.GONE);
                mBinding.llAuditSucceed.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onGetStoreOrderStatisticalSuccess(StoreOrderStatisticalBean bean) {
        mBinding.btnOrderConfirm.setNumber(bean.getPendingSum());
        mBinding.btnOrderService.setNumber(bean.getAcceptAndServiceSum());
        mBinding.btnOrderComplete.setNumber(bean.getSuccessSum());
        mBinding.btnOrderRefund.setNumber(bean.getRefundSum());
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        CityPicker.from(getActivity()).locateComplete(new LocatedCity(aMapLocation.getDistrict(), aMapLocation.getAdCode()), LocateState.SUCCESS);
        mSaveLocationBody.userId(AccountManager.getInstance().getUserId())
                .latitude(String.valueOf(aMapLocation.getLatitude()))
                .longitude(String.valueOf(aMapLocation.getLongitude()))
                .location(aMapLocation.getAddress())
                .districtId(aMapLocation.getAdCode())
                .city(aMapLocation.getCity())
                .province(aMapLocation.getProvince());
        if (aMapLocation.getAdCode() != null && !TextUtils.isEmpty(aMapLocation.getAdCode())) {
            getMvpPresenter().saveLocation(mSaveLocationBody.build());
        }
        mBinding.tvLocation.setText(aMapLocation.getDistrict());
    }


    @Override
    public void getBannerSuccess(ArrayList<BannerBean> beans) {
        if (config == null) {
            config = new ImageLoaderConfig.Builder().
                    setCropType(ImageLoaderConfig.CENTER_CROP).
                    setAsBitmap(true).
                    setPlaceHolderResId(R.drawable.home_bg).
                    setErrorResId(R.drawable.home_bg).
                    setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE).
                    setPrioriy(ImageLoaderConfig.LoadPriority.HIGH).build();
        }

        data = beans;
        ArrayList<String> photos = new ArrayList<>();
        for (BannerBean bean : beans) {
            photos.add(bean.getImage());
        }
        mBinding.banner.setImages(photos)
                .setImageLoader(new ImageLoaders() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        ImageLoader.loadImage(imageView, (String) path, config, null);
                    }
                })
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .isAutoPlay(true)
                .setDelayTime(PLAY_TIME)
                .setOnBannerListener(this)
                .start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void OnBannerClick(int position) {
        BannerBean bean = data.get(position);
        String title = bean.getTitle();
        String url = bean.getUrl();
        if (!TextUtils.isEmpty(url.trim())) {
            WebActivity.startActivity(getActivity(), url, title);
        }
    }
}
