package com.yl.technician.module.home.service.setting;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.recycleview.GridSpacingItemDecoration;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityServiceSettingBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.ServerInfoBean;
import com.yl.technician.model.vo.bean.ServerTypeBean;
import com.yl.technician.model.vo.bean.ServiceSettingBean;
import com.yl.technician.model.vo.requestbody.SaveServerRequestBody;
import com.yl.technician.module.home.service.setting.two.SetServiceTimeAdapter;
import com.yl.technician.util.InputUtils;
import com.yl.technician.util.TypeConvertUtils;
import com.yl.technician.widget.popwindow.PopupUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 服务设置第一种页面
 * Created by lvlong on 2018/10/9.
 */
@CreatePresenter(ServiceSettingPresenter.class)
public class ServiceSettingActivity extends BaseMvpAppCompatActivity<ServiceSettingView, ServiceSettingPresenter> implements ClickHandler, ServiceSettingView {

    ActivityServiceSettingBinding mBinding;
    private String mCategoryId;
    private String locktime = "";
    private String mServiceId;
    private TextView mTv_determine;
    private InputMethodManager mImm;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_service_setting;
    }

    @Override
    protected void init() {

        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityServiceSettingBinding) viewDataBinding;
        mBinding.setClick(this);
        //键盘管理
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initView();
        loadData();
    }

    private void loadData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String typeName = bundle.getString("typeName");
            mServiceId = bundle.getString("serviceId");
            mCategoryId = bundle.getString("categoryId");
            DLog.d("serviceId=" + mServiceId);
            mBinding.tvServiceType.setText(typeName);
            if (mServiceId != null) getMvpPresenter().getServiceInfo(mServiceId);
        }

    }

    private void initView() {
        mBinding.titleView.setLeftClickListener(view -> {
            finish();
        });
        InputUtils.setPriceMode(mBinding.etPric,2);
        initPop();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:         //保存
                goSave();
                break;
            case R.id.rl_service_time: //服务时间
                if (mTempPosition != -1) {
                    for (int i = 0; i < mTimes.size(); i++) {
                        ServiceSettingBean serviceSettingBean = mTimes.get(i);
                        if (mTempPosition == i) {
                            serviceSettingBean.setChecked(true);
                        } else {
                            serviceSettingBean.setChecked(false);
                        }
                    }
                    mTimeAdapter.setDatas(mTimes, true);
                }
                mTv_determine.setVisibility(View.GONE);
                next = false;
                mPopupUtil.showAtLocation(mBinding.btnSave, Gravity.BOTTOM, 0, 0);
                break;
        }
    }

    private void goSave() {
        SaveServerRequestBody.Builder mServerRequestBody = new SaveServerRequestBody.Builder();
        mServerRequestBody.categoryId(mCategoryId);//项目ID
        mServerRequestBody.costprice(mBinding.etPric.getText().toString().trim());//服务原价
        mServerRequestBody.decription("无");//描述
        String duration = mBinding.tvServiceTime.getText().toString();
        if (!duration.equals("请选择所需时间")) {
            float f = Float.valueOf(mBinding.tvServiceTime.getText().toString().replace("小时", "")) * 60;
            duration = String.valueOf((int) f);
        } else {
            ToastUtils.shortToast("请选择所需时间");
            return;
        }
        mServerRequestBody.duration(duration);//时间
        if (duration.equals("30")) {//半小时的服务
            locktime = "0.5";
        } else if (duration.equals("60")) {//一小时的服务
            locktime = "0.5,1.0";
        }
        mServerRequestBody.locktime(locktime);//锁定时间
        mServerRequestBody.isoption("0");//是否有选项
        mServerRequestBody.packageType(Constants.PACKAGE_TYPE_1);//套餐类型1单项2多项
        mServerRequestBody.serviceType(Constants.SERVICE_TYPE_1);//服务类型1.无选项服务,2有选项服务,3单项套餐,4多项套餐
        mServerRequestBody.servicename(mBinding.tvServiceType.getText().toString());//服务名称
        mServerRequestBody.stylistId(AccountManager.getInstance().getStylistId());//美发师ID
        mServerRequestBody.price(mBinding.etPric.getText().toString().trim());//套餐价格
        if (mServiceId != null) mServerRequestBody.serviceId(mServiceId);
        getMvpPresenter().save(mServerRequestBody.build(), this);
    }

    private SetServiceTimeAdapter mTimeAdapter;
    private int mTempPosition = -1;
    private boolean next = false;
    private PopupUtil mPopupUtil;
    private ArrayList<ServiceSettingBean> mTempTimes;
    private ArrayList<ServiceSettingBean> mTimes;

    private void initPop() {
        mTempTimes = new ArrayList<>();
        mTimes = initTimes();
        mPopupUtil = PopupUtil.create()
                .setContext(this)
                .setContentView(R.layout.popwin_service_time_layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.TimePopupWindowAnimation)
                .setOnViewListener(new PopupUtil.OnViewListener() {
                    @Override
                    public void initViews(View view, PopupUtil basePopup) {
                        Button bt_ok = view.findViewById(R.id.bt_ok);
                        mTv_determine = view.findViewById(R.id.tv_determine);
                        bt_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (next) {
                                    setLocktime(mTimeAdapter.getDatas());
                                    mPopupUtil.dismiss();
                                    next = false;
                                    mTv_determine.setVisibility(View.GONE);
                                } else {
                                    if (mTempPosition == -1) {
                                        ToastUtils.shortToast("请选择服务所需要时间");
                                    } else if (mTempPosition > 1) {
                                        mBinding.tvServiceTime.setText(mTimeAdapter.getDatas().get(mTempPosition).getLabel());
                                        mTempTimes.get(0).setChecked(true);
                                        mTempTimes.get(0).setEnabled(false);
                                        mTempTimes.get(mTempTimes.size() - 1).setChecked(true);
                                        mTempTimes.get(mTempTimes.size() - 1).setEnabled(false);
                                        mTv_determine.setVisibility(View.VISIBLE);
                                        next = true;
                                        setLocktime(mTempTimes);
                                        mTimeAdapter.setDatas(mTempTimes, true);
                                    } else {
                                        mBinding.tvServiceTime.setText(mTimeAdapter.getDatas().get(mTempPosition).getLabel());
                                        mTv_determine.setVisibility(View.GONE);
                                        mPopupUtil.dismiss();
                                    }
                                }

                            }
                        });
                        RecyclerView recyclerView = view.findViewById(R.id.rv_time);
                        recyclerView.setLayoutManager(new GridLayoutManager(ServiceSettingActivity.this, 4));
                        recyclerView.addItemDecoration(new GridSpacingItemDecoration(4, 50, false));
                        mTimeAdapter = new SetServiceTimeAdapter(ServiceSettingActivity.this);
                        recyclerView.setAdapter(mTimeAdapter);
                        mTimeAdapter.setDatas(mTimes, true);
                        mTimeAdapter.setItemListener(new BaseRecycleViewAdapter.RecycleViewItemListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                if (next) {
                                    ServiceSettingBean serviceSettingBean = mTimeAdapter.getDatas().get(position);
                                    serviceSettingBean.setChecked(!serviceSettingBean.isChecked());
                                    mTimeAdapter.notifyDataSetChanged();

                                } else {
                                    mTempPosition = position;
                                    mTempTimes.clear();
                                    for (int i = 0; i < mTimeAdapter.getDatas().size(); i++) {
                                        mTimeAdapter.getDatas().get(i).setChecked(false);
                                        mTimeAdapter.getDatas().get(i).setEnabled(true);
                                        //构造数据只能单选
                                        if (i <= mTempPosition) {
                                            mTempTimes.add(mTimes.get(i));
                                        }
                                    }
                                    mTimeAdapter.getDatas().get(position).setChecked(!mTimeAdapter.getDatas().get(position).isChecked());
                                    mTimeAdapter.notifyDataSetChanged();
                                }

                            }

                            @Override
                            public void OnItemLongClickListener(View view, int position) {

                            }
                        });
                    }
                })
                .setFocusAndOutsideEnable(true)
                .setBackgroundDimEnable(true)
                .setDimValue(0.4f)
                .apply();
    }

    public void setLocktime(ArrayList<ServiceSettingBean> serviceSettingBeans) {
        locktime = "";
        for (ServiceSettingBean data : serviceSettingBeans) {
            if (data.isChecked()) {
                locktime += String.valueOf(data.getLabel().replace("小时", "")) + ",";
            }
        }
        locktime = locktime.substring(0, locktime.length() - 1);
    }

    private ArrayList<ServiceSettingBean> initTimes() {
        ArrayList<ServiceSettingBean> mTimes = new ArrayList<>();
        double time = 0.5;
        for (int i = 0; i < 12; i++) {
            mTimes.add(new ServiceSettingBean(time + "小时", false, true));
            time += 0.5;
        }
        return mTimes;
    }

    //非EditText隐藏键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (mImm.isActive()) {
                if (mImm != null) {
                    mImm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onSuccess(ServerInfoBean serverInfoBean) {
        mBinding.tvServiceTime.setText((Double.parseDouble(serverInfoBean.getDuration()) / 60) + "小时");
        mBinding.etPric.setText(serverInfoBean.getPrice());
        locktime = serverInfoBean.getLocktime();
        mCategoryId = serverInfoBean.getCategoryId();
        mServiceId = serverInfoBean.getServiceId();
    }

    @Override
    public void saveSuccess() {
        finish();
    }

    @Override
    public void getServerTypeSuccess(ServerTypeBean serverTypeBeans) {

    }

    @Override
    public void onUploadFileSuccess(String s) {

    }


}
