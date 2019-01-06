package com.yl.technician.module.home.service.setting.two;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.recycleview.GridSpacingItemDecoration;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityServiceSettingTwoBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.ServerInfoBean;
import com.yl.technician.model.vo.bean.ServerTypeBean;
import com.yl.technician.model.vo.bean.ServiceSettingBean;
import com.yl.technician.model.vo.requestbody.SaveServerRequestBody;
import com.yl.technician.module.certification.CertificationActivity;
import com.yl.technician.module.home.service.setting.ServiceSettingPresenter;
import com.yl.technician.module.home.service.setting.ServiceSettingView;
import com.yl.technician.util.FilePathUtil;
import com.yl.technician.util.InputUtils;
import com.yl.technician.util.PhoneUtil;
import com.yl.technician.util.compressutil.CompressPicUtil;
import com.yl.technician.util.compressutil.OnCompressListener;
import com.yl.technician.widget.bottomview.BottomViewFactory;
import com.yl.technician.widget.bottomview.SelectPhotoView;
import com.yl.technician.widget.bottomview.base.BaseBottomView;
import com.yl.technician.widget.popwindow.PopupUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/*
服务设置第二种页面
 * Create by lvlong on  2018/10/24
 */
@CreatePresenter(ServiceSettingPresenter.class)
public class ServiceSettingTwoActivity extends BaseMvpAppCompatActivity<ServiceSettingView, ServiceSettingPresenter> implements ServiceSettingView, ClickHandler {

