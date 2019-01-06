package com.yl.technician.module.home.personpay.qrcode;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.LoaderListener;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.qrcode.QRCodeEncoder;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityReserCertificateBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.EventBean;
import com.yl.technician.model.vo.bean.PersonPayBean;
import com.yl.technician.util.ColorUtil;
import com.yl.technician.util.FilePathUtil;
import com.yl.technician.widget.bottomview.BottomViewFactory;
import com.yl.technician.widget.bottomview.base.BaseBottomView;
import com.yl.technician.widget.dialog.YLDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 当面付二维码
 * <p>
 * Create by lyj on 2018/11/26
 */
@CreatePresenter(OrderCertificatePresenter.class)
public class OrderCertificateActivity extends BaseMvpAppCompatActivity<OrderCertificateView, OrderCertificatePresenter>
        implements OrderCertificateView, ClickHandler {

    private ActivityReserCertificateBinding mBinding;
    private String mStoreid,mMoeny,mUserHead,mQrCode;
    private Bitmap mUserHeadBitMap;
    private BaseBottomView mSelectPhotoView;
    private PersonPayBean personPayBean;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_reser_certificate;
    }

    @Override
    protected void init() {
        initView();
        setData();
    }

    private void initView() {
        EventBus.getDefault().register(this);

        mBinding = (ActivityReserCertificateBinding) viewDataBinding;
        mBinding.setClick(this);
        mBinding.titleView.setLeftClickListener(v -> finish());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mStoreid = bundle.getString("storeid");
            mMoeny = bundle.getString("money");
            mBinding.tvQrcodeMoney.setText("￥"+mMoeny);
            Gson mGson = new Gson();
            personPayBean = new PersonPayBean();
            personPayBean.setAmount(mMoeny);
            personPayBean.setStoreId(mStoreid);
            personPayBean.setStylistId(AccountManager.getInstance().getStylistId());
            personPayBean.setUserId(AccountManager.getInstance().getUserId());
            mQrCode = mGson.toJson(personPayBean);

        }
        mUserHead = AccountManager.getInstance().getUserHeadImg();
        payTypeOpus(2);
    }

    private void setData() {
        ImageLoader.downloadImage(this, mUserHead, FilePathUtil.getCompressPath(), new LoaderListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                mUserHeadBitMap = bitmap;
//                // 生成订单编号二维码
//                payTypeOpus(1);
            }

            @Override
            public void onError() {
//                // 生成订单编号二维码
//                payTypeOpus(1);
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_paytype: // 选择二维码生成方式
                if (mSelectPhotoView == null) {
                    mSelectPhotoView = BottomViewFactory.create(OrderCertificateActivity.this, BottomViewFactory.Type.PAY);
                }
                mSelectPhotoView.showBottomView(true);
                DLog.e("tv_select_paytypetv_selecttv_select_paytypetv_select_paytype");
                break;
        }
    }

    //选择支付方式
    public void payTypeOpus(int type) {
        switch (type) {

            case Constants.PAYTYPE_APP:
                // 生成订单编号二维码
                if (!TextUtils.isEmpty(mQrCode)) {
                    mBinding.tvSelectPaytype.setText("APP支付>");
                    mBinding.tvQrCode.setText("APP扫描二维码向我付款");
                    QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(this);
                    if (mUserHeadBitMap!=null){
                        qrCodeEncoder.createQrCode2ImageView(mQrCode, mBinding.ivQrCode, mUserHeadBitMap);
                    }else {
                        qrCodeEncoder.createQrCode2ImageView(mQrCode, mBinding.ivQrCode, R.mipmap.logo_round);
                    }

                }else {
                    ToastUtils.shortToast("二维码生成失败");
                }
                break;
            case Constants.PAYTYPE_WECHAT:
//                ToastUtils.shortToast("暂未开放,敬请期待!");
                getMvpPresenter().getQrCode(mStoreid,AccountManager.getInstance().getStylistId(),mMoeny,this);
                break;
        }
    }

    @Override
    public void getQCodeSuccess(Object object) {
        if (object!=null){
            mBinding.tvSelectPaytype.setText("微信支付>");
            mBinding.tvQrCode.setText("微信扫描二维码向我付款");
            ImageLoader.loadImage(mBinding.ivQrCode,object.toString());
        }
    }

    @Override
    public void getStoreListFail() {

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBean.FriendChangeEventBean event) {
        if (event != null) {
            if (Constants.EVENT_PERSON_PAY == event.getTag()) {
                showDLDialog();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 加入门店弹窗
     * */
    private void showDLDialog() {
        new YLDialog.Builder(this)
                .setTitle("提示")
                .setType(YLDialog.DIALOG_TYPE_NORMAL)
                .setMessage("当面付收款成功")
                .setPositiveButton("确认", (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                })
                .setCancelable(false)
                .create()
                .show();
    }


    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
    }

}
