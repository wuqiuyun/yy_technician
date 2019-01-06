package com.yl.technician.module.home.card;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.KeyboardUtil;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.recycleview.GridSpacingItemDecoration;
import com.yl.technician.component.recycleview.SpaceItemDecoration;
import com.yl.technician.component.recycleview.SpaceItemHorizontalDecoration;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityMyCardBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.ReCodeBean;
import com.yl.technician.model.vo.bean.StylistCardBean;
import com.yl.technician.module.home.coupons.CouponsActivity;
import com.yl.technician.module.home.evaluation.EvaluationManagerActivity;
import com.yl.technician.module.home.in.InStoresActivity;
import com.yl.technician.module.home.service.ServiceManageActivity;
import com.yl.technician.module.home.store.StoreManagerActivity;
import com.yl.technician.module.home.works.MyWorksActivity;
import com.yl.technician.module.im.sharetofriend.ShareToFriendActivity;
import com.yl.technician.module.mine.info.UserInfoActivity;
import com.yl.technician.module.mine.works.many.ManyWorksActivity;
import com.yl.technician.util.FilePathUtil;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.util.PhoneUtil;
import com.yl.technician.util.ShareUtils;
import com.yl.technician.util.StringUtil;
import com.yl.technician.util.compressutil.CompressPicUtil;
import com.yl.technician.util.compressutil.OnCompressListener;
import com.yl.technician.widget.bottomview.BottomViewFactory;
import com.yl.technician.widget.bottomview.SelectPhotoView;
import com.yl.technician.widget.bottomview.base.BaseBottomView;
import com.yl.technician.widget.dialog.BaseEasyDialog;
import com.yl.technician.widget.dialog.EasyDialog;
import com.yl.technician.widget.dialog.ViewConvertListener;
import com.yl.technician.widget.dialog.ViewHolder;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 我的名片
 * Create by lvlong on  2018/10/30
 */
@CreatePresenter(MyCardPresenter.class)
public class MyCardActivity extends BaseMvpAppCompatActivity<MyCardView, MyCardPresenter> implements ClickHandler, MyCardView {

    private ImageLoaderConfig config;
    private ActivityMyCardBinding mBinding;

    private CouponAdapter mCouponAdapter;//优惠券
    private CommentAdapter mCommentAdapter;//顾客评价
    private WorksAdapter mWorksAdapter;//作品列表
    private ServiceBundleAdapter mServiceBundleAdapter;//套餐项目
    private ServiceProjectAdapter mServiceProjectAdapter;//服务项目
    private StoreAdapter mStoreAdapter;//加入的门店
    private BaseBottomView mSelectPhotoView;

    private String stylistId;
    private boolean isMe; //是否是自己
    private boolean isCollection; //是否收藏了该美发师
    private String stylistImg;//美发师头像

    private String headPath = FilePathUtil.getCacheCrop() + System.currentTimeMillis() + ".jpg"; // 背景图片保存地址
    private String bgPath;

    private String yearbirth;//美发师《"00后/白羊座/吃鸡肉"

    private String inviteCode = null;//邀请码

