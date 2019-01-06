package com.yl.technician.module.mine.coupon;

import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.databinding.ActivityMineCouponBinding;

/**
 * 我的优惠券
 * <p>
 * Create by zm on 2018/9/29
 */
public class CouponActivity extends BaseMvpAppCompatActivity<ICouponView, CouponPresenter> implements ICouponView {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_mine_coupon;
    }

    @Override
    protected void init() {

        initView();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        ActivityMineCouponBinding couponBinding = (ActivityMineCouponBinding) viewDataBinding;
        // 返回
        couponBinding.titleView.setLeftClickListener(v -> finish());
    }

    @Override
    public void showToast(String message) {

    }
}
