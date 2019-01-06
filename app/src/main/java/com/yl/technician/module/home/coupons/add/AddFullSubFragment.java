package com.yl.technician.module.home.coupons.add;

import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.view.TimePickerView;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.DateUtil;
import com.yl.core.util.KeyboardUtil;
import com.yl.technician.R;
import com.yl.technician.base.fragment.BaseMvpFragment;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.FragmentAddFullsubBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.bean.EventBean;
import com.yl.technician.module.home.coupons.CouponBean;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.widget.picker.PickerViewFactory;
import com.yl.technician.widget.picker.imp.YMDTimePickerView;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhangzz on 2018/11/2
 */
@CreatePresenter(AddCouponsPresenter.class)
public class AddFullSubFragment extends BaseMvpFragment<AddCouponsView, AddCouponsPresenter> implements AddCouponsView, ClickHandler {
    private FragmentAddFullsubBinding mBinding;
    private TimePickerView pvTime; // 时间选择器


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_fullsub;
    }

    @Override
    protected void initView() {
        mBinding = (FragmentAddFullsubBinding) viewDataBinding;
        mBinding.setClick(this);

        mBinding.tvEffectiveStartTime.setText(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD));
        mBinding.tvEffectiveEndTime.setText(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD));
        mBinding.tvCanReceiveStartTime.setText(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD));
        mBinding.tvCanReceiveEndTime.setText(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD));

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_effective_start_time:
                KeyboardUtil.closeSoftKeyboard(getActivity());
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(new Date());
                pvTime = PickerViewFactory.createPickersView(YMDTimePickerView.class)
                        .init(getActivity(), calendar1, null, (date, v)
                                -> {
                            mBinding.tvEffectiveStartTime.setText(DateUtil.date2Str(date, DateUtil.FORMAT_YMD));
                            mBinding.tvEffectiveEndTime.setText(DateUtil.date2Str(date, DateUtil.FORMAT_YMD));
                        });
                pvTime.show();
                break;
            case R.id.tv_effective_end_time:
                KeyboardUtil.closeSoftKeyboard(getActivity());
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(DateUtil.str2Date(mBinding.tvEffectiveStartTime.getText().toString() +  " 00:00:00", DateUtil.FORMAT_YMD));
                pvTime = PickerViewFactory.createPickersView(YMDTimePickerView.class)
                        .init(getActivity(), calendar2,null, (date, v)
                                -> {
                            mBinding.tvEffectiveEndTime.setText(DateUtil.date2Str(date, DateUtil.FORMAT_YMD));
                            mBinding.tvCanReceiveEndTime.setText(mBinding.tvEffectiveEndTime.getText());
                        });
                pvTime.show();
                break;
            case R.id.tv_can_receive_start_time:
                KeyboardUtil.closeSoftKeyboard(getActivity());
                Calendar calendar3 = Calendar.getInstance();
                calendar3.setTime(new Date());
                Calendar calendar31 = Calendar.getInstance();
                calendar31.setTime(DateUtil.str2Date(mBinding.tvEffectiveEndTime.getText().toString() + " 23:59:59", DateUtil.FORMAT_YMDHMS));
                pvTime = PickerViewFactory.createPickersView(YMDTimePickerView.class)
                        .init(getActivity(), calendar3, calendar31, (date, v)
                                -> {
                            mBinding.tvCanReceiveStartTime.setText(DateUtil.date2Str(date, DateUtil.FORMAT_YMD));
                            mBinding.tvCanReceiveEndTime.setText(mBinding.tvEffectiveEndTime.getText());
                        });
                pvTime.show();
                break;
            case R.id.tv_can_receive_end_time:
                KeyboardUtil.closeSoftKeyboard(getActivity());

                Calendar calendar4 = Calendar.getInstance();
                calendar4.setTime(DateUtil.str2Date(mBinding.tvCanReceiveStartTime.getText().toString() + " 00:00:00", DateUtil.FORMAT_YMD));
                Calendar calendar41 = Calendar.getInstance();
                calendar41.setTime(DateUtil.str2Date(mBinding.tvEffectiveEndTime.getText().toString() + " 23:59:59", DateUtil.FORMAT_YMD));
                pvTime = PickerViewFactory.createPickersView(YMDTimePickerView.class)
                        .init(getActivity(),calendar4 , calendar41 , (date, v)
                                -> {
                            mBinding.tvCanReceiveEndTime.setText(DateUtil.date2Str(date, DateUtil.FORMAT_YMD));
                        });
                pvTime.show();
                break;
            case R.id.btn_release:
                CouponBean couponBean = new CouponBean();
                String amountStr = mBinding.etFull.getText().toString();
                if (TextUtils.isEmpty(amountStr)) {
                    showToast("满足金额不能为空");
                    return;
                } else {
                    long amout = Long.parseLong(amountStr);
                    couponBean.setAmount(amout);
                }

                String minusStr = mBinding.etMinus.getText().toString();
                if (TextUtils.isEmpty(minusStr)) {
                    showToast("减去金额不能为空");
                    return;
                } else {
                    if(minusStr.endsWith(".")){
                        minusStr = minusStr.substring(0,minusStr.length()-1);
                    }
                    long minus = Long.parseLong(minusStr);
                    if (minus!=0){
                        couponBean.setDeduction(minus);
                    }else {
                        mBinding.etMinus.setText("");
                        ToastUtils.shortToast("减去金额不能为0");
                        return;
                    }

                }

                String dailyAmountStr = mBinding.etDailyAmount.getText().toString();
                if (TextUtils.isEmpty(dailyAmountStr)) {
                    showToast("每日发放总数不能为空");
                    return;
                } else {
                    int dailyAmount = Integer.parseInt(dailyAmountStr);
                    if (dailyAmount!=0){
                        couponBean.setQuantity(dailyAmount);
                    }else {
                        mBinding.etDailyAmount.setText("");
                        ToastUtils.shortToast("每日发放总数不能为0");
                        return;
                    }

                }


                couponBean.setValidstart(mBinding.tvEffectiveStartTime.getText().toString());
                couponBean.setValidend(mBinding.tvEffectiveEndTime.getText().toString());
                couponBean.setReceivestart(mBinding.tvCanReceiveStartTime.getText().toString());
                couponBean.setReceiveend(mBinding.tvCanReceiveEndTime.getText().toString());
                couponBean.setType(1);
                couponBean.setStylistId(Long.parseLong(AccountManager.getInstance().getStylistId()));
                getMvpPresenter().requestAddCoupon(couponBean, getActivity());
                break;
        }
    }


    @Override
    public void addSuccess() {
        EventBus.getDefault().post(new EventBean.CouponsListUpdate(0));
        getActivity().finish();
    }
}
