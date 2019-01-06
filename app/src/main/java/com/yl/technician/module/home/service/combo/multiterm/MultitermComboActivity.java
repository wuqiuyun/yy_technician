package com.yl.technician.module.home.service.combo.multiterm;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.yl.technician.databinding.ActivityMultitermComboBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.ServerInfoBean;
import com.yl.technician.model.vo.bean.ServerTypeBean;
import com.yl.technician.model.vo.bean.ServiceSettingBean;
import com.yl.technician.model.vo.requestbody.SaveServerRequestBody;
import com.yl.technician.module.home.service.setting.two.SetServiceTimeAdapter;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.util.InputUtils;
import com.yl.technician.widget.SwipeRecyclerView;
import com.yl.technician.widget.popwindow.PopupUtil;

import java.util.ArrayList;


/*
    多项套餐
 * Create by lvlong on  2018/10/24
 */
@CreatePresenter(MultitermComboPresenter.class)
public class MultitermComboActivity extends BaseMvpAppCompatActivity<MultitermComboView, MultitermComboPresenter> implements MultitermComboView , ClickHandler {

    private ActivityMultitermComboBinding mBinding;
    private MultitermComboAdapter mAdapter;
    private ArrayList<ServerTypeBean> mServerTypeBeans;
    private ServerInfoBean serverInfoBean;
    private ArrayList<SaveServerRequestBody.PackageOptionsBean> mPackageOptionsBeans;
    private String locktime="";
    private String mServiceId;
    private TextView mTv_determine;
    private InputMethodManager imm;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_multiterm_combo;
    }

    @Override
    protected void init() {
         imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mBinding = (ActivityMultitermComboBinding) viewDataBinding;
        mBinding.setClick(this);
        initView();
        initData();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        mServerTypeBeans= new ArrayList<>();
        mBinding.titleView.setLeftClickListener(v -> finish());
        InputUtils.setPriceMode(mBinding.etComboPrice,2);
        InputUtils.setPriceMode(mBinding.etOriginalPrice,2);
        SwipeRecyclerView recyclerView = mBinding.rvServerType;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MultitermComboAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setRightClickListener(new SwipeRecyclerView.OnRightClickListener() {
            @Override
            public void onRightClick(int position, String id) {
                mAdapter.getDatas().remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        mPackageOptionsBeans = new ArrayList<>();
        mAdapter.setDatas(mPackageOptionsBeans,true);
        initPop();
    }
    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            mServiceId = bundle.getString("serviceId");
        }
        if (mServiceId !=null){
            getMvpPresenter().getServiceInfo(mServiceId);
        }else {
            getMvpPresenter().getServerType(Constants.PACKAGE_TYPE_2);
        }
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.rl__time:     //选择时间
                if (mTempPosition!=-1){
                    for (int i = 0; i < mTimes.size(); i++) {
                        ServiceSettingBean serviceSettingBean = mTimes.get(i);
                        if (mTempPosition==i){
                            serviceSettingBean.setChecked(true);
                        }else {
                            serviceSettingBean.setChecked(false);
                        }
                    }
                    mTimeAdapter.setDatas(mTimes,true);
                }
                mTv_determine.setVisibility(View.GONE);
                next=false;
                mPopupUtil.showAtLocation(mBinding.btnSave, Gravity.BOTTOM,0,0);
                break;

            case R.id.tv_add_service:   //添加服务项目

                mAdapter.getDatas().add(new SaveServerRequestBody.PackageOptionsBean());
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_save:     //保存
               goSave();
                break;
        }

    }
    //非EditText隐藏键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (imm.isActive()) {
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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
    private String optiontitle;
    private void goSave() {
        optiontitle="";
        if (mAdapter.getDatas().size()!=0){
            SaveServerRequestBody.Builder builder = new SaveServerRequestBody.Builder();
            ArrayList<SaveServerRequestBody.PackageOptionsBean> commitBody = new ArrayList<>();
            for (SaveServerRequestBody.PackageOptionsBean optionsBean:mAdapter.getDatas()){
                SaveServerRequestBody.PackageOptionsBean optionsBean2=new SaveServerRequestBody.PackageOptionsBean();
                if (TextUtils.isEmpty(optiontitle)&&!optionsBean.isOptionv()){
                    optiontitle=optionsBean.getOptionname();
                }
                optionsBean2.setOptionvalue(optionsBean.getOptionvalue()==null?"":optionsBean.getOptionvalue());
                optionsBean2.setOptionname(optionsBean.getOptionname()==null?"":optionsBean.getOptionname());
                optionsBean2.setCategoryId(optionsBean.getCategoryId()==null?"":optionsBean.getCategoryId());
                optionsBean2.setOptiontitle(optionsBean.getOptiontitle()==null?"":optionsBean.getOptiontitle());
                optionsBean2.setOptionId(optionsBean.getOptionId()==null?"":optionsBean.getOptionId());
                optionsBean2.setPrice("0");
                if (optionsBean.getSpcId()!=null)optionsBean2.setSpcId(optionsBean.getSpcId());
                commitBody.add(optionsBean2);
            }
            if (!TextUtils.isEmpty(optiontitle)){
                showToast(optiontitle+"不可为空");
                return;
            }

            builder.packageOptions(commitBody);
            builder.servicename(mBinding.etComboName.getText().toString().trim());//服务名称
            builder.costprice(mBinding.etOriginalPrice.getText().toString().trim());//服务原价
            builder.price(mBinding.etComboPrice.getText().toString().trim());//套餐价格
            String duration = mBinding.tvServiceTime.getText().toString();
            if (!duration.equals("请选择所需时间")){
                float f = Float.valueOf(mBinding.tvServiceTime.getText().toString().replace("小时", ""))*60;
                duration=String.valueOf((int)f);
            }else {
                ToastUtils.shortToast("请选择所需时间");
                return;
            }
            builder.decription("无");//描述
            builder.duration(duration);//时间
            if (duration.equals("30")) {//半小时的服务
                locktime = "0.5";
            } else if (duration.equals("60")) {//一小时的服务
                locktime = "0.5,1.0";
            }
            builder.locktime(locktime);//锁定时间
            builder.isoption("1");//是否有选项
            builder.packageType(Constants.PACKAGE_TYPE_2);//套餐类型1单项2多项
            builder.serviceType(Constants.SERVICE_TYPE_4);//服务类型1.无选项服务,2有选项服务,3单项套餐,4多项套餐
            if (mServiceId!=null)builder.serviceId(mServiceId);//服务ID
            builder.stylistId(AccountManager.getInstance().getStylistId());//美发师ID
            getMvpPresenter().save(builder.build(),this);
        }else {
            ToastUtils.shortToast("请添加项目");
        }
    }

    private SetServiceTimeAdapter mTimeAdapter;
    private int mTempPosition=-1;
    private boolean next=false;
    private PopupUtil mPopupUtil;
    private ArrayList<ServiceSettingBean> mTempTimes;
    private ArrayList<ServiceSettingBean> mTimes;
    private void initPop() {
        mTempTimes = new ArrayList<>();
        mTimes = initTimes();
        mPopupUtil = PopupUtil.create()
                .setContext(this)
                .setContentView(R.layout.popwin_service_time_layout, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
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
                        recyclerView.setLayoutManager(new GridLayoutManager(MultitermComboActivity.this, 4));
                        recyclerView.addItemDecoration(new GridSpacingItemDecoration(4, 50, false));
                        mTimeAdapter = new SetServiceTimeAdapter(MultitermComboActivity.this);
                        recyclerView.setAdapter(mTimeAdapter);
                        mTimeAdapter.setDatas(mTimes,true);
                        mTimeAdapter.setItemListener(new BaseRecycleViewAdapter.RecycleViewItemListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                if (next){
                                    ServiceSettingBean serviceSettingBean = mTimeAdapter.getDatas().get(position);
                                    serviceSettingBean.setChecked(!serviceSettingBean.isChecked());
                                    mTimeAdapter.notifyDataSetChanged();

                                }else {
                                    mTempPosition=position;
                                    mTempTimes.clear();
                                    for (int i = 0; i < mTimeAdapter.getDatas().size(); i++) {
                                        mTimeAdapter.getDatas().get(i).setChecked(false);
                                        mTimeAdapter.getDatas().get(i).setEnabled(true);
                                        //构造数据只能单选
                                        if (i<=mTempPosition){
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
        ArrayList<ServiceSettingBean>  mTimes = new ArrayList<>();
        double time=0.5;
        for (int i = 0; i <12 ; i++) {
            mTimes.add(new ServiceSettingBean(time+"小时", false,true));
            time+=0.5;
        }
        return mTimes;
    }
    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onSuccess(ServerInfoBean serverInfoBean) {
        this.serverInfoBean=serverInfoBean;

        mBinding.etComboName.setText(FormatUtil.Formatstring(serverInfoBean.getServicename()));
        mBinding.etOriginalPrice.setText(FormatUtil.Formatstring(serverInfoBean.getCostprice()));
        mBinding.etComboPrice.setText(FormatUtil.Formatstring(serverInfoBean.getPrice()));
        mBinding.tvServiceTime.setText((Float.parseFloat(serverInfoBean.getDuration())/60)+"小时");
        locktime=serverInfoBean.getLocktime();
        mServiceId=serverInfoBean.getServiceId();
        getMvpPresenter().getServerType(Constants.PACKAGE_TYPE_2);
    }

    @Override
    public void getServerType(ArrayList<ServerTypeBean> serverTypeBeans) {
        mAdapter.initSpData(serverTypeBeans);
        if (serverInfoBean!=null){
                for (SaveServerRequestBody.PackageOptionsBean optionsBean:serverInfoBean.getPackageOptions()) {
                    for (int i = 0; i < serverTypeBeans.size(); i++) {
                        if (optionsBean.getCategoryId().equals(String.valueOf(serverTypeBeans.get(i).getId()))){
                            for (SaveServerRequestBody.PackageOptionsBean.PackageOptionDetails packageOptionDetails :optionsBean.getPackageOptionDetails()) {
                                DLog.d(packageOptionDetails.getSpcId());
                                SaveServerRequestBody.PackageOptionsBean optionsBean2 = new SaveServerRequestBody.PackageOptionsBean();
                                optionsBean2.setPosition(i);
                                optionsBean2.setSpcId(packageOptionDetails.getSpcId());
                                optionsBean2.setOptionvalue(packageOptionDetails.getOptionvalue());
                                optionsBean2.setOptionv(true);
                                mAdapter.getDatas().add(optionsBean2);
                            }
                            break;
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void saveSuccess() {
        showToast("保存成功");
        finish();
    }

}
