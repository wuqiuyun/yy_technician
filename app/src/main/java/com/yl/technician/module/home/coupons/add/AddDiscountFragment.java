package com.yl.technician.module.home.coupons.add;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.bigkoo.pickerview.view.TimePickerView;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.DateUtil;
import com.yl.core.util.KeyboardUtil;
import com.yl.technician.R;
import com.yl.technician.base.fragment.BaseMvpFragment;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.FragmentAddDiscoountBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.bean.EventBean;
import com.yl.technician.module.home.coupons.CouponBean;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.widget.mytimepickview.CustomTimePicker;
import com.yl.technician.widget.mytimepickview.SelectTime;
import com.yl.technician.widget.picker.PickerViewFactory;
import com.yl.technician.widget.picker.imp.YMDTimePickerView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhangzz on 2018/11/2
 */
@CreatePresenter(AddCouponsPresenter.class)
public class AddDiscountFragment extends BaseMvpFragment<AddCouponsView, AddCouponsPresenter> implements AddCouponsView, ClickHandler {
    FragmentAddDiscoountBinding mBinding;
    private TimePickerView pvTime; // 时间选择器
    private CustomTimePicker mTimePicker;

    private String validStartTime = "10:00";//有效时间开始
    private String validEndTime = "23:00";//有效时间结束

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_discoount;
    }

    @Override
    protected void initView() {
        mBinding = (FragmentAddDiscoountBinding) viewDataBinding;
        mBinding.setClick(this);
        mBinding.tvEffectiveStartTime.setText(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD));
        mBinding.tvEffectiveEndTime.setText(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD));
        mBinding.tvCanReceiveStartTime.setText(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD));
        mBinding.tvCanReceiveEndTime.setText(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD));
        // 初始化时间控件
        initTimerPicker();
        mBinding.etDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DLog.e("onTextChanged","------------"+s.toString().trim());
                //优惠券必须要达到的金额Integer.parseInt(fullPrice);
                if(s == null || s.length() == 0){
                    return;
                }
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 1) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 2);
                        mBinding.etDiscount.setText(s);
                        mBinding.etDiscount.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    mBinding.etDiscount.setText(s);
                    mBinding.etDiscount.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        mBinding.etDiscount.setText(s.subSequence(0, 1));
                        mBinding.etDiscount.setSelection(1);
                        return;
                    }
                }

                if(s.toString().trim().substring(0,1).equals(".") ||
                        s.toString().trim().substring(s.toString().trim().length()-1,s.toString().trim().length()).equals(".")){
                    return;
                }
                String amount = s.toString().trim();
                if(amount.endsWith(".")){
                    amount = amount.substring(0,amount.length()-1);
                }
                double amountDouble = Double.parseDouble(amount);
                if (amountDouble>10){
                    mBinding.etDiscount.setText("");
                    ToastUtils.shortToast("输入折扣过大");
                    return;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
            case R.id.tv_discounts_time:
                KeyboardUtil.closeSoftKeyboard(getActivity());
                mTimePicker.show();
                break;
            case R.id.btn_release:
                CouponBean couponBean = new CouponBean();

                String discountStr = mBinding.etDiscount.getText().toString();
                if (TextUtils.isEmpty(discountStr)) {
                    showToast("折扣不能为空");
                    return;
                } else {
                    if(discountStr.endsWith(".")){
                        discountStr = discountStr.substring(0,discountStr.length()-1);
                    }
                    double amountDouble = Double.parseDouble(discountStr);
                    if (amountDouble!=0){
                        couponBean.setDeduction(amountDouble);
                    }else {
                        mBinding.etDiscount.setText("");
                        ToastUtils.shortToast("输入折扣不能为0");
                        return;
                    }
                }

                String dailyAmountStr = mBinding.etDiscountAmount.getText().toString();
                if (TextUtils.isEmpty(dailyAmountStr)) {
                    showToast("每日发放总数不能为空");
                    return;
                } else {
                    int dailyAmount = Integer.parseInt(dailyAmountStr);
                    if (dailyAmount!=0){
                        couponBean.setQuantity(dailyAmount);
                    }else {
                        mBinding.etDiscountAmount.setText("");
                        ToastUtils.shortToast("每日发放总数不能为0");
                        return;
                    }
                }

                String discountsTime = mBinding.tvDiscountsTime.getText().toString();
                if (TextUtils.isEmpty(discountsTime)) {
                    showToast("优惠时间不能为空");
                    return;
                } else {
                    couponBean.setUsestart(validStartTime + ":00");
                    couponBean.setUseend(validEndTime + ":00");
                }

                String effectiveStartTime = mBinding.tvEffectiveStartTime.getText().toString();
                String effectiveEndTime = mBinding.tvEffectiveEndTime.getText().toString();


                couponBean.setValidstart(effectiveStartTime);
                couponBean.setValidend(effectiveEndTime);
                couponBean.setReceivestart(mBinding.tvCanReceiveStartTime.getText().toString());
                couponBean.setReceiveend(mBinding.tvCanReceiveEndTime.getText().toString());
                couponBean.setType(2);
                couponBean.setLimited("0");
                couponBean.setStylistId(Long.parseLong(AccountManager.getInstance().getStylistId()));

                getMvpPresenter().requestAddCoupon(couponBean, getActivity());
                break;
            case R.id.layout_discounts_time:
                KeyboardUtil.closeSoftKeyboard(getActivity());
                break;
        }
    }


    private void initTimerPicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
        String time = sdf.format(new Date());
        mTimePicker = new CustomTimePicker(getActivity(), "优惠时间", validStartTime, validEndTime, new CustomTimePicker.ResultHandler() {
            @Override
            public void handle(SelectTime mSelectTime) {
                validStartTime = mSelectTime.getStartTime();
                validEndTime = mSelectTime.getEndTime();
                mBinding.tvDiscountsTime.setText(validStartTime + "-" + validEndTime);
            }

            @Override
            public void cancel() {

            }
        });
    }

    @Override
    public void addSuccess() {
        EventBus.getDefault().post(new EventBean.CouponsListUpdate(0));
        getActivity().finish();
    }
}
