package com.yl.technician.module.mine.wallet.withdraw.overview.monthbill.income;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.DateUtil;
import com.yl.technician.R;
import com.yl.technician.base.fragment.BaseMvpFragment;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.FragmentChartBarBinding;
import com.yl.technician.model.vo.bean.MonthSumBean;
import com.yl.technician.module.mine.wallet.withdraw.overview.monthbill.MonthBillActivity;
import com.yl.technician.module.mine.stylist.IUpDataFragment;
import com.yl.technician.util.BarChartManager;
import com.yl.technician.util.FormatUtil;

import java.util.Date;

/**
 * 总收入-柱状图
 */
@CreatePresenter(IncomePresenter.class)
public class IncomeChartBarFragment extends BaseMvpFragment<IIncomeView, IncomePresenter> implements IIncomeView ,ClickHandler ,IUpDataFragment{

    private FragmentChartBarBinding mBinding;
    private BarChartManager mBarChartManager;
    private Date mDate=new Date();
    public static Fragment newInstance() {
        Fragment incomeFragment = new IncomeChartBarFragment();
        Bundle bundle = new Bundle();
        incomeFragment.setArguments(bundle);
        return incomeFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chart_bar;
    }

    @Override
    protected void initView() {
        hasExtras();
        mBinding = (FragmentChartBarBinding) viewDataBinding;
        mBinding.setClick(this);
        MonthBillActivity activity = (MonthBillActivity) getActivity();
        activity.setIUpChartBarFragment(this);
        mBarChartManager = new BarChartManager(mBinding.barChart, getContext());
    }


    private void hasExtras() {
        Bundle bundle = getArguments();
    }


    @Override
    protected void loadData() {
        getMvpPresenter().getMonthSumIn(DateUtil.date2Str(mDate, DateUtil.FORMAT_YM),"2");
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void getMonthSumIn(MonthSumBean monthSumInBean) {
        if (monthSumInBean.getShowfyMonth()==null){
            mBinding.barChart.clear();
        }else {
            mBarChartManager.showBarChart(monthSumInBean.getShowfyMonth(),mDate);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_left_button:
                if (DateUtil.getMonth(mBarChartManager.getDate())==5)return;
                mDate=DateUtil.addMonth(mBarChartManager.getDate(), -1);
                getMvpPresenter().getMonthSumIn(DateUtil.date2Str(mDate, DateUtil.FORMAT_YM),"2");
                break;
            case R.id.iv_right_button:
                if (DateUtil.getMonth(mBarChartManager.getDate())==12)return;
                mDate=DateUtil.addMonth(mBarChartManager.getDate(), 1);
                getMvpPresenter().getMonthSumIn(DateUtil.date2Str(mDate, DateUtil.FORMAT_YM),"2");
                break;
        }
    }

    @Override
    public void onUpData(int filterType, Object o) {
        String date = (String) o;
        mDate=DateUtil.str2Date(date,DateUtil.FORMAT_YM);
        getMvpPresenter().getMonthSumIn(date,"2");
    }
}
