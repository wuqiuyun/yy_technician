package com.yl.technician.module.home.store;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.hyphenate.chat.EMMessage;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.amap.LocationPresenter;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.recycleview.SpaceItemHorizontalDecoration;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityStoreManagerBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.ChatAdapterItemTypeBean;
import com.yl.technician.model.vo.bean.CheckMsgBean;
import com.yl.technician.model.vo.bean.EventBean;
import com.yl.technician.model.vo.bean.NexuStatusBean;
import com.yl.technician.model.vo.bean.ReCodeBean;
import com.yl.technician.model.vo.bean.SelfDefinedInfoBean;
import com.yl.technician.model.vo.bean.StoreManageNexusStyScroolBean;
import com.yl.technician.model.vo.bean.StoreManageScopeBean;
import com.yl.technician.model.vo.bean.StoreManageScopeInfoBean;
import com.yl.technician.model.vo.result.CheckMsgResult;
import com.yl.technician.module.home.evaluation.EvaluationManagerActivity;
import com.yl.technician.module.home.join.StoreStylistActivity;
import com.yl.technician.module.im.chat.ChatActivity;
import com.yl.technician.module.im.sharetofriend.ShareToFriendActivity;
import com.yl.technician.module.mine.stylist.invitejoin.InviteJoinActivity;
import com.yl.technician.util.FormatKmUtil;
import com.yl.technician.util.PhoneUtil;
import com.yl.technician.util.ShareUtils;
import com.yl.technician.util.StringUtil;
import com.yl.technician.util.easyutils.EasyUtil;
import com.yl.technician.widget.banner.BannerConfig;
import com.yl.technician.widget.banner.listener.OnBannerListener;
import com.yl.technician.widget.banner.loader.ImageLoaders;
import com.yl.technician.widget.dialog.BaseEasyDialog;
import com.yl.technician.widget.dialog.EasyDialog;
import com.yl.technician.widget.dialog.ViewConvertListener;
import com.yl.technician.widget.dialog.ViewHolder;
import com.yl.technician.widget.dialog.YLDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 门店管理
 * <p>
 * Create by zm on 2018/10/12
 */
