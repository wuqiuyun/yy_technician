package com.yl.technician.module.home.service.combo;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.recycleview.GridSpacingItemDecoration;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivitySingleComboBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.ServerInfoBean;
import com.yl.technician.model.vo.bean.ServerTypeBean;
import com.yl.technician.model.vo.bean.ServiceSettingBean;
import com.yl.technician.model.vo.requestbody.SaveServerRequestBody;
import com.yl.technician.module.home.service.add.AddServiceActivity;
import com.yl.technician.module.home.service.setting.ServiceSettingAdapter;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.widget.dialog.YLDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;


/*
    单项套餐
 * Create by lvlong on  2018/10/24
 */
@CreatePresenter(SingleComboPresenter.class)
public class SingleComboActivity extends BaseMvpAppCompatActivity<SingleComboView, SingleComboPresenter> implements SingleComboView ,ClickHandler {

    private ActivitySingleComboBinding mBinding;
    private ServiceSettingAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<ServiceSettingBean> mData = new ArrayList<>();
    private ArrayList<ServerTypeBean> serverTypeBeans;
    private String mServiceId;
    private ServerInfoBean serverInfoBean;
    private String mStylistId;
    private int tempPosition=-1;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_single_combo;
    }

    @Override
    protected void init() {

        mBinding = (ActivitySingleComboBinding) viewDataBinding;
        mBinding.setClick(this);

        initView();
        initData();

    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        mBinding.titleView.setLeftClickListener(v -> finish());

        mRecyclerView = mBinding.rcvService;
        mAdapter = new ServiceSettingAdapter(this);
        mAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {

                String price = serverTypeBeans.get(position).getPrice();//价钱
                String number = mBinding.etUseNum.getText().toString().trim();//次数
                mBinding.tvPrice.setText(FormatUtil.Formatstring(calculatePrice(number,price)));
                for (ServiceSettingBean serviceSettingBean:mData) {
                    serviceSettingBean.setChecked(false);
                }
                tempPosition=position;
                mData.get(position).setChecked(!mData.get(position).isChecked());
                mAdapter.notifyDataSetChanged();
                
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4, 50, false));

        mBinding.etUseNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                if (tempPosition!=-1){
                    String price = serverTypeBeans.get(tempPosition).getPrice();//价钱
                    String number = editable.toString();//次数
                    mBinding.tvPrice.setText(FormatUtil.Formatstring(calculatePrice(number,price)));
                }

            }
        });


    }

    private String calculatePrice(String s,String s2){
        if (TextUtils.isEmpty(s)){
            return "0.0";
        }
        int i = Integer.valueOf(s);
        float f = Float.valueOf(s2);
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(i*f*0.88);
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        mStylistId = AccountManager.getInstance().getStylistId();
        if (bundle!=null){
            mServiceId = bundle.getString("serviceId");
        }

        if (mServiceId!=null){
            getMvpPresenter().getServiceInfo(mServiceId);
        }else {
            getMvpPresenter().getSinglePackage(mStylistId);
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_save:     //保存
                if (serverTypeBeans!=null){
                    ServerTypeBean serverTypeBean=null;
                    for (int i = 0; i < mAdapter.getDatas().size(); i++) {
                        if (mAdapter.getDatas().get(i).isChecked()){
                             serverTypeBean = serverTypeBeans.get(i);
                            break;
                        }
                    }

                    if (serverTypeBean==null){
                        ToastUtils.shortToast("请选择关联服务");
                        return;
                    }

                    if (TextUtils.isEmpty(mBinding.etComboName.getText().toString().trim())) {
                        ToastUtils.shortToast("请输入套餐名称");
                        return;
                    }
                    if (TextUtils.isEmpty(mBinding.etUseNum.getText().toString().trim())||Integer.valueOf(mBinding.etUseNum.getText().toString().trim())<10) {
                        ToastUtils.shortToast("使用次数不能少于10");
                        return;
                    }

                    String useNum = mBinding.etUseNum.getText().toString().trim();
                    String price = serverTypeBeans.get(tempPosition).getPrice();

                    SaveServerRequestBody.Builder builder = new SaveServerRequestBody.Builder();
                    builder.costprice(String.valueOf(Integer.valueOf(useNum)*Double.valueOf(price)));//项目ID
                    builder.categoryId(String.valueOf(serverTypeBean.getId()));//项目ID
                    builder.decription("无");//描述
                    builder.duration(serverTypeBean.getDuration());//时间
                    builder.isoption("1");//是否有选项
                    builder.packageType(Constants.PACKAGE_TYPE_1);//套餐类型1单项2多项
                    builder.serviceType(Constants.SERVICE_TYPE_3);//服务类型1.无选项服务,2有选项服务,3单项套餐,4多项套餐
                    builder.servicename(mBinding.etComboName.getText().toString().trim());//服务名称
                    builder.stylistId(AccountManager.getInstance().getStylistId());//美发师ID
                    builder.times(mBinding.etUseNum.getText().toString().trim());//使用次数
                    builder.price(mBinding.tvPrice.getText().toString().trim());//套餐价格
                    builder.locktime(serverTypeBean.getLocktime());//锁定时间
                    builder.serviceId(mServiceId);//锁定时间
                    getMvpPresenter().save(builder.build(),this);
                }else {
                    ToastUtils.shortToast("关联服务加载异常");
                }
                break;
        }

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onSuccess(ServerInfoBean serverInfoBean) {
        this.serverInfoBean=serverInfoBean;
        mBinding.etComboName.setText(FormatUtil.Formatstring(serverInfoBean.getServicename()));
        mBinding.tvPrice.setText(FormatUtil.Formatstring(serverInfoBean.getPrice()));
        mBinding.etUseNum.setText(FormatUtil.Formatstring(serverInfoBean.getTimes()));
        getMvpPresenter().getSinglePackage(mStylistId);
    }

    @Override
    public void getServerType(ArrayList<ServerTypeBean> serverTypeBeans) {
        if (serverTypeBeans.size()==0){
            new YLDialog.Builder(this)
                    .setType(YLDialog.DIALOG_TYPE_NORMAL)
                    .setMessage("添加提示")
                    .setSubMessage("您还未上架单项服务,请前往添加")
                    .setPositiveButton("确定", (dialog, which) -> {
                        dialog.dismiss();
                        AddServiceActivity.startActivity(this,AddServiceActivity.class);
                        finish();
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                        dialog.dismiss();
                        finish();
                    })
                    .create()
                    .show();
            return;
        }

        this.serverTypeBeans=serverTypeBeans;
        for (int i = 0; i < serverTypeBeans.size(); i++) {
            ServerTypeBean s = serverTypeBeans.get(i);
            if (serverInfoBean!=null&&serverInfoBean.getCategoryId().equals(String.valueOf(s.getId()))){
                mData.add(new ServiceSettingBean(s.getName(), true));
                tempPosition=i;
            }else {
                mData.add(new ServiceSettingBean(s.getName(), false));
            }
        }

        mAdapter.setDatas(mData, true);
    }

    @Override
    public void saveSuccess() {
        finish();
    }

}
