package com.yl.technician.module.mine.wallet.withdraw.overview.monthbill.income;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.DateUtil;
import com.yl.technician.R;
import com.yl.technician.base.fragment.BaseMvpFragment;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.FragmentChartPieBinding;
import com.yl.technician.model.vo.bean.MonthSumBean;
import com.yl.technician.module.mine.wallet.withdraw.overview.monthbill.MonthBillActivity;
import com.yl.technician.module.mine.stylist.IUpDataFragment;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.util.PieChartManager;

import java.util.ArrayList;
import java.util.Date;


/**
 * 总收入-饼图
 */
@CreatePresenter(IncomePresenter.class)
public class IncomeChartPieFragment extends BaseMvpFragment<IIncomeView, IncomePresenter> implements IIncomeView,IUpDataFragment{

    private FragmentChartPieBinding mBinding;
    private Bundle mBundle;
    public static IncomeFragment mIncomeFragment;

    public static Fragment newInstance(IncomeFragment incomeFragment) {
        Fragment incomeChartPieFragment = new IncomeChartPieFragment();
        Bundle bundle = new Bundle();
        incomeChartPieFragment.setArguments(bundle);
        mIncomeFragment=incomeFragment;
        return  incomeChartPieFragment;
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
        return R.layout.fragment_chart_pie;
    }

    @Override
    protected void initView() {
        mBinding = (FragmentChartPieBinding) viewDataBinding;
        hasExtras();
        MonthBillActivity activity = (MonthBillActivity) getActivity();
        activity.setIUpChartPieFragment(this);
    }

    private void hasExtras() {
        mBundle = getArguments();
    }


    @Override
    protected void loadData() {
            getMvpPresenter().getMonthSumIn(DateUtil.date2Str(new Date(), DateUtil.FORMAT_YM),"1");
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }


    @Override
    public void getMonthSumIn(MonthSumBean monthSumInBean) {
        if (mIncomeFragment!=null)mIncomeFragment.setOrders(monthSumInBean);
        if (monthSumInBean.getClassifyIn()==null||monthSumInBean.getClassifyIn().size()==0){
            mBinding.pieChart.clear();
        }else {
            mBinding.pieChart.upData(monthSumInBean.getClassifyIn());
        }

    }

    @Override
    public void onUpData(int filterType, Object o) {
        getMvpPresenter().getMonthSumIn((String) o,"1");
    }
}