@CreatePresenter(StoreManagerPresenter.class)
public class StoreManagerActivity extends BaseMvpAppCompatActivity<IStoreManagerView, StoreManagerPresenter>
        implements IStoreManagerView, ClickHandler, AMapLocationListener, OnBannerListener {

    private int PLAY_TIME = 5000;

    public static final int SERVICESCOPE = 0x003;
    public static final int SERVICEADDRESS = 0x004;

    ActivityStoreManagerBinding mBinding;

    private String storeId;    //门店的id

    private String inviteCode = null;//邀请码

    private int isCollection;// 0 未收藏 1 已收藏

    private StylistAdapter mStylistAdapter;
    private ArrayList<StoreManageNexusStyScroolBean> mStylistBeans = new ArrayList<>();

    //服务范围list
    private ServiceScopeAdapter mServiceAdapter;
    private ArrayList<String> mScopeList = new ArrayList<>();
    private String lat, lnt;
    private LocationPresenter locationPresenter;
    private ImageLoaderConfig config;
    private NexuStatusBean nexuStatusBean;
    private boolean isFromChat = false;//是否来自聊天页面查看详情，做不同UI展示,默认否
    private SelfDefinedInfoBean selfDefinedInfoBean;//自定义消息bean对象  用于在同意或者拒绝时再次发送自定义消息给对方

    private StoreManageScopeInfoBean storeManageScopeInfoBean;
    private String msgId;//申请记录的id, 聊天页面带入

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_store_manager;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        locationPresenter = new LocationPresenter(this);
        locationPresenter.setMapLocationListener(this);
        new RxPermissions(this)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(grant -> {
                    if (grant) {
                        locationPresenter.startLocation(true);
                    } else {
                        ToastUtils.shortToast("定位权限未开启.");
                    }
                });
        getBundle();

        config = new ImageLoaderConfig.Builder().
                setCropType(ImageLoaderConfig.CENTER_CROP).
                setAsBitmap(true).
                setPlaceHolderResId(R.drawable.home_bg).
                setErrorResId(R.drawable.home_bg).
                setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE).
                setPrioriy(ImageLoaderConfig.LoadPriority.HIGH).build();
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            storeId = bundle.getString(Constants.STORE_ID);
            isFromChat = bundle.getBoolean("isFromChat", false);
            selfDefinedInfoBean = bundle.getParcelable(Constants.IM_SELF_BEAN);
            if (selfDefinedInfoBean!=null) {
                msgId = selfDefinedInfoBean.getEnterRecordID();
            }
            initView();
            loadData();
            getMvpPresenter().findReCode();
        } else {
            showToast("数据获取失败");
            finish();
        }

    }

    private void initView() {
        mBinding = (ActivityStoreManagerBinding) viewDataBinding;
        mBinding.setClick(this);

        mBinding.ratingBar.setEnabled(false);
        mBinding.mrScore.setEnabled(false);
        // titleview
        mBinding.titleView.setLeftClickListener(v -> finish());
        mBinding.titleView.setRightImgClickListener(v -> {
            // 分享
            new Thread() {
                public void run() {
                    showShareDialog();
                }
            }.start();
        });
        //进入他人的门店详情，则增加收藏按钮，隐藏编辑区域
        mBinding.titleView.setSubRightIcon(getResources().getDrawable(R.drawable.icon_collection_nor));
        mBinding.titleView.setSubRightImgClickListener(v -> {
            // 收藏
            getMvpPresenter().updateCollectionType(String.valueOf(isCollection), storeId, AccountManager.getInstance().getUserId(), StoreManagerActivity.this);
        });

        // stylist list
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 横向滚动
        mBinding.stylistList.setLayoutManager(linearLayoutManager);
        mBinding.stylistList.addItemDecoration(new SpaceItemHorizontalDecoration(20));
        mStylistAdapter = new StylistAdapter(getBaseContext());
        mBinding.stylistList.setAdapter(mStylistAdapter);
        // service scope list
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getBaseContext());
        LayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 横向滚动
        mBinding.serviceScopeList.setLayoutManager(LayoutManager);
        mBinding.serviceScopeList.addItemDecoration(new SpaceItemHorizontalDecoration(30));
        mServiceAdapter = new ServiceScopeAdapter(getBaseContext());
        mBinding.serviceScopeList.setAdapter(mServiceAdapter);
    }

    private void loadData() {
        getMvpPresenter().getStoreScore(storeId);
        getMvpPresenter().getNexuStatus(storeId, this);
        getMvpPresenter().getNexusStyScrool(3, storeId);//入驻/签约美发师
        getMvpPresenter().getStoreServerScope(storeId);//门店范围
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationPresenter != null) {
            locationPresenter.stopLocation();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_look_more: // 查看更多
                Bundle bundle = new Bundle();
                bundle.putString(Constants.STORE_ID, storeId);
                StoreStylistActivity.startActivity(getBaseContext(), StoreStylistActivity.class, bundle);
                break;
            case R.id.tv_look_comment: // 查看全部评价
                Bundle EvaluationBundle = new Bundle();
                EvaluationBundle.putInt(Constants.EVALUATION_TYPE, 3);
                EvaluationBundle.putString(Constants.STORE_ID, storeId);
                EvaluationManagerActivity.startActivity(getBaseContext(), EvaluationManagerActivity.class, EvaluationBundle);
                break;
            case R.id.tv_reservation: // 解约/入驻
                if (nexuStatusBean == null) return;
                switch (nexuStatusBean.getApplyStatus()) {
                    case "0":
                        //前往申请入驻
                        SelfDefinedInfoBean chatNoFriendBeanInv = new SelfDefinedInfoBean();
                        chatNoFriendBeanInv.setImusername(nexuStatusBean.getStoreIMName());
                        chatNoFriendBeanInv.setNickname(nexuStatusBean.getStoreNickname());
                        chatNoFriendBeanInv.setPath(nexuStatusBean.getStoreHeadImg());
                        if (!TextUtils.isEmpty(nexuStatusBean.getStoreUserId())) {
                            chatNoFriendBeanInv.setUserId(Long.parseLong(nexuStatusBean.getStoreUserId()));
                        }

                        Intent intent = new Intent(this, InviteJoinActivity.class);

                        intent.putExtra(Constants.IM_SELF_BEAN, chatNoFriendBeanInv);
                        intent.putExtra("storeid", nexuStatusBean.getStoreId() + "");
                        startActivity(intent);
                        break;
                    case "2":
                        //解约
                        String etTextStr = mBinding.tvReservation.getText().toString();
                        if (!TextUtils.isEmpty(etTextStr) && ("已同意".equals(etTextStr) || "已拒绝".equals(etTextStr))){
                            return;
                        } else {
                            showRelieveDialog();
                        }
                        break;
                    default:
                        break;
                }
                break;
            case R.id.tv_advisory: // 咨询
                /**
                 *      * @param context
                 * @param mChatId 环信id
                 * @param userid  用户id
                 * @param name    昵称
                 * @param path    头像路径
                 */
                ChatActivity.startFromZIxunActivity(this, nexuStatusBean.getStoreIMName(), nexuStatusBean.getStoreId() + "", nexuStatusBean.getStoreNickname(), nexuStatusBean.getStoreHeadImg());
                break;
            case R.id.tv_call: //电话
                if (nexuStatusBean != null && !TextUtils.isEmpty(nexuStatusBean.getMobile())) {
                    PhoneUtil.toCallPhone(StoreManagerActivity.this, nexuStatusBean.getMobile());
                }
                break;

            case R.id.btn_comment_refuse://门店用户ID  美发师用户ID   签约类型0入驻，1签约
                getMvpPresenter().nexus(null, nexuStatusBean.getStoreUserId(), AccountManager.getInstance().getUserId(), false, msgId, this);
                break;
            case R.id.btn_comment_agree:
                //111.邀请平台美发师加入 112.邀请门店美发师加入
                // 113平台美发师加入申请 114门店美发师加入申请 只有这种时候 门店端才能点击操作
                // 0 入驻(平台) 1 签约(门店)
                int nex = 0;
                if (selfDefinedInfoBean.getDefinedMsgType() == 111) {
                    nex = 0;
                } else if (selfDefinedInfoBean.getDefinedMsgType() == 112) {
                    nex = 1;
                }
                getMvpPresenter().nexus(nex + "", nexuStatusBean.getStoreId() + "", AccountManager.getInstance().getStylistId(), true, msgId, this);
                break;
        }
    }

    private void showRelieveDialog() {
        new YLDialog.Builder(this)
                .setType(YLDialog.DIALOG_TYPE_NORMAL)
                .setMessage("是否确定解约?")
                .setPositiveButton("确定", (dialog, which) -> {
                    getMvpPresenter().relieve(AccountManager.getInstance().getStylistId(), storeId, this);
                    dialog.dismiss();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    @Override
    public void nexusSuccess(boolean isAgree) {
        setResult(Constants.RESULT_REFRESH_CODE);
        if (isAgree) {
            selfDefinedInfoBean.setMsgStatus(2);//1为发送人发起加价状态 2为已回复 同意  3.为已回复 拒绝的状态
            ToastUtils.shortToast("已同意");
            mBinding.tvReservation.setEnabled(false);
        } else {
            selfDefinedInfoBean.setMsgStatus(3);//1为发送人发起加价状态 2为已回复 同意  3.为已回复 拒绝的状态
            ToastUtils.shortToast("已拒绝");
            mBinding.tvReservation.setEnabled(false);
        }

        loadData();
        EMMessage messageIv = null;
        String currUsername = EasyUtil.getEmManager().getCurrentUser();//获取环信本地登录账号id
        if (!selfDefinedInfoBean.getImusername().equals(currUsername)) {
            selfDefinedInfoBean.setImusername(AccountManager.getInstance().getAccount().getImusername());
            selfDefinedInfoBean.setPath(AccountManager.getInstance().getAccount().getHeadImg());
            selfDefinedInfoBean.setNickname(AccountManager.getInstance().getAccount().getNickname());
            String usid = AccountManager.getInstance().getUserId();
            if (!TextUtils.isEmpty(usid)) {
                selfDefinedInfoBean.setUserId(Long.parseLong(usid));
            }

            if (nexuStatusBean != null) {
                selfDefinedInfoBean.setRecvImusername(nexuStatusBean.getStoreIMName());
                if (!TextUtils.isEmpty(nexuStatusBean.getStoreUserId())) {
                    selfDefinedInfoBean.setRecvUserId(Long.parseLong(nexuStatusBean.getStoreUserId()));
                }
                selfDefinedInfoBean.setRecvPath(nexuStatusBean.getStoreHeadImg());
                selfDefinedInfoBean.setRecvNickname(nexuStatusBean.getStoreNickname());
            }
            messageIv = EasyUtil.getEmManager().sendOrderAddMoneyMes(selfDefinedInfoBean, selfDefinedInfoBean.getRecvImusername());
        } else {
            if (nexuStatusBean != null) {
                messageIv = EasyUtil.getEmManager().sendOrderAddMoneyMes(selfDefinedInfoBean, nexuStatusBean.getStoreIMName());
            }
        }


        EventBus.getDefault().post(new EventBean.InviteMsgUpdate(0, selfDefinedInfoBean, messageIv, ChatAdapterItemTypeBean.CHAT_SEND_SHARE_MSG));
    }

    @Override
    public void findReCodeSuc(ReCodeBean recode) {
        inviteCode = recode.getInvitecode();
    }

    @Override
    public void getStoreScoreSucceed(StoreManageScopeBean storeManageScopeBean) {
        if (storeManageScopeBean != null) {
            double envi = storeManageScopeBean.getEnvironmentScore();
            double service = storeManageScopeBean.getServerScore();
            double pareenvi = storeManageScopeBean.getPareEnvirtAvg();
            double pareservice = storeManageScopeBean.getPareServerAvg();

            mBinding.mrScore.setRating((float) storeManageScopeBean.getScore());
            mBinding.tvLookComment.setText((String.format(getString(R.string.comment_look_all), String.valueOf(storeManageScopeBean.getScoretimes()))));
            mBinding.tvSkillScore.setText((String.valueOf(envi)));
            mBinding.tvServiceScore.setText((String.valueOf(service)));
            // 环境水平
            if (pareenvi == 10) {
                mBinding.tvSkillGrade.setVisibility(View.GONE);
            } else {
                mBinding.tvSkillGrade.setVisibility(View.VISIBLE);
            }
            if (pareenvi < 0) {
                mBinding.tvSkillGrade.setText("低于平均水平");
            } else if (pareenvi == 0) {
                mBinding.tvSkillGrade.setText("等于平均水平");
            } else if (pareenvi > 0) {
                mBinding.tvSkillGrade.setText("高于平均水平");
            }

            // 服务水平
            if (pareservice == 10) {
                mBinding.tvServiceGrade.setVisibility(View.GONE);
            } else {
                mBinding.tvServiceGrade.setVisibility(View.VISIBLE);
            }
            if (pareservice < 0) {
                mBinding.tvServiceGrade.setText("低于平均水平");
            } else if (pareservice == 0) {
                mBinding.tvServiceGrade.setText("等于平均水平");
            } else if (pareservice > 0) {
                mBinding.tvServiceGrade.setText("高于平均水平");
            }
        }
    }

    @Override
    public void getStoreServerScoreSucceed(StoreManageScopeBean storeManageScopeBean) {
        if (storeManageScopeBean != null) {
            if (storeManageScopeBean.getCatergoryNames() != null && storeManageScopeBean.getCatergoryNames().size() > 0) {
                mScopeList.clear();
                mScopeList.addAll(storeManageScopeBean.getCatergoryNames());
                mServiceAdapter.setDatas(mScopeList, true);
            }

        }
    }

    @Override
    public void getNexusStyScroolSucceed(ArrayList<StoreManageNexusStyScroolBean> storeManageNexusStyScroolBean) {
        if (storeManageNexusStyScroolBean != null && storeManageNexusStyScroolBean.size() > 0) {
            mStylistBeans.clear();
            mStylistBeans.addAll(storeManageNexusStyScroolBean);
            mStylistAdapter.setDatas(mStylistBeans, true);
        }
    }

    @Override
    public void getStoreInfoSucceed(StoreManageScopeInfoBean storeManageScopeInfoBean) {
        if (storeManageScopeInfoBean != null) {
            this.storeManageScopeInfoBean = storeManageScopeInfoBean;
            mBinding.ratingBar.setRating((float) storeManageScopeInfoBean.getGrade());
            mBinding.tvRatingNum.setText(storeManageScopeInfoBean.getGrade() + "分");
            mBinding.tvStoreName.setText(storeManageScopeInfoBean.getStoreName());
            mBinding.tvAddressArea.setText(storeManageScopeInfoBean.getLocation());
            mBinding.tvLocation.setText("(距您" + FormatKmUtil.FormatKmStr(storeManageScopeInfoBean.getDistance()) + ")");
            isCollection = storeManageScopeInfoBean.getIsCollection();
            if (isCollection == 0) {
                mBinding.titleView.setSubRightIcon(getResources().getDrawable(R.drawable.icon_collection_nor));
            } else {
                mBinding.titleView.setSubRightIcon(getResources().getDrawable(R.drawable.icon_collection_selected));
            }
            mBinding.baBanner.setImages(storeManageScopeInfoBean.getStorePhotos())
                    .setImageLoader(new ImageLoaders() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            ImageLoader.loadImage(imageView, (String) path, config, null);
                        }
                    })
                    .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_NOTITLE_INSIDE)
                    .isAutoPlay(true)
                    .setDelayTime(PLAY_TIME)
                    .setOnBannerListener(this)
                    .start();
        }
    }

    /**
     * 收藏操作成功回调
     */
    @Override
    public void updateCollectionTypeSuc() {
        getMvpPresenter().getStoreInfo(lat, lnt, storeId);//门店信息
        setResult(Constants.RESULT_REFRESH_CODE);
    }

    //申请状态
    @Override
    public void getNexuStatus(NexuStatusBean nexuStatusBean) {
        this.nexuStatusBean = nexuStatusBean;
//        申请状态 0 未申请 1 申请中 2 成功 3 未通过
        if (isFromChat) {
            // nexus为2   显示为同意、拒绝， 咨询也不显示
            //	 nexuse为0或1  显示为解约 ， 咨询也不显示
            //
            mBinding.tvAdvisory.setVisibility(View.GONE);
            if ("3".equals(nexuStatusBean.getNexus())) {
                mBinding.tvReservation.setVisibility(View.GONE);
                mBinding.layoutAgreeorrefuse.setVisibility(View.VISIBLE);
            } else {
                mBinding.layoutAgreeorrefuse.setVisibility(View.GONE);
                mBinding.tvReservation.setVisibility(View.VISIBLE);
                mBinding.tvReservation.setText(getString(R.string.stylist_dismissal));
            }

            if (selfDefinedInfoBean!=null && !TextUtils.isEmpty(selfDefinedInfoBean.getEnterRecordID())) {
                getMvpPresenter().checkMsg(selfDefinedInfoBean.getEnterRecordID(), this);//如果是聊天进入需要请求这个接口
            }
        } else {
            setReservationStatus();
        }
    }

    @Override
    public void checkMsg(CheckMsgResult checkMsgResult) {
        if (checkMsgResult!=null){
            CheckMsgBean checkMsgBean = checkMsgResult.getData();
            if (checkMsgBean!=null) {
                switch (checkMsgBean.getStatus()){//status -1 消息不存在 0未处理，1同意 2拒绝
                    case -1://老数据无id  不做处理
                        break;
                    case 0:
                        mBinding.tvReservation.setVisibility(View.GONE);
                        mBinding.layoutAgreeorrefuse.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        mBinding.layoutAgreeorrefuse.setVisibility(View.GONE);
                        mBinding.tvReservation.setVisibility(View.VISIBLE);
                        mBinding.tvReservation.setText("已同意");
                        mBinding.tvReservation.setBackgroundColor(this.getResources().getColor(R.color.color_999999));
                        break;
                    case 2:
                        mBinding.layoutAgreeorrefuse.setVisibility(View.GONE);
                        mBinding.tvReservation.setVisibility(View.VISIBLE);
                        mBinding.tvReservation.setText("已拒绝");
                        mBinding.tvReservation.setBackgroundColor(this.getResources().getColor(R.color.color_999999));
                        break;
                }
            }
        }
    }

    private void setReservationStatus() {
        mBinding.tvReservation.setEnabled(true);
        switch (nexuStatusBean.getApplyStatus()) {
            case "0":
                mBinding.tvReservation.setText(getString(R.string.join_stylist));
                break;
//            case "1"://没了
//                mBinding.tvReservation.setText(getString(R.string.join_stylist2));
//                mBinding.tvReservation.setEnabled(false);
//                break;
            case "2":
                mBinding.tvReservation.setText(getString(R.string.stylist_dismissal));
                break;
            default:
                mBinding.tvReservation.setText(getString(R.string.join_stylist));
                break;
        }
    }

    //签约/解约成功
    @Override
    public void onSucceed() {
        //刷新数据
        getMvpPresenter().getStoreInfo(lat, lnt, storeId);//门店信息
        setResult(Constants.RESULT_REFRESH_CODE);
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        lat = String.valueOf(aMapLocation.getLatitude());
        lnt = String.valueOf(aMapLocation.getLongitude());
        getMvpPresenter().getStoreInfo(lat, lnt, storeId);//门店信息
    }

    @Override
    public void OnBannerClick(int position) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case SERVICEADDRESS: // 地址
                    getMvpPresenter().getStoreInfo(lat, lnt, storeId);//门店信息
                    break;
                case SERVICESCOPE: // 服务范围
                    getMvpPresenter().getStoreServerScope(storeId);//门店范围
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //分享弹框
    private void showShareDialog() {
        EasyDialog.init()
                .setLayoutId(R.layout.dialog_share)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseEasyDialog dialog) {
                        holder.getView(R.id.tv_share_cancel).setOnClickListener(v -> {
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_wechat).setOnClickListener(v -> {
                            ShareUtils.shareWechat(
                                    getResources().getString(R.string.dialog_share_title_apprecard),
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    storeManageScopeInfoBean.getStoreHeadImg(),
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_wechatmoments).setOnClickListener(v -> {
                            ShareUtils.shareWechatMoments(
                                    getResources().getString(R.string.dialog_share_title_apprecard),
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    storeManageScopeInfoBean.getStoreHeadImg(),
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_qq).setOnClickListener(v -> {
                            ShareUtils.shareQQ(
                                    getResources().getString(R.string.dialog_share_title_apprecard),
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    storeManageScopeInfoBean.getStoreHeadImg(),
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_friend).setOnClickListener(v -> {
                            //分享的店铺的相关信息传递 没有传null 此页面门店的storeId必传
                            if (nexuStatusBean != null) {
                                String location = "";
                                if (storeManageScopeInfoBean != null) {
                                    location = storeManageScopeInfoBean.getLocation();
                                }
                                ShareToFriendActivity.startShareToFriendActivity(StoreManagerActivity.this, 101, nexuStatusBean.getStoreIMName(), nexuStatusBean.getStoreNickname(), nexuStatusBean.getStoreId() + "",
                                        nexuStatusBean.getStoreUserId(), nexuStatusBean.getStoreHeadImg(), location);
                            }
                            dialog.dismiss();
                        });
                    }
                })
                .setPosition(Gravity.BOTTOM)
                .setMargin(0)
                .setOutCancel(true)
                .show(this.getSupportFragmentManager());
    }

    /**
     * 分享回调
     */
    PlatformActionListener platformActionListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            DLog.e("kid", "分享成功");
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            DLog.e("kid", "分享失败");
        }

        @Override
        public void onCancel(Platform platform, int i) {
            DLog.e("kid", "分享取消");
        }
    };

    //生成分享附加参数
    private String getShareParam() {
        StringBuilder param = new StringBuilder();//分享附加参数
        String eName = AccountManager.getInstance().getNickname();
        //邀请码不为空
        if (!TextUtils.isEmpty(inviteCode)) {
            param.append("?").append(Constants.WEB_CODE).append(inviteCode);
            param.append("&").append(Constants.WEB_STORE_ID).append(storeId);
            param.append("&").append(Constants.WEB_NICKNAME).append(StringUtil.baseConvertStr(eName));
//            param.append("&").append(Constants.WEB_OPUS_ID).append("");
//            param.append("&").append(Constants.WEB_STYLIST_ID).append("");
        } else {
            param.append("?").append(Constants.WEB_STORE_ID).append(storeId);
            param.append("&").append(Constants.WEB_NICKNAME).append(StringUtil.baseConvertStr(eName));
//            param.append("?").append(Constants.WEB_OPUS_ID).append("");
//            param.append("?").append(Constants.WEB_STYLIST_ID).append("");
        }

        return Constants.WEB_STORE_DETAILS + param.toString();
    }

}
