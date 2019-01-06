package com.yl.technician.module.home.coupons.add;

import android.view.View;

import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.databinding.ActivityAddCouponsBinding;
import com.yl.technician.util.FragmentManagerUtil;


/*
    添加优惠券
 * Create by lvlong on  2018/10/29
 */

public class AddCouponsActivity extends BaseAppCompatActivity implements ClickHandler {

    private ActivityAddCouponsBinding mBinding;

    FragmentManagerUtil fragmentManagerUtil;
    AddFullSubFragment addFullSubFragment;
    AddDiscountFragment addDiscountFragment;
    private int currFragement = 1;//1为满减 2为折扣

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_coupons;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityAddCouponsBinding) viewDataBinding;
        mBinding.setClick(this);

        fragmentManagerUtil = new FragmentManagerUtil(this, R.id.layout_frame);
        addFullSubFragment = new AddFullSubFragment();
        addFullSubFragment.setArguments(getIntent().getExtras());

        fragmentManagerUtil.chAddFrag(addFullSubFragment, "", false);

        addDiscountFragment = new AddDiscountFragment();
        addDiscountFragment.setArguments(getIntent().getExtras());
        initView();

    }

    private void initView() {
        mBinding.titleView.setLeftClickListener(v -> finish());

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_full_reduction:          //满减优惠
                if (currFragement != 1) {
                    if (addFullSubFragment != null && addFullSubFragment.isAdded()) {
                        if (addDiscountFragment != null) {
                            fragmentManagerUtil.chHideFrag(addDiscountFragment);
                        }
                        fragmentManagerUtil.chShowFrag(addFullSubFragment);
                    } else {
                        fragmentManagerUtil.chAddFrag(addFullSubFragment, "", false);
                    }

                    currFragement = 1;
                }
                break;

            case R.id.rb_discount:                //折扣优惠
                if (currFragement != 2) {
                    if (addDiscountFragment != null && addDiscountFragment.isAdded()) {
                        if (addFullSubFragment != null) {
                            fragmentManagerUtil.chHideFrag(addFullSubFragment);
                        }
                        fragmentManagerUtil.chShowFrag(addDiscountFragment);
                    } else {
                        fragmentManagerUtil.chAddFrag(addDiscountFragment, "", false);
                    }
                    currFragement = 2;
                }
                break;

        }

    }

}