    private StylistCardBean mData;//美发师详情bean

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_card;
    }

    @Override
    protected void init() {
        mBinding = (ActivityMyCardBinding) viewDataBinding;
        mBinding.setClick(this);

        Bundle bundle = getIntent().getExtras();
        if (null == bundle) {//说明是从主页进来
            stylistId = AccountManager.getInstance().getStylistId();
            isMe = true;
        } else {//说明是从其他入口进来
            stylistId = bundle.getString(Constants.STYLIST_ID);
            if (stylistId.equals(AccountManager.getInstance().getStylistId())) {
                isMe = true;
            } else {
                isMe = false;
            }
        }

        initView();
        initPersonal(isMe);
//        getMvpPresenter().getStylistCard(stylistId);
        getMvpPresenter().findReCode();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMvpPresenter().getStylistCard(stylistId);
    }

    private void initPersonal(boolean isShow) {
        //是本人
        if (isShow) {
            //显示编辑区域
            mBinding.tvMyInfoEdit.setVisibility(View.VISIBLE);
            mBinding.tvIntroEdit.setVisibility(View.VISIBLE);
            mBinding.tvCouponsEdit.setVisibility(View.VISIBLE);
            mBinding.tvServiceEdit.setVisibility(View.VISIBLE);
            mBinding.tvPackageEdit.setVisibility(View.VISIBLE);
            mBinding.tvLookComment.setVisibility(View.VISIBLE);
            mBinding.tvOpusEdit.setVisibility(View.VISIBLE);
            mBinding.tvStoreEdit.setVisibility(View.VISIBLE);

            //设置与自己相关的内容
            mBinding.tvPackageTitle.setText("我的套餐");
            mBinding.tvOpusTitle.setText("我的作品");
        }
        //不是本人
        else {
            //隐藏编辑区域
            mBinding.tvMyInfoEdit.setVisibility(View.GONE);
            mBinding.tvIntroEdit.setVisibility(View.GONE);
            mBinding.tvCouponsEdit.setVisibility(View.GONE);
            mBinding.tvServiceEdit.setVisibility(View.GONE);
            mBinding.tvPackageEdit.setVisibility(View.GONE);
            mBinding.tvLookComment.setVisibility(View.GONE);
            mBinding.tvOpusEdit.setVisibility(View.GONE);
            mBinding.tvStoreEdit.setVisibility(View.GONE);

            //显示与他人相关的内容
            mBinding.tvPackageTitle.setText("TA的套餐");
            mBinding.tvOpusTitle.setText("TA的作品");
        }
    }

    private void initView() {

        mBinding.rlWorks.setVisibility(View.GONE);

        //收藏功能相关
        mBinding.ivCollection.setOnClickListener(view -> {
            int type = isCollection ? 0 : 1;
            getMvpPresenter().stylistCollection(stylistId, type);
        });

        mBinding.ratingStylist.setOnTouchListener((v, event) -> true);
        mBinding.ratingBar.setOnTouchListener((v, event) -> true);

        //优惠券
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 横向滚动
        mBinding.rvCouponsList.setLayoutManager(linearLayoutManager);
        mBinding.rvCouponsList.addItemDecoration(new SpaceItemHorizontalDecoration(20));
        mCouponAdapter = new CouponAdapter(getBaseContext());
        mBinding.rvCouponsList.setAdapter(mCouponAdapter);


        //服务项目
        mBinding.projectList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mBinding.projectList.addItemDecoration(new SpaceItemDecoration(20));
        mServiceProjectAdapter = new ServiceProjectAdapter(getBaseContext());
        mBinding.projectList.setHasFixedSize(true);
        mBinding.projectList.setNestedScrollingEnabled(false);
        mBinding.projectList.setAdapter(mServiceProjectAdapter);

        //套餐
        mBinding.listService.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mBinding.listService.addItemDecoration(new SpaceItemDecoration(30));
        mServiceBundleAdapter = new ServiceBundleAdapter(getBaseContext());
        mBinding.listService.setHasFixedSize(true);
        mBinding.listService.setNestedScrollingEnabled(false);
        mBinding.listService.setAdapter(mServiceBundleAdapter);

        //门店列表
        mBinding.storeList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mBinding.storeList.addItemDecoration(new SpaceItemDecoration(20));
        mBinding.storeList.setHasFixedSize(true);
        mBinding.storeList.setNestedScrollingEnabled(false);
        mStoreAdapter = new StoreAdapter(getBaseContext());
        mBinding.storeList.setAdapter(mStoreAdapter);

        //评分条目
        mBinding.commentList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mBinding.commentList.addItemDecoration(new SpaceItemDecoration(20));
        mBinding.commentList.setHasFixedSize(true);
        mBinding.commentList.setNestedScrollingEnabled(false);
        mCommentAdapter = new CommentAdapter(getBaseContext());
        mBinding.commentList.setAdapter(mCommentAdapter);


        //我的作品
        mBinding.worksList.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
        mBinding.worksList.addItemDecoration(new GridSpacingItemDecoration(3, 20, false));
        mBinding.worksList.setHasFixedSize(true);
        mBinding.worksList.setNestedScrollingEnabled(false);
        mWorksAdapter = new WorksAdapter(getBaseContext());
        mBinding.worksList.setAdapter(mWorksAdapter);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.iv_bg:    //设置背景

                if (isMe == true) {
                    KeyboardUtil.closeSoftKeyboard(this);
                    if (mSelectPhotoView == null) {
                        mSelectPhotoView = BottomViewFactory.create(this, BottomViewFactory.Type.Avatar);
                    }
                    mSelectPhotoView.showBottomView(true);
                }

                break;

            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                showShareDialog();
                break;
            //编辑个人信息
            case R.id.tv_my_info_edit:
                UserInfoActivity.startActivity(this, UserInfoActivity.class);
                break;
            //编辑个人介绍
            case R.id.tv_intro_edit:
                Bundle b = new Bundle();
                UserInfoActivity.startActivity(this, UserInfoActivity.class, b);
                break;
            //编辑优惠券
            case R.id.tv_coupons_edit:
                CouponsActivity.startActivity(this, CouponsActivity.class);
                break;
            //编辑服务项目
            case R.id.tv_service_edit:
                ServiceManageActivity.startActivity(this, ServiceManageActivity.class);
                break;
            //编辑套餐
            case R.id.tv_package_edit:
                ServiceManageActivity.startActivity(this, ServiceManageActivity.class);
                break;
            //编辑门店
            case R.id.tv_store_edit:
                StoreManagerActivity.startActivity(this, InStoresActivity.class);
                break;
            //编辑作品
            case R.id.tv_opus_edit:
                MyWorksActivity.startActivity(this, MyWorksActivity.class);
                break;
            //评价管理
            case R.id.tv_look_comment:
                Bundle EvaluationBundle = new Bundle();
                EvaluationBundle.putInt(Constants.EVALUATION_TYPE, 2);
                EvaluationBundle.putString(Constants.STYLIST_ID, stylistId);
                EvaluationManagerActivity.startActivity(this, EvaluationManagerActivity.class ,EvaluationBundle);
                break;
            //查看作品集
            case R.id.iv_look_more:
                if (mData!=null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.STYLIST_ID, stylistId);
                    bundle.putString(Constants.HEAD_PORTRAIT, mData.getHeadPortrait());
                    bundle.putString(Constants.NICK_NAME, mData.getNickname());
                    ManyWorksActivity.startActivity(this, ManyWorksActivity.class, bundle);
                }
                break;
        }

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void getStylistCardSuc(StylistCardBean data) {
        mData = data;
        //设置基本数据
        yearbirth = data.getYearbirth();
        mBinding.tvName.setText(data.getNickname());
        mBinding.tvName.setCompoundDrawables(null, null, getSexDrawable(data.getGender()), null);
        mBinding.tvServiceType.setText("服务类别:" + data.getServerTypes());
        mBinding.ratingStylist.setRating(data.getStar());
        mBinding.ratingNum.setText(data.getPoint() + "");
        mBinding.tvIntro.setText(data.getDescription());
        mBinding.ratingBar.setRating(data.getStar());
        mBinding.tvLabel.setText(FormatUtil.Formatstring(data.getYearbirth() + "/" +data.getPosition()));
        mBinding.tvLookComment.setText(getString(R.string.comment_look_alll) + "(" + data.getEvaluatenumer() + ")");
        mBinding.tvLabel.setText(FormatUtil.Formatstring(data.getYearbirth() + "/" + data.getPosition()));
        stylistImg = data.getHeadPortrait();
        //设置收藏功能
        if (isMe) {
            //隐藏收藏按钮
            mBinding.ivCollection.setVisibility(View.GONE);
        } else {
            //显示收藏按钮
            mBinding.ivCollection.setVisibility(View.VISIBLE);
            isCollection = data.isCollection();
            if (isCollection) mBinding.ivCollection.setImageResource(R.drawable.icon_collection);
            else mBinding.ivCollection.setImageResource(R.drawable.icon_collection_false);
        }

        //头像
        if (config == null) {
            ImageLoaderConfig configBg = new ImageLoaderConfig.Builder()
                    .setErrorResId(R.drawable.icon_head_pic_def)
                    .setPlaceHolderResId(R.drawable.icon_head_pic_def)
                    .setAsBitmap(true)
                    .setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE)
                    .setPrioriy(ImageLoaderConfig.LoadPriority.HIGH)
                    .build();
        }
        ImageLoader.loadImage(mBinding.ivPhoto, stylistImg, config, null);

        //背景
        ImageLoaderConfig configBg = new ImageLoaderConfig.Builder()
                .setErrorResId(R.drawable.icon_head_pic_def)
                .setPlaceHolderResId(R.drawable.icon_head_pic_def)
                .setAsBitmap(true)
                .setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE)
                .setPrioriy(ImageLoaderConfig.LoadPriority.HIGH)
                .build();
        ImageLoader.loadImage(mBinding.ivBg, data.getBackGroundImg(), configBg, null);

        //设置adapter数据
        mCouponAdapter.setDatas(data.getCardCouponDTOs(), true);//优惠券
        mServiceProjectAdapter.setDatas(data.getCardServerItems(), true);//服务项目
        mServiceBundleAdapter.setDatas(data.getCardPackages(), true);//套餐
        mStoreAdapter.setDatas(data.getCardStoreDTOs(), true);//加入的门店

        if (null == data.getCardOpusDTOs() || data.getCardOpusDTOs().size() == 0) {//作品列表
            mBinding.rlWorks.setVisibility(View.GONE);
        } else {
            mBinding.rlWorks.setVisibility(View.VISIBLE);
            mWorksAdapter.setDatas(data.getCardOpusDTOs(), true);
        }
        mCommentAdapter.setDatas(data.getCardGradeDTOs(), true);//顾客评价
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(MyCardActivity.this, 0, null);
    }

    private Drawable getSexDrawable(int gender) {
        Drawable drawable;
        if (gender == 1) drawable = getResources().getDrawable(R.drawable.icon_boy);
        else drawable = getResources().getDrawable(R.drawable.icon_girl);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    @Override
    public void getStylistCardFail() {
        showToast("获取美发师信息失败");
        finish();
    }

    @Override
    public void stylistCollectionSuc() {
        if (isCollection) {
            mBinding.ivCollection.setImageResource(R.drawable.icon_collection_false);
            isCollection = false;
            showToast("已取消收藏");
        } else {
            mBinding.ivCollection.setImageResource(R.drawable.icon_collection);
            isCollection = true;
            showToast("收藏成功");
        }
        setResult(Constants.RESULT_REFRESH_CODE);
    }

    @Override
    public void stylistCollectionFail() {
        if (isCollection) {
            showToast("取消收藏失败");
        } else {
            showToast("收藏失败");
        }
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
                                    getResources().getString(R.string.dialog_share_title_appself),
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    stylistImg,
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_wechatmoments).setOnClickListener(v -> {
                            ShareUtils.shareWechatMoments(
                                    getResources().getString(R.string.dialog_share_title_appself),
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    stylistImg,
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_qq).setOnClickListener(v -> {
                            ShareUtils.shareQQ(
                                    getResources().getString(R.string.dialog_share_title_appself),
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    stylistImg,
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_friend).setOnClickListener(v -> {
                            //分享的店铺的相关信息传递 没有传null 此页面门店的storeId必传
                            ShareToFriendActivity.startShareToFriendActivity(
                                    MyCardActivity.this,
                                    103,
                                    AccountManager.getInstance().getAccount().getImusername(),
                                    AccountManager.getInstance().getNickname(),
                                    AccountManager.getInstance().getStylistId(),
                                    AccountManager.getInstance().getUserId(),
                                    AccountManager.getInstance().getAccount().getHeadImg(),
                                    yearbirth);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhoneUtil.REQUESTCODE_SYS_CAMERA: // 相机
                    String imagePath = ((SelectPhotoView) mSelectPhotoView).getImagePath();
                    Uri uri = null;
                    if (mSelectPhotoView instanceof SelectPhotoView) {
                        uri = ((SelectPhotoView) mSelectPhotoView).getUri();
                    }
                    if (uri == null) {
                        return;
                    }
                    compressPicAndUpload(imagePath);
                    break;

                case PhoneUtil.REQUESTCODE_SYS_PICK_IMAGE: // 图库

                    compressPicAndUpload(FilePathUtil.getPath(data.getData()));
                    break;

                case PhoneUtil.REQUESTCODE_SYS_CROP: // 裁剪
                    if (TextUtils.isEmpty(headPath)) return;

                    getMvpPresenter().uploadImage(headPath);
                    break;

            }
        }
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
                        getMvpPresenter().uploadImage(file.getPath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 当压缩过程出现问题时调用 上传原图
                        getMvpPresenter().uploadImage(filePath);
                    }
                }).launch();
    }

    @Override
    public void onUploadFileSuccess(String filePath) {
        bgPath = filePath;
        getMvpPresenter().saveBackGround(filePath);
    }

    @Override
    public void onSaveBackGround() {
        ToastUtils.shortToast("修改成功");
        ImageLoader.loadImage(mBinding.ivBg, bgPath);
    }

    @Override
    public void findReCodeSuc(ReCodeBean reCode) {
        inviteCode = reCode.getInvitecode();
    }

    //生成分享附加参数
    private String getShareParam() {
        StringBuilder param = new StringBuilder();//分享附加参数
        String eName = AccountManager.getInstance().getNickname();
        //邀请码不为空
        if (!TextUtils.isEmpty(inviteCode)) {
            param.append("?").append(Constants.WEB_CODE).append(inviteCode);
            param.append("&").append(Constants.WEB_STYLIST_ID).append(stylistId);
            param.append("&").append(Constants.WEB_NICKNAME).append(StringUtil.baseConvertStr(eName));
//            param.append("&").append(Constants.WEB_OPUS_ID).append("");
//            param.append("&").append(Constants.WEB_STORE_ID).append("");
        } else {
            param.append("?").append(Constants.WEB_STYLIST_ID).append(stylistId);
            param.append("&").append(Constants.WEB_NICKNAME).append(StringUtil.baseConvertStr(eName));
//            param.append("?").append(Constants.WEB_OPUS_ID).append("");
//            param.append("?").append(Constants.WEB_STORE_ID).append("");
        }

        return Constants.WEB_HAIRDRESSER_DETAILS + param.toString();
    }

}
