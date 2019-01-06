package com.yl.technician.module.home.coupons;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityCouponsBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.bean.EventBean;
import com.yl.technician.model.vo.result.CouponResult;
import com.yl.technician.module.home.coupons.add.AddCouponsActivity;
import com.yl.technician.widget.dialog.YLDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/*
    优惠券
 * Create by lvlong on  2018/10/29
 */
@CreatePresenter(CouponsPresenter.class)
public class CouponsActivity extends BaseMvpAppCompatActivity<CouponsView, CouponsPresenter> implements CouponsView {

    private ActivityCouponsBinding mBinding;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private BaseMultiItemQuickAdapter mAdapter;
    private List<CouponBean> couponBeanList = new ArrayList<>();
    private int couponsStatus;
    private String strContent,stylistCouponId;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_coupons;
    }

    /**
     * 优惠券添加成功，优惠券页面刷新
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBean.CouponsListUpdate event) {
        if (event.getTag() == 0) {
            getMvpPresenter().getAllCoupons(AccountManager.getInstance().getStylistId(), this);
        }
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityCouponsBinding) viewDataBinding;
        EventBus.getDefault().register(this);
        initView();

    }

    private void initView() {
        mBinding.titleView.setLeftClickListener(v -> finish());
        mBinding.titleView.setRightImgClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CouponsActivity.this, AddCouponsActivity.class);
            }
        });

        mRecyclerView = mBinding.rcvCoupons;
        mAdapter = new CouponsItemTypeAdapter(couponBeanList);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        getMvpPresenter().getAllCoupons(AccountManager.getInstance().getStylistId(), this);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CouponBean couponBean = couponBeanList.get(position);
            couponsStatus = couponBean.getStatus();
            stylistCouponId = couponBean.getStylistCouponId();
            if (view.getId() == R.id.tv_full_reduction_down) {
                if (couponsStatus == 0) {
                    strContent = "是否上架该优惠券?";
                } else {
                    strContent = "是否下架该优惠券,下架后用户无法继续领取该优惠券,已领取按设定使用";
                }
                showDLDialog();
            } else if (view.getId() == R.id.tv_discount_down) {
                if (couponsStatus== 0) {
                    strContent = "是否上架该优惠券?";
                } else {
                    strContent = "是否下架该优惠券,下架后用户无法继续领取该优惠券,已领取按设定使用";
                }
                showDLDialog();
            } else if (view.getId() == R.id.btn_delete) {
                if (couponBean.getStatus() == 1) {
                    ToastUtils.shortToast("删除失败，请先下架该优惠券");
                } else {
                    getMvpPresenter().deleteCoupon(CouponsActivity.this, couponBean.getStylistCouponId()+"", position);
                }
            }
        });
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }


    @Override
    public void editPutawaySuccess() {
        getMvpPresenter().getAllCoupons(AccountManager.getInstance().getStylistId(), this);
    }

    @Override
    public void getAllSuccess(CouponResult couponResult) {
        if (couponResult != null && couponResult.getData() != null) {
            couponBeanList = couponResult.getData();
            if (couponBeanList != null && couponBeanList.size() > 0) {
                mBinding.rcvCoupons.setVisibility(View.VISIBLE);
                mBinding.layoutEmpty.setVisibility(View.GONE);
                mAdapter.setNewData(couponBeanList);
            } else {
                mBinding.rcvCoupons.setVisibility(View.GONE);
                mBinding.layoutEmpty.setVisibility(View.VISIBLE);
            }
        } else {
            mBinding.rcvCoupons.setVisibility(View.GONE);
            mBinding.layoutEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void deleteCouponSuccess(int position) {
        mAdapter.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    /**
     * 优惠券上下架弹窗
     * */
    private void showDLDialog() {
        new YLDialog.Builder(this)
                .setType(YLDialog.DIALOG_TYPE_NORMAL)
                .setMessage(strContent)
                .setTitle("提示")
                .setPositiveButton("确定", (dialog, which) -> {
                    if (couponsStatus == 1) {
                        getMvpPresenter().requestEditPutaway(stylistCouponId + "", "0", CouponsActivity.this);
                    } else {
                        getMvpPresenter().requestEditPutaway(stylistCouponId + "", "1", CouponsActivity.this);
                    }
                    dialog.dismiss();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }
}