    private ActivityServiceSettingTwoBinding mBinding;
    private ServiceSettingTwoAdapter mAdapter;
    private int mTempPosition = -1;
    private boolean next = false;
    private PopupUtil mPopupUtil;
    private ArrayList<ServiceSettingBean> mTempTimes;
    private ArrayList<ServiceSettingBean> mTimes;
    private String mCategoryId;
    private String locktime = "";
    private String mServiceId;
    private String mPicture;
    private ServerInfoBean serverInfoBean;
    private BaseBottomView mBaseBottomView;
    private TextView mTv_determine;
    private InputMethodManager mImm;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_service_setting_two;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityServiceSettingTwoBinding) viewDataBinding;
        mBinding.setClick(this);
        //键盘管理
        mImm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        initView();
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String typeName = bundle.getString("typeName");
            mServiceId = bundle.getString("serviceId");
            mCategoryId = bundle.getString("categoryId");
            DLog.d("serviceId=" + mServiceId);
            mBinding.tvServiceType.setText(typeName);
            if (mServiceId != null) {
                getMvpPresenter().getServiceInfo(mServiceId);
            } else {
                if (mCategoryId != null) getMvpPresenter().getCategoryById(mCategoryId);
            }
        }

    }

    private void initView() {
        mBinding.titleView.setLeftClickListener(v -> finish());
        InputUtils.setPriceMode(mBinding.etPrice,2);
        RecyclerView recyclerView = mBinding.rcvServiceSetting;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ServiceSettingTwoAdapter(this);
        recyclerView.setAdapter(mAdapter);
        mTempTimes = new ArrayList<>();
        mTimes = initTimes();
        initPop();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_save: //保存

                goSave();

                break;

            case R.id.rl_service_time:  //选择时间
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
                mPopupUtil.showAtLocation(mBinding.btnSave, Gravity.BOTTOM, 0, 0);
                break;

            case R.id.iv_up_master_map: //上传图片

                if (mBaseBottomView == null) {
                    mBaseBottomView = BottomViewFactory.create(this, BottomViewFactory.Type.Avatar);
                }
                mBaseBottomView.showBottomView(true);
                break;
        }

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

    private void goSave() {
        if (TextUtils.isEmpty(mPicture)) {
            ToastUtils.shortToast("请选择主图");
            return;
        }
        String checkValue="";
        SaveServerRequestBody.Builder builder = new SaveServerRequestBody.Builder();
        ArrayList<SaveServerRequestBody.ServiceOptionsBean> serviceOptionsBeans = new ArrayList<>();
        for (ServiceSubTypeAdapter serviceSubTypeAdapter : mAdapter.getServiceSubTypeAdapters()) {
            for (SaveServerRequestBody.ServiceOptionsBean serviceOptionsBean:serviceSubTypeAdapter.getDatas()) {
                if (TextUtils.isEmpty(serviceOptionsBean.getOptionvalue())){
                    checkValue=serviceOptionsBean.getOptiontitle()+serviceOptionsBean.getOptionname()+"不可为空";
                    ToastUtils.shortToast(checkValue);
                    return;
                }
                if (TextUtils.isEmpty(serviceOptionsBean.getPrice())){
                    checkValue=serviceOptionsBean.getOptiontitle()+"价格不可为空";
                    ToastUtils.shortToast(checkValue);
                    return;
                }
                if (Float.valueOf(serviceOptionsBean.getPrice())<=0){
                    checkValue=serviceOptionsBean.getOptiontitle()+"价格不可为0";
                    ToastUtils.shortToast(checkValue);
                    return;
                }
            }
            serviceOptionsBeans.addAll(serviceSubTypeAdapter.getDatas());
        }
        if (serviceOptionsBeans.size()==0){
            ToastUtils.shortToast("请"+mAdapter.getOptionbutton());
            return;
        }
        builder.serviceOptions(serviceOptionsBeans);
        builder.servicename(mBinding.tvServiceType.getText().toString());//服务名称
        builder.costprice(mBinding.etPrice.getText().toString().trim());//服务原价
        builder.decription(mBinding.etIntroduce.getText().toString().trim());//描述
        builder.price(mBinding.etPrice.getText().toString().trim());//套餐价格
        String duration = mBinding.tvServiceTime.getText().toString();
        if (!duration.equals("请选择所需时间")) {
            float f = Float.valueOf(mBinding.tvServiceTime.getText().toString().replace("小时", "")) * 60;
            duration = String.valueOf((int) f);
        } else {
            ToastUtils.shortToast("请选择所需时间");
            return;
        }
        builder.duration(duration);//时间
        if (duration.equals("30")) {//半小时的服务
            locktime = "0.5";
        } else if (duration.equals("60")) {//一小时的服务
            locktime = "0.5,1.0";
        }
        builder.locktime(locktime);//锁定时间
        builder.categoryId(mCategoryId);//主图
        builder.picture(mPicture);//主图
        builder.isoption(serviceOptionsBeans.size() == 0 ? "1" : "2");//是否有选项
        builder.serviceType(serviceOptionsBeans.size() == 0 ? Constants.SERVICE_TYPE_1 : Constants.SERVICE_TYPE_2);//服务类型1.无选项服务,2有选项服务,3单项套餐,4多项套餐
        builder.stylistId(AccountManager.getInstance().getStylistId());//美发师ID
        if (mServiceId != null) builder.serviceId(mServiceId);//服务ID
        getMvpPresenter().save(builder.build(), this);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    private SetServiceTimeAdapter mTimeAdapter;

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
                        recyclerView.setLayoutManager(new GridLayoutManager(ServiceSettingTwoActivity.this, 4));
                        recyclerView.addItemDecoration(new GridSpacingItemDecoration(4, 50, false));
                        mTimeAdapter = new SetServiceTimeAdapter(ServiceSettingTwoActivity.this);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            String filePath = null;
            switch (requestCode) {
                case PhoneUtil.REQUESTCODE_SYS_CAMERA: // 相机
                    if (mBaseBottomView instanceof SelectPhotoView) {
                        filePath = ((SelectPhotoView) mBaseBottomView).getImagePath();
                    }
                    break;
                case PhoneUtil.REQUESTCODE_SYS_PICK_IMAGE: // 图库
                    if (data == null) return;
                    filePath = FilePathUtil.getPath(data.getData());
                    break;
            }
            compressPicAndUpload(filePath);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 压缩后上传
     *
     * @param filePath
     */
    private void compressPicAndUpload(String filePath) {
        CompressPicUtil.with()
                .load(filePath)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // 压缩成功后调用，返回压缩后的图片文件
                        getMvpPresenter().uploadImage(ServiceSettingTwoActivity.this, file.getPath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 当压缩过程出现问题时调用 上传原图
                        getMvpPresenter().uploadImage(ServiceSettingTwoActivity.this, filePath);
                    }
                }).launch();
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

    @Override
    public void onSuccess(ServerInfoBean serverInfoBean) {
        this.serverInfoBean = serverInfoBean;
        mBinding.ivUpMasterMap.setBackground(null);
        ImageLoader.loadImage(mBinding.ivUpMasterMap,serverInfoBean.getPicture());
        mBinding.tvServiceTime.setText((Float.parseFloat(serverInfoBean.getDuration()) / 60) + "小时");
        mBinding.etPrice.setText(serverInfoBean.getPrice());
        mBinding.etIntroduce.setText(serverInfoBean.getDecription());
        locktime = serverInfoBean.getLocktime();
        mCategoryId = serverInfoBean.getCategoryId();
        mServiceId = serverInfoBean.getServiceId();
        mPicture = serverInfoBean.getPicture();
        if (mCategoryId != null) getMvpPresenter().getCategoryById(mCategoryId);
    }

    @Override
    public void saveSuccess() {
        finish();
    }

    @Override
    public void getServerTypeSuccess(ServerTypeBean serverTypeBeans) {
        if (serverInfoBean != null) {
            mAdapter.setSubAdpterData((ArrayList<SaveServerRequestBody.ServiceOptionsBean>) serverInfoBean.getServiceOptions());
        }
        mAdapter.getDatas().addAll(serverTypeBeans.getOptions());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUploadFileSuccess(String s) {
        ImageLoader.loadImage(mBinding.ivUpMasterMap, s);
        mBinding.ivUpMasterMap.setBackground(null);
        mPicture = s;
    }

}
