package com.yl.technician.module.mine.pplarz.banding;

import android.view.View;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityBandingRefereesBinding;
import com.yl.technician.model.local.preferences.CommonSharedPreferences;
import com.yl.technician.model.vo.bean.EventBean;
import com.yl.technician.util.FormatUtil;

import org.greenrobot.eventbus.EventBus;


/*
 *  @创建者:   27407
 *  @创建时间:  2018/10/17 16:59
 *  @描述：    绑定推荐人
 */

@CreatePresenter(BandingRefereesPresenter.class)
public class BandingRefereesActivity extends BaseMvpAppCompatActivity<IBandingRefereesView, BandingRefereesPresenter>
        implements ClickHandler, IBandingRefereesView {

    ActivityBandingRefereesBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_banding_referees;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityBandingRefereesBinding) viewDataBinding;
        mBinding.setClick(this);

        mBinding.titleView.setLeftClickListener(v -> finish());
        mBinding.tvInviteReward.setText(FormatUtil.Formatstring(CommonSharedPreferences.getInstance().getBasicDataBean().getInviteReward()));
        StatusBarUtil.setLightMode(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_commit:   //提交
                getMvpPresenter().bindInviteCode(mBinding.etInviteCode.getText().toString());
                break;
        }

    }

    @Override
    public void bindingSuc() {
        showToast("绑定成功！");
        //通知上一页刷新绑定的数据
        EventBus.getDefault().post(new EventBean.BandingRefereesSuc());
        finish();
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }
}
